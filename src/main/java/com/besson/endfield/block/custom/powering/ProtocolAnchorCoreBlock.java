package com.besson.endfield.block.custom.powering;

import com.besson.endfield.block.ModBlockEntityWithFacing;
import com.besson.endfield.block.ModBlocks;
import com.besson.endfield.blockEntity.ModBlockEntities;
import com.besson.endfield.blockEntity.custom.powering.ProtocolAnchorCoreBlockEntity;
import com.mojang.serialization.MapCodec;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
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
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ProtocolAnchorCoreBlock extends ModBlockEntityWithFacing {
    private static final MapCodec<ProtocolAnchorCoreBlock> CODEC = simpleCodec(ProtocolAnchorCoreBlock::new);
    public ProtocolAnchorCoreBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new ProtocolAnchorCoreBlockEntity(pPos, pState);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (!level.isClientSide()) {
            BlockEntity entity = level.getBlockEntity(pos);
            if (entity instanceof ProtocolAnchorCoreBlockEntity be) {
                player.openMenu(be, be::writeScreenData);
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.CONSUME;
    }

    @Override
    protected RenderShape getRenderShape(BlockState state) {
        return RenderShape.ENTITYBLOCK_ANIMATED;
    }

    @Override
    public void setPlacedBy(Level pLevel, BlockPos pPos, BlockState pState, @Nullable LivingEntity pPlacer, ItemStack pStack) {
        if (!pLevel.isClientSide()) {
            for (BlockPos p: BlockPos.betweenClosed(pPos.offset(4, 0, 4), pPos.offset(-4, 0, -4))) {
                BlockState checkState = pLevel.getBlockState(p);
                if (checkState.is(this)) {
                    continue;
                }
                pLevel.setBlockAndUpdate(p, ModBlocks.PROTOCOL_ANCHOR_CORE_SIDE.get().defaultBlockState());
            }
        }
    }

    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pMovedByPiston) {
        if (!pLevel.isClientSide() && pState.getBlock() != pNewState.getBlock()) {
            BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
            if (blockEntity instanceof ProtocolAnchorCoreBlockEntity entity) {
                Containers.dropContents(pLevel, pPos, entity.getItems());
                pLevel.updateNeighbourForOutputSignal(pPos, this);
            }

            for (BlockPos p: BlockPos.betweenClosed(pPos.offset(4, 0, 4), pPos.offset(-4, 0, -4))) {
                BlockState checkState = pLevel.getBlockState(p);
                if (checkState.is(ModBlocks.PROTOCOL_ANCHOR_CORE_SIDE.get())) {
                    pLevel.destroyBlock(p, false);
                }
            }
        }
        super.onRemove(pState, pLevel, pPos, pNewState, pMovedByPiston);
    }
    
    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        return createTickerHelper(pBlockEntityType, ModBlockEntities.PROTOCOL_ANCHOR_CORE.get(), ProtocolAnchorCoreBlockEntity::tick);
    }

    @Override
    public boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos) {
        if (!pLevel.isClientSide()) {
            for (BlockPos p : BlockPos.betweenClosed(pPos.offset(4, 0, 4), pPos.offset(-4, 0, -4))) {
                if (!pLevel.getBlockState(p).getBlock().defaultBlockState().isAir()) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public void appendHoverText(ItemStack pStack, Item.TooltipContext context, List<Component> pTooltip, TooltipFlag pFlag) {
        pTooltip.add(Component.translatable("endfield.protocolAnchorCoreTooltip").withStyle(ChatFormatting.RED));
        pTooltip.add(Component.translatable("endfield.protocolAnchorCoreTooltip2").withStyle(ChatFormatting.GRAY));
    }
}
