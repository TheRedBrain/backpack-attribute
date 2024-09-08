package com.github.theredbrain.backpackattribute.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;

@Config(
		name = "client"
)
public class ClientConfig implements ConfigData {

	@ConfigEntry.Gui.PrefixText
	public boolean show_inactive_slots = false;

	public ClientConfig() {
	}
}
