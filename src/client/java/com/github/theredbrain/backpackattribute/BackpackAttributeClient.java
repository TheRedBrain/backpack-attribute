package com.github.theredbrain.backpackattribute;

import com.github.theredbrain.backpackattribute.compat.InventorySizeAttributesClientCompat;
import com.github.theredbrain.backpackattribute.gui.screen.ingame.BackpackScreen;
import com.github.theredbrain.backpackattribute.network.packet.OpenBackpackScreenPacket;
import com.github.theredbrain.backpackattribute.registry.KeyBindingsRegistry;
import com.github.theredbrain.backpackattribute.registry.ScreenHandlerTypesRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.HandledScreens;

public class BackpackAttributeClient implements ClientModInitializer {

	@Override
	public void onInitializeClient() {
		// Registry
		KeyBindingsRegistry.registerKeyBindings();
		HandledScreens.register(ScreenHandlerTypesRegistry.BACKPACK_SCREEN_HANDLER, BackpackScreen::new);
	}

	public static boolean showInactiveInventorySlots() {
		return !BackpackAttribute.isInventorySizeAttributesLoaded || InventorySizeAttributesClientCompat.showInactiveInventorySlots();
	}

	public static void openBackpackScreen(MinecraftClient client) {
		if (client.player != null) {
			ClientPlayNetworking.send(new OpenBackpackScreenPacket());
		}
	}
}