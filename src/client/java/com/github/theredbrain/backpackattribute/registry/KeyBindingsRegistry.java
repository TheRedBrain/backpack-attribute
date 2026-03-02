package com.github.theredbrain.backpackattribute.registry;

import com.github.theredbrain.backpackattribute.BackpackAttribute;
import com.github.theredbrain.backpackattribute.BackpackAttributeClient;
import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.network.chat.Component;
import org.lwjgl.glfw.GLFW;

public class KeyBindingsRegistry {

    public static KeyMapping openBackpackScreen;
    public static boolean openBackpackScreenBoolean;
    public static KeyMapping.Category BACKPACK_ATTRIBUTE;

    public static void registerKeyBindings() {
        KeyBindingsRegistry.openBackpackScreen = KeyBindingHelper.registerKeyBinding(new KeyMapping(
                "key.backpackattribute.backpackScreen",
                InputConstants.Type.KEYSYM,
                GLFW.GLFW_KEY_B,
                BACKPACK_ATTRIBUTE
        ));
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (KeyBindingsRegistry.openBackpackScreen.isDown()) {
                if (!openBackpackScreenBoolean) {
                    if (BackpackAttribute.SERVER_CONFIG.is_backpack_screen_hotkey_enabled.get()) {
                        BackpackAttributeClient.openBackpackScreen(client);
                    } else if (client.player != null) {
                        client.player.displayClientMessage(Component.translatable("gui.backpack.hotkey_disabled_by_server"), false);
                    }
                }
                openBackpackScreenBoolean = true;
            } else if (openBackpackScreenBoolean) {
                openBackpackScreenBoolean = false;
            }
        });
    }

    static {
        BACKPACK_ATTRIBUTE = KeyMapping.Category.register(BackpackAttribute.identifier("key_binding_category"));
    }

}
