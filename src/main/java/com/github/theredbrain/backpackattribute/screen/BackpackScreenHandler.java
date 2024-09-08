package com.github.theredbrain.backpackattribute.screen;

import com.github.theredbrain.backpackattribute.BackpackAttribute;
import com.github.theredbrain.backpackattribute.entity.player.DuckPlayerEntityMixin;
import com.github.theredbrain.backpackattribute.inventory.BackpackInventory;
import com.github.theredbrain.backpackattribute.registry.ScreenHandlerTypesRegistry;
import com.github.theredbrain.slotcustomizationapi.api.SlotCustomization;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;

public class BackpackScreenHandler extends ScreenHandler {

    private final PlayerInventory playerInventory;
    private final BackpackInventory backpackInventory;
    private final int backpackCapacity;

    public BackpackScreenHandler(int syncId, PlayerInventory playerInventory) {
        super(ScreenHandlerTypesRegistry.BACKPACK_SCREEN_HANDLER, syncId);
        this.playerInventory = playerInventory;
        this.backpackInventory = ((DuckPlayerEntityMixin) playerInventory.player).backpackattribute$getBackpackInventory();
        this.backpackCapacity = ((DuckPlayerEntityMixin) playerInventory.player).backpackattribute$getActiveBackpackCapacity();

        int i;
        // hotbar 0 - 8
        for (i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
        }
        // main inventory 9 - 35
        for (i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }
        // backpack 36 - 62
        for (i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(backpackInventory, j + i * 9, 8 + j * 18, 18 + i * 18));
            }
        }

        // Inventory Size Attributes compatibility
        int activeHotbarSize = BackpackAttribute.getActiveHotbarSize(playerInventory.player);
        int activeInventorySize = BackpackAttribute.getActiveInventorySize(playerInventory.player);
        for (i = 0; i < 9; i++) {
            ((SlotCustomization) this.slots.get(i)).slotcustomizationapi$setDisabledOverride(i >= activeHotbarSize);
        }
        for (i = 9; i < 36; i++) {
            ((SlotCustomization) this.slots.get(i)).slotcustomizationapi$setDisabledOverride(i >= 9 + activeInventorySize);
        }
    }

    public ItemStack quickMove(PlayerEntity player, int slot) {

        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot2 = (Slot)this.slots.get(slot);
        if (slot2 != null && slot2.hasStack()) {
            ItemStack itemStack2 = slot2.getStack();
            itemStack = itemStack2.copy();
            if (slot < 36) {
                if (!this.insertItem(itemStack2, 36, Math.min(36 + ((DuckPlayerEntityMixin) player).backpackattribute$getActiveBackpackCapacity(), this.slots.size()), false)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.insertItem(itemStack2, 0, 36, false)) {
                return ItemStack.EMPTY;
            }

            if (itemStack2.isEmpty()) {
                slot2.setStack(ItemStack.EMPTY);
            } else {
                slot2.markDirty();
            }
        }

        return itemStack;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return true;
    }

    public BackpackInventory getBackpackInventory() {
        return this.backpackInventory;
    }

    public PlayerInventory getPlayerInventory() {
        return this.playerInventory;
    }

    public int getBackpackCapacity() {
        return backpackCapacity;
    }
}
