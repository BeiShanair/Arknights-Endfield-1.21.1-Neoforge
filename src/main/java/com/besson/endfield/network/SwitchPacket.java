package com.besson.endfield.network;

import com.besson.endfield.ArknightsEndField;
import com.besson.endfield.blockEntity.custom.*;
import com.besson.endfield.blockEntity.custom.logicitis.ProtocolStashBlockEntity;
import com.besson.endfield.blockEntity.custom.powering.ThermalBankBlockEntity;
import com.besson.endfield.blockEntity.custom.resourcing.BaseRigBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class SwitchPacket implements CustomPacketPayload {
    private final BlockPos pos;
    private final boolean enable;
    public static final Type<SwitchPacket> TYPE = new Type<>(
            ResourceLocation.fromNamespaceAndPath(ArknightsEndField.MOD_ID, "switch_packet"));

    public static final StreamCodec<FriendlyByteBuf, SwitchPacket> CODEC = StreamCodec.of(SwitchPacket::encode, SwitchPacket::decode);
    public SwitchPacket(BlockPos pos, boolean enable) {
        this.pos = pos;
        this.enable = enable;
    }

    public static void encode(FriendlyByteBuf buf, SwitchPacket msg) {
        buf.writeBlockPos(msg.pos);
        buf.writeBoolean(msg.enable);
    }

    public static SwitchPacket decode(FriendlyByteBuf buf) {
        return new SwitchPacket(
                buf.readBlockPos(),
                buf.readBoolean()
        );
    }
    
    public static void handle(SwitchPacket msg, IPayloadContext ctx) {
        ctx.enqueueWork(() -> {
            Player player = ctx.player();

            if (!(player instanceof ServerPlayer serverPlayer)) return;

            BlockEntity be = serverPlayer.level().getBlockEntity(msg.pos);
            if (be instanceof BaseRigBlockEntity<?> rig) {
                rig.setEnable(msg.enable);
            }
            if (be instanceof ThermalBankBlockEntity tb) {
                tb.setEnable(msg.enable);
            }
            if (be instanceof BaseIOBlockEntity<?> b) {
                b.setEnable(msg.enable);
            }
            if (be instanceof ProtocolStashBlockEntity ps) {
                ps.setEnable(msg.enable);
            }
        });
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
