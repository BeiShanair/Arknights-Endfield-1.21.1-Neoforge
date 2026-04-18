package com.besson.endfield.util;

import com.besson.endfield.network.OpenStoragePacket;
import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;

import java.util.Objects;

@EventBusSubscriber(value = Dist.CLIENT)
public class KeyInputHandler {
    
    @SubscribeEvent
    public static void onTick(ClientTickEvent.Post event) {
        while (ModKeyBindings.OPEN_STORAGE.consumeClick()) {
            sendOpenPacket();
        }
    }

    private static void sendOpenPacket() {
        Objects.requireNonNull(Minecraft.getInstance().getConnection()).send(new OpenStoragePacket());
    }
}
