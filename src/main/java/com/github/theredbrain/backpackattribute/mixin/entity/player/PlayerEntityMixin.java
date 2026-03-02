package com.github.theredbrain.backpackattribute.mixin.entity.player;

import com.github.theredbrain.backpackattribute.BackpackAttribute;
import com.github.theredbrain.backpackattribute.config.ServerConfig;
import com.github.theredbrain.backpackattribute.entity.player.DuckPlayerEntityMixin;
import com.github.theredbrain.backpackattribute.inventory.BackpackInventory;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.StackWithSlot;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity implements DuckPlayerEntityMixin {

    @Shadow public abstract PlayerInventory getInventory();

    @Unique
    private static final TrackedData<Integer> OLD_BACKPACK_CAPACITY = DataTracker.registerData(PlayerEntity.class, TrackedDataHandlerRegistry.INTEGER);

    @Unique
    protected BackpackInventory backpackInventory = new BackpackInventory(27, ((PlayerEntity) (Object) this));

    @Unique
    private boolean shouldCheckForItemsInInactiveSlots = true;

    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "initDataTracker", at = @At("RETURN"))
    protected void backpackattribute$initDataTracker(DataTracker.Builder builder, CallbackInfo ci) {
        builder.add(OLD_BACKPACK_CAPACITY, 0);

    }

    @Inject(method = "tick", at = @At("TAIL"))
    public void backpackattribute$tick(CallbackInfo ci) {
        if (!this.getEntityWorld().isClient()) {
            this.ejectItemsFromInactiveBackpackSlots();
        }
    }

    @Inject(method = "dropInventory", at = @At("TAIL"))
    protected void dropInventory(CallbackInfo ci) {
        ServerConfig serverConfig = BackpackAttribute.SERVER_CONFIG;
        if (!serverConfig.keep_backpack_inventory_on_death.get()) {
            if (serverConfig.clear_backpack_inventory_on_death.get()) {
                this.backpackInventory.clear();
            } else {
                this.backpackInventory.dropAll();
            }
        }

    }

    @Inject(method = "readCustomData", at = @At("TAIL"))
    public void backpackattribute$readCustomData(ReadView view, CallbackInfo ci) {

        this.backpackInventory.readData(view.getTypedListView("backpack_items", StackWithSlot.CODEC));

    }

    @Inject(method = "writeCustomData", at = @At("TAIL"))
    public void backpackattribute$writeCustomData(WriteView view, CallbackInfo ci) {

        this.backpackInventory.writeData(view.getListAppender("backpack_items", StackWithSlot.CODEC));

    }

    @Override
    public int backpackattribute$getActiveBackpackCapacity() {
        return Math.min(27, Math.max(0, Math.min(27, Math.max(0, BackpackAttribute.SERVER_CONFIG.natural_backpack_slot_amount.get())) + this.backpackattribute$getBackpackCapacity()));
    }

    @Override
    public int backpackattribute$getBackpackCapacity() {
        return (int) this.getAttributeValue(BackpackAttribute.BACKPACK_CAPACITY);
    }

    @Override
    public int backpackattribute$getOldBackpackCapacity() {
        return this.dataTracker.get(OLD_BACKPACK_CAPACITY);
    }

    @Override
    public void backpackattribute$setOldBackpackCapacity(int backpack_capacity) {
        this.dataTracker.set(OLD_BACKPACK_CAPACITY, backpack_capacity);
    }

    @Override
    public BackpackInventory backpackattribute$getBackpackInventory() {
        return this.backpackInventory;
    }

    @Override
    public void backpackattribute$setBackpackInventory(BackpackInventory backpackInventory) {
        this.backpackInventory = backpackInventory;
    }

    @Unique
    private void ejectItemsFromInactiveBackpackSlots() {
        int backpack_capacity = backpackattribute$getActiveBackpackCapacity();
        if (this.backpackattribute$getOldBackpackCapacity() != backpack_capacity) {
            this.shouldCheckForItemsInInactiveSlots = true;
            this.backpackattribute$setOldBackpackCapacity(backpack_capacity);
        }

        // use a separate boolean to guarantee a check on login to account for changes to the server config
        if (this.shouldCheckForItemsInInactiveSlots) {
            boolean bl = false;
            for (int j = backpack_capacity; j < 27; j++) {
                PlayerInventory playerInventory = this.getInventory();
                BackpackInventory backpackInventory = this.backpackattribute$getBackpackInventory();

                if (!backpackInventory.getStack(j).isEmpty()) {
                    playerInventory.offerOrDrop(backpackInventory.removeStack(j));
                    bl = true;
                }
            }
            if (bl && ((PlayerEntity) (Object) this) instanceof ServerPlayerEntity serverPlayerEntity) {
                serverPlayerEntity.sendMessage(Text.translatable("hud.message.itemRemovedFromInactiveBackpackSlots"), false);
            }
            this.shouldCheckForItemsInInactiveSlots = false;
        }
    }
}
