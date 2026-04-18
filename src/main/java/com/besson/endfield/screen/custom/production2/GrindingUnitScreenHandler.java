package com.besson.endfield.screen.custom.production2;

import com.besson.endfield.blockEntity.custom.production2.GrindingUnitBlockEntity;
import com.besson.endfield.screen.ModScreens;
import com.besson.endfield.screen.custom.BaseIOScreenHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.SlotItemHandler;

import java.util.Objects;

public class GrindingUnitScreenHandler extends BaseIOScreenHandler<GrindingUnitBlockEntity> {
    public GrindingUnitScreenHandler(int syncId, Inventory playerInventory, FriendlyByteBuf packetByteBuf) {
        this(syncId, playerInventory, Objects.requireNonNull(getClientEntity(playerInventory, packetByteBuf)),
                new SimpleContainerData(3));
    }

    public GrindingUnitScreenHandler(int syncId, Inventory playerInventory, GrindingUnitBlockEntity blockEntity, ContainerData propertyDelegate) {
        super(ModScreens.GRINDING_UNIT_SCREEN.get(), syncId, playerInventory, blockEntity, propertyDelegate, 3);

        IItemHandler handler = Capabilities.ItemHandler.BLOCK.getCapability(level, entity.getBlockPos(), entity.getBlockState(),
                entity, null);
        if (handler != null) {
            this.addSlot(new SlotItemHandler(handler, 0, 47, 22));
            this.addSlot(new SlotItemHandler(handler, 1, 47, 49));
            this.addSlot(new SlotItemHandler(handler, 2, 113, 35));
        }
    }

    private static GrindingUnitBlockEntity getClientEntity(Inventory playerInventory, FriendlyByteBuf buf) {
        BlockPos pos = buf.readBlockPos();
        BlockEntity be = playerInventory.player.level().getBlockEntity(pos);
        return be instanceof GrindingUnitBlockEntity e ? e : null;
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
}
