package com.github.theredbrain.backpackattribute.network.packet;

import com.github.theredbrain.backpackattribute.entity.DuckLivingEntityMixin;
import com.github.theredbrain.backpackattribute.screen.BackpackMenu;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.chat.Component;
import net.minecraft.world.SimpleMenuProvider;

public class OpenBackpackScreenPacketReceiver implements ServerPlayNetworking.PlayPayloadHandler<OpenBackpackScreenPacket> {

    @Override
    public void receive(OpenBackpackScreenPacket payload, ServerPlayNetworking.Context context) {

        int i = ((DuckLivingEntityMixin)context.player()).backpackattribute$getBackpackCapacity();
        if (i <= 0) {
            context.player().sendSystemMessage(Component.translatable("hud.message.no_active_capacity"), true);
            return;
        }
        context.player().openMenu(new SimpleMenuProvider((syncId, inventory, player) -> new BackpackMenu(syncId, inventory), Component.translatable("gui.backpack_screen.title")));
    }
}
