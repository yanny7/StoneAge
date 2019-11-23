package com.yanny.age.zero.client.renderer;

import com.mojang.blaze3d.platform.GlStateManager;
import com.yanny.age.zero.blocks.AquaductTileEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.lwjgl.opengl.GL11;

@OnlyIn(Dist.CLIENT)
public class AquaductRenderer extends TileEntityRenderer<AquaductTileEntity> {
    private static final ResourceLocation WATER = new ResourceLocation("minecraft", "textures/block/water_still.png");

    @Override
    public void render(AquaductTileEntity tileEntityIn, double x, double y, double z, float partialTicks, int destroyStage) {
        if (tileEntityIn.getCapacity() < 0.01) {
            return;
        }

        float off;
        int tick = tileEntityIn.getTick();
        int color = getWorld().getBiome(tileEntityIn.getPos()).getWaterColor();
        float r = (color & 0xff0000) >> 16;
        float g = (color & 0xff00) >> 8;
        float b = (color & 0xff);
        float v = tileEntityIn.getCapacity() * (10 / 16f) + 4 / 16f;

        off = (tick % 32) * (1 / 32f);

        GlStateManager.pushMatrix();
        GlStateManager.translatef((float)x, (float)y, (float)z);
        GlStateManager.enableLighting();
        GlStateManager.enableNormalize();
        GlStateManager.enableRescaleNormal();
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GlStateManager.color4f(r / 0xff, g / 0xff, b / 0xff, 1f);
        GlStateManager.enableTexture();

        if (Minecraft.isAmbientOcclusionEnabled()) {
            GlStateManager.shadeModel(GL11.GL_SMOOTH);
        } else {
            GlStateManager.shadeModel(GL11.GL_FLAT);
        }

        RenderHelper.disableStandardItemLighting();
        Minecraft.getInstance().getTextureManager().bindTexture(WATER);

        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
        bufferbuilder.pos(0, v, 1).tex(0, off + 1/32f).endVertex();
        bufferbuilder.pos(1, v, 1).tex(1, off + 1/32f).endVertex();
        bufferbuilder.pos(1, v, 0).tex(1, off).endVertex();
        bufferbuilder.pos(0, v, 0).tex(0, off).endVertex();
        tessellator.draw();

        RenderHelper.enableStandardItemLighting();
        GlStateManager.disableBlend();
        GlStateManager.disableRescaleNormal();
        GlStateManager.disableNormalize();
        GlStateManager.disableLighting();
        GlStateManager.popMatrix();
    }
}
