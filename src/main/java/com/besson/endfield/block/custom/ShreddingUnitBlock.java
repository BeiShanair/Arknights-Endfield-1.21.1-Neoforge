package com.besson.endfield.block.custom;

import com.besson.endfield.block.ModBlockEntityWithFacing;
import com.besson.endfield.block.ModBlocks;
import com.besson.endfield.blockEntity.ModBlockEntities;
import com.besson.endfield.blockEntity.custom.ShreddingUnitBlockEntity;
import com.besson.endfield.blockEntity.custom.ShreddingUnitSideBlockEntity;
import com.mojang.serialization.MapCodec;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ShreddingUnitBlock extends ModBlockEntityWithFacing {
    private static final MapCodec<ShreddingUnitBlock> CODEC = simpleCodec(ShreddingUnitBlock::new);
    public ShreddingUnitBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new ShreddingUnitBlockEntity(pos, state);
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        return createTickerHelper(pBlockEntityType, ModBlockEntities.SHREDDING_UNIT.get(), ShreddingUnitBlockEntity::tick);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (!level.isClientSide()) {
            BlockEntity entity = level.getBlockEntity(pos);
            if (entity instanceof ShreddingUnitBlockEntity be) {
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
            if (blockEntity instanceof ShreddingUnitBlockEntity) {
                Containers.dropContents(pLevel, pPos, ((ShreddingUnitBlockEntity) blockEntity).getItems());
                pLevel.updateNeighborsAt(pPos, this);
            }
            BlockPos[] adjacentPositions = getAdjacentPositions(pState, pPos);

            for (BlockPos p : adjacentPositions) {
                if (pLevel.getBlockState(p).getBlock() == ModBlocks.SHREDDING_UNIT_SIDE.get()) {
                    pLevel.destroyBlock(p, false);
                }
            }
            
            super.onRemove(pState, pLevel, pPos, pNewState, pMovedByPiston);
        }
    }

    @Override
    public void setPlacedBy(Level pLevel, BlockPos pPos, BlockState pState, @Nullable LivingEntity pPlacer, ItemStack pStack) {
        if (!pLevel.isClientSide()) {
            BlockPos[] adjacentPositions = getAdjacentPositions(pState, pPos);

            for (BlockPos p : adjacentPositions) {
                pLevel.setBlockAndUpdate(p, ModBlocks.SHREDDING_UNIT_SIDE.get().defaultBlockState().setValue(FACING, pState.getValue(FACING)));
                BlockEntity be = pLevel.getBlockEntity(p);
                if (be instanceof ShreddingUnitSideBlockEntity adjunct) {
                    adjunct.setParentPos(pPos);
                }
            }
        }
    }
    
    @Override
    public boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos) {
        if (!pLevel.isClientSide()) {
            BlockPos[] sidePositions = getAdjacentPositions(pState, pPos);

            for (BlockPos p : sidePositions) {
                if (!pLevel.getBlockState(p).getBlock().defaultBlockState().isAir()) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    private BlockPos[] getAdjacentPositions(BlockState state, BlockPos pPos) {
        Direction facing = state.getValue(FACING);
        Direction left = facing.getCounterClockWise();
        Direction right = facing.getClockWise();
        Direction back = facing.getOpposite();
        Direction backLeft = back.getClockWise();
        Direction backRight = back.getCounterClockWise();

        return new BlockPos[]{
                pPos.relative(facing), pPos.relative(facing).relative(left),
                pPos.relative(right), pPos.relative(left),
                pPos.relative(facing).relative(right), pPos.relative(back),
                pPos.relative(back).relative(backLeft), pPos.relative(back).relative(backRight)
        };
    }

    @Override
    public void appendHoverText(ItemStack pStack, Item.TooltipContext context, List<Component> pTooltip, TooltipFlag pFlag) {
        pTooltip.add(Component.translatable("endfield.powerCost", 5).withStyle(ChatFormatting.GRAY));
    }
}
