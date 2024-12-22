package com.github.theredbrain.backpackattribute.config;

import com.github.theredbrain.backpackattribute.BackpackAttribute;
import me.fzzyhmstrs.fzzy_config.annotations.ConvertFrom;
import me.fzzyhmstrs.fzzy_config.config.Config;
import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedInt;

@ConvertFrom(fileName = "server.json5", folder = "backpackattribute")
public class ServerConfig extends Config {

	public ServerConfig() {
		super(BackpackAttribute.identifier("server"));
	}

	public ValidatedInt default_backpack_slot_amount = new ValidatedInt(0, 0, 27);
}
