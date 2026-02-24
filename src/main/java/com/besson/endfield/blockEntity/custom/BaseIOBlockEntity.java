package com.besson.endfield.blockEntity.custom;

import com.besson.endfield.util.PowerNetworkManager;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public abstract class BaseIOBlockEntity<R extends Recipe<?>> extends BlockEntity implements MenuProvider {
    protected int tickNum = 0;
    protected boolean isPowered = false;
    protected boolean registeredToManager = false;
    protected int storedPower;
    protected static final int MAX_STORED_POWER = 10000;
    protected boolean isWorking;
    protected boolean enable = true;
    protected int progress = 0;
    protected int maxProgress;

    protected final ContainerData propertyDelegate;
    protected boolean needsInit = true;

    protected final ItemStackHandler itemStackHandler = new ItemStackHandler(getInvSize()) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }
    };
    protected IItemHandler input = getInput();
    protected IItemHandler output = getOutput();

    public BaseIOBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state, int maxProgress) {
        super(type, pos, state);

        this.maxProgress = maxProgress;
        this.propertyDelegate = createPropertyDelegate();

    }
    
    protected abstract int getInvSize();
    protected abstract ContainerData createPropertyDelegate();
    protected abstract int getPowerCostPerTick();
    protected abstract Optional<RecipeHolder<R>> getMatchRecipe(Level world);
    protected abstract void craftItem(Level world);
    protected abstract boolean hasCorrectRecipe(Level world);
    protected abstract IItemHandler getInput();
    protected abstract IItemHandler getOutput();
    
    public static <T extends BaseIOBlockEntity<?>> void tick(Level world, BlockPos pos, BlockState state, T be) {
        if (world.isClientSide()) return;
        
        if (be.needsInit && world instanceof ServerLevel serverWorld) {
            be.needsInit = false;

            PowerNetworkManager.get(serverWorld).registerConsumer(be.getBlockPos(), be::getRequiredPower, be::receiveElectricCharge);
            be.registeredToManager = true;
        }

        if (!be.getEnable()) {
            be.isWorking = false;
            world.sendBlockUpdated(pos, state, state, 3);
            be.setChanged();
            return;
        }

        be.tickNum++;

        if (be.tickNum % 20 == 0) {
            // TODO: 使用全局电网节点管理器来获取最近的 供电桩 / 中继器 ，不再使用遍历
            for (BlockPos target : BlockPos.betweenClosed(pos.offset(-10, 0, -10), pos.offset(10, 0, 10))) {
                BlockEntity b = world.getBlockEntity(target);
                if (b instanceof ElectricPylonBlockEntity) {
                    be.isPowered = ((ElectricPylonBlockEntity) b).isPowered;
                    break;
                }
                be.isPowered = false;
                be.isWorking = false;
                be.setChanged();
                world.sendBlockUpdated(pos, state, state, 3);
            }
            be.tickNum = 0;
        }

        if (!be.isPowered) return;

        if (be.isOutputSlotAvailable()) {
            boolean hasRecipe = be.hasCorrectRecipe(world);
            if (be.needsPower() || !hasRecipe) {
                be.isWorking = false;
            } else if (!be.needsPower() && !be.isWorking) {
                be.isWorking = true;
            }
            be.setChanged();
            world.sendBlockUpdated(pos, state, state, 3);

            if (hasRecipe && be.storedPower >= be.getPowerCostPerTick()) {
                be.incrementProgress();
                be.storedPower -= be.getPowerCostPerTick();
                if (be.hasCraftingFinished()) {
                    be.craftItem(world);
                    be.resetProgress();
                }
            } else {
                be.resetProgress();
            }
        } else {
            be.resetProgress();
        }
        be.setChanged();
    }

    public IItemHandler getInputHandler() {
        return input;
    }

    public IItemHandler getOutputHandler() {
        return output;
    }
    
    protected boolean hasCraftingFinished() {
        return progress >= maxProgress;
    }

    public NonNullList<ItemStack> getItems() {
        NonNullList<ItemStack> items = NonNullList.withSize(itemStackHandler.getSlots(), ItemStack.EMPTY);
        for (int i = 0; i < itemStackHandler.getSlots(); i++) {
            items.set(i, itemStackHandler.getStackInSlot(i));
        }
        return items;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
        setChanged();
        if (level != null) {
            BlockPos pos = getBlockPos();
            level.sendBlockUpdated(pos, level.getBlockState(pos), level.getBlockState(pos), 3);
        }
    }

    public boolean getEnable() {
        return this.enable;
    }

    @Override
    public void setLevel(Level pLevel) {
        super.setLevel(pLevel);
        if (pLevel instanceof ServerLevel) {
            needsInit = true;
        }
    }

    @Override
    public void setRemoved() {
        if (level instanceof ServerLevel serverLevel) {
            PowerNetworkManager.get(serverLevel).unregisterConsumer(this.getBlockPos());
        }
        super.setRemoved();
    }
    
    public void receiveElectricCharge(int amount) {
        this.storedPower = Math.min(this.storedPower + amount, MAX_STORED_POWER);
    }

    public boolean needsPower() {
        return this.storedPower < getPowerCostPerTick();
    }

    public int getRequiredPower() {
        if (isWorking && isPowered || storedPower < MAX_STORED_POWER) {
            return getPowerCostPerTick();
        }
        return 0;
    }

    // region 数据持久化 & 同步
    @Override
    public void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        itemStackHandler.deserializeNBT(registries, tag.getCompound("inventory"));
        this.progress = tag.getInt("progress");
        this.storedPower = tag.getInt("storedPower");
        this.isWorking = tag.getBoolean("isWorking");
        this.enable = tag.getBoolean("enable");
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.put("inventory", itemStackHandler.serializeNBT(registries));
        tag.putInt("progress", this.progress);
        tag.putInt("storedPower", this.storedPower);
        tag.putBoolean("isWorking", this.isWorking);
        tag.putBoolean("enable", this.enable);
    }
    
    @Override
    public @Nullable Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        return saveWithFullMetadata(registries);
    }
    // endregion

    protected void resetProgress() {
        this.progress = 0;
    }

    protected void incrementProgress() {
        this.progress++;
    }

    protected boolean canOutputAccept(ItemStack result) {
        ItemStack out = itemStackHandler.getStackInSlot(getOutputSlotIndex());
        return (out.isEmpty() || out.getItem() == result.getItem())
                && out.getCount() + result.getCount() <= 64;
    }

    protected boolean isOutputSlotAvailable() {
        ItemStack outputStack = itemStackHandler.getStackInSlot(getOutputSlotIndex());
        return outputStack.isEmpty() || outputStack.getCount() < 64;
    }

    public ItemStackHandler getItemStackHandler() {
        return itemStackHandler;
    }

    protected abstract int getOutputSlotIndex();
}
