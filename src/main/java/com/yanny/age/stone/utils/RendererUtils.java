package com.yanny.age.stone.utils;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.fluid.Fluid;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ILightReader;

import javax.annotation.Nonnull;

public class RendererUtils {

    public static void add(@Nonnull Fluid fluid, @Nonnull ILightReader lightReader, @Nonnull BlockPos posIn, @Nonnull IVertexBuilder renderer,
                           @Nonnull MatrixStack stack, float x, float y, float z, float u, float v) {
        int i = fluid.getFluid().getAttributes().getColor(lightReader, posIn);
        float alpha = (float)(i >> 24 & 255) / 255.0F;
        float r = (float)(i >> 16 & 255) / 255.0F;
        float g = (float)(i >> 8 & 255) / 255.0F;
        float b = (float)(i & 255) / 255.0F;
        int j = getCombinedAverageLight(lightReader, posIn);
        renderer.pos(stack.getLast().getMatrix(), x, y, z)
                .color(r, g, b, alpha)
                .tex(u, v)
                .lightmap(j & 0xffff, j >> 16 & 0xffff)
                .normal(1, 0, 0)
                .endVertex();
    }

    public static int getCombinedAverageLight(@Nonnull ILightReader lightReaderIn, @Nonnull BlockPos posIn) {
        int i = WorldRenderer.getCombinedLight(lightReaderIn, posIn);
        int j = WorldRenderer.getCombinedLight(lightReaderIn, posIn.up());
        int k = i & 255;
        int l = j & 255;
        int i1 = i >> 16 & 255;
        int j1 = j >> 16 & 255;
        return (Math.max(k, l)) | (Math.max(i1, j1)) << 16;
    }
}
