package com.besson.endfield.screen.custom;

import com.besson.endfield.ArknightsEndField;
import com.besson.endfield.blockEntity.custom.PortableOriginiumRigBlockEntity;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class PortableOriginiumRigScreen extends BaseRigScreen<PortableOriginiumRigScreenHandler, PortableOriginiumRigBlockEntity> {
    public PortableOriginiumRigScreen(PortableOriginiumRigScreenHandler handler, Inventory inventory, Component title) {
        super(handler, inventory, title);
    }

    @Override
    protected ResourceLocation setTexture() {
        return ResourceLocation.fromNamespaceAndPath(ArknightsEndField.MOD_ID, "textures/gui/portable_originium_rig.png");
    }

    @Override
    protected void renderProgressArrow(GuiGraphics context, int x, int y) {
        if (menu.isCrafting()){
            context.blit(TEXTURE,x + 68, y + 41, 176,0, menu.getScaledProgress(), 8);
        }
    }
}
