package com.besson.endfield.block.custom;

import com.besson.endfield.block.ModBlockEntityWithFacing;
import com.besson.endfield.block.ModBlocks;
import com.besson.endfield.blockEntity.ModBlockEntities;
import com.besson.endfield.blockEntity.custom.ProtocolAnchorCoreBlockEntity;
import com.besson.endfield.blockEntity.custom.ThermalBankBlockEntity;
import com.besson.endfield.blockEntity.custom.ThermalBankSideBlockEntity;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public class ThermalBankBlock extends ModBlockEntityWithFacing {
    private static final MapCodec<ThermalBankBlock> CODEC = simpleCodec(ThermalBankBlock::new);
    public ThermalBankBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new ThermalBankBlockEntity(pPos, pState);
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        return createTickerHelper(pBlockEntityType, ModBlockEntities.THERMAL_BANK.get(),
                (world1, pos, state1, blockEntity) ->
                        ThermalBankBlockEntity.tick(world1, pos, state1, (ThermalBankBlockEntity) blockEntity));
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (!level.isClientSide()) {
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity instanceof ThermalBankBlockEntity be) {
                player.openMenu(be, pos);
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.CONSUME;
    }

    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pMovedByPiston) {
        if (!pLevel.isClientSide() && pState.getBlock() != pNewState.getBlock()) {
            BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
            if (blockEntity instanceof ThermalBankBlockEntity bank) {
                Containers.dropContents(pLevel, pPos, bank.getItems());
                pLevel.updateNeighbourForOutputSignal(pPos, this);
            }
            Direction facing = pState.getValue(FACING);
            Direction right = facing.getClockWise();
            Direction back = facing.getOpposite();
            Direction backRight = back.getCounterClockWise();

            BlockPos[] positionsToCheck = {
                pPos.relative(right),
                pPos.relative(back),
                pPos.relative(back).relative(backRight)
            };
            for (BlockPos checkPos : positionsToCheck) {
                if (pLevel.getBlockState(checkPos).getBlock() == ModBlocks.THERMAL_BANK_SIDE.get()) {
                    pLevel.destroyBlock(checkPos, false);
                }
            }
            super.onRemove(pState, pLevel, pPos, pNewState, pMovedByPiston);
        }
    }

    @Override
    public void setPlacedBy(Level pLevel, BlockPos pPos, BlockState pState, @Nullable LivingEntity pPlacer, ItemStack pStack) {
        if (!pLevel.isClientSide()) {
            Direction facing = pState.getValue(FACING);
            Direction right = facing.getClockWise();
            Direction back = facing.getOpposite();
            Direction backRight = back.getCounterClockWise();

            BlockPos[] positionsToCheck = {
                pPos.relative(right),
                pPos.relative(back),
                pPos.relative(back).relative(backRight)
            };
            for (BlockPos checkPos : positionsToCheck) {
                pLevel.setBlockAndUpdate(checkPos, ModBlocks.THERMAL_BANK_SIDE.get().defaultBlockState().setValue(FACING, pState.getValue(FACING)));
                BlockEntity entity = pLevel.getBlockEntity(checkPos);
                if (entity instanceof ThermalBankSideBlockEntity entity1) {
                    entity1.setParentPos(pPos);
                }
            }
        }
    }
}
