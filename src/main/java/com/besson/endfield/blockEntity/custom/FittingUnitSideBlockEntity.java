package com.besson.endfield.blockEntity.custom;

import com.besson.endfield.blockEntity.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class FittingUnitSideBlockEntity extends BlockEntity {
    private BlockPos parentPos;

    public FittingUnitSideBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.FITTING_UNIT_SIDE.get(), pos, state);
    }

    public @Nullable FittingUnitBlockEntity getParentBlock() {
        if (parentPos == null || level == null) return null;
        BlockEntity entity = this.level.getBlockEntity(parentPos);
        if (entity instanceof FittingUnitBlockEntity parent) {
            return parent;
        }
        return null;
    }

    public NonNullList<ItemStack> getItems() {
        FittingUnitBlockEntity parent = getParentBlock();
        if (parent != null) {
            return parent.getItems();
        }
        return NonNullList.withSize(0, ItemStack.EMPTY);
    }

    public void setParentPos(BlockPos parentPos) {
        this.parentPos = parentPos;
        setChanged();
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        if (parentPos != null) {
            tag.putLong("parentPos", parentPos.asLong());
        }
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        if (tag.contains("parentPos")) {
            this.parentPos = BlockPos.of(tag.getLong("parentPos"));
        }
    }

    @Override
    public @Nullable Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        return saveWithFullMetadata(registries);
    }
}
