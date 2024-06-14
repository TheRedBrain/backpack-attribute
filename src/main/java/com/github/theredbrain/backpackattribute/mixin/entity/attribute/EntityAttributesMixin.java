package com.github.theredbrain.backpackattribute.mixin.entity.attribute;

import com.github.theredbrain.backpackattribute.BackpackAttribute;
import net.minecraft.entity.attribute.ClampedEntityAttribute;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(EntityAttributes.class)
public class EntityAttributesMixin {
    static {
        BackpackAttribute.BACKPACK_CAPACITY = Registry.registerReference(Registries.ATTRIBUTE, BackpackAttribute.identifier("generic.backpack_capacity"), new ClampedEntityAttribute("attribute.name.generic.backpack_capacity", 0.0, 0.0, 27).setTracked(true));
    }
}
