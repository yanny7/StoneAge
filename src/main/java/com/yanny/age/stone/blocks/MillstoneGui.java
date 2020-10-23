package com.yanny.age.stone.blocks;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.yanny.age.stone.Reference;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

import javax.annotation.Nonnull;

public class MillstoneGui extends ContainerScreen<MillstoneContainer> {

    private final ResourceLocation GUI = new ResourceLocation(Reference.MODID, "textures/gui/container/millstone.png");

    public MillstoneGui(@Nonnull MillstoneContainer screenContainer, @Nonnull PlayerInventory inv, @Nonnull ITextComponent titleIn) {
        super(screenContainer, inv, titleIn);
    }

    @Override
    public void render(@Nonnull MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        this.renderHoveredTooltip(matrixStack, mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(@Nonnull MatrixStack matrixStack, float partialTicks, int mouseX, int mouseY) {
        if (minecraft == null) {
            return;
        }

        minecraft.getTextureManager().bindTexture(GUI);
        blit(matrixStack, guiLeft, guiTop, 0, 0, xSize, ySize);

        if (container.getProgress() > 0) {
            int l = (int) Math.ceil(container.getProgress() / 100.0 * 16);
            blit(matrixStack, guiLeft + 80, guiTop + 35, 176, 0, l, 16);
        }
    }
}
