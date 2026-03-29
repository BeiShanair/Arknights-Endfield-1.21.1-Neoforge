package com.besson.endfield.blockEntity.custom.powering;

import com.besson.endfield.blockEntity.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class ThermalBankSideBlockEntity extends BlockEntity {
    private BlockPos parentPos;

    public ThermalBankSideBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.THERMAL_BANK_SIDE.get(), pos, state);
    }

    public @Nullable ThermalBankBlockEntity getParentBlock() {
        if (parentPos == null || level == null) return null;
        BlockEntity entity = level.getBlockEntity(parentPos);
        if (entity instanceof ThermalBankBlockEntity entity1) {
            return entity1;
        }
        return null;
    }

    public void setParentPos(BlockPos parentPos) {
        this.parentPos = parentPos;
        setChanged();
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        if (tag.contains("parent")) {
            parentPos = BlockPos.of(tag.getLong("parent"));
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        if (parentPos != null) {
            tag.putLong("parent", parentPos.asLong());
        }
    }

    @Override
    public @Nullable Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        return saveCustomAndMetadata(registries);
    }
}
