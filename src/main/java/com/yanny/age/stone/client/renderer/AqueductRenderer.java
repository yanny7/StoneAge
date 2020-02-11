package com.yanny.age.stone.client.renderer;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.yanny.age.stone.blocks.AqueductTileEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.Matrix3f;
import net.minecraft.client.renderer.Matrix4f;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;
import java.util.Objects;

@OnlyIn(Dist.CLIENT)
public class AqueductRenderer extends TileEntityRenderer<AqueductTileEntity> {
    private static final ResourceLocation WATER = new ResourceLocation("minecraft", "textures/block/water_still.png");

    public AqueductRenderer(TileEntityRendererDispatcher p_i226006_1_) {
        super(p_i226006_1_);
    }

    @Override
    public void render(AqueductTileEntity tileEntityIn, float partialTicks, @Nonnull MatrixStack matrixStack,
                       @Nonnull IRenderTypeBuffer renderTypeBuffer, int overlayUV, int lightmapUV) {
        if (!tileEntityIn.hasWorld()) { //TODO rendering not working
            return;
        }

        if (tileEntityIn.getCapacity() < 0.01) {
            return;
        }

        float off;
        int tick = tileEntityIn.getTick();
        float v = tileEntityIn.getCapacity() * (10 / 16f) + 4 / 16f;

        off = (tick % 32) * (1 / 32f);

        matrixStack.push();

        RenderSystem.enableBlend();
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0f);
        RenderSystem.defaultBlendFunc();
        RenderSystem.disableTexture();

        IVertexBuilder vertexBuilder = renderTypeBuffer.getBuffer(RenderType.entityTranslucentCull(WATER));
        MatrixStack.Entry matrix = matrixStack.getLast();
        Matrix4f matrix4f = matrix.getPositionMatrix();
        Matrix3f matrix3f = matrix.getNormalMatrix();

        func_229132_a_(tileEntityIn, vertexBuilder, matrix4f, matrix3f, 0, v, 1, 0, off + 1/32f, overlayUV, lightmapUV);
        func_229132_a_(tileEntityIn, vertexBuilder, matrix4f, matrix3f, 1, v, 1, 1, off + 1/32f, overlayUV, lightmapUV);
        func_229132_a_(tileEntityIn, vertexBuilder, matrix4f, matrix3f, 1, v, 0, 1, off, overlayUV, lightmapUV);
        func_229132_a_(tileEntityIn, vertexBuilder, matrix4f, matrix3f, 0, v, 0, 0, off, overlayUV, lightmapUV);

        matrixStack.pop();
    }

    private static void func_229132_a_(TileEntity tileEntity, IVertexBuilder vertexBuilder, Matrix4f matrix4f, Matrix3f matrix3f,
                                       float x, float y, float z, float u, float v, int overlayUV, int lightmapUV) {
        int color = Objects.requireNonNull(tileEntity.getWorld()).getBiome(tileEntity.getPos()).getWaterColor();
        float r = (color & 0xff0000) >> 16;
        float g = (color & 0xff00) >> 8;
        float b = (color & 0xff);
        vertexBuilder.pos(matrix4f, x, y, z).color(r / 16f, g / 16f, b / 16f, 1.0f)
                .tex(u, v).overlay(overlayUV).lightmap(lightmapUV).normal(matrix3f, 0.0F, 1.0F, 0.0F).endVertex();
    }
}
