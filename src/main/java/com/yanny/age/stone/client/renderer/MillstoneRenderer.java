package com.yanny.age.stone.client.renderer;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.yanny.age.stone.Reference;
import com.yanny.age.stone.blocks.MillstoneTileEntity;
import com.yanny.age.stone.client.models.MillstoneModel;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;

@OnlyIn(Dist.CLIENT)
public class MillstoneRenderer extends TileEntityRenderer<MillstoneTileEntity> {
    private static final ResourceLocation TEXTURE_NORMAL = new ResourceLocation(Reference.MODID, "textures/entity/millstone.png");
    private final MillstoneModel model = new MillstoneModel();

    public MillstoneRenderer(TileEntityRendererDispatcher rendererDispatcher) {
        super(rendererDispatcher);
    }

    @Override
    public void render(@Nonnull MillstoneTileEntity tileEntity, float partialTicks, @Nonnull MatrixStack matrixStack,
                       @Nonnull IRenderTypeBuffer renderTypeBuffer, int overlayUV, int lightmapUV) {
        matrixStack.push();
        matrixStack.translate(0.5, 0.05 * (1 / 16.0), 0.5);
        model.rotate(tileEntity.rotateAngle());
        model.render(matrixStack, renderTypeBuffer.getBuffer(RenderType.getEntityTranslucentCull(TEXTURE_NORMAL)), overlayUV, lightmapUV, 1f, 1f, 1f, 1f);
        matrixStack.pop();
    }
}
