package com.github.theredbrain.backpackattribute.registry;

import com.github.theredbrain.backpackattribute.BackpackAttribute;
import com.github.theredbrain.backpackattribute.screen.BackpackMenu;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;

public class MenuTypesRegistry {
    public static final MenuType<BackpackMenu> BACKPACK_MENU = new MenuType<>(BackpackMenu::new, FeatureFlags.VANILLA_SET);

    public static void registerAll() {
        Registry.register(BuiltInRegistries.MENU, BackpackAttribute.identifier("backpack"), BACKPACK_MENU);
    }
}
