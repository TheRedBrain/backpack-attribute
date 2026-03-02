package com.github.theredbrain.backpackattribute.inventory;

import net.minecraft.world.ItemStackWithSlot;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

public class BackpackContainer extends SimpleContainer {
    public final Player player;

    public BackpackContainer(int size, Player player) {
        super(size);
        this.player = player;
    }

    public void readData(ValueInput.TypedInputList<ItemStackWithSlot> list) {
        for (int i = 0; i < this.getContainerSize(); i++) {
            this.setItem(i, ItemStack.EMPTY);
        }

        for (ItemStackWithSlot stackWithSlot : list) {
            if (stackWithSlot.isValidInContainer(this.getContainerSize())) {
                this.setItem(stackWithSlot.slot(), stackWithSlot.stack());
            }
        }
    }

    public void writeData(ValueOutput.TypedOutputList<ItemStackWithSlot> list) {
        for (int i = 0; i < this.getContainerSize(); i++) {
            ItemStack itemStack = this.getItem(i);
            if (!itemStack.isEmpty()) {
                list.add(new ItemStackWithSlot(i, itemStack));
            }
        }
    }

    public void dropAll() {
        for (int i = 0; i < this.getContainerSize(); ++i) {
            ItemStack itemStack = this.getItem(i);
            if (itemStack.isEmpty()) continue;
            this.player.drop(itemStack, true, false);
            this.setItem(i, ItemStack.EMPTY);
        }
    }
}
