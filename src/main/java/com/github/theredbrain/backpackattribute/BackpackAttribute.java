package com.github.theredbrain.backpackattribute;

import com.github.theredbrain.backpackattribute.network.packet.OpenBackpackScreenPacket;
import com.github.theredbrain.backpackattribute.network.packet.OpenBackpackScreenPacketReceiver;
import com.github.theredbrain.backpackattribute.registry.GameRulesRegistry;
import com.github.theredbrain.backpackattribute.registry.ScreenHandlerTypesRegistry;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.attribute.ClampedEntityAttribute;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BackpackAttribute implements ModInitializer {
	public static final String MOD_ID = "backpackattribute";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static final RegistryEntry<EntityAttribute> BACKPACK_CAPACITY = Registry.registerReference(Registries.ATTRIBUTE, identifier("player.backpack_capacity"), new ClampedEntityAttribute("attribute.name.player.backpack_capacity", 0.0, 0.0, 27.0).setTracked(true));

	@Override
	public void onInitialize() {
		LOGGER.info("Your backpack can be attributed to an attribute!");

		ScreenHandlerTypesRegistry.registerAll();
		GameRulesRegistry.init();

		PayloadTypeRegistry.playC2S().register(OpenBackpackScreenPacket.PACKET_ID, OpenBackpackScreenPacket.PACKET_CODEC);
		ServerPlayNetworking.registerGlobalReceiver(OpenBackpackScreenPacket.PACKET_ID, new OpenBackpackScreenPacketReceiver());

	}

	public static Identifier identifier(String path) {
		return new Identifier(MOD_ID, path);
	}
}