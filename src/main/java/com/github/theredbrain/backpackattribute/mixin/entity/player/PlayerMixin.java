package com.github.theredbrain.backpackattribute.mixin.entity.player;

import com.github.theredbrain.backpackattribute.BackpackAttribute;
import com.github.theredbrain.backpackattribute.config.ServerConfig;
import com.github.theredbrain.backpackattribute.entity.DuckLivingEntityMixin;
import com.github.theredbrain.backpackattribute.entity.PlayerHelper;
import com.github.theredbrain.backpackattribute.entity.player.DuckPlayerMixin;
import com.github.theredbrain.backpackattribute.inventory.BackpackContainer;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.ItemStackWithSlot;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Player.class)
public abstract class PlayerMixin extends LivingEntity implements DuckPlayerMixin, DuckLivingEntityMixin {

	@Shadow
	public abstract Inventory getInventory();

	@Unique
	protected BackpackContainer backpackContainer = new BackpackContainer(27, ((Player) (Object) this));

	@Unique
	private boolean shouldCheckForItemsInInactiveSlots = true;

	protected PlayerMixin(EntityType<? extends LivingEntity> entityType, Level world) {
		super(entityType, world);
	}

	@Inject(method = "tick", at = @At("TAIL"))
	public void backpackattribute$tick(CallbackInfo ci) {
		if (!this.level().isClientSide()) {
			this.getAttributes().addTransientAttributeModifiers(PlayerHelper.getNaturalAttributeModifiers());
			this.ejectItemsFromInactiveBackpackSlots();
		}
	}

	@Inject(method = "dropEquipment", at = @At("TAIL"))
	protected void backpackattribute$dropEquipment(CallbackInfo ci) {
		ServerConfig serverConfig = BackpackAttribute.SERVER_CONFIG;
		if (!serverConfig.keep_backpack_inventory_on_death.get()) {
			if (serverConfig.clear_backpack_inventory_on_death.get()) {
				this.backpackContainer.clearContent();
			} else {
				this.backpackContainer.dropAll();
			}
		}

	}

	@Inject(method = "readAdditionalSaveData", at = @At("TAIL"))
	public void backpackattribute$readAdditionalSaveData(ValueInput view, CallbackInfo ci) {

		this.backpackContainer.readData(view.listOrEmpty("backpack_items", ItemStackWithSlot.CODEC));

	}

	@Inject(method = "addAdditionalSaveData", at = @At("TAIL"))
	public void backpackattribute$addAdditionalSaveData(ValueOutput view, CallbackInfo ci) {

		this.backpackContainer.writeData(view.list("backpack_items", ItemStackWithSlot.CODEC));

	}

	@Override
	public BackpackContainer backpackattribute$getBackpackInventory() {
		return this.backpackContainer;
	}

	@Override
	public void backpackattribute$setBackpackInventory(BackpackContainer backpackContainer) {
		this.backpackContainer = backpackContainer;
	}

	@Unique
	private void ejectItemsFromInactiveBackpackSlots() {
		int backpack_capacity = backpackattribute$getBackpackCapacity();
		if (this.backpackattribute$getOldBackpackCapacity() != backpack_capacity) {
			this.shouldCheckForItemsInInactiveSlots = true;
			this.backpackattribute$setOldBackpackCapacity(backpack_capacity);
		}

		// use a separate boolean to guarantee a check on login to account for changes to the server config
		if (this.shouldCheckForItemsInInactiveSlots) {
			boolean bl = false;
			for (int j = backpack_capacity; j < 27; j++) {
				Inventory playerInventory = this.getInventory();
				BackpackContainer backpackContainer = this.backpackattribute$getBackpackInventory();

				if (!backpackContainer.getItem(j).isEmpty()) {
					playerInventory.placeItemBackInInventory(backpackContainer.removeItemNoUpdate(j));
					bl = true;
				}
			}
			if (bl && ((Player) (Object) this) instanceof ServerPlayer serverPlayerEntity) {
				serverPlayerEntity.displayClientMessage(Component.translatable("hud.message.itemRemovedFromInactiveBackpackSlots"), false);
			}
			this.shouldCheckForItemsInInactiveSlots = false;
		}
	}
}
