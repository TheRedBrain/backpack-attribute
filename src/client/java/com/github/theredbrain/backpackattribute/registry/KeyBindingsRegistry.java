package com.github.theredbrain.backpackattribute.registry;

import com.github.theredbrain.backpackattribute.BackpackAttribute;
import com.github.theredbrain.backpackattribute.BackpackAttributeClient;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

public class KeyBindingsRegistry {

    public static KeyBinding openBackpackScreen;
    public static boolean openBackpackScreenBoolean;
    public static KeyBinding.Category BACKPACK_ATTRIBUTE;

    public static void registerKeyBindings() {
        KeyBindingsRegistry.openBackpackScreen = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.backpackattribute.backpackScreen",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_B,
                BACKPACK_ATTRIBUTE
        ));
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (KeyBindingsRegistry.openBackpackScreen.wasPressed()) {
                if (!openBackpackScreenBoolean) {
                    if (BackpackAttribute.SERVER_CONFIG.is_backpack_screen_hotkey_enabled.get()) {
                        BackpackAttributeClient.openBackpackScreen(client);
                    } else if (client.player != null) {
                        client.player.sendMessage(Text.translatable("gui.backpack.hotkey_disabled_by_server"), false);
                    }
                }
                openBackpackScreenBoolean = true;
            } else if (openBackpackScreenBoolean) {
                openBackpackScreenBoolean = false;
            }
        });
    }

    static {
        BACKPACK_ATTRIBUTE = KeyBinding.Category.create(BackpackAttribute.identifier("key_binding_category"));
    }

}
