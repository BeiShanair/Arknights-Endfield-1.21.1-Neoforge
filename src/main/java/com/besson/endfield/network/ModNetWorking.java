package com.besson.endfield.network;

import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;

public class ModNetWorking {
    private static final String PROTOCOL = "1.0";

    public static void register(RegisterPayloadHandlersEvent event) {
        var registry = event.registrar(PROTOCOL);
        registry.playToServer(CycleRecipePacket.TYPE, CycleRecipePacket.CODEC, CycleRecipePacket::handle);
        registry.playToServer(SwitchPacket.TYPE, SwitchPacket.CODEC, SwitchPacket::handle);
        registry.playToServer(OpenStoragePacket.TYPE, OpenStoragePacket.CODEC, OpenStoragePacket::handle);
        registry.playToServer(RequestItemPacket.TYPE, RequestItemPacket.CODEC, RequestItemPacket::handle);
        registry.playToClient(SyncStoragePacket.TYPE, SyncStoragePacket.CODEC, SyncStoragePacket::handle);
    }

}
