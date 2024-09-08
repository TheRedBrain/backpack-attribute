package com.github.theredbrain.backpackattribute;

import com.github.theredbrain.backpackattribute.config.ServerConfig;
import com.github.theredbrain.backpackattribute.config.ServerConfigWrapper;
import com.github.theredbrain.backpackattribute.network.packet.OpenBackpackScreenPacket;
import com.github.theredbrain.backpackattribute.network.packet.OpenBackpackScreenPacketReceiver;
import com.github.theredbrain.backpackattribute.registry.GameRulesRegistry;
import com.github.theredbrain.backpackattribute.registry.ScreenHandlerTypesRegistry;
import com.github.theredbrain.inventorysizeattributes.entity.player.DuckPlayerEntityMixin;
import com.google.gson.Gson;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import me.shedaniel.autoconfig.serializer.PartitioningSerializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BackpackAttribute implements ModInitializer {
	public static final String MOD_ID = "backpackattribute";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static ServerConfig serverConfig;

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

		// Config
		AutoConfig.register(ServerConfigWrapper.class, PartitioningSerializer.wrap(JanksonConfigSerializer::new));
		serverConfig = ((ServerConfigWrapper)AutoConfig.getConfigHolder(ServerConfigWrapper.class).getConfig()).server;

		// Events
		PayloadTypeRegistry.playS2C().register(ServerConfigSyncPacket.PACKET_ID, ServerConfigSyncPacket.PACKET_CODEC);
		ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
			ServerPlayNetworking.send(handler.player, new ServerConfigSyncPacket(serverConfig));
		});
		PayloadTypeRegistry.playC2S().register(OpenBackpackScreenPacket.PACKET_ID, OpenBackpackScreenPacket.PACKET_CODEC);
		ServerPlayNetworking.registerGlobalReceiver(OpenBackpackScreenPacket.PACKET_ID, new OpenBackpackScreenPacketReceiver());

		// Registry
		ScreenHandlerTypesRegistry.registerAll();
		GameRulesRegistry.init();
	}

	public record ServerConfigSyncPacket(ServerConfig serverConfig) implements CustomPayload {
		public static final CustomPayload.Id<ServerConfigSyncPacket> PACKET_ID = new CustomPayload.Id<>(identifier("server_config_sync"));
		public static final PacketCodec<RegistryByteBuf, ServerConfigSyncPacket> PACKET_CODEC = PacketCodec.of(ServerConfigSyncPacket::write, ServerConfigSyncPacket::new);

		public ServerConfigSyncPacket(RegistryByteBuf registryByteBuf) {
			this(new Gson().fromJson(registryByteBuf.readString(), ServerConfig.class));
		}

		private void write(RegistryByteBuf registryByteBuf) {
			registryByteBuf.writeString(new Gson().toJson(serverConfig));
		}

		@Override
		public CustomPayload.Id<? extends CustomPayload> getId() {
			return PACKET_ID;
		}
	}

	public static Identifier identifier(String path) {
		return Identifier.of(MOD_ID, path);
	}

}