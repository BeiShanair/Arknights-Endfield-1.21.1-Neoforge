package com.besson.endfield.network;

import com.besson.endfield.ArknightsEndField;
import com.besson.endfield.screen.custom.logicitis.StorageScreenHandler;
import com.besson.endfield.util.storage.StorageEntry;
import net.minecraft.client.Minecraft;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SyncStoragePacket implements CustomPacketPayload {
    private final List<StorageEntry> entries;
    public static final Type<SyncStoragePacket> TYPE = new Type<>(
            ResourceLocation.fromNamespaceAndPath(ArknightsEndField.MOD_ID, "sync_storage_packet"));

    public static final StreamCodec<FriendlyByteBuf, SyncStoragePacket> CODEC =
            StreamCodec.of(SyncStoragePacket::encode, SyncStoragePacket::decode);

    public SyncStoragePacket(Collection<StorageEntry> entries) {
        this.entries = new ArrayList<>(entries);
    }
    
    public static SyncStoragePacket decode(FriendlyByteBuf buf) {
        int size = buf.readInt();
        List<StorageEntry> entries = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            var itemId = buf.readResourceLocation();
            var item = BuiltInRegistries.ITEM.get(itemId);
            long count = buf.readLong();

            entries.add(new StorageEntry(item, count, 0));
        }
        return new SyncStoragePacket(entries);
    }
    
    public static void encode(FriendlyByteBuf buf, SyncStoragePacket msg) {
        buf.writeInt(msg.entries.size());

        for (StorageEntry entry : msg.entries) {
            buf.writeResourceLocation(
                    BuiltInRegistries.ITEM.getKey(entry.getItem())
            );
            buf.writeLong(entry.getCount());
        }
    }
    
    public static void handle(SyncStoragePacket msg, IPayloadContext ctx) {
        ctx.enqueueWork(() -> {
            Minecraft client = Minecraft.getInstance();

            if (client.player != null &&
                    client.player.containerMenu instanceof StorageScreenHandler screenHandler) {

                screenHandler.updateEntries(msg.entries);
            }
        });

    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
