package com.github.theredbrain.backpackattribute;

import com.github.theredbrain.backpackattribute.compat.InventorySizeAttributesCompat;
import com.github.theredbrain.backpackattribute.config.ServerConfig;
import com.github.theredbrain.backpackattribute.network.packet.OpenBackpackScreenPacket;
import com.github.theredbrain.backpackattribute.network.packet.OpenBackpackScreenPacketReceiver;
import com.github.theredbrain.backpackattribute.registry.DataAttachmentRegistry;
import com.github.theredbrain.backpackattribute.registry.MenuTypesRegistry;
import me.fzzyhmstrs.fzzy_config.api.ConfigApiJava;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.Holder;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.player.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BackpackAttribute implements ModInitializer {
	public static final String MOD_ID = "backpackattribute";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static ServerConfig SERVER_CONFIG;

	public static Holder<Attribute> BACKPACK_CAPACITY;

	public static final boolean isInventorySizeAttributesLoaded = FabricLoader.getInstance().isModLoaded("inventorysizeattributes");

	public static int getActiveInventorySize(Player player) {
		return isInventorySizeAttributesLoaded ? InventorySizeAttributesCompat.getActiveInventorySize(player) : 27;
	}

	public static int getActiveHotbarSize(Player player) {
		return isInventorySizeAttributesLoaded ? InventorySizeAttributesCompat.getActiveHotbarSize(player) : 9;
	}

	@Override
	public void onInitialize() {
		LOGGER.info("Your backpack can be attributed to an attribute!");
		SERVER_CONFIG = ConfigApiJava.registerAndLoadConfig(ServerConfig::new);

		// Events
		PayloadTypeRegistry.playC2S().register(OpenBackpackScreenPacket.PACKET_ID, OpenBackpackScreenPacket.PACKET_CODEC);
		ServerPlayNetworking.registerGlobalReceiver(OpenBackpackScreenPacket.PACKET_ID, new OpenBackpackScreenPacketReceiver());

		// Registry
		DataAttachmentRegistry.init();
		MenuTypesRegistry.registerAll();
	}

	public static Identifier identifier(String path) {
		return Identifier.fromNamespaceAndPath(MOD_ID, path);
	}

}