package com.github.theredbrain.backpackattribute;

import com.github.theredbrain.backpackattribute.config.ClientConfig;
import com.github.theredbrain.backpackattribute.config.ClientConfigWrapper;
import com.github.theredbrain.backpackattribute.gui.screen.ingame.BackpackScreen;
import com.github.theredbrain.backpackattribute.registry.KeyBindingsRegistry;
import com.github.theredbrain.backpackattribute.registry.ScreenHandlerTypesRegistry;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import me.shedaniel.autoconfig.serializer.PartitioningSerializer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.gui.screen.ingame.HandledScreens;

public class BackpackAttributeClient implements ClientModInitializer {
    public static ClientConfig clientConfig;

    @Override
    public void onInitializeClient() {
        // Config
        AutoConfig.register(ClientConfigWrapper.class, PartitioningSerializer.wrap(JanksonConfigSerializer::new));
        clientConfig = ((ClientConfigWrapper) AutoConfig.getConfigHolder(ClientConfigWrapper.class).getConfig()).client;

        // Packets
        ClientPlayNetworking.registerGlobalReceiver(BackpackAttribute.ServerConfigSyncPacket.PACKET_ID, (payload, context) -> {
            BackpackAttribute.serverConfig = payload.serverConfig();
        });

        // Registry
        KeyBindingsRegistry.registerKeyBindings();
        HandledScreens.register(ScreenHandlerTypesRegistry.BACKPACK_SCREEN_HANDLER, BackpackScreen::new);
    }
}