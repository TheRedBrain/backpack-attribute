package com.github.theredbrain.backpackattribute.entity.player;

import com.github.theredbrain.backpackattribute.inventory.BackpackContainer;

public interface DuckPlayerMixin {

    int backpackattribute$getActiveBackpackCapacity();
    int backpackattribute$getBackpackCapacity();
    int backpackattribute$getOldBackpackCapacity();
    void backpackattribute$setOldBackpackCapacity(int backpack_capacity);
    BackpackContainer backpackattribute$getBackpackInventory();
    void backpackattribute$setBackpackInventory(BackpackContainer backpackContainer);
}
