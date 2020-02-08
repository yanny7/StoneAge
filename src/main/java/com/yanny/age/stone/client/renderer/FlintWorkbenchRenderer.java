package com.yanny.age.stone.client.renderer;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.yanny.age.stone.blocks.FlintWorkbenchTileEntity;
import com.yanny.ages.api.utils.ItemStackUtils;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.Vector3f;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.util.Direction;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;

import static net.minecraft.client.renderer.model.ItemCameraTransforms.*;

@OnlyIn(Dist.CLIENT)
public class FlintWorkbenchRenderer extends TileEntityRenderer<FlintWorkbenchTileEntity> {
    public FlintWorkbenchRenderer(TileEntityRendererDispatcher rendererDispatcher) {
        super(rendererDispatcher);
    }

    @Override
    public void render(@Nonnull FlintWorkbenchTileEntity tileEntity, float partialTicks, @Nonnull MatrixStack matrixStack,
                       @Nonnull IRenderTypeBuffer renderTypeBuffer, int overlayUV, int lightmapUV) {
        Direction direction = tileEntity.getBlockState().get(HorizontalBlock.HORIZONTAL_FACING);
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

                matrixStack.push();
                matrixStack.translate(off * x + t, 0.125f, off * y + t);

                switch (direction) {
                    case SOUTH:
                        matrixStack.rotate(Vector3f.YP.rotationDegrees(180));
                        break;
                    case WEST:
                        matrixStack.rotate(Vector3f.YP.rotationDegrees(90));
                        break;
                    case EAST:
                        matrixStack.rotate(Vector3f.YP.rotationDegrees(270));
                        break;
                }

                matrixStack.rotate(Vector3f.XP.rotationDegrees(90));
                matrixStack.scale(0.25F, 0.25F, 0.25F);

                Minecraft.getInstance().getItemRenderer().renderItem(tileEntity.getInventory().getStackInSlot(i * 3 + j), TransformType.FIXED,
                        overlayUV, lightmapUV, matrixStack, renderTypeBuffer);

                matrixStack.pop();
            }
        }

        if (!tileEntity.getRecipeOutput().isEmpty()) {
            matrixStack.push();

            switch (direction) {
                case NORTH:
                    matrixStack.translate(0.5f, 0.4f, 0.95f);
                    break;
                case SOUTH:
                    matrixStack.translate(0.5f, 0.4f, 0.05f);
                    matrixStack.rotate(Vector3f.YP.rotationDegrees(180));
                    break;
                case WEST:
                    matrixStack.translate(0.95f, 0.4f, 0.5f);
                    matrixStack.rotate(Vector3f.YP.rotationDegrees(90));
                    break;
                case EAST:
                    matrixStack.translate(0.05f, 0.4f, 0.5f);
                    matrixStack.rotate(Vector3f.YP.rotationDegrees(270));
                    break;
            }

            matrixStack.scale(0.5F, 0.5F, 0.5F);
            ItemStackUtils.renderItem(tileEntity.getRecipeOutput(), TransformType.FIXED, overlayUV, lightmapUV, matrixStack, renderTypeBuffer, 0.6f);

            matrixStack.pop();
        }
    }
}
