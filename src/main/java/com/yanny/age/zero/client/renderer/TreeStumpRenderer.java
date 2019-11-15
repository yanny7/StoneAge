package com.yanny.age.zero.client.renderer;

import com.mojang.blaze3d.platform.GlStateManager;
import com.yanny.age.zero.blocks.TreeStumpTileEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;

public class TreeStumpRenderer extends TileEntityRenderer<TreeStumpTileEntity> {
    @SuppressWarnings("deprecation")
    @Override
    public void render(TreeStumpTileEntity tileEntityIn, double x, double y, double z, float partialTicks, int destroyStage) {
        GlStateManager.pushMatrix();
        GlStateManager.translatef((float)x + 0.5f, (float)y + 0.75f, (float)z + 0.5f);
        GlStateManager.rotatef(90, 1.0F, 0.0F, 0.0F);
        GlStateManager.scalef(0.7f, 0.7f, 0.7f);

        Minecraft.getInstance().getItemRenderer().renderItem(tileEntityIn.getInventory().getStackInSlot(0), ItemCameraTransforms.TransformType.FIXED);

        GlStateManager.enableLighting();
        GlStateManager.popMatrix();
    }
}
