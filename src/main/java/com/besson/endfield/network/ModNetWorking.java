package com.besson.endfield.network;

import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;

public class ModNetWorking {
    private static final String PROTOCOL = "1.0";

    public static void register(RegisterPayloadHandlersEvent event) {
        var registry = event.registrar(PROTOCOL);
        registry.playToServer(CycleRecipePacket.TYPE, CycleRecipePacket.CODEC, CycleRecipePacket::handle);
    }

}
