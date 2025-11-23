package com.besson.endfield.blockEntity.custom;

import com.besson.endfield.block.ElectrifiableDevice;
import com.besson.endfield.blockEntity.ModBlockEntities;
import com.besson.endfield.recipe.InputEntry;
import com.besson.endfield.recipe.ModRecipes;
import com.besson.endfield.recipe.custom.RefiningUnitRecipe;
import com.besson.endfield.screen.custom.RefiningUnitScreenHandler;
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

public class RefiningUnitBlockEntity extends BlockEntity implements GeoBlockEntity, MenuProvider, ElectrifiableDevice {

    public static final int INPUT_SLOT = 0;
    public static final int OUTPUT_SLOT = 1;

    private final ItemStackHandler itemStackHandler = new ItemStackHandler(2) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }
    };
    private IItemHandler input = new InputItemHandler(itemStackHandler);
    private IItemHandler output = new OutputItemHandler(itemStackHandler);

    protected final ContainerData propertyDelegate;
    private int progress = 0;
    private int maxProgress = 40;

    private int storePower = 0;
    private static final int POWER_PRE_TICK = 5;
    private boolean isWorking = false;

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public RefiningUnitBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.REFINING_UNIT.get(), pos, state);
        this.propertyDelegate = new ContainerData() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> RefiningUnitBlockEntity.this.progress;
                    case 1 -> RefiningUnitBlockEntity.this.maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0 -> RefiningUnitBlockEntity.this.progress = value;
                    case 1 -> RefiningUnitBlockEntity.this.maxProgress = value;
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

    public @Nullable IItemHandler getItemStackHandler() {
        return itemStackHandler;
    }

    public IItemHandler getInputHandler() {
        return input;
    }

    public IItemHandler getOutputHandler() {
        return output;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    public NonNullList<ItemStack> getItems() {
        NonNullList<ItemStack> items = NonNullList.withSize(2, ItemStack.EMPTY);
        items.set(INPUT_SLOT, this.itemStackHandler.getStackInSlot(INPUT_SLOT));
        items.set(OUTPUT_SLOT, this.itemStackHandler.getStackInSlot(OUTPUT_SLOT));
        return items;
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("blockEntity.refining_unit");
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new RefiningUnitScreenHandler(pContainerId, pPlayerInventory, this, this.propertyDelegate);
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
        tag.putInt("progress", progress);
        tag.putInt("storePower", storePower);
        tag.putBoolean("isWorking", isWorking);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        itemStackHandler.deserializeNBT(registries, tag.getCompound("inventory"));
        this.progress = tag.getInt("progress");
        this.storePower = tag.getInt("storePower");
        this.isWorking = tag.getBoolean("isWorking");
    }

    public static void tick(Level world, BlockPos pos, BlockState state, RefiningUnitBlockEntity be) {
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

            if (hasRecipe && be.storePower >= POWER_PRE_TICK) {
                be.storePower -= POWER_PRE_TICK;
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

        Optional<RecipeHolder<RefiningUnitRecipe>> match = getMatchRecipe(world);

        if (match.isPresent()) {
            ItemStack result = match.get().value().getResultItem(world.registryAccess());
            itemStackHandler.setStackInSlot(OUTPUT_SLOT,
                    new ItemStack(result.getItem(), itemStackHandler.getStackInSlot(OUTPUT_SLOT).getCount() + result.getCount()));
            InputEntry recipeInput = match.get().value().getInput();

            ItemStack stack = itemStackHandler.getStackInSlot(0);
            if (recipeInput.ingredient().test(stack) && stack.getCount() >= recipeInput.count()) {
                itemStackHandler.extractItem(0, recipeInput.count(), false);
            }
        }
    }

    private Optional<RecipeHolder<RefiningUnitRecipe>> getMatchRecipe(Level world) {
        SimpleContainer inv = new SimpleContainer(2);
        for (int i = 0; i < 2; i++) {
            inv.setItem(i, this.itemStackHandler.getStackInSlot(i));
        }
        SingleRecipeInput input = new SingleRecipeInput(inv.getItem(0));

        return world.getRecipeManager()
                .getRecipeFor(ModRecipes.REFINING_UNIT_TYPE.get(), input, world);
    }

    private boolean hasCraftingFinished() {
        return progress >= maxProgress;
    }

    private void increaseProgress() {
        this.progress++;
    }

    private boolean hasCorrectRecipe(Level world) {
        Optional<RecipeHolder<RefiningUnitRecipe>> match = getMatchRecipe(world);

        if (match.isPresent()) {
            InputEntry recipeInput = match.get().value().getInput();
            boolean matched = false;
            ItemStack stack = this.itemStackHandler.getStackInSlot(0);
            if (recipeInput.ingredient().test(stack) && stack.getCount() >= recipeInput.count()) {
                matched = true;
            }
            if (!matched) return false;

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

    private record InputItemHandler(ItemStackHandler parent) implements IItemHandler {

        @Override
            public int getSlots() {
                return 1;
            }

            @Override
            public ItemStack getStackInSlot(int slot) {
                return parent.getStackInSlot(RefiningUnitBlockEntity.INPUT_SLOT);
            }

            @Override
            public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
                ItemStack current = parent.getStackInSlot(RefiningUnitBlockEntity.INPUT_SLOT);
                if (current.isEmpty() || ItemStack.isSameItem(current, stack)) {
                    return parent.insertItem(RefiningUnitBlockEntity.INPUT_SLOT, stack, simulate);
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
                return parent.getStackInSlot(RefiningUnitBlockEntity.OUTPUT_SLOT);
            }

            @Override
            public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
                return stack;
            }

            @Override
            public ItemStack extractItem(int slot, int amount, boolean simulate) {
                return parent.extractItem(RefiningUnitBlockEntity.OUTPUT_SLOT, amount, simulate);
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