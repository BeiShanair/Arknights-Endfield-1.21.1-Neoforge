package com.besson.endfield.network;

import com.besson.endfield.ArknightsEndField;
import com.besson.endfield.screen.custom.CrafterScreenHandler;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jetbrains.annotations.NotNull;

public class CycleRecipePacket implements CustomPacketPayload {
    public static final Type<CycleRecipePacket> TYPE = new Type<>(
            ResourceLocation.fromNamespaceAndPath(ArknightsEndField.MOD_ID, "cycle_recipe"));

    public static final StreamCodec<FriendlyByteBuf, CycleRecipePacket> CODEC = StreamCodec.of(
            (packet, buf) -> {}, buf -> new CycleRecipePacket());

    public static void handle(CycleRecipePacket packet, IPayloadContext ctx) {
        ctx.enqueueWork(() -> {
            var player = ctx.player();
            if (player != null && player.containerMenu instanceof CrafterScreenHandler screenHandler) {
                screenHandler.changeRecipe();
            }
        });
    }

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
