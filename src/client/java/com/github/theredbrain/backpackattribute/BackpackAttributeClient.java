package com.github.theredbrain.backpackattribute;

import com.github.theredbrain.backpackattribute.compat.InventorySizeAttributesClientCompat;
import com.github.theredbrain.backpackattribute.config.ClientConfig;
import com.github.theredbrain.backpackattribute.gui.screen.ingame.BackpackScreen;
import com.github.theredbrain.backpackattribute.network.packet.OpenBackpackScreenPacket;
import com.github.theredbrain.backpackattribute.registry.KeyBindingsRegistry;
import com.github.theredbrain.backpackattribute.registry.MenuTypesRegistry;
import me.fzzyhmstrs.fzzy_config.api.ConfigApiJava;
import me.fzzyhmstrs.fzzy_config.api.RegisterType;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.MenuScreens;

public class BackpackAttributeClient implements ClientModInitializer {
	public static ClientConfig CLIENT_CONFIG;

	@Override
	public void onInitializeClient() {
		CLIENT_CONFIG = ConfigApiJava.registerAndLoadConfig(ClientConfig::new, RegisterType.CLIENT);

		// Registry
		KeyBindingsRegistry.registerKeyBindings();
		MenuScreens.register(MenuTypesRegistry.BACKPACK_MENU, BackpackScreen::new);
	}

	public static boolean showInactiveInventorySlots() {
		return !BackpackAttribute.isInventorySizeAttributesLoaded || InventorySizeAttributesClientCompat.showInactiveInventorySlots();
	}

	public static void openBackpackScreen(Minecraft client) {
		if (client.player != null) {
			ClientPlayNetworking.send(new OpenBackpackScreenPacket());
		}
	}
}