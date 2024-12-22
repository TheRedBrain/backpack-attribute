package com.github.theredbrain.backpackattribute;

import com.github.theredbrain.backpackattribute.config.ServerConfig;
import com.github.theredbrain.backpackattribute.network.packet.OpenBackpackScreenPacket;
import com.github.theredbrain.backpackattribute.network.packet.OpenBackpackScreenPacketReceiver;
import com.github.theredbrain.backpackattribute.registry.GameRulesRegistry;
import com.github.theredbrain.backpackattribute.registry.ScreenHandlerTypesRegistry;
import com.github.theredbrain.inventorysizeattributes.entity.player.DuckPlayerEntityMixin;
import me.fzzyhmstrs.fzzy_config.api.ConfigApiJava;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BackpackAttribute implements ModInitializer {
	public static final String MOD_ID = "backpackattribute";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static ServerConfig SERVER_CONFIG = ConfigApiJava.registerAndLoadConfig(ServerConfig::new);

	public static RegistryEntry<EntityAttribute> BACKPACK_CAPACITY;

	public static final boolean isInventorySizeAttributesLoaded = FabricLoader.getInstance().isModLoaded("inventorysizeattributes");

	public static int getActiveInventorySize(PlayerEntity player) {
		return isInventorySizeAttributesLoaded ? ((DuckPlayerEntityMixin) player).inventorysizeattributes$getActiveInventorySlotAmount() : 27;
	}

	public static int getActiveHotbarSize(PlayerEntity player) {
		return isInventorySizeAttributesLoaded ? ((DuckPlayerEntityMixin) player).inventorysizeattributes$getActiveHotbarSlotAmount() : 9;
	}

	@Override
	public void onInitialize() {
		LOGGER.info("Your backpack can be attributed to an attribute!");

		// Events
		PayloadTypeRegistry.playC2S().register(OpenBackpackScreenPacket.PACKET_ID, OpenBackpackScreenPacket.PACKET_CODEC);
		ServerPlayNetworking.registerGlobalReceiver(OpenBackpackScreenPacket.PACKET_ID, new OpenBackpackScreenPacketReceiver());

		// Registry
		ScreenHandlerTypesRegistry.registerAll();
		GameRulesRegistry.init();
	}

	public static Identifier identifier(String path) {
		return Identifier.of(MOD_ID, path);
	}

}