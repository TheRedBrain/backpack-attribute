package com.github.theredbrain.backpackattribute.network.packet;

import com.github.theredbrain.backpackattribute.entity.player.DuckPlayerEntityMixin;
import com.github.theredbrain.backpackattribute.screen.BackpackScreenHandler;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

public class OpenBackpackScreenPacketReceiver implements ServerPlayNetworking.PlayPacketHandler<OpenBackpackScreenPacket> {

    @Override
    public void receive(OpenBackpackScreenPacket packet, ServerPlayerEntity serverPlayerEntity, PacketSender responseSender) {

        int i = ((DuckPlayerEntityMixin)serverPlayerEntity).backpackattribute$getActiveBackpackCapacity();
        if (i <= 0) {
            serverPlayerEntity.sendMessageToClient(Text.translatable("hud.message.no_active_capacity"), true);
            return;
        }
        serverPlayerEntity.openHandledScreen(new SimpleNamedScreenHandlerFactory((syncId, inventory, player) -> new BackpackScreenHandler(syncId, inventory), Text.translatable("gui.backpack_screen.title")));
    }
}
