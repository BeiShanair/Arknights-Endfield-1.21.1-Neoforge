package com.besson.endfield.blockEntity.custom.logicitis;

import com.besson.endfield.block.custom.logicitis.BeltBlock;
import com.besson.endfield.block.custom.logicitis.BeltShape;
import com.besson.endfield.blockEntity.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.Containers;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.items.IItemHandler;
import org.jetbrains.annotations.Nullable;

public class BeltBlockEntity extends BlockEntity {
    public ItemStack storedItem = ItemStack.EMPTY;

    public float progress = 0f;
    public float lastProgress = 0f;
    public static final float SPEED = 0.025f;
    public Direction travelDirection = null;

    public BeltBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.BELT.get(), pos, state);
    }

    public static void tick(Level world, BlockPos pos, BlockState state, BeltBlockEntity be) {
        if (world.isClientSide()) return;

        if (!be.storedItem.isEmpty() && be.travelDirection != null) {

            be.lastProgress = be.progress;
            be.progress += SPEED;

            if (be.progress >= 1f) {

                int pushResult = be.tryPushToStorage(world, pos, state);
                if (pushResult == 1) {
                    be.resetItem();
                    return;
                } else if (pushResult == 0) {
                    be.lastProgress = be.progress = 1f;
                    be.setChanged();
                    world.sendBlockUpdated(pos, be.getBlockState(), be.getBlockState(), 3);
                    return;
                }

                boolean moved = be.tryPushForward(world, pos, state);
                if (moved) {
                    be.progress = 0f;
                } else {
                    be.lastProgress = be.progress = 1f;
                    be.setChanged();
                    world.sendBlockUpdated(pos, be.getBlockState(), be.getBlockState(), 3);
                    return;
                }
            }
        }
        be.setChanged();
        world.sendBlockUpdated(pos, be.getBlockState(), be.getBlockState(), 3);
    }

    public void resetItem() {
        this.progress = 0f;
        this.lastProgress = 0f;
        this.storedItem = ItemStack.EMPTY;
        this.travelDirection = null;
    }

    private void sync() {
        if (level != null && !level.isClientSide) {
            setChanged();
            level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);
        }
    }
    
    private int tryPushToStorage(Level world, BlockPos pos, BlockState state) {
        if (this.storedItem.isEmpty()) return -1;

        BeltShape shape = state.getValue(BeltBlock.SHAPE);

        Direction next = BeltBlock.getNextDirection(shape, this.travelDirection);
        if (next == null) return -1;

        BlockPos outputPos = BeltBlock.getNextPos(pos, shape, next);

        BlockEntity be = level.getBlockEntity(outputPos);
        if (be == null) return -1;

        IItemHandler handler = world.getCapability(Capabilities.ItemHandler.BLOCK, outputPos, next.getOpposite());

        if (handler == null) return -1;
        
        ItemStack toInsert = this.storedItem.copy();
        toInsert.setCount(1);

        for (int i = 0; i < handler.getSlots(); i++) {
            ItemStack remainder = handler.insertItem(i, toInsert, true);

            if (remainder.isEmpty()) {
                handler.insertItem(i, toInsert, false);

                this.storedItem.shrink(1);
                this.travelDirection = next;

                setChanged();
                return 1;
            }
        }

        return 0;
    }
    public boolean canAcceptFrom(Direction from) {
        if (!this.storedItem.isEmpty()) return false;
        BeltShape shape = this.getBlockState().getValue(BeltBlock.SHAPE);
        return switch (from) {
            case NORTH ->
                    shape == BeltShape.NORTH_SOUTH || shape == BeltShape.NORTH_EAST || shape == BeltShape.NORTH_WEST;
            case SOUTH ->
                    shape == BeltShape.NORTH_SOUTH || shape == BeltShape.SOUTH_EAST || shape == BeltShape.SOUTH_WEST;
            case WEST ->
                    shape == BeltShape.EAST_WEST || shape == BeltShape.NORTH_WEST || shape == BeltShape.SOUTH_WEST;
            case EAST ->
                    shape == BeltShape.EAST_WEST || shape == BeltShape.NORTH_EAST || shape == BeltShape.SOUTH_EAST;
            default -> false;
        };
    }
    
    public void acceptItem(ItemStack stack, Direction from) {
        if (!this.storedItem.isEmpty()) return;
        this.storedItem = stack;
        this.progress = 0f;
        this.lastProgress = 0f;
        this.travelDirection = from;

        sync();
    }

    private boolean tryPushForward(Level world, BlockPos pos, BlockState state) {
        BeltShape shape = state.getValue(BeltBlock.SHAPE);

        Direction next = BeltBlock.getNextDirection(shape, this.travelDirection);
        if (next == null) {
            Containers.dropItemStack(world, pos.getX(), pos.getY(), pos.getZ(), this.storedItem);
            this.storedItem = ItemStack.EMPTY;
            return true;
        }

        BlockPos forwardPos = BeltBlock.getNextPos(pos, shape, next);

        BlockEntity forwardBE = world.getBlockEntity(forwardPos);

        if (forwardBE instanceof BeltBridgeBlockEntity bridge) {
            return bridge.tryPassThrough(world, forwardPos, next, this);
        }
        if (forwardBE instanceof SplitterBlockEntity splitter) {
            return splitter.tryDistribute(world, forwardPos, next, this);
        }
        if (forwardBE instanceof ConvergerBlockEntity converger) {
            return converger.tryMerge(world, forwardPos, next, this);
        }
        
        if (forwardBE instanceof BeltBlockEntity forwardBelt) {
            if (forwardBelt.storedItem.isEmpty()) {
                forwardBelt.storedItem = this.storedItem;
                forwardBelt.travelDirection = next.getOpposite();
                this.resetItem();
                return true;
            } else {
                return false;
            }
        } else if (world.getBlockEntity(forwardPos.below()) instanceof BeltBlockEntity b) {
            if (b.storedItem.isEmpty()) {
                b.storedItem = this.storedItem;
                b.travelDirection = next.getOpposite();
                this.resetItem();
                return true;
            } else {
                return false;
            }
        } else {
            Containers.dropItemStack(world, forwardPos.getX(), forwardPos.getY(), forwardPos.getZ(), this.storedItem);
            this.storedItem = ItemStack.EMPTY;
            return true;
        }
    }
    
    public ItemStack getStoredItem() {
        return storedItem;
    }
    
    public NonNullList<ItemStack> getItem() {
        NonNullList<ItemStack> list = NonNullList.create();
        list.add(this.storedItem);
        return list;
    }

    @Override
    protected void saveAdditional(CompoundTag nbt, HolderLookup.Provider registries) {
        super.saveAdditional(nbt, registries);
        nbt.putFloat("progress", progress);
        if (!this.storedItem.isEmpty()) {
            nbt.put("storedItem", this.storedItem.save(registries));
        }
        if (this.travelDirection != null) {
            nbt.putInt("travelDirection", this.travelDirection.get3DDataValue());
        }
    }

    @Override
    public void loadAdditional(CompoundTag nbt, HolderLookup.Provider registries) {
        super.loadAdditional(nbt, registries);
        this.progress = nbt.getFloat("progress");
        if (nbt.contains("storedItem")) {
            this.storedItem = ItemStack.parseOptional(registries, nbt.getCompound("storedItem"));
        } else {
            this.storedItem = ItemStack.EMPTY;
        }
        if (nbt.contains("travelDirection")) {
            this.travelDirection = Direction.from3DDataValue(nbt.getInt("travelDirection"));
        } else {
            this.travelDirection = null;
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

    public Direction getTravelDirection() {
        return this.travelDirection;
    }
}
