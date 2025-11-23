package com.besson.endfield.blockEntity.custom;

import com.besson.endfield.block.ElectrifiableDevice;
import com.besson.endfield.blockEntity.ModBlockEntities;
import com.besson.endfield.recipe.ModRecipes;
import com.besson.endfield.recipe.custom.OreRigRecipe;
import com.besson.endfield.screen.custom.ElectricMiningRigScreenHandler;
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
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.Optional;

public class ElectricMiningRigBlockEntity extends BlockEntity implements GeoBlockEntity, MenuProvider, ElectrifiableDevice {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    private final ItemStackHandler itemStackHandler = new ItemStackHandler(1) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }
    };

    private static final int OUTPUT_SLOT = 0;

    private int storePower = 0;
    private static final int POWER_PRE_TICK = 5;
    private boolean isWorking = false;

    protected final ContainerData propertyDelegate;
    private int progress = 0;
    private int maxProgress = 40;

    public ElectricMiningRigBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.ELECTRIC_MINING_RIG.get(), pos, state);
        this.propertyDelegate = new ContainerData() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> ElectricMiningRigBlockEntity.this.progress;
                    case 1 -> ElectricMiningRigBlockEntity.this.maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0 -> ElectricMiningRigBlockEntity.this.progress = value;
                    case 1 -> ElectricMiningRigBlockEntity.this.maxProgress = value;
                }
            }

            @Override
            public int getCount() {
                return 2;
            }
        };
    }



    public ItemStackHandler getItemStackHandler() {
        return itemStackHandler;
    }

    public static void tick(Level world, BlockPos pos, BlockState state, ElectricMiningRigBlockEntity entity) {
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
                entity.increaseProgress();
                entity.storePower -= POWER_PRE_TICK;
                if (entity.hasCraftFinished()) {
                    entity.craftItem(world);
                    entity.resetProgress();
                }
            } else {
                entity.resetProgress();
            }
        } else {
            entity.resetProgress();
        }
        entity.setChanged();
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
        BlockState below = world.getBlockState(this.getBlockPos().below());
        ItemStack belowStack = below.getBlock().asItem().getDefaultInstance();
        inv.setItem(0, belowStack);

        SingleRecipeInput input = new SingleRecipeInput(inv.getItem(0));
        return world.getRecipeManager()
                .getRecipeFor(ModRecipes.ORE_RIG_TYPE.get(), input, world);
     }

     private boolean hasCraftFinished() {
        return this.progress >= this.maxProgress;
     }

     private void increaseProgress() {
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
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "controller", 0,
                state -> this.isWorking
                        ? state.setAndContinue(RawAnimation.begin().thenLoop("working"))
                        : state.setAndContinue(RawAnimation.begin().thenLoop("idle"))));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.put("inventory", itemStackHandler.serializeNBT(registries));
        tag.putInt("progress", progress);
        tag.putBoolean("isWorking", isWorking);
        tag.putInt("storePower", storePower);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        itemStackHandler.deserializeNBT(registries, tag.getCompound("inventory"));
        this.progress = tag.getInt("progress");
        this.isWorking = tag.getBoolean("isWorking");
        this.storePower = tag.getInt("storePower");
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

    public NonNullList<ItemStack> getItems() {
        NonNullList<ItemStack> items = NonNullList.withSize(1, ItemStack.EMPTY);
        items.set(OUTPUT_SLOT, this.itemStackHandler.getStackInSlot(OUTPUT_SLOT));
        return items;
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("blockEntity.electric_mining_rig");
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new ElectricMiningRigScreenHandler(pContainerId, pPlayerInventory, this, this.propertyDelegate);
    }
}
