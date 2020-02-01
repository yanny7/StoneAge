package com.yanny.age.stone.client.renderer;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.yanny.age.stone.blocks.DryingRackTileEntity;
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
public class DryingRackRenderer extends TileEntityRenderer<DryingRackTileEntity> {
    public DryingRackRenderer(TileEntityRendererDispatcher p_i226006_1_) {
        super(p_i226006_1_);
    }

    @Override
    public void render(@Nonnull DryingRackTileEntity tileEntityIn, float partialTicks, @Nonnull MatrixStack matrixStack,
                       @Nonnull IRenderTypeBuffer renderTypeBuffer, int overlayUV, int lightmapUV) {
        Direction direction = tileEntityIn.getBlockState().get(HorizontalBlock.HORIZONTAL_FACING);

        for (int i = 0; i < DryingRackTileEntity.ITEMS; i++) {
            float xOffset = (direction == Direction.EAST || direction == Direction.WEST) ? ((i / 2 == 0) ? 0.46f : 0.54f) : ((i % 2 == 0) ? 0.35f : 0.65f);
            float zOffset = (direction == Direction.NORTH || direction == Direction.SOUTH) ? ((i / 2 == 0) ? 0.46f : 0.54f) : ((i % 2 == 0) ? 0.35f : 0.65f);

            matrixStack.push();
            matrixStack.translate(xOffset, 14 / 16.0, zOffset);
            matrixStack.rotate(Vector3f.YP.rotationDegrees((direction == Direction.EAST || direction == Direction.WEST) ? 90 : 0));
            matrixStack.scale(0.25F, 0.25F, 0.25F);

            Minecraft.getInstance().getItemRenderer().renderItem(tileEntityIn.getInventory().getStackInSlot(i), TransformType.FIXED,
                    overlayUV, lightmapUV, matrixStack, renderTypeBuffer);
            Minecraft.getInstance().getItemRenderer().renderItem(tileEntityIn.getInventory().getStackInSlot(i + DryingRackTileEntity.ITEMS), TransformType.FIXED,
                    overlayUV, lightmapUV, matrixStack, renderTypeBuffer);

            matrixStack.pop();
        }
    }
}
