package com.besson.endfield.blockEntity.custom;

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
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.items.IItemHandler;
import org.jetbrains.annotations.Nullable;

import static com.besson.endfield.block.ModBlockEntityWithFacing.FACING;

public abstract class BaseIOSideBlockEntity extends BlockEntity {
    protected BlockPos parentPos;
    public BaseIOSideBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public void setParentPos(BlockPos parentPos) {
        this.parentPos = parentPos;
        setChanged();
    }

    protected void saveAdditional(CompoundTag nbt, HolderLookup.Provider registries) {
        super.saveAdditional(nbt, registries);
        if (parentPos != null) {
            nbt.putLong("parent", parentPos.asLong());
        }
    }

    @Override
    public void loadAdditional(CompoundTag nbt, HolderLookup.Provider registries) {
        super.loadAdditional(nbt, registries);
        if (nbt.contains("parent")) {
            parentPos = BlockPos.of(nbt.getLong("parent"));
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

    public abstract @Nullable BaseIOBlockEntity<?> getParentBlock();

    public static <T extends BaseIOSideBlockEntity> void tick(Level world, BlockPos pos, BlockState state, T be) {
        if (world.isClientSide()) return;

        BaseIOBlockEntity<?> parent = be.getParentBlock();
        if (parent != null) {
            Direction facing = state.getValue(FACING);
            BlockEntity machineBe = world.getBlockEntity(pos);
            if (machineBe == null) return;

            IItemHandler handler = world.getCapability(Capabilities.ItemHandler.BLOCK, pos, facing);

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
