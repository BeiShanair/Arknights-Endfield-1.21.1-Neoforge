package com.besson.endfield.network;

import com.besson.endfield.ArknightsEndField;
import com.besson.endfield.util.storage.GlobalStorageManager;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class RequestItemPacket implements CustomPacketPayload {
    private final Item item;
    private final int amount;

    public static final Type<RequestItemPacket> TYPE = new Type<>(
            ResourceLocation.fromNamespaceAndPath(ArknightsEndField.MOD_ID, "request_item_packet"));
    public static final StreamCodec<FriendlyByteBuf, RequestItemPacket> CODEC =
            StreamCodec.of(RequestItemPacket::encode, RequestItemPacket::decode);

    public RequestItemPacket(Item item, int amount) {
        this.item = item;
        this.amount = amount;
    }

    public static RequestItemPacket decode(FriendlyByteBuf buf) {
        Item item = BuiltInRegistries.ITEM.get(buf.readResourceLocation());
        int amount = buf.readInt();
        return new RequestItemPacket(item, amount);
    }

    public static void encode(FriendlyByteBuf buf, RequestItemPacket msg) {
        buf.writeResourceLocation(BuiltInRegistries.ITEM.getKey(msg.item));
        buf.writeInt(msg.amount);
    }

    public static void handle(RequestItemPacket msg, IPayloadContext ctx) {
        ctx.enqueueWork(() -> {
            Player player = ctx.player();

            if (!(player instanceof ServerPlayer serverPlayer)) return;

            ServerLevel level = serverPlayer.serverLevel();
            ItemStack extracted = GlobalStorageManager.get(level).extract(msg.item, msg.amount);
            if (!serverPlayer.getInventory().add(extracted)) {
                serverPlayer.drop(extracted, false);
            }
        });
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
