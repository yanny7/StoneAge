package com.yanny.age.stone.client.renderer;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.yanny.age.stone.blocks.TanningRackTileEntity;
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

import static net.minecraft.client.renderer.model.ItemCameraTransforms.TransformType;

@OnlyIn(Dist.CLIENT)
public class TanningRackRenderer extends TileEntityRenderer<TanningRackTileEntity> {
    public TanningRackRenderer(TileEntityRendererDispatcher rendererDispatcher) {
        super(rendererDispatcher);
    }

    @Override
    public void render(@Nonnull TanningRackTileEntity tileEntity, float partialTicks, @Nonnull MatrixStack matrixStack,
                       @Nonnull IRenderTypeBuffer renderTypeBuffer, int overlayUV, int lightmapUV) {
        Direction direction = tileEntity.getBlockState().get(HorizontalBlock.HORIZONTAL_FACING);

        for (int i = 0; i < TanningRackTileEntity.ITEMS; i++) {
            boolean n = direction == Direction.NORTH;
            boolean s = direction == Direction.SOUTH;
            boolean e = direction == Direction.EAST;
            boolean w = direction == Direction.WEST;
            float xOffset = (w || e) ? (w ? 0.3f : 0.7f) : 0.5f;
            float zOffset = (n || s) ? (n ? 0.3f : 0.7f) : 0.5f;

            matrixStack.push();
            matrixStack.translate(xOffset, 0.5f, zOffset);
            matrixStack.rotate(Vector3f.YP.rotationDegrees((e || w) ? 90 : 0));
            matrixStack.rotate(Vector3f.XP.rotationDegrees((n || w) ? 22.5f : -22.5f));
            matrixStack.scale(0.9f, 0.9f, 0.9f);

            Minecraft.getInstance().getItemRenderer().renderItem(tileEntity.getInventory().getStackInSlot(i), TransformType.FIXED,
                    overlayUV, lightmapUV, matrixStack, renderTypeBuffer);
            Minecraft.getInstance().getItemRenderer().renderItem(tileEntity.getInventory().getStackInSlot(i + TanningRackTileEntity.ITEMS), TransformType.FIXED,
                    overlayUV, lightmapUV, matrixStack, renderTypeBuffer);

            matrixStack.pop();
        }
    }
}
