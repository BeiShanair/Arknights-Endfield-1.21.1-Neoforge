package com.besson.endfield.blockEntity.custom;

import com.besson.endfield.blockEntity.ModBlockEntities;
import com.besson.endfield.recipe.ModRecipes;
import com.besson.endfield.recipe.custom.OreRigRecipe;
import com.besson.endfield.screen.custom.PortableOriginiumRigScreenHandler;
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
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.Optional;

public class PortableOriginiumRigBlockEntity extends BlockEntity implements GeoBlockEntity, MenuProvider {

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private boolean isWorking;
    private static final int OUTPUT_SLOT = 0;

    protected final ContainerData propertyDelegate;
    private int progress = 0;
    private int maxProgress = 40;

    private final ItemStackHandler itemStackHandler = new ItemStackHandler(1) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }
    };

    public PortableOriginiumRigBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.PORTABLE_ORIGINIUM_RIG.get(), pos, state);
        this.propertyDelegate = new ContainerData() {
            @Override
            public int get(int pIndex) {
                return switch (pIndex) {
                    case 0 -> PortableOriginiumRigBlockEntity.this.progress;
                    case 1 -> PortableOriginiumRigBlockEntity.this.maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int pIndex, int pValue) {
                switch (pIndex) {
                    case 0 -> PortableOriginiumRigBlockEntity.this.progress = pValue;
                    case 1 -> PortableOriginiumRigBlockEntity.this.maxProgress = pValue;
                }
            }

            @Override
            public int getCount() {
                return 2;
            }
        };
    }

    public @Nullable IItemHandler getItemStackHandler() {
        return itemStackHandler;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "controller", 0,
                state -> this.isWorking
                        ? state.setAndContinue(RawAnimation.begin().thenLoop("working"))
                        : state.setAndContinue(RawAnimation.begin().thenLoop("idle"))));
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.put("inventory", itemStackHandler.serializeNBT(registries));
        tag.putInt("progress", progress);
        tag.putBoolean("isWorking", isWorking);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        itemStackHandler.deserializeNBT(registries, tag.getCompound("inventory"));
        this.progress = tag.getInt("progress");
        this.isWorking = tag.getBoolean("isWorking");
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
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }


    @Override
    public Component getDisplayName() {
        return Component.translatable("blockEntity.portable_originium_rig");
    }

    public boolean isWorking() {
        return isWorking;
    }

    public void tick(Level world, BlockPos pos, BlockState state, PortableOriginiumRigBlockEntity entity) {
        if (world.isClientSide()) {
            return;
        }

        boolean activeNow = this.hasCorrectRecipe(world);

        if (this.isOutputSlotAvailable()) {
            if (activeNow) {

                this.incrementProgress();
                setChanged(world, pos, state);

                if (this.hasCraftFinished()) {
                    this.craftItem(world);
                    this.resetProgress();
                }
            } else {
                this.resetProgress();
            }
        } else {
            this.resetProgress();
            setChanged(world, pos, state);
        }

        if (entity.isWorking != activeNow) {
            entity.isWorking = activeNow;
            entity.setChanged();
            world.sendBlockUpdated(pos, state, state, 3);
        }
    }

    private void resetProgress() {
        this.progress = 0;
    }

    private void craftItem(Level world) {

        Optional<RecipeHolder<OreRigRecipe>> match = getMatchRecipe(world);

        if (match.isPresent()) {
            ItemStack result = match.get().value().getResultItem(world.registryAccess());
            ItemStack outputStack = itemStackHandler.getStackInSlot(OUTPUT_SLOT);
            itemStackHandler.setStackInSlot(OUTPUT_SLOT,
                    new ItemStack(result.getItem(), outputStack.getCount() + result.getCount()));
        }

    }

    private Optional<RecipeHolder<OreRigRecipe>> getMatchRecipe(Level world) {
        SimpleContainer inv = new SimpleContainer(1);
        BlockState belowState = world.getBlockState(this.getBlockPos().below());
        ItemStack belowStack = belowState.getBlock().asItem().getDefaultInstance();
        inv.setItem(0, belowStack);

        SingleRecipeInput input = new SingleRecipeInput(inv.getItem(0));

        return world.getRecipeManager()
                .getRecipeFor(ModRecipes.ORE_RIG_TYPE.get(), input, world);
    }

    private boolean hasCraftFinished () {
        return this.progress >= this.maxProgress;
    }

    private void incrementProgress() {
        this.progress++;
    }

    private boolean hasCorrectRecipe(Level world) {

        Optional<RecipeHolder<OreRigRecipe>> match = getMatchRecipe(world);

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

    @Override
    public @Nullable AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new PortableOriginiumRigScreenHandler(pContainerId, pPlayerInventory, this, this.propertyDelegate);
    }

    public ContainerData getPropertyDelegate() {
        return propertyDelegate;
    }

    public NonNullList<ItemStack> getItems() {
        NonNullList<ItemStack> items = NonNullList.withSize(1, ItemStack.EMPTY);
        for (int i = 0; i < items.size(); i++) {
            items.set(i, itemStackHandler.getStackInSlot(i));
        }
        return items;
    }
}
