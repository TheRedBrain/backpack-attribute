package com.github.theredbrain.backpackattribute.config;

import com.github.theredbrain.backpackattribute.BackpackAttribute;
import me.fzzyhmstrs.fzzy_config.annotations.ConvertFrom;
import me.fzzyhmstrs.fzzy_config.config.Config;
import me.fzzyhmstrs.fzzy_config.validation.misc.ValidatedBoolean;
import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedInt;

@ConvertFrom(fileName = "server.json5", folder = "backpackattribute")
public class ServerConfig extends Config {

	public ServerConfig() {
		super(BackpackAttribute.identifier("server"));
	}

	public ValidatedInt natural_backpack_capacity = new ValidatedInt(0, 27, 0);
	public ValidatedBoolean keep_backpack_inventory_on_death = new ValidatedBoolean(true);
	public ValidatedBoolean clear_backpack_inventory_on_death = new ValidatedBoolean(false);
	public ValidatedBoolean is_backpack_screen_hotkey_enabled = new ValidatedBoolean(true);
}
