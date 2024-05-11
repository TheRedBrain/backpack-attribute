package com.github.theredbrain.backpackattribute.registry;

import net.fabricmc.fabric.api.gamerule.v1.GameRuleFactory;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleRegistry;
import net.minecraft.world.GameRules;

public class GameRulesRegistry {
    public static final GameRules.Key<GameRules.BooleanRule> KEEP_BACKPACK_INVENTORY =
            GameRuleRegistry.register("keepBackpackInventory", GameRules.Category.PLAYER, GameRuleFactory.createBooleanRule(false));
    public static final GameRules.Key<GameRules.BooleanRule> CLEAR_BACKPACK_INVENTORY_ON_DEATH =
            GameRuleRegistry.register("clearBackpackInventoryOnDeath", GameRules.Category.PLAYER, GameRuleFactory.createBooleanRule(false));
    public static void init() {}
}
