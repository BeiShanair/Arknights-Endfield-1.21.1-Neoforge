package com.besson.endfield.network;

import com.besson.endfield.ArknightsEndField;
import com.besson.endfield.screen.custom.CrafterScreenHandler;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jetbrains.annotations.NotNull;

public class CycleRecipePacket implements CustomPacketPayload {
    public static final Type<CycleRecipePacket> TYPE = new Type<>(
            ResourceLocation.fromNamespaceAndPath(ArknightsEndField.MOD_ID, "cycle_recipe"));

    public static final StreamCodec<FriendlyByteBuf, CycleRecipePacket> CODEC = StreamCodec.of(
            (packet, buf) -> {}, buf -> new CycleRecipePacket());

    public static void handle(CycleRecipePacket msg, IPayloadContext ctx) {
        ctx.enqueueWork(() -> {
            Player player = ctx.player();

            if (!(player instanceof ServerPlayer serverPlayer)) return;

            if (serverPlayer != null && serverPlayer.containerMenu instanceof CrafterScreenHandler screenHandler) {
                screenHandler.changeRecipe();
            }
        });
    }

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
