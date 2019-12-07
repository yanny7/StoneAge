package com.yanny.age.stone.client.renderer;

import com.mojang.blaze3d.platform.GlStateManager;
import com.yanny.age.stone.blocks.FeederTileEntity;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.util.Direction;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class FeederRenderer extends TileEntityRenderer<FeederTileEntity> {
    @SuppressWarnings("deprecation")
    @Override
    public void render(FeederTileEntity tileEntityIn, double x, double y, double z, float partialTicks, int destroyStage) {
        Direction direction = tileEntityIn.getBlockState().get(HorizontalBlock.HORIZONTAL_FACING);

        for (int i = 0; i < FeederTileEntity.ITEMS; i++) {
            GlStateManager.pushMatrix();

            if (direction == Direction.NORTH || direction == Direction.SOUTH) {
                GlStateManager.translatef((float)x + i * 0.1875f + 0.21875f, (float)y + 0.3125f + i * 0.001f, (float)z + 0.5f);
            } else {
                GlStateManager.translatef((float)x + 0.5f, (float)y + 0.3125f + i * 0.001f, (float)z + i * 0.1875f + 0.21875f);
                GlStateManager.rotatef(90, 0.0F, 1.0F, 0.0F);
            }

            GlStateManager.rotatef(90, 1.0f, 0.0f, 0.0f);
            GlStateManager.scalef(0.3f, 0.3f, 0.3f);

            Minecraft.getInstance().getItemRenderer().renderItem(tileEntityIn.getInventory().getStackInSlot(i),
                    ItemCameraTransforms.TransformType.FIXED);

            GlStateManager.enableLighting();
            GlStateManager.popMatrix();
        }
    }
}
