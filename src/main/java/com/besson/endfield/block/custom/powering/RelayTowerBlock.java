package com.besson.endfield.block.custom.powering;

import com.besson.endfield.block.ModBlockEntityWithFacing;
import com.besson.endfield.blockEntity.ModBlockEntities;
import com.besson.endfield.blockEntity.custom.powering.RelayTowerBlockEntity;
import com.besson.endfield.util.power.PowerNetworkManager;
import com.mojang.serialization.MapCodec;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public class RelayTowerBlock extends ModBlockEntityWithFacing {
    private static final MapCodec<RelayTowerBlock> CODEC = simpleCodec(RelayTowerBlock::new);
    public RelayTowerBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new RelayTowerBlockEntity(pPos, pState);
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        return createTickerHelper(pBlockEntityType, ModBlockEntities.RELAY_TOWER.get(), RelayTowerBlockEntity::tick);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (!level.isClientSide()) {
//            Item item = pPlayer.getItemInHand(pHand).getItem();
//            if (item instanceof ControlItem) return super.use(pState, pLevel, pPos, pPlayer, pHand, pHit);
            PowerNetworkManager manager = PowerNetworkManager.get((ServerLevel) level);
            if (player != null) {
                player.sendSystemMessage(Component.literal("----------------------"));
                player.sendSystemMessage(Component.translatable("electric_pylon.title"));
                player.sendSystemMessage(Component.translatable("electric_pylon.total_generated", manager.getLastTotalGenerated()).withStyle(ChatFormatting.GREEN));
                player.sendSystemMessage(Component.translatable("electric_pylon.total_demand", manager.getLastTotalDemand()).withStyle(ChatFormatting.RED));
                player.sendSystemMessage(Component.translatable("electric_pylon.stored_power", manager.getCurrentStoredEnergy()).withStyle(ChatFormatting.YELLOW));
                player.sendSystemMessage(Component.literal("----------------------"));
                return InteractionResult.SUCCESS;
            }
            return InteractionResult.CONSUME;
        }

        return InteractionResult.CONSUME;
    }
}
