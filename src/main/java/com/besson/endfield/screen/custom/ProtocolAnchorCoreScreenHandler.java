package com.besson.endfield.screen.custom;

import com.besson.endfield.blockEntity.custom.ProtocolAnchorCoreBlockEntity;
import com.besson.endfield.screen.ModScreens;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.SlotItemHandler;

public class ProtocolAnchorCoreScreenHandler extends AbstractContainerMenu {
    public final ProtocolAnchorCoreBlockEntity entity;
    private final BlockPos pos;
    public double supplyRatio;
    public int totalGenerated;
    public int totalDemand;
    public int storedEnergy;

    public ProtocolAnchorCoreScreenHandler(int syncId, Inventory playerInventory, FriendlyByteBuf packetByteBuf) {
        this(syncId, playerInventory, playerInventory.player.level().getBlockEntity(packetByteBuf.readBlockPos()));
        this.supplyRatio = packetByteBuf.readDouble();
        this.totalGenerated = packetByteBuf.readInt();
        this.totalDemand = packetByteBuf.readInt();
        this.storedEnergy = packetByteBuf.readInt();
    }
    public ProtocolAnchorCoreScreenHandler(int syncId, Inventory playerInventory, BlockEntity blockEntity) {
        super(ModScreens.PROTOCOL_ANCHOR_CORE_SCREEN.get(), syncId);
        this.entity = (ProtocolAnchorCoreBlockEntity) blockEntity;
        this.pos = this.entity.getBlockPos();

        int i = 36;

        IItemHandler handler = Capabilities.ItemHandler.BLOCK.getCapability(entity.getLevel(), entity.getBlockPos(), entity.getBlockState(),
                entity, null);
        if (handler != null) {
            for (int j = 0; j < 6; j++) {
                for (int k = 0; k < 9; k++) {
                    this.addSlot(new SlotItemHandler(handler, k + j * 9, 8 + k * 18, j * 18 + 18));
                }
            }
        }

        for (int j = 0; j < 3; j++) {
            for (int k = 0; k < 9; k++) {
                this.addSlot(new Slot(playerInventory, k + j * 9 + 9, 8 + k * 18, 103 + j * 18 + i));
            }
        }

        for (int j = 0; j < 9; j++) {
            this.addSlot(new Slot(playerInventory, j, 8 + j * 18, 161 + i));
        }
    }

    public BlockPos getPos() {
        return pos;
    }

    @Override
    public ItemStack quickMoveStack(Player pPlayer, int invSlot) {
        ItemStack newStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(invSlot);
        if (slot != null && slot.hasItem()) {
            ItemStack originalStack = slot.getItem();
            newStack = originalStack.copy();
            int containerSlots = 54;
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
        return entity != null;
    }
}
