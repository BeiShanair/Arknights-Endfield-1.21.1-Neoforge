package com.besson.endfield.blockEntity.custom;

import com.besson.endfield.util.PowerNetworkManager;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
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
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public abstract class BaseRigBlockEntity<R extends Recipe<?>> extends BlockEntity implements MenuProvider {
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

    protected final ItemStackHandler itemStackHandler = new ItemStackHandler(1) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }
    };
    
    public BaseRigBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state, int maxProgress) {
        super(type, pos, state);
        this.maxProgress = maxProgress;
        this.propertyDelegate = createPropertyDelegate();
    }
    protected abstract ContainerData createPropertyDelegate();
    protected abstract int getPowerCostPerTick();
    protected abstract Optional<RecipeHolder<R>> getMatchRecipe(Level world);
    protected abstract void craftItem(Level world);
    protected abstract boolean hasCorrectRecipe(Level world);
    
    public static <T extends BaseRigBlockEntity<?>> void tick(Level world, BlockPos pos, BlockState state, T be) {
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
        be.tryPushToBelt(world, pos, state);
        be.setChanged();
    }

    protected void tryPushToBelt(Level world, BlockPos pos, BlockState state) {
        BlockEntity machineBe = world.getBlockEntity(pos);
        if (machineBe == null) return;

        IItemHandler handler = world.getCapability(Capabilities.ItemHandler.BLOCK, pos, null);

        if (handler == null) return;

        for (Direction outputDir : Direction.values()) {
            BlockPos beltPos = pos.relative(outputDir);
            BlockEntity targetBe = world.getBlockEntity(beltPos);

            if (!(targetBe instanceof BeltBlockEntity belt)) continue;

            if (!belt.canAcceptFrom(outputDir.getOpposite())) continue;

            for (int i = 0; i < handler.getSlots(); i++) {

                ItemStack simulated = handler.extractItem(i, 1, true);

                if (!simulated.isEmpty()) {

                    ItemStack extracted = handler.extractItem(i, 1, false);

                    if (!extracted.isEmpty()) {
                        belt.acceptItem(extracted, outputDir.getOpposite());
                        return;
                    }
                }
            }
        }
    }
    
    public ItemStackHandler getItemStackHandler() {
        return itemStackHandler;
    }

    public boolean isWorking() {
        return isWorking;
    }
    
    protected boolean hasCraftingFinished() {
        return progress >= maxProgress;
    }
    
    public NonNullList<ItemStack> getItems() {
        NonNullList<ItemStack> items = NonNullList.withSize(1, ItemStack.EMPTY);
        items.set(0, this.itemStackHandler.getStackInSlot(0));
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

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.put("inventory", itemStackHandler.serializeNBT(registries));
        tag.putInt("progress", progress);
        tag.putBoolean("isWorking", isWorking);
        tag.putInt("storePower", storedPower);
        tag.putBoolean("enable", enable);
    }

    @Override
    public void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        itemStackHandler.deserializeNBT(registries, tag.getCompound("inventory"));
        this.progress = tag.getInt("progress");
        this.isWorking = tag.getBoolean("isWorking");
        this.storedPower = tag.getInt("storePower");
        this.enable = tag.getBoolean("enable");
    }

    @Override
    public @Nullable Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        return saveWithFullMetadata(registries);
    }

    protected void resetProgress() {
        this.progress = 0;
    }

    protected void incrementProgress() {
        this.progress++;
    }

    protected boolean canOutputAccept(ItemStack result) {
        ItemStack out = itemStackHandler.getStackInSlot(0);
        return (out.isEmpty() || out.getItem() == result.getItem())
                && out.getCount() + result.getCount() <= 64;
    }

    protected boolean isOutputSlotAvailable() {
        ItemStack outputStack = itemStackHandler.getStackInSlot(0);
        return outputStack.isEmpty() || outputStack.getCount() < 64;
    }
}
