package com.yanny.age.stone.client.renderer;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.yanny.age.stone.blocks.TreeStumpTileEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.Vector3f;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;

import static net.minecraft.client.renderer.model.ItemCameraTransforms.TransformType;

@OnlyIn(Dist.CLIENT)
public class TreeStumpRenderer extends TileEntityRenderer<TreeStumpTileEntity> {
    public TreeStumpRenderer(TileEntityRendererDispatcher rendererDispatcher) {
        super(rendererDispatcher);
    }

    @Override
    public void render(@Nonnull TreeStumpTileEntity tileEntity, float partialTicks, @Nonnull MatrixStack matrixStack,
                       @Nonnull IRenderTypeBuffer renderTypeBuffer, int overlayUV, int lightmapUV) {
        matrixStack.push();
        matrixStack.translate(0.5, 0.75, 0.5);
        matrixStack.rotate(Vector3f.XP.rotationDegrees(90));
        matrixStack.scale(0.7f, 0.7f, 0.7f);

        Minecraft.getInstance().getItemRenderer().renderItem(tileEntity.getInventory().getStackInSlot(0), TransformType.FIXED,
                overlayUV, lightmapUV, matrixStack, renderTypeBuffer);

        matrixStack.pop();
    }
}
