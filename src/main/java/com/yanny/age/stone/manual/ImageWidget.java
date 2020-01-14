package com.yanny.age.stone.manual;

import com.google.gson.JsonObject;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.ResourceLocation;

import static com.yanny.age.stone.manual.ConfigHolder.*;

public class ImageWidget extends MarginWidget {
    public static final String TYPE = "image";

    protected final ResourceLocation imgRes;
    protected final float scale;
    protected final int tmpWidth;
    protected final int tmpHeight;
    protected final int imgWidth;
    protected final int imgHeight;
    protected final int u;
    protected final int v;

    public ImageWidget(JsonObject object, IManual manual) {
        super(object, manual, SCALE, WIDTH, HEIGHT, IMG_WIDTH, IMG_HEIGHT, IMG_U, IMG_V, IMAGE);

        scale = configHolder.getValue(SCALE);
        tmpWidth = configHolder.getValue(WIDTH);
        tmpHeight = configHolder.getValue(HEIGHT);
        imgWidth = configHolder.getValue(IMG_WIDTH);
        imgHeight = configHolder.getValue(IMG_HEIGHT);
        u = configHolder.getValue(IMG_U);
        v = configHolder.getValue(IMG_V);
        imgRes = configHolder.getValue(IMAGE);
    }

    @Override
    int getRawWidth() {
        return Math.round(tmpWidth <= 0 ? imgWidth * scale : tmpWidth * scale) + Math.max(getRawMarginLeft(), 0) + Math.max(getRawMarginRight(), 0);
    }

    @Override
    public int getMinWidth(int height) {
        return getRawWidth();
    }

    @Override
    public int getMinHeight(int width) {
        return Math.round(tmpHeight <= 0 ? imgHeight * scale : tmpHeight * scale) + getMarginTop() + getMarginBottom();
    }

    @Override
    public void drawBackgroundLayer(Screen screen, int mx, int my) {
        mc.getTextureManager().bindTexture(imgRes);

        GlStateManager.pushMatrix();
        GlStateManager.translatef(getX() + getMarginLeft(), getY() + getMarginTop(), 0.0f);
        GlStateManager.scalef(scale, scale, 0);
        GlStateManager.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        AbstractGui.blit(0, 0, 0, u, v, tmpWidth, tmpHeight, imgWidth, imgHeight);
        GlStateManager.popMatrix();
    }
}
