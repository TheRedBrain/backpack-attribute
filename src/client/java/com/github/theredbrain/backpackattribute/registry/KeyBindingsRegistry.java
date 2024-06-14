package com.github.theredbrain.backpackattribute.registry;

import com.github.theredbrain.backpackattribute.network.packet.OpenBackpackScreenPacket;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class KeyBindingsRegistry {

    public static KeyBinding openBackpackScreen;
    public static boolean openBackpackScreenBoolean;

    public static void registerKeyBindings() {
        KeyBindingsRegistry.openBackpackScreen = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.backpackattribute.backpackScreen",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_B,
                "category.backpackattribute.category"
        ));
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (KeyBindingsRegistry.openBackpackScreen.wasPressed()) {
                if (!openBackpackScreenBoolean) {
                    openBackpackScreen(client);
                }
                openBackpackScreenBoolean = true;
            } else if (openBackpackScreenBoolean) {
                openBackpackScreenBoolean = false;
            }
        });
    }

    public static void openBackpackScreen(MinecraftClient client) {
        if (client.player != null) {
            ClientPlayNetworking.send(new OpenBackpackScreenPacket());
        }
    }
}
