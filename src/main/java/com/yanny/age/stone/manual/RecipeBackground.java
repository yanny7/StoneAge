package com.yanny.age.stone.manual;

import net.minecraft.util.ResourceLocation;

public class RecipeBackground {
    final ResourceLocation image;
    final float u;
    final float v;
    final int width;
    final int height;
    final int imgW;
    final int imgH;

    public RecipeBackground(ResourceLocation image, float u, float v, int width, int height, int imgW, int imgH) {
        this.image = image;
        this.u = u;
        this.v = v;
        this.width = width;
        this.height = height;
        this.imgW = imgW;
        this.imgH = imgH;
    }
}
