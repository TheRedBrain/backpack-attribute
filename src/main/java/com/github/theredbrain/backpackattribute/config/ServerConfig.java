package com.github.theredbrain.backpackattribute.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;

@Config(
        name = "server"
)
public class ServerConfig implements ConfigData {
    @Comment("""
            The default amount of backpack slots.
            Must be between 0 and 27 (both inclusive)
            """)
    public int default_backpack_slot_amount = 0;
    public ServerConfig() {}
}
