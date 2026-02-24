package com.besson.endfield.blockEntity.custom;

import com.besson.endfield.block.ElectrifiableDevice;
import com.besson.endfield.blockEntity.ModBlockEntities;
import com.besson.endfield.power.PowerNetworkManager;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.ArrayList;
import java.util.List;

public class ElectricPylonBlockEntity extends BlockEntity implements GeoBlockEntity {
    private BlockPos connectedNode;
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private boolean registeredToManager = false;

    // 新增：标记是否已完成首次节点检测（避免重复遍历）
    private boolean firstNodeDetectDone = false;
    // 新增：Tick 间隔（每20刻执行一次遍历，减少性能消耗）
    private static final int TICK_INTERVAL = 20;

    public ElectricPylonBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.ELECTRIC_PYLON.get(), pos, state);
    }

    // 1. 清理 setLevel()：仅保留电力网络注册，移除所有遍历/检测逻辑
    @Override
    public void setLevel(Level pLevel) {
        super.setLevel(pLevel);
        if (!registeredToManager && pLevel instanceof ServerLevel serverLevel) {
            PowerNetworkManager.get(serverLevel).registerConsumer(this.getBlockPos(), () -> {
                try {
                    return this.getSurroundingDemand();
                } catch (Throwable t) {
                    return 0;
                }
            }, (amount) -> {
                try {
                    this.distributeToSurroundings(amount);
                } catch (Throwable ignored) {
                }
            });
            registeredToManager = true;
        }
        // 移除所有方块遍历、节点检测、状态同步代码
    }

    // 将供电检测逻辑写到了Tick中
    public static void tick(Level level, BlockPos pos, BlockState state, ElectricPylonBlockEntity pylon) {
        if (level.isClientSide()) return;

        // 20tk检测一次是否有电
        if (level.getGameTime() % TICK_INTERVAL != 0) return;

        if (!pylon.firstNodeDetectDone) {
            pylon.detectConnectedNode(level, pos);
            pylon.firstNodeDetectDone = true;
            return;
        }
        if (pylon.connectedNode == null || level.getBlockEntity(pylon.connectedNode) == null) {
            pylon.detectConnectedNode(level, pos);
        }
    }

    // 3. 独立的节点检测方法（迁移自 setLevel()）
    private void detectConnectedNode(Level level, BlockPos pos) {
        BlockPos closest = null;
        double closestDist = Double.MAX_VALUE;
        // 优化：缩小遍历半径（30→15），减少性能消耗
        int searchRadius = 15;

        for (BlockPos p : BlockPos.betweenClosed(pos.offset(-searchRadius, -searchRadius, -searchRadius), pos.offset(searchRadius, searchRadius, searchRadius))) {
            if (p.equals(pos)) continue;

            BlockEntity candidate = level.getBlockEntity(p);
            if (candidate instanceof ProtocolAnchorCoreBlockEntity || candidate instanceof RelayTowerBlockEntity) {
                double d = pos.distSqr(p);
                if (d < closestDist) {
                    closest = p.immutable();
                    closestDist = d;
                }
            }
        }

        // 更新节点并同步状态
        this.connectedNode = closest;
        this.setChanged();
        level.sendBlockUpdated(pos, this.getBlockState(), this.getBlockState(), 3);
    }

    // 4. 保留原有电力分配逻辑（优化遍历范围）
    private void distributeToSurroundings(Integer amount) {
        if (level == null || amount <= 0) return;
        List<ElectrifiableDevice> devices = new ArrayList<>();
        // 优化：缩小遍历范围（10→8），减少 getBlockEntity() 调用
        int range = 8;
        for (BlockPos target : BlockPos.betweenClosed(getBlockPos().offset(-range, -range, -range), getBlockPos().offset(range, range, range))) {
            BlockEntity be = level.getBlockEntity(target);
            if (be instanceof ElectrifiableDevice device && device.needsPower()) {
                devices.add(device);
            }
        }
        if (devices.isEmpty()) return;

        devices.sort((a, b) -> Integer.compare(b.getRequiredPower(), a.getRequiredPower()));

        for (ElectrifiableDevice device : devices) {
            if (amount <= 0) break;
            int required = device.getRequiredPower();
            int toGive = Math.min(required, amount);
            device.receiveElectricCharge(toGive);
            amount -= toGive;
        }
    }

    // 5. 保留原有电力需求计算逻辑（同步优化遍历范围）
    private Integer getSurroundingDemand() {
        if (level == null) return 0;
        int totalDemand = 0;
        int range = 8;
        for (BlockPos target : BlockPos.betweenClosed(getBlockPos().offset(-range, -range, -range), getBlockPos().offset(range, range, range))) {
            BlockEntity be = level.getBlockEntity(target);
            if (be instanceof ElectrifiableDevice device && device.needsPower()) {
                totalDemand += device.getRequiredPower();
            }
        }
        return totalDemand;
    }

    @Override
    public void setRemoved() {
        if (level instanceof ServerLevel serverLevel) {
            PowerNetworkManager.get(serverLevel).unregisterConsumer(this.getBlockPos());
        }
        super.setRemoved();
    }

    public BlockPos getConnectedNode() {
        return connectedNode;
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        if (connectedNode != null) {
            tag.putLong("connectedNode", connectedNode.asLong());
        }
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        if (tag.contains("connectedNode")) {
            connectedNode = BlockPos.of(tag.getLong("connectedNode"));
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

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "controller", 0,
                state -> state.setAndContinue(RawAnimation.begin().thenLoop("working"))));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }
}