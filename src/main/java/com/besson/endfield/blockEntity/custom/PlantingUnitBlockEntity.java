package com.besson.endfield.blockEntity.custom;

import com.besson.endfield.block.ElectrifiableDevice;
import com.besson.endfield.blockEntity.ModBlockEntities;
import com.besson.endfield.recipe.ModRecipes;
import com.besson.endfield.recipe.custom.PlantingUnitRecipe;
import com.besson.endfield.screen.custom.PlantingUnitScreenHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.Optional;

public class PlantingUnitBlockEntity extends BlockEntity implements GeoBlockEntity, MenuProvider, ElectrifiableDevice {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public static final int INPUT_SLOT = 0;
    public static final int OUTPUT_SLOT = 1;

    protected final ContainerData propertyDelegate;
    private int progress = 0;
    private int maxProgress = 40;

    private int storedPower = 0;
    private static final int POWER_PRE_TICK = 10;
    private boolean isWorking = false;

    private final ItemStackHandler itemStackHandler = new ItemStackHandler(2) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }
    };
    private IItemHandler input = new InputItemHandler(itemStackHandler);
    private IItemHandler output = new OutputItemHandler(itemStackHandler);

    public PlantingUnitBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.PLANTING_UNIT.get(), pos, state);
        this.propertyDelegate = new ContainerData() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> PlantingUnitBlockEntity.this.progress;
                    case 1 -> PlantingUnitBlockEntity.this.maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0 -> PlantingUnitBlockEntity.this.progress = value;
                    case 1 -> PlantingUnitBlockEntity.this.maxProgress = value;
                }
            }

            @Override
            public int getCount() {
                return 2;
            }
        };
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {

    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    @Override
    public void receiveElectricCharge(int amount) {
        this.storedPower += amount;
        if (this.storedPower > 100) {
            this.storedPower = 100;
        }
    }

    @Override
    public boolean needsPower() {
        return this.storedPower < POWER_PRE_TICK;
    }

    @Override
    public int getRequiredPower() {
        return POWER_PRE_TICK;
    }

    public NonNullList<ItemStack> getItems() {
        NonNullList<ItemStack> items = NonNullList.withSize(itemStackHandler.getSlots(), ItemStack.EMPTY);
        for (int i = 0; i < itemStackHandler.getSlots(); i++) {
            items.set(i, itemStackHandler.getStackInSlot(i));
        }
        return items;
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("blockEntity.planting_unit");
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new PlantingUnitScreenHandler(pContainerId, pPlayerInventory, this, this.propertyDelegate);
    }

    @Override
    public @Nullable Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        return saveWithFullMetadata(registries);
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.put("inventory", itemStackHandler.serializeNBT(registries));
        tag.putInt("progress", this.progress);
        tag.putBoolean("isWorking", this.isWorking);
        tag.putInt("storedPower", this.storedPower);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        itemStackHandler.deserializeNBT(registries, tag.getCompound("inventory"));
        this.progress = tag.getInt("progress");
        this.isWorking = tag.getBoolean("isWorking");
        this.storedPower = tag.getInt("storedPower");
    }

    public static void tick(Level world, BlockPos pos, BlockState state, PlantingUnitBlockEntity be) {
        if (world.isClientSide()) return;

        if (be.isOutputSlotAvailable()) {
            boolean hasRecipe = be.hasCorrectRecipe(world);

            if (be.needsPower() || !hasRecipe) {
                be.isWorking = false;
            } else if (!be.needsPower() && !be.isWorking) {
                be.isWorking = true;
            }
            be.setChanged();
            world.sendBlockUpdated(pos, state, state, 3);

            if (hasRecipe && be.storedPower >= POWER_PRE_TICK) {
                be.storedPower -= POWER_PRE_TICK;
                be.increaseProgress();

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

    private void resetProgress() {
        this.progress = 0;
    }

    private void craftItem(Level world) {

        Optional<RecipeHolder<PlantingUnitRecipe>> match = getMatchRecipe(world);

        if (match.isPresent()) {
            ItemStack result = match.get().value().getResultItem(world.registryAccess());
            this.itemStackHandler.setStackInSlot(OUTPUT_SLOT,
                    new ItemStack(result.getItem(), itemStackHandler.getStackInSlot(OUTPUT_SLOT).getCount() + result.getCount()));
            this.itemStackHandler.extractItem(INPUT_SLOT, 1, false);
        }

    }

    private Optional<RecipeHolder<PlantingUnitRecipe>> getMatchRecipe(Level world) {
        SimpleContainer inv = new SimpleContainer(2);
        for (int i = 0; i < 2; i++) {
            inv.setItem(i, this.itemStackHandler.getStackInSlot(i));
        }
        SingleRecipeInput input = new SingleRecipeInput(inv.getItem(0));
        return world.getRecipeManager()
                .getRecipeFor(ModRecipes.PLANTING_UNIT_TYPE.get(), input, world);
    }

    private boolean hasCraftingFinished() {
        return progress >= maxProgress;
    }

    private void increaseProgress() {
        this.progress++;
    }

    private boolean hasCorrectRecipe(Level world) {
        Optional<RecipeHolder<PlantingUnitRecipe>> match = getMatchRecipe(world);

        if (match.isPresent()) {
            ItemStack result = match.get().value().getResultItem(world.registryAccess());
            return canInsertItem(result);
        }

        return false;
    }

    private boolean canInsertItem(ItemStack item) {
        ItemStack outputStack = itemStackHandler.getStackInSlot(OUTPUT_SLOT);
        return outputStack.isEmpty() || (outputStack.getItem() == item.getItem()
                && outputStack.getCount() + item.getCount() <= outputStack.getMaxStackSize());
    }

    private boolean isOutputSlotAvailable() {
        ItemStack outputStack = itemStackHandler.getStackInSlot(OUTPUT_SLOT);
        return outputStack.isEmpty() || outputStack.getCount() < outputStack.getMaxStackSize();
    }

    public @Nullable IItemHandler getItemStackHandler() {
        return itemStackHandler;
    }

    public IItemHandler getInputHandler() {
        return input;
    }

    public IItemHandler getOutputHandler() {
        return output;
    }

    private record InputItemHandler(ItemStackHandler parent) implements IItemHandler {

        @Override
            public int getSlots() {
                return 1;
            }

            @Override
            public ItemStack getStackInSlot(int slot) {
                return parent.getStackInSlot(PlantingUnitBlockEntity.INPUT_SLOT);
            }

            @Override
            public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
                ItemStack current = parent.getStackInSlot(PlantingUnitBlockEntity.INPUT_SLOT);
                if (current.isEmpty() || ItemStack.isSameItem(current, stack)) {
                    return parent.insertItem(PlantingUnitBlockEntity.INPUT_SLOT, stack, simulate);
                }
                return stack;
            }

            @Override
            public ItemStack extractItem(int slot, int amount, boolean simulate) {
                return ItemStack.EMPTY;
            }

            @Override
            public int getSlotLimit(int slot) {
                return 64;
            }

            @Override
            public boolean isItemValid(int slot, ItemStack stack) {
                return true;
            }
        }

    private record OutputItemHandler(ItemStackHandler parent) implements IItemHandler {

        @Override
            public int getSlots() {
                return 1;
            }

            @Override
            public ItemStack getStackInSlot(int slot) {
                return parent.getStackInSlot(PlantingUnitBlockEntity.OUTPUT_SLOT);
            }

            @Override
            public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
                return stack;
            }

            @Override
            public ItemStack extractItem(int slot, int amount, boolean simulate) {
                return parent.extractItem(PlantingUnitBlockEntity.OUTPUT_SLOT, amount, simulate);
            }

            @Override
            public int getSlotLimit(int slot) {
                return 64;
            }

            @Override
            public boolean isItemValid(int slot, ItemStack stack) {
                return false;
            }
        }
}
