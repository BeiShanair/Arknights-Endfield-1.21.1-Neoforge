package com.besson.endfield.block.custom;

import com.besson.endfield.block.ModBlockEntityWithFacing;
import com.besson.endfield.block.ModBlocks;
import com.besson.endfield.blockEntity.custom.FillingUnitBlockEntity;
import com.besson.endfield.blockEntity.custom.FillingUnitSideBlockEntity;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.Nullable;

public class FillingUnitSideBlock extends ModBlockEntityWithFacing {
    private static final MapCodec<FillingUnitSideBlock> CODEC = simpleCodec(FillingUnitSideBlock::new);
    public FillingUnitSideBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new FillingUnitSideBlockEntity(pPos, pState);
    }

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.INVISIBLE;
    }

    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pMovedByPiston) {
        if (!pLevel.isClientSide() && pState.getBlock() != pNewState.getBlock()) {
            BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
            if (blockEntity instanceof FillingUnitSideBlockEntity sideBlockEntity) {
                FillingUnitBlockEntity parent = sideBlockEntity.getParentBlock();
                if (parent != null) {
                    BlockPos parentPos = parent.getBlockPos();
                    pLevel.destroyBlock(parentPos, true);
                }
            }
        }
        super.onRemove(pState, pLevel, pPos, pNewState, pMovedByPiston);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (!level.isClientSide()) {
            BlockEntity entity = level.getBlockEntity(pos);
            if (entity instanceof FillingUnitSideBlockEntity entity1) {
                FillingUnitBlockEntity parent = entity1.getParentBlock();
                if (parent != null) {
                    player.openMenu(parent, parent.getBlockPos());
                    return InteractionResult.SUCCESS;
                }
            }
        }
        return InteractionResult.CONSUME;
    }

    @Override
    public ItemStack getCloneItemStack(BlockState state, HitResult target, LevelReader level, BlockPos pos, Player player) {
        return  new ItemStack(ModBlocks.FILLING_UNIT.get());
    }
}
