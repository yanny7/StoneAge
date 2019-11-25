package com.yanny.age.stone.client.renderer;

import com.mojang.blaze3d.platform.GlStateManager;
import com.yanny.age.stone.blocks.FlintWorkbenchTileEntity;
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
    public void render(FlintWorkbenchTileEntity tileEntityIn, double dx, double dy, double dz, float partialTicks, int destroyStage) {
        Direction direction = tileEntityIn.getBlockState().get(HorizontalBlock.HORIZONTAL_FACING);
        float border = 0.0625f;
        float part = (1 - 4 * border) / 3f;
        float t = border + part / 2f;
        float off = part + border;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                int x = 0;
                int y = 0;

                // handle rotation
                switch (direction) {
                    case NORTH:
                        x = 2 - j;
                        y = 2 - i;
                        break;
                    case SOUTH:
                        x = j;
                        y = i;
                        break;
                    case WEST:
                        x = 2 - i;
                        y = j;
                        break;
                    case EAST:
                        x = i;
                        y = 2 - j;
                        break;
                }

                GlStateManager.pushMatrix();
                GlStateManager.translatef((float)dx + off * x + t, (float)dy + 0.125f, (float)dz + off * y + t);

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
