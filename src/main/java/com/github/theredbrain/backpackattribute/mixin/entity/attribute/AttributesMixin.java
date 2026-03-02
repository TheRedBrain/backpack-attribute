package com.github.theredbrain.backpackattribute.mixin.entity.attribute;

import com.github.theredbrain.backpackattribute.BackpackAttribute;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Attributes.class)
public class AttributesMixin {
    static {
        BackpackAttribute.BACKPACK_CAPACITY = Registry.registerForHolder(BuiltInRegistries.ATTRIBUTE, BackpackAttribute.identifier("backpack_capacity"), new RangedAttribute("attribute.name.backpack_capacity", 0.0, 0.0, 27).setSyncable(true));
    }
}
