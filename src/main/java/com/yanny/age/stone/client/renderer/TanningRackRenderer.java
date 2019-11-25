package com.yanny.age.stone.client.renderer;

import com.mojang.blaze3d.platform.GlStateManager;
import com.yanny.age.stone.blocks.TanningRackTileEntity;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.util.Direction;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class TanningRackRenderer extends TileEntityRenderer<TanningRackTileEntity> {
    @SuppressWarnings("deprecation")
    @Override
    public void render(TanningRackTileEntity tileEntityIn, double x, double y, double z, float partialTicks, int destroyStage) {
        Direction direction = tileEntityIn.getBlockState().get(HorizontalBlock.HORIZONTAL_FACING);

        for (int i = 0; i < TanningRackTileEntity.ITEMS; i++) {
            boolean n = direction == Direction.NORTH;
            boolean s = direction == Direction.SOUTH;
            boolean e = direction == Direction.EAST;
            boolean w = direction == Direction.WEST;
            float xOffset = (w || e) ? (w ? 0.3f : 0.7f) : 0.5f;
            float zOffset = (n || s) ? (n ? 0.3f : 0.7f) : 0.5f;

            GlStateManager.pushMatrix();
            GlStateManager.translatef((float)x + xOffset, (float)y + 0.5f, (float)z + zOffset);
            GlStateManager.rotatef((e || w) ? 90 : 0, 0.0F, 1.0F, 0.0F);
            GlStateManager.rotatef((n || w) ? 22.5f : -22.5f, 1.0f, 0.0f, 0.0f);
            GlStateManager.scalef(0.9f, 0.9f, 0.9f);

            Minecraft.getInstance().getItemRenderer().renderItem(tileEntityIn.getInventory().getStackInSlot(i),
                    ItemCameraTransforms.TransformType.FIXED);
            Minecraft.getInstance().getItemRenderer().renderItem(tileEntityIn.getInventory().getStackInSlot(i + TanningRackTileEntity.ITEMS),
                    ItemCameraTransforms.TransformType.FIXED);

            GlStateManager.enableLighting();
            GlStateManager.popMatrix();
        }
    }
}
