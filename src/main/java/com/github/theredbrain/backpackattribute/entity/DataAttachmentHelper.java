package com.github.theredbrain.backpackattribute.entity;

import com.github.theredbrain.backpackattribute.registry.DataAttachmentRegistry;
import net.minecraft.world.entity.LivingEntity;

public class DataAttachmentHelper {

	public static float getOldBackpackCapacity(LivingEntity livingEntity) {
		return livingEntity.getAttachedOrElse(DataAttachmentRegistry.OLD_BACKPACK_CAPACITY, 0.0F);
	}

	public static void setOldBackpackCapacity(LivingEntity livingEntity, double old_backpack_capacity) {
		livingEntity.setAttached(DataAttachmentRegistry.OLD_BACKPACK_CAPACITY, (float) old_backpack_capacity);
	}

}
