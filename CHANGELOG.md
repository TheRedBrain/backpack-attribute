# 3.0.0

- updated to 1.21.11
- migrated to Mojang Mappings

## Additions

- added server config option to disable the hotkey for opening the backpack screen

## Changes

- replaced game rules with server config options

## Fixes

- fixed potential issues regarding config initialization and "Inventory Size Attributes" integration

## Technical

- replaced synced data with a data attachment, following the best practice for custom entity data
- removed deprecated API method

# 2.2.0

- removed dependency on cloth config
- added dependency on fzzy config

# 2.1.0

- update to 1.21.1
- added compatibility with "Inventory Size Attributes"
- rendering of disabled slots is now controlled by a client config

# 2.0.0

- update to 1.21

# 1.1.0

- Changed the way the attribute is registered, which increased compatibility with other mods
- the backpack capacity attribute is now found under "generic.backpack_capacity"

# 1.0.0

Initial release.

#