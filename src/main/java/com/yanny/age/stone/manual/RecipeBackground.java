package com.yanny.age.stone.manual;

import net.minecraft.util.ResourceLocation;

public class RecipeBackground {
    final ResourceLocation image;
    final float u;
    final float v;
    final int imgW;
    final int imgH;

    public RecipeBackground(ResourceLocation image, float u, float v, int imgW, int imgH) {
        this.image = image;
        this.u = u;
        this.v = v;
        this.imgW = imgW;
        this.imgH = imgH;
    }
}
