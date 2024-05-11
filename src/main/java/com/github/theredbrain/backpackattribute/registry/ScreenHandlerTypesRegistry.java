package com.github.theredbrain.backpackattribute.registry;

import com.github.theredbrain.backpackattribute.BackpackAttribute;
import com.github.theredbrain.backpackattribute.screen.BackpackScreenHandler;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.resource.featuretoggle.FeatureFlags;
import net.minecraft.screen.ScreenHandlerType;

public class ScreenHandlerTypesRegistry {
    public static final ScreenHandlerType<BackpackScreenHandler> BACKPACK_SCREEN_HANDLER = new ScreenHandlerType<>(BackpackScreenHandler::new, FeatureFlags.VANILLA_FEATURES);

    public static void registerAll() {
        Registry.register(Registries.SCREEN_HANDLER, BackpackAttribute.identifier("backpack"), BACKPACK_SCREEN_HANDLER);
    }
}
