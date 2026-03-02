package com.github.theredbrain.backpackattribute.entity;

import com.github.theredbrain.backpackattribute.BackpackAttribute;
import com.google.common.collect.HashMultimap;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;

public class PlayerHelper {

	public static HashMultimap<Holder<Attribute>, AttributeModifier> getNaturalAttributeModifiers() {
		HashMultimap<Holder<Attribute>, AttributeModifier> hashMultimap = HashMultimap.create();
		hashMultimap.put(BackpackAttribute.BACKPACK_CAPACITY, new AttributeModifier(BackpackAttribute.identifier("natural_backpack_capacity_modifier"), BackpackAttribute.SERVER_CONFIG.natural_backpack_capacity.get(), AttributeModifier.Operation.ADD_VALUE));
		return hashMultimap;
	}
}
