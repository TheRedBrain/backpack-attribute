package com.github.theredbrain.backpackattribute;

import com.github.theredbrain.backpackattribute.gui.screen.ingame.BackpackScreen;
import com.github.theredbrain.backpackattribute.registry.KeyBindingsRegistry;
import com.github.theredbrain.backpackattribute.registry.ScreenHandlerTypesRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.gui.screen.ingame.HandledScreens;

public class BackpackAttributeClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {

        // Packets
        ClientPlayNetworking.registerGlobalReceiver(BackpackAttribute.ServerConfigSyncPacket.PACKET_ID, (payload, context) -> {
            BackpackAttribute.serverConfig = payload.serverConfig();
        });

        // Registry
        KeyBindingsRegistry.registerKeyBindings();
        HandledScreens.register(ScreenHandlerTypesRegistry.BACKPACK_SCREEN_HANDLER, BackpackScreen::new);
    }
}