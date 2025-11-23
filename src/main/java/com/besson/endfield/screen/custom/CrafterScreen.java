package com.besson.endfield.screen.custom;

import com.besson.endfield.ArknightsEndField;
import com.besson.endfield.network.CycleRecipePacket;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.neoforged.neoforge.network.PacketDistributor;


public class CrafterScreen extends AbstractContainerScreen<CrafterScreenHandler> {
    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(ArknightsEndField.MOD_ID, "textures/gui/crafter.png");

    public CrafterScreen(CrafterScreenHandler pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
    }

    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        renderBackground(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        renderTooltip(pGuiGraphics, pMouseX, pMouseY);
    }

    @Override
    protected void renderBg(GuiGraphics pGuiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (this.width - this.imageWidth) / 2;
        int y = (this.height - this.imageHeight) / 2;

        pGuiGraphics.blit(TEXTURE, x, y, 0, 0, imageWidth, imageHeight);
        this.addRenderableWidget(Button.builder(Component.literal(">"), button -> {
            PacketDistributor.sendToServer(new CycleRecipePacket());
        }).bounds(x + 150, y + 30, 20, 20).build());
    }
}
