package com.github.theredbrain.backpackattribute.entity.player;

import com.github.theredbrain.backpackattribute.inventory.BackpackInventory;

public interface DuckPlayerEntityMixin {

    int backpackattribute$getBackpackCapacity();
    int backpackattribute$getOldBackpackCapacity();
    void backpackattribute$setOldBackpackCapacity(int backpack_capacity);
    BackpackInventory backpackattribute$getBackpackInventory();
    void backpackattribute$setBackpackInventory(BackpackInventory backpackInventory);
}
