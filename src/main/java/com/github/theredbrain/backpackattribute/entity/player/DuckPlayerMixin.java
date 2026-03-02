package com.github.theredbrain.backpackattribute.entity.player;

import com.github.theredbrain.backpackattribute.inventory.BackpackContainer;

public interface DuckPlayerMixin {

	BackpackContainer backpackattribute$getBackpackInventory();

	void backpackattribute$setBackpackInventory(BackpackContainer backpackContainer);
}
