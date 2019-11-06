package com.yanny.age.zero.client.renderer;

import com.mojang.blaze3d.platform.GlStateManager;
import com.yanny.age.zero.blocks.FlintWorkbenchTileEntity;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.util.Direction;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class FlintWorkbenchRenderer extends TileEntityRenderer<FlintWorkbenchTileEntity> {
    @SuppressWarnings("deprecation")
    @Override
    public void render(FlintWorkbenchTileEntity tileEntityIn, double x, double y, double z, float partialTicks, int destroyStage) {
        Direction direction = tileEntityIn.getBlockState().get(HorizontalBlock.HORIZONTAL_FACING);
        float border = 0.0625f;
        float part = (1 - 4 * border) / 3f;
        float t = border + part / 2f;
        float off = part + border;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                int m = 0;
                int n = 0;

                // handle rotation
                switch (direction) {
                    case NORTH:
                        m = 2 - i;
                        n = 2 - j;
                        break;
                    case SOUTH:
                        m = i;
                        n = j;
                        break;
                    case WEST:
                        m = 2 - i;
                        n = j;
                        break;
                    case EAST:
                        m = i;
                        n = 2 - j;
                        break;
                }

                GlStateManager.pushMatrix();
                GlStateManager.translatef((float)x + off * m + t, (float)y + 0.125f, (float)z + off * n + t);

                switch (direction) {
                    case SOUTH:
                        GlStateManager.rotatef(180f, 0.0F, 1.0F, 0.0F);
                        break;
                    case WEST:
                        GlStateManager.rotatef(90f, 0.0F, 1.0F, 0.0F);
                        break;
                    case EAST:
                        GlStateManager.rotatef(270f, 0.0F, 1.0F, 0.0F);
                        break;
                }

                GlStateManager.rotatef(90.0F, 1.0F, 0.0F, 0.0F);
                GlStateManager.scalef(0.25F, 0.25F, 0.25F);
                Minecraft.getInstance().getItemRenderer().renderItem(tileEntityIn.getInventory().getStackInSlot(i * 3 + j),
                        ItemCameraTransforms.TransformType.FIXED);
                GlStateManager.enableLighting();
                GlStateManager.popMatrix();
            }
        }
    }
}
