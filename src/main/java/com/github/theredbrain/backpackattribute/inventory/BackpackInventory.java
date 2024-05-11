package com.github.theredbrain.backpackattribute.inventory;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.registry.RegistryWrapper;

public class BackpackInventory extends SimpleInventory {
    public final PlayerEntity player;

    public BackpackInventory(int size, PlayerEntity player) {
        super(size);
        this.player = player;
    }

    @Override
    public void readNbtList(NbtList list, RegistryWrapper.WrapperLookup registries) {
        int i;
        for (i = 0; i < this.size(); ++i) {
            this.setStack(i, ItemStack.EMPTY);
        }
        for (i = 0; i < list.size(); ++i) {
            NbtCompound nbtCompound = list.getCompound(i);
            int j = nbtCompound.getByte("Slot") & 0xFF;
            if (j < 0 || j >= this.size()) continue;
            this.setStack(j, ItemStack.fromNbt(registries, nbtCompound).orElse(ItemStack.EMPTY));
        }
    }

    @Override
    public NbtList toNbtList(RegistryWrapper.WrapperLookup registries) {
        NbtList nbtList = new NbtList();
        for (int i = 0; i < this.size(); ++i) {
            ItemStack itemStack = this.getStack(i);
            if (itemStack.isEmpty()) continue;
            NbtCompound nbtCompound = new NbtCompound();
            nbtCompound.putByte("Slot", (byte)i);
            nbtList.add(itemStack.encode(registries, nbtCompound));
        }
        return nbtList;
    }

    public void dropAll() {
        for (int i = 0; i < this.heldStacks.size(); ++i) {
            ItemStack itemStack = this.heldStacks.get(i);
            if (itemStack.isEmpty()) continue;
            this.player.dropItem(itemStack, true, false);
            this.heldStacks.set(i, ItemStack.EMPTY);
        }
    }

}
