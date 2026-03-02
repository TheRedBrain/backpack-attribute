package com.github.theredbrain.backpackattribute.config;

import com.github.theredbrain.backpackattribute.BackpackAttribute;
import me.fzzyhmstrs.fzzy_config.annotations.ConvertFrom;
import me.fzzyhmstrs.fzzy_config.config.Config;
import me.fzzyhmstrs.fzzy_config.validation.misc.ValidatedBoolean;

@ConvertFrom(fileName = "client.json5", folder = "backpackattribute")
public class ClientConfig extends Config {

	public ClientConfig() {
		super(BackpackAttribute.identifier("client"));
	}

	public ValidatedBoolean show_inactive_backpack_slots = new ValidatedBoolean(false);

}