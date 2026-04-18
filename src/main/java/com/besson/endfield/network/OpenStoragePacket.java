package com.besson.endfield.network;

import com.besson.endfield.ArknightsEndField;
import com.besson.endfield.screen.custom.logicitis.StorageScreenHandlerFactory;
import com.besson.endfield.util.storage.GlobalStorageManager;
import com.besson.endfield.util.storage.StorageEntry;
import com.besson.endfield.util.storage.StorageState;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.ArrayList;
import java.util.List;

public class OpenStoragePacket implements CustomPacketPayload {
    public static final Type<OpenStoragePacket> TYPE = new Type<>(
            ResourceLocation.fromNamespaceAndPath(ArknightsEndField.MOD_ID, "open_storage_packet"));
    public static final StreamCodec<FriendlyByteBuf, OpenStoragePacket> CODEC = StreamCodec.of(
            (packet, buf) -> {}, buf -> new OpenStoragePacket());

    public static void handle(OpenStoragePacket msg, IPayloadContext ctx) {
        ctx.enqueueWork(() -> {
            if (!(ctx.player() instanceof ServerPlayer player)) return;

            player.openMenu(new StorageScreenHandlerFactory());

            ServerLevel level = player.serverLevel();
            StorageState state = GlobalStorageManager.get(level).getState();

            List<StorageEntry> entries = new ArrayList<>(state.getStorage().values());

            player.connection.send(new SyncStoragePacket(entries));

        });
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
