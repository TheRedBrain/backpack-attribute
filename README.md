# Backpack Attribute

This is a different approach to a backpack in Minecraft. Instead of an item you equip to gain additional inventory slots, this mod adds a new gamerule (backpackSlotAmount) and a new attribute (player.backpack_capacity), which control how many slots you have available in a new backpack screen.\
The backpack can be opened via a hotkey (B by default).

The minimum amount of slots is 0, in which case the backpack screen won't open. The player gets notified that they currently don't have backpack slots.

The maximum amount of slots is 27.

## "How do get more backpack slots?"

The value "default_backpack_slot_amount" in the serverConfig file controls the amount of backpack slots each player has by default.

The entity attribute "player.backpack_capacity" controls how many slots are added to/removed from the default amount. 
This is 0 by default, but with entity attribute modifiers (EAMs) it can be changed.

Content mods or data packs can provide backpack slots via multiple different methods.

### Examples
- A chest plate adds 9 backpack slots. When the player has that chest plate equipped, that player now has 9 additional backpack slots.

- A status effect lowers your slots by 2. Every player with that effect has 2 slots less until the effect is removed / runs out.

Disclaimer: This mod adds none of the examples to the game, it only gives other mods/datapacks the ability to do so.

## "What happens to items in my backpack when my backpack gets smaller?"

When a backpack slot contains an item and becomes inactive, that item is moved to the normal inventory or dropped at the players location, when the inventory is full. This is announced to the player via a chat message.

## "What happens to items in my backpack when I die?"

Two new game rules control what happens in the case of player death.\
"keepBackpackInventory" defaults to false.\
"clearBackpackInventoryOnDeath" defaults to false.

When "keepBackpackInventory" is set to true, the items stay in the backpack. This is similar to the vanilla gameRule "keepInventory".

When "keepBackpackInventory" is set to false, two outcomes are possible:
- When "clearBackpackInventoryOnDeath" is true, the items are destroyed.
- When "clearBackpackInventoryOnDeath" is false, the items are dropped on the players position.
