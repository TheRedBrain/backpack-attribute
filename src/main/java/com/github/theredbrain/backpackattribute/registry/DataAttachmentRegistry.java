package com.github.theredbrain.backpackattribute.registry;

import com.github.theredbrain.backpackattribute.BackpackAttribute;
import com.mojang.serialization.Codec;
import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.fabricmc.fabric.api.attachment.v1.AttachmentSyncPredicate;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
import net.minecraft.network.codec.ByteBufCodecs;

public class DataAttachmentRegistry {
	public static AttachmentType<Float> OLD_BACKPACK_CAPACITY;

	public static void init() {
	}

	static {
		OLD_BACKPACK_CAPACITY = AttachmentRegistry.create(BackpackAttribute.identifier("old_backpack_capacity"), builder -> builder
				.persistent(Codec.FLOAT)
				.syncWith(ByteBufCodecs.FLOAT, AttachmentSyncPredicate.all())
		);
	}
}
