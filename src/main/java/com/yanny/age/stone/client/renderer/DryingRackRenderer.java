package com.yanny.age.stone.client.renderer;

import com.mojang.blaze3d.platform.GlStateManager;
import com.yanny.age.stone.blocks.DryingRackTileEntity;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.util.Direction;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class DryingRackRenderer extends TileEntityRenderer<DryingRackTileEntity> {
    @SuppressWarnings("deprecation")
    @Override
    public void render(DryingRackTileEntity tileEntityIn, double x, double y, double z, float partialTicks, int destroyStage) {
        Direction direction = tileEntityIn.getBlockState().get(HorizontalBlock.HORIZONTAL_FACING);

        for (int i = 0; i < DryingRackTileEntity.ITEMS; i++) {
            float xOffset = (direction == Direction.EAST || direction == Direction.WEST) ? ((i / 2 == 0) ? 0.46f : 0.54f) : ((i % 2 == 0) ? 0.35f : 0.65f);
            float zOffset = (direction == Direction.NORTH || direction == Direction.SOUTH) ? ((i / 2 == 0) ? 0.46f : 0.54f) : ((i % 2 == 0) ? 0.35f : 0.65f);

            GlStateManager.pushMatrix();
            GlStateManager.translatef((float)x + xOffset, (float)y + 14f / 16f, (float)z + zOffset);
            GlStateManager.rotatef((direction == Direction.EAST || direction == Direction.WEST) ? 90 : 0, 0.0F, 1.0F, 0.0F);
            GlStateManager.scalef(0.25F, 0.25F, 0.25F);

            Minecraft.getInstance().getItemRenderer().renderItem(tileEntityIn.getInventory().getStackInSlot(i),
                    ItemCameraTransforms.TransformType.FIXED);
            Minecraft.getInstance().getItemRenderer().renderItem(tileEntityIn.getInventory().getStackInSlot(i + DryingRackTileEntity.ITEMS),
                    ItemCameraTransforms.TransformType.FIXED);

            GlStateManager.enableLighting();
            GlStateManager.popMatrix();
        }
    }
}
