package com.github.theredbrain.backpackattribute.network.packet;

import com.github.theredbrain.backpackattribute.entity.player.DuckPlayerEntityMixin;
import com.github.theredbrain.backpackattribute.screen.BackpackScreenHandler;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
import net.minecraft.text.Text;

public class OpenBackpackScreenPacketReceiver implements ServerPlayNetworking.PlayPayloadHandler<OpenBackpackScreenPacket> {

    @Override
    public void receive(OpenBackpackScreenPacket payload, ServerPlayNetworking.Context context) {

        int i = ((DuckPlayerEntityMixin)context.player()).backpackattribute$getActiveBackpackCapacity();
        if (i <= 0) {
            context.player().sendMessageToClient(Text.translatable("hud.message.no_active_capacity"), true);
            return;
        }
        context.player().openHandledScreen(new SimpleNamedScreenHandlerFactory((syncId, inventory, player) -> new BackpackScreenHandler(syncId, inventory), Text.translatable("gui.backpack_screen.title")));
    }
}
