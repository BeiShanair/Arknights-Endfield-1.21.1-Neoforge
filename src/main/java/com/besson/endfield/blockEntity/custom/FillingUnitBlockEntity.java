package com.besson.endfield.blockEntity.custom;

import com.besson.endfield.blockEntity.ModBlockEntities;
import com.besson.endfield.recipe.InputEntry;
import com.besson.endfield.recipe.ModRecipes;
import com.besson.endfield.recipe.custom.DoubleRecipeInput;
import com.besson.endfield.recipe.custom.FillingUnitRecipe;
import com.besson.endfield.screen.custom.FillingUnitScreenHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.Optional;

public class FillingUnitBlockEntity extends BaseIOBlockEntity<FillingUnitRecipe> implements GeoBlockEntity {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private static final int INPUT_SLOT1 = 0;
    private static final int INPUT_SLOT2 = 1;
    private static final int OUTPUT_SLOT = 2;
    private static final int POWER_PRE_TICK = 10;
    public FillingUnitBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.FILLING_UNIT.get(), pos, state, 200);
    }

    @Override
    protected int getPowerCostPerTick() {
        return POWER_PRE_TICK;
    }

    @Override
    protected ContainerData createPropertyDelegate() {
        return new ContainerData() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> FillingUnitBlockEntity.this.progress;
                    case 1 -> FillingUnitBlockEntity.this.maxProgress;
                    case 2 -> FillingUnitBlockEntity.this.enable ? 1 : 0;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0 -> FillingUnitBlockEntity.this.progress = value;
                    case 1 -> FillingUnitBlockEntity.this.maxProgress = value;
                    case 2 -> FillingUnitBlockEntity.this.enable = value == 1;
                }
            }

            @Override
            public int getCount() {
                return 3;
            }
        };
    }

    @Override
    public IItemHandler getInput() {
        return new InputItemHandler(itemStackHandler);
    }

    @Override
    public IItemHandler getOutput() {
        return new OutputItemHandler(itemStackHandler);
    }

    @Override
    protected int getInvSize() {
        return 3;
    }

    @Override
    protected int getOutputSlotIndex() {
        return OUTPUT_SLOT;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "controller", 0,
                state -> state.setAndContinue(RawAnimation.begin().thenLoop("working"))));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("blockEntity.filling_unit");
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new FillingUnitScreenHandler(pContainerId, pPlayerInventory, this, this.propertyDelegate);
    }

    @Override
    protected Optional<RecipeHolder<FillingUnitRecipe>> getMatchRecipe(Level world) {
        SimpleContainer inv = new SimpleContainer(3);
        for (int i = 0; i < 3; i++) {
            inv.setItem(i, this.itemStackHandler.getStackInSlot(i));
        }
        DoubleRecipeInput input = new DoubleRecipeInput(inv);
        return world.getRecipeManager()
                .getRecipeFor(ModRecipes.FILLING_UNIT_TYPE.get(), input, world);
    }

    @Override
    protected void craftItem(Level world) {
        getMatchRecipe(world).ifPresent(r -> {
            ItemStack result = r.value().getResultItem(world.registryAccess());
            itemStackHandler.setStackInSlot(OUTPUT_SLOT,
                    new ItemStack(result.getItem(), itemStackHandler.getStackInSlot(OUTPUT_SLOT).getCount() + result.getCount()));

            NonNullList<InputEntry> recipeInputs = r.value().getInput();
            boolean[] used = new boolean[2];
            for (InputEntry entry: recipeInputs) {
                for (int i = 0; i < used.length; i++) {
                    ItemStack stack = this.itemStackHandler.getStackInSlot(i);
                    if (!used[i] && entry.ingredient().test(stack)) {
                        itemStackHandler.extractItem(i, entry.count(), false);
                        used[i] = true;
                        break;
                    }
                }
            }
        });
    }

    @Override
    protected boolean hasCorrectRecipe(Level world) {
        Optional<RecipeHolder<FillingUnitRecipe>> match = getMatchRecipe(world);
        if (match.isPresent()) {
            NonNullList<InputEntry> recipeInputs = match.get().value().getInput();
            boolean[] used = new boolean[recipeInputs.size()];
            for (InputEntry entry: recipeInputs) {
                boolean matched = false;
                for (int i = 0; i < used.length; i++) {
                    ItemStack stack = this.itemStackHandler.getStackInSlot(i);
                    if (!used[i] && entry.ingredient().test(stack) && stack.getCount() >= entry.count()) {
                        matched = true;
                        used[i] = true;
                        break;
                    }
                }
                if (!matched) return false;
            }
            ItemStack result = match.get().value().getResultItem(world.registryAccess());
            return canOutputAccept(result);
        }
        return false;
    }
    
    private record InputItemHandler(ItemStackHandler parent) implements IItemHandler {

        @Override
            public int getSlots() {
                return 2;
            }

            @Override
            public @NotNull ItemStack getStackInSlot(int slot) {
                if (slot == INPUT_SLOT1) return parent.getStackInSlot(FillingUnitBlockEntity.INPUT_SLOT1);
                if (slot == INPUT_SLOT2) return parent.getStackInSlot(FillingUnitBlockEntity.INPUT_SLOT2);
                return ItemStack.EMPTY;
            }

            @Override
            public @NotNull ItemStack insertItem(int slot, @NotNull ItemStack stack, boolean simulate) {
                if (slot == INPUT_SLOT1) {
                    return parent.insertItem(FillingUnitBlockEntity.INPUT_SLOT1, stack, simulate);
                } else if (slot == INPUT_SLOT2) {
                    return parent.insertItem(FillingUnitBlockEntity.INPUT_SLOT2, stack, simulate);
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
                return parent.getStackInSlot(FillingUnitBlockEntity.OUTPUT_SLOT);
            }

            @Override
            public @NotNull ItemStack insertItem(int slot, @NotNull ItemStack stack, boolean simulate) {
                return stack;
            }

            @Override
            public @NotNull ItemStack extractItem(int slot, int amount, boolean simulate) {
                return parent.extractItem(FillingUnitBlockEntity.OUTPUT_SLOT, amount, simulate);
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
