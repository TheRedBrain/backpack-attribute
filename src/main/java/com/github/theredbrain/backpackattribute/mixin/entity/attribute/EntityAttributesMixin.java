package com.github.theredbrain.backpackattribute.mixin.entity.attribute;

import com.github.theredbrain.backpackattribute.BackpackAttribute;
import net.minecraft.entity.attribute.ClampedEntityAttribute;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(EntityAttributes.class)
public class EntityAttributesMixin {
    @Shadow
    private static EntityAttribute register(String id, EntityAttribute attribute) {
        throw new AssertionError();
    }

    static {
        BackpackAttribute.BACKPACK_CAPACITY = register(BackpackAttribute.MOD_ID + ":generic.backpack_capacity", new ClampedEntityAttribute("attribute.name.generic.backpack_capacity", 0.0, 0.0, 27).setTracked(true));
    }
}
