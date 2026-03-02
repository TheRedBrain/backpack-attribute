package com.github.theredbrain.backpackattribute.screen;

import com.github.theredbrain.backpackattribute.BackpackAttribute;
import com.github.theredbrain.backpackattribute.entity.player.DuckPlayerMixin;
import com.github.theredbrain.backpackattribute.inventory.BackpackContainer;
import com.github.theredbrain.backpackattribute.registry.MenuTypesRegistry;
import com.github.theredbrain.slotcustomizationapi.api.SlotCustomization;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class BackpackMenu extends AbstractContainerMenu {

    private final Inventory playerInventory;
    private final BackpackContainer backpackContainer;
    private final int backpackCapacity;

    public BackpackMenu(int syncId, Inventory playerInventory) {
        super(MenuTypesRegistry.BACKPACK_MENU, syncId);
        this.playerInventory = playerInventory;
        this.backpackContainer = ((DuckPlayerMixin) playerInventory.player).backpackattribute$getBackpackInventory();
        this.backpackCapacity = ((DuckPlayerMixin) playerInventory.player).backpackattribute$getActiveBackpackCapacity();

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
                this.addSlot(new Slot(backpackContainer, j + i * 9, 8 + j * 18, 18 + i * 18));
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

        for (i = 36; i < this.slots.size(); i++) {
            ((SlotCustomization) this.slots.get(i)).slotcustomizationapi$setDisabledOverride(i >= 36 + this.backpackCapacity);
        }

    }

    public ItemStack quickMoveStack(Player player, int slot) {

        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot2 = (Slot)this.slots.get(slot);
        if (slot2 != null && slot2.hasItem()) {
            ItemStack itemStack2 = slot2.getItem();
            itemStack = itemStack2.copy();
            if (slot < 36) {
                if (!this.moveItemStackTo(itemStack2, 36, Math.min(36 + ((DuckPlayerMixin) player).backpackattribute$getActiveBackpackCapacity(), this.slots.size()), false)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(itemStack2, 0, 36, false)) {
                return ItemStack.EMPTY;
            }

            if (itemStack2.isEmpty()) {
                slot2.setByPlayer(ItemStack.EMPTY);
            } else {
                slot2.setChanged();
            }
        }

        return itemStack;
    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }

    public BackpackContainer getBackpackInventory() {
        return this.backpackContainer;
    }

    public Inventory getPlayerInventory() {
        return this.playerInventory;
    }

    public int getBackpackCapacity() {
        return backpackCapacity;
    }
}
