package com.besson.endfield.blockEntity.custom.logicitis;

import com.besson.endfield.block.ModBlockEntityWithFacing;
import com.besson.endfield.blockEntity.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.items.IItemHandler;
import org.jetbrains.annotations.Nullable;

public class ProtocolStashSideBlockEntity extends BlockEntity {
    private BlockPos parentPos;

    public ProtocolStashSideBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.PROTOCOL_STASH_SIDE.get(), pos, state);
    }

    public void setParentPos(BlockPos parentPos) {
        this.parentPos = parentPos;
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
    public @Nullable Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        return saveWithFullMetadata(registries);
    }


    @Nullable
    public ProtocolStashBlockEntity getParentBlock() {
        if (parentPos == null || level == null) return null;
        BlockEntity entity = this.level.getBlockEntity(parentPos);
        if (entity instanceof ProtocolStashBlockEntity parent) {
            return parent;
        }

        return null;
    }


    public static void tick(Level world, BlockPos pos, BlockState state, ProtocolStashSideBlockEntity be) {
        if (world.isClientSide()) return;

        ProtocolStashBlockEntity parent = be.getParentBlock();
        if (parent != null) {
            Direction facing = state.getValue(ModBlockEntityWithFacing.FACING);
            BlockEntity machineBe = world.getBlockEntity(pos);
            if (machineBe == null) return;
            IItemHandler handler =
                    world.getCapability(Capabilities.ItemHandler.BLOCK, pos, facing.getOpposite());

            if (handler == null) return;

            BlockPos beltPos = pos.relative(facing.getOpposite());
            BlockEntity targetBe = world.getBlockEntity(beltPos);

            if (!(targetBe instanceof BeltBlockEntity belt)) return;

            if (!belt.canAcceptFrom(facing)) return;

            for (int i = 0; i < handler.getSlots(); i++) {

                ItemStack simulated = handler.extractItem(i, 1, true);

                if (!simulated.isEmpty()) {

                    ItemStack extracted = handler.extractItem(i, 1, false);

                    if (!extracted.isEmpty()) {
                        belt.acceptItem(extracted, facing);
                        return;
                    }
                }
            }
        }
    }
}
