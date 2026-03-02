package com.github.theredbrain.backpackattribute.gui.screen.ingame;

import com.github.theredbrain.backpackattribute.BackpackAttribute;
import com.github.theredbrain.backpackattribute.BackpackAttributeClient;
import com.github.theredbrain.backpackattribute.registry.KeyBindingsRegistry;
import com.github.theredbrain.backpackattribute.screen.BackpackMenu;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;

@Environment(value = EnvType.CLIENT)
public class BackpackScreen extends AbstractContainerScreen<BackpackMenu> {
    public static final Identifier BACKGROUND_TEXTURE = BackpackAttribute.identifier("textures/gui/container/backpack_background.png");
    public static final Identifier SLOT_TEXTURE = Identifier.withDefaultNamespace("textures/gui/sprites/container/slot.png");
    private final int backpackCapacity;
    private final int hotbarSize;
    private final int inventorySize;

    public BackpackScreen(BackpackMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, title);
        this.backpackCapacity = menu.getBackpackCapacity();
        this.hotbarSize = BackpackAttribute.getActiveHotbarSize(inventory.player);
        this.inventorySize = BackpackAttribute.getActiveInventorySize(inventory.player);
    }

    @Override
    public boolean keyPressed(KeyEvent keyEvent) {
        if (KeyBindingsRegistry.openBackpackScreen.matches(keyEvent)) {
            this.onClose();
            return true;
        }
        return super.keyPressed(keyEvent);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
        this.renderBackground(guiGraphics, mouseX, mouseY, delta);
        super.render(guiGraphics, mouseX, mouseY, delta);
        this.renderTooltip(guiGraphics, mouseX, mouseY);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float delta, int mouseX, int mouseY) {
        int i = (this.width - this.imageWidth) / 2;
        int j = (this.height - this.imageHeight) / 2;
        int k;
        int m;
        boolean showInactiveInventorySlots = BackpackAttributeClient.showInactiveInventorySlots();

        guiGraphics.blit(RenderPipelines.GUI_TEXTURED, BACKGROUND_TEXTURE, i, j, 0, 0, this.imageWidth, this.imageHeight, this.imageWidth, this.imageHeight);

        for (k = 0; k < (BackpackAttributeClient.CLIENT_CONFIG.show_inactive_backpack_slots.get() ? 27 : Math.min(this.backpackCapacity, 27)); ++k) {
            m = (k / 9);
            guiGraphics.blit(RenderPipelines.GUI_TEXTURED, SLOT_TEXTURE, i + 7 + (k - (m * 9)) * 18, j + 17 + (m * 18), 0, 0, 18, 18, 18, 18);
        }
        for (k = 0; k < (showInactiveInventorySlots ? 27 : Math.min(this.inventorySize, 27)); ++k) {
            m = (k / 9);
            guiGraphics.blit(RenderPipelines.GUI_TEXTURED, SLOT_TEXTURE, i + 7 + (k - (m * 9)) * 18, j + 83 + (m * 18), 0, 0, 18, 18, 18, 18);
        }
        for (k = 0; k < (showInactiveInventorySlots ? 9 : Math.min(this.hotbarSize, 9)); ++k) {
            guiGraphics.blit(RenderPipelines.GUI_TEXTURED, SLOT_TEXTURE, i + 7 + k * 18, j + 141, 0, 0, 18, 18, 18, 18);
        }
    }
}

