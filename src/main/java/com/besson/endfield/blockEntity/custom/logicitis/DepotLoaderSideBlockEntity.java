package com.besson.endfield.blockEntity.custom.logicitis;

import com.besson.endfield.blockEntity.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class DepotLoaderSideBlockEntity extends BlockEntity {
    private BlockPos parentPos;

    public DepotLoaderSideBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.DEPOT_LOADER_SIDE.get(), pos, state);
    }

    public void setParentPos(BlockPos pos) {
        this.parentPos = pos;
        setChanged();
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        if (parentPos != null) {
            tag.putLong("parent", parentPos.asLong());
        }
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        if (tag.contains("parent")) {
            parentPos = BlockPos.of(tag.getLong("parent"));
        }
    }

    @Override
    public @Nullable net.minecraft.network.protocol.Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        return saveWithFullMetadata(registries);
    }

    @Nullable
    public DepotLoaderBlockEntity getParentBlock() {
        if (parentPos == null || level == null) return null;
        BlockEntity be = level.getBlockEntity(parentPos);
        if (be instanceof DepotLoaderBlockEntity parent) {
            return parent;
        }
        return null;
    }
}
