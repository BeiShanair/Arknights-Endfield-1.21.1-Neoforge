package com.besson.endfield.blockEntity.custom.production1;

import com.besson.endfield.block.ElectrifiableDevice;
import com.besson.endfield.blockEntity.ModBlockEntities;
import com.besson.endfield.blockEntity.custom.BaseIOBlockEntity;
import com.besson.endfield.recipe.InputEntry;
import com.besson.endfield.recipe.ModRecipes;
import com.besson.endfield.recipe.custom.MouldingUnitRecipe;
import com.besson.endfield.screen.custom.production1.MouldingUnitScreenHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
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
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.Optional;

public class MouldingUnitBlockEntity extends BaseIOBlockEntity<MouldingUnitRecipe> implements GeoBlockEntity, MenuProvider, ElectrifiableDevice {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    public static final int INPUT_SLOT = 0;
    public static final int OUTPUT_SLOT = 1;
    private static final int POWER_PRE_TICK = 10;

    public MouldingUnitBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.MOULDING_UNIT.get(), pos, state, 40);
    }

    @Override
    protected ContainerData createPropertyDelegate() {
        return new ContainerData() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> MouldingUnitBlockEntity.this.progress;
                    case 1 -> MouldingUnitBlockEntity.this.maxProgress;
                    case 2 -> MouldingUnitBlockEntity.this.enable ? 1 : 0;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0 -> MouldingUnitBlockEntity.this.progress = value;
                    case 1 -> MouldingUnitBlockEntity.this.maxProgress = value;
                    case 2 -> MouldingUnitBlockEntity.this.enable = value == 1;
                }
            }

            @Override
            public int getCount() {
                return 3;
            }
        };
    }

    @Override
    protected int getPowerCostPerTick() {
        return POWER_PRE_TICK;
    }

    @Override
    protected int getInvSize() {
        return 2;
    }

    @Override
    protected int getOutputSlotIndex() {
        return OUTPUT_SLOT;
    }

    @Override
    public IItemHandler getOutput() {
        return new OutputItemHandler(itemStackHandler);
    }

    @Override
    public IItemHandler getInput() {
        return new InputItemHandler(itemStackHandler);
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
        return Component.translatable("blockEntity.moulding_unit");
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new MouldingUnitScreenHandler(pContainerId, pPlayerInventory, this, this.propertyDelegate);
    }
    
    @Override
    protected void craftItem(Level world) {
        getMatchRecipe(world).ifPresent(r -> {
            ItemStack result = r.value().getResultItem(world.registryAccess());
            this.itemStackHandler.setStackInSlot(OUTPUT_SLOT,
                    new ItemStack(result.getItem(), itemStackHandler.getStackInSlot(OUTPUT_SLOT).getCount() + result.getCount()));
            InputEntry recipeInput = r.value().getInput();
            ItemStack stack = this.itemStackHandler.getStackInSlot(INPUT_SLOT);
            if (recipeInput.ingredient().test(stack) && stack.getCount() >= recipeInput.count()) {
                itemStackHandler.extractItem(INPUT_SLOT, recipeInput.count(), false);
            }
        });
    }

    @Override
    protected Optional<RecipeHolder<MouldingUnitRecipe>> getMatchRecipe(Level world) {
        SimpleContainer inv = new SimpleContainer(2);
        for (int i = 0; i < 2; i++) {
            inv.setItem(i, this.itemStackHandler.getStackInSlot(i));
        }
        SingleRecipeInput input = new SingleRecipeInput(inv.getItem(0));

        return world.getRecipeManager()
                .getRecipeFor(ModRecipes.MOULDING_UNIT_TYPE.get(), input, world);
    }

    @Override
    protected boolean hasCorrectRecipe(Level world) {
        Optional<RecipeHolder<MouldingUnitRecipe>> match = getMatchRecipe(world);

        if (match.isPresent()) {
            InputEntry recipeInput = match.get().value().getInput();
            boolean matched = false;
            ItemStack stack = this.itemStackHandler.getStackInSlot(INPUT_SLOT);
            if (recipeInput.ingredient().test(stack) && stack.getCount() >= recipeInput.count()) {
                matched = true;
            }
            if (!matched) return false;

            ItemStack result = match.get().value().getResultItem(world.registryAccess());
            return canOutputAccept(result);
        }

        return false;
    }

    private record InputItemHandler(ItemStackHandler parent) implements IItemHandler {

        @Override
            public int getSlots() {
                return 1;
            }

            @Override
            public ItemStack getStackInSlot(int slot) {
                return parent.getStackInSlot(MouldingUnitBlockEntity.INPUT_SLOT);
            }

            @Override
            public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
                ItemStack current = parent.getStackInSlot(MouldingUnitBlockEntity.INPUT_SLOT);
                if (current.isEmpty() || ItemStack.isSameItem(current, stack)) {
                    return parent.insertItem(MouldingUnitBlockEntity.INPUT_SLOT, stack, simulate);
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
                return parent.getStackInSlot(MouldingUnitBlockEntity.OUTPUT_SLOT);
            }

            @Override
            public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
                return stack;
            }

            @Override
            public ItemStack extractItem(int slot, int amount, boolean simulate) {
                return parent.extractItem(MouldingUnitBlockEntity.OUTPUT_SLOT, amount, simulate);
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
