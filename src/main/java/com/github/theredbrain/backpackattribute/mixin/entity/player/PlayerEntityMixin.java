package com.github.theredbrain.backpackattribute.mixin.entity.player;

import com.github.theredbrain.backpackattribute.BackpackAttribute;
import com.github.theredbrain.backpackattribute.entity.player.DuckPlayerEntityMixin;
import com.github.theredbrain.backpackattribute.inventory.BackpackInventory;
import com.github.theredbrain.backpackattribute.registry.GameRulesRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity implements DuckPlayerEntityMixin {

    @Shadow public abstract PlayerInventory getInventory();

    @Shadow public abstract ItemStack getEquippedStack(EquipmentSlot slot);

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
        if (!this.getWorld().isClient) {
            this.ejectItemsFromInactiveBackpackSlots();
        }
    }

    @Inject(method = "dropInventory", at = @At("TAIL"))
    protected void dropInventory(CallbackInfo ci) {
        if (!this.getWorld().getGameRules().getBoolean(GameRulesRegistry.KEEP_BACKPACK_INVENTORY)) {
            if (this.getWorld().getGameRules().getBoolean(GameRulesRegistry.CLEAR_BACKPACK_INVENTORY_ON_DEATH)) {
                this.backpackInventory.clear();
            } else {
                this.backpackInventory.dropAll();
            }
        }

    }

    @Inject(method = "readCustomDataFromNbt", at = @At("TAIL"))
    public void backpackattribute$readCustomDataFromNbt(NbtCompound nbt, CallbackInfo ci) {

        if (nbt.contains("backpack_items", NbtElement.LIST_TYPE)) {
            this.backpackInventory.readNbtList(nbt.getList("backpack_items", NbtElement.COMPOUND_TYPE), this.getRegistryManager());
        }

    }

    @Inject(method = "writeCustomDataToNbt", at = @At("TAIL"))
    public void backpackattribute$writeCustomDataToNbt(NbtCompound nbt, CallbackInfo ci) {

        nbt.put("backpack_items", this.backpackInventory.toNbtList(this.getRegistryManager()));

    }

    @Override
    public int backpackattribute$getActiveBackpackCapacity() {
        return Math.min(27, Math.max(0, Math.min(27, Math.max(0, BackpackAttribute.serverConfig.default_backpack_slot_amount)) + this.backpackattribute$getBackpackCapacity()));
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
