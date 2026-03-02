package com.github.theredbrain.backpackattribute.gui.screen.ingame;

import com.github.theredbrain.backpackattribute.BackpackAttribute;
import com.github.theredbrain.backpackattribute.BackpackAttributeClient;
import com.github.theredbrain.backpackattribute.registry.KeyBindingsRegistry;
import com.github.theredbrain.backpackattribute.screen.BackpackScreenHandler;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.input.KeyInput;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

@Environment(value = EnvType.CLIENT)
public class BackpackScreen extends HandledScreen<BackpackScreenHandler> {
    public static final Identifier BACKGROUND_TEXTURE = BackpackAttribute.identifier("textures/gui/container/backpack_background.png");
    public static final Identifier SLOT_TEXTURE = Identifier.ofVanilla("textures/gui/sprites/container/slot.png");
    private final int backpackCapacity;
    private final int hotbarSize;
    private final int inventorySize;

    public BackpackScreen(BackpackScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
        this.backpackCapacity = handler.getBackpackCapacity();
        this.hotbarSize = BackpackAttribute.getActiveHotbarSize(inventory.player);
        this.inventorySize = BackpackAttribute.getActiveInventorySize(inventory.player);
    }

    @Override
    public boolean keyPressed(KeyInput input) {
        if (KeyBindingsRegistry.openBackpackScreen.matchesKey(input)) {
            this.close();
            return true;
        }
        return super.keyPressed(input);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context, mouseX, mouseY, delta);
        super.render(context, mouseX, mouseY, delta);
        this.drawMouseoverTooltip(context, mouseX, mouseY);
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        int i = (this.width - this.backgroundWidth) / 2;
        int j = (this.height - this.backgroundHeight) / 2;
        int k;
        int m;
        boolean showInactiveSlots = BackpackAttributeClient.CLIENT_CONFIG.show_inactive_slots.get();

        context.drawTexture(RenderPipelines.GUI_TEXTURED, BACKGROUND_TEXTURE, i, j, 0, 0, this.backgroundWidth, this.backgroundHeight, this.backgroundWidth, this.backgroundHeight);

        for (k = 0; k < (showInactiveSlots ? 27 : Math.min(this.backpackCapacity, 27)); ++k) {
            m = (k / 9);
            context.drawTexture(RenderPipelines.GUI_TEXTURED, SLOT_TEXTURE, i + 7 + (k - (m * 9)) * 18, j + 17 + (m * 18), 0, 0, 18, 18, 18, 18);
        }
        for (k = 0; k < (showInactiveSlots ? 27 : Math.min(this.inventorySize, 27)); ++k) {
            m = (k / 9);
            context.drawTexture(RenderPipelines.GUI_TEXTURED, SLOT_TEXTURE, i + 7 + (k - (m * 9)) * 18, j + 83 + (m * 18), 0, 0, 18, 18, 18, 18);
        }
        for (k = 0; k < (showInactiveSlots ? 9 : Math.min(this.hotbarSize, 9)); ++k) {
            context.drawTexture(RenderPipelines.GUI_TEXTURED, SLOT_TEXTURE, i + 7 + k * 18, j + 141, 0, 0, 18, 18, 18, 18);
        }
    }
}

