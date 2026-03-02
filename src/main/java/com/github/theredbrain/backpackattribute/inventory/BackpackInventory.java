package com.github.theredbrain.backpackattribute.inventory;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.inventory.StackWithSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;

public class BackpackInventory extends SimpleInventory {
    public final PlayerEntity player;

    public BackpackInventory(int size, PlayerEntity player) {
        super(size);
        this.player = player;
    }

    public void readData(ReadView.TypedListReadView<StackWithSlot> list) {
        for (int i = 0; i < this.size(); i++) {
            this.setStack(i, ItemStack.EMPTY);
        }

        for (StackWithSlot stackWithSlot : list) {
            if (stackWithSlot.isValidSlot(this.size())) {
                this.setStack(stackWithSlot.slot(), stackWithSlot.stack());
            }
        }
    }

    public void writeData(WriteView.ListAppender<StackWithSlot> list) {
        for (int i = 0; i < this.size(); i++) {
            ItemStack itemStack = this.getStack(i);
            if (!itemStack.isEmpty()) {
                list.add(new StackWithSlot(i, itemStack));
            }
        }
    }

    public void dropAll() {
        for (int i = 0; i < this.size(); ++i) {
            ItemStack itemStack = this.getStack(i);
            if (itemStack.isEmpty()) continue;
            this.player.dropItem(itemStack, true, false);
            this.setStack(i, ItemStack.EMPTY);
        }
    }
}
