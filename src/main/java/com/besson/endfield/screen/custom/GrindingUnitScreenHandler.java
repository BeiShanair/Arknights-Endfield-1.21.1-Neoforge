package com.besson.endfield.screen.custom;

import com.besson.endfield.block.ModBlocks;
import com.besson.endfield.blockEntity.custom.GrindingUnitBlockEntity;
import com.besson.endfield.screen.ModScreens;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.SlotItemHandler;

public class GrindingUnitScreenHandler extends AbstractContainerMenu {
    private final ContainerData propertyDelegate;
    public final GrindingUnitBlockEntity entity;
    private final Level level;

    public GrindingUnitScreenHandler(int syncId, Inventory playerInventory, FriendlyByteBuf packetByteBuf) {
        this(syncId, playerInventory, playerInventory.player.level().getBlockEntity(packetByteBuf.readBlockPos()),
                new SimpleContainerData(3));
    }

    public GrindingUnitScreenHandler(int syncId, Inventory playerInventory, BlockEntity blockEntity, ContainerData propertyDelegate) {
        super(ModScreens.GRINDING_UNIT_SCREEN.get(), syncId);
        checkContainerSize(playerInventory, 3);
        this.propertyDelegate = propertyDelegate;
        this.entity = (GrindingUnitBlockEntity) blockEntity;
        this.level = playerInventory.player.level();

        IItemHandler handler = Capabilities.ItemHandler.BLOCK.getCapability(level, entity.getBlockPos(), entity.getBlockState(),
                entity, null);
        if (handler != null) {
            this.addSlot(new SlotItemHandler(handler, 0, 47, 22));
            this.addSlot(new SlotItemHandler(handler, 1, 47, 49));
            this.addSlot(new SlotItemHandler(handler, 2, 113, 35));
        }

        addPlayerInventory(playerInventory);
        addPlayerHotbar(playerInventory);

        addDataSlots(propertyDelegate);
    }

    @Override
    public ItemStack quickMoveStack(Player player, int invSlot) {
        ItemStack newStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(invSlot);
        if (slot != null && slot.hasItem()) {
            ItemStack originalStack = slot.getItem();
            newStack = originalStack.copy();
            int containerSlots = 1;
            if (invSlot < containerSlots) {
                if (!this.moveItemStackTo(originalStack, containerSlots, this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(originalStack, 0, containerSlots, false)) {
                return ItemStack.EMPTY;
            }

            if (originalStack.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
        }

        return newStack;
    }

    @Override
    public boolean stillValid(Player pPlayer) {
        return stillValid(ContainerLevelAccess.create(level, entity.getBlockPos()),
                pPlayer, ModBlocks.GRINDING_UNIT.get());
    }

    private void addPlayerInventory(Inventory playerInventory) {
        for (int i = 0; i < 3; ++i) {
            for (int l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + i * 9 + 9, 8 + l * 18, 84 + i * 18));
            }
        }
    }

    private void addPlayerHotbar(Inventory playerInventory) {
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
        }
    }
    public boolean isCrafting(){
        return propertyDelegate.get(0) > 0;
    }

    public int getScaledProgress() {
        int progress = this.propertyDelegate.get(0);
        int maxProgress = this.propertyDelegate.get(1);
        int progressArrowSize = 26;

        return maxProgress != 0 && progress != 0 ? progress * progressArrowSize / maxProgress : 0;
    }
}
