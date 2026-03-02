package com.github.theredbrain.backpackattribute.mixin.entity;

import com.github.theredbrain.backpackattribute.BackpackAttribute;
import com.github.theredbrain.backpackattribute.entity.DataAttachmentHelper;
import com.github.theredbrain.backpackattribute.entity.DuckLivingEntityMixin;
import net.minecraft.core.Holder;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity implements DuckLivingEntityMixin {

    @Shadow
    public abstract double getAttributeValue(Holder<Attribute> holder);

    public LivingEntityMixin(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(method = "createLivingAttributes", at = @At("RETURN"))
    private static void staminaattributes$createLivingAttributes(CallbackInfoReturnable<AttributeSupplier.Builder> cir) {
        cir.getReturnValue()
                .add(BackpackAttribute.BACKPACK_CAPACITY)
        ;
    }

    @Override
    public int backpackattribute$getBackpackCapacity() {
        return (int) this.getAttributeValue(BackpackAttribute.BACKPACK_CAPACITY);
    }

    @Override
    public float backpackattribute$getOldBackpackCapacity() {
        return DataAttachmentHelper.getOldBackpackCapacity((LivingEntity) (Object) this);
    }

    @Override
    public void backpackattribute$setOldBackpackCapacity(float backpack_capacity) {
        DataAttachmentHelper.setOldBackpackCapacity((LivingEntity) (Object) this, (float) Mth.clamp(backpack_capacity, 0.0, 27.0));
    }

}
