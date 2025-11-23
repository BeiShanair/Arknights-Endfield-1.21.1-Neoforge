package com.besson.endfield.blockEntity.custom;

import com.besson.endfield.block.ElectrifiableDevice;
import com.besson.endfield.blockEntity.ModBlockEntities;
import com.besson.endfield.recipe.InputEntry;
import com.besson.endfield.recipe.ModRecipes;
import com.besson.endfield.recipe.custom.DoubleRecipeInput;
import com.besson.endfield.recipe.custom.GearingUnitRecipe;
import com.besson.endfield.screen.custom.GearingUnitScreenHandler;
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
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.Optional;

public class GearingUnitBlockEntity extends BlockEntity implements GeoBlockEntity, MenuProvider, ElectrifiableDevice {

    public static final int INPUT_SLOT1 = 0;
    public static final int INPUT_SLOT2 = 1;
    public static final int OUTPUT_SLOT = 2;

    protected final ContainerData propertyDelegate;
    private int progress = 0;
    private int maxProgress = 200;

    private int storePower = 0;
    private static final int POWER_PRE_TICK = 10;
    private boolean isWorking = false;

    private final ItemStackHandler itemStackHandler = new ItemStackHandler(3) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }
    };

    private IItemHandler input = new InputItemHandler(itemStackHandler);
    private IItemHandler output = new OutputItemHandler(itemStackHandler);

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public GearingUnitBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.GEARING_UNIT.get(), pos, state);
        this.propertyDelegate = new ContainerData() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> GearingUnitBlockEntity.this.progress;
                    case 1 -> GearingUnitBlockEntity.this.maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0 -> GearingUnitBlockEntity.this.progress = value;
                    case 1 -> GearingUnitBlockEntity.this.maxProgress = value;
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

    public NonNullList<ItemStack> getItems() {
        NonNullList<ItemStack> items = NonNullList.withSize(this.itemStackHandler.getSlots(), ItemStack.EMPTY);
        for (int i = 0; i < this.itemStackHandler.getSlots(); i++) {
            items.set(i, this.itemStackHandler.getStackInSlot(i));
        }
        return items;
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("blockEntity.gearing_unit");
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new GearingUnitScreenHandler(pContainerId, pPlayerInventory, this, this.propertyDelegate);
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
        tag.putInt("storePower", this.storePower);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        itemStackHandler.deserializeNBT(registries, tag.getCompound("inventory"));
        this.progress = tag.getInt("progress");
        this.isWorking = tag.getBoolean("isWorking");
        this.storePower = tag.getInt("storePower");
    }

    public static void tick(Level world, BlockPos pos, BlockState state, GearingUnitBlockEntity entity) {
        if (world.isClientSide()) return;

        if (entity.isOutputSlotAvailable()) {
            boolean hasRecipe = entity.hasCorrectRecipe(world);
            if (entity.needsPower() || !hasRecipe) {
                entity.isWorking = false;
            } else if (!entity.needsPower() && !entity.isWorking) {
                entity.isWorking = true;
            }
            entity.setChanged();
            world.sendBlockUpdated(pos, state, state, 3);

            if (hasRecipe && entity.storePower >= POWER_PRE_TICK) {
                entity.storePower -= POWER_PRE_TICK;
                entity.incrementProgress();
                entity.setChanged();

                if (entity.hasCraftingFinished()) {
                    entity.craftItem(world);
                    entity.resetProgress();
                    entity.setChanged();
                }
            } else {
                entity.resetProgress();
                entity.setChanged();
            }
        } else {
            entity.resetProgress();
            entity.setChanged();
        }
    }

    private void resetProgress() {
        this.progress = 0;
    }

    private Optional<RecipeHolder<GearingUnitRecipe>> getMatchRecipe(Level world) {
        SimpleContainer inv = new SimpleContainer(3);
        for (int i = 0; i < 3; i++) {
            inv.setItem(i, this.itemStackHandler.getStackInSlot(i));
        }

        DoubleRecipeInput input = new DoubleRecipeInput(inv);
        return world.getRecipeManager()
                .getRecipeFor(ModRecipes.GEARING_UNIT_TYPE.get(), input, world);
    }

    private void craftItem(Level world) {
        Optional<RecipeHolder<GearingUnitRecipe>> match = getMatchRecipe(world);
        if (match.isPresent()) {
            ItemStack result = match.get().value().getResultItem(world.registryAccess());
            this.itemStackHandler.setStackInSlot(OUTPUT_SLOT,
                    new ItemStack(result.getItem(), this.itemStackHandler.getStackInSlot(OUTPUT_SLOT).getCount() + result.getCount()));

            NonNullList<InputEntry> recipeInputs = match.get().value().input();
            boolean[] used = new boolean[2]; // 不包括输出槽
            for (InputEntry entry : recipeInputs) {
                for (int i = 0; i < used.length; i++) {
                    ItemStack stack = this.itemStackHandler.getStackInSlot(i);
                    if (!used[i] && entry.ingredient().test(stack) && stack.getCount() >= entry.count()) {
                        this.itemStackHandler.extractItem(i, entry.count(), false);
                        used[i] = true;
                        break;
                    }
                }
            }
        }
    }

    private boolean hasCorrectRecipe(Level world) {
        Optional<RecipeHolder<GearingUnitRecipe>> match = getMatchRecipe(world);
        if (match.isPresent()) {

            NonNullList<InputEntry> recipeInputs = match.get().value().input();
            boolean[] used = new boolean[recipeInputs.size()];
            for (InputEntry entry : recipeInputs) {
                boolean matched = false;
                for (int i = 0; i < used.length; i++) {
                    ItemStack stack = this.itemStackHandler.getStackInSlot(i);
                    if (!used[i] && entry.ingredient().test(stack) && stack.getCount() >= entry.count()) {
                        used[i] = true;
                        matched = true;
                        break;
                    }
                }
                if (!matched) return false;
            }

            ItemStack result = match.get().value().getResultItem(world.registryAccess());
            return canInsertItem(result);
        }

        return false;
    }

    private boolean hasCraftingFinished() {
        return progress >= maxProgress;
    }

    private void incrementProgress() {
        this.progress++;
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

    @Override
    public void receiveElectricCharge(int amount) {
        this.storePower += amount;
        if (this.storePower > 100) {
            this.storePower = 100;
        }
    }

    @Override
    public boolean needsPower() {
        return this.storePower < POWER_PRE_TICK;
    }

    @Override
    public int getRequiredPower() {
        return POWER_PRE_TICK;
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
                return 2;
            }

            @Override
            public @NotNull ItemStack getStackInSlot(int slot) {
                if (slot == INPUT_SLOT1) return parent.getStackInSlot(GearingUnitBlockEntity.INPUT_SLOT1);
                if (slot == INPUT_SLOT2) return parent.getStackInSlot(GearingUnitBlockEntity.INPUT_SLOT2);
                return ItemStack.EMPTY;
            }

            @Override
            public @NotNull ItemStack insertItem(int slot, @NotNull ItemStack stack, boolean simulate) {
                if (slot == INPUT_SLOT1) {
                    return parent.insertItem(GearingUnitBlockEntity.INPUT_SLOT1, stack, simulate);
                } else if (slot == INPUT_SLOT2) {
                    return parent.insertItem(GearingUnitBlockEntity.INPUT_SLOT2, stack, simulate);
                }
                return stack;
            }

            @Override
            public @NotNull ItemStack extractItem(int slot, int amount, boolean simulate) {
                return ItemStack.EMPTY;
            }

            @Override
            public int getSlotLimit(int slot) {
                return 64;
            }

            @Override
            public boolean isItemValid(int slot, @NotNull ItemStack stack) {
                return true;
            }
        }

    private record OutputItemHandler(ItemStackHandler parent) implements IItemHandler {

        @Override
            public int getSlots() {
                return 1;
            }

            @Override
            public @NotNull ItemStack getStackInSlot(int slot) {
                return parent.getStackInSlot(GearingUnitBlockEntity.OUTPUT_SLOT);
            }

            @Override
            public @NotNull ItemStack insertItem(int slot, @NotNull ItemStack stack, boolean simulate) {
                return stack;
            }

            @Override
            public @NotNull ItemStack extractItem(int slot, int amount, boolean simulate) {
                return parent.extractItem(GearingUnitBlockEntity.OUTPUT_SLOT, amount, simulate);
            }

            @Override
            public int getSlotLimit(int slot) {
                return 64;
            }

            @Override
            public boolean isItemValid(int slot, @NotNull ItemStack stack) {
                return false;
            }
        }
}
