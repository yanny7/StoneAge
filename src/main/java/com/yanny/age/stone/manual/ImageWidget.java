package com.yanny.age.stone.manual;

import com.google.gson.JsonObject;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.ResourceLocation;

import static com.yanny.age.stone.manual.ConfigHolder.*;

public class ImageWidget extends ConfigurableWidget {
    public static final String TYPE = "image";

    protected final ResourceLocation imgRes;
    protected final float scale;
    protected final int tmpWidth;
    protected final int tmpHeight;
    protected final int imgWidth;
    protected final int imgHeight;
    protected final int u;
    protected final int v;
    protected final int margin_top;
    protected final int margin_bottom;
    protected final int tmpMarginLeft;
    protected final int tmpMarginRight;
    protected final Align align;

    protected int margin_left;

    public ImageWidget(JsonObject object, IManual manual) {
        super(object, manual, SCALE, WIDTH, HEIGHT, IMG_WIDTH, IMG_HEIGHT, IMG_U, IMG_V, IMAGE, MARGIN_TOP, MARGIN_LEFT, MARGIN_BOTTOM, MARGIN_RIGHT, ALIGN_CENTER);

        scale = configHolder.getValue(SCALE);
        tmpWidth = configHolder.getValue(WIDTH);
        tmpHeight = configHolder.getValue(HEIGHT);
        imgWidth = configHolder.getValue(IMG_WIDTH);
        imgHeight = configHolder.getValue(IMG_HEIGHT);
        u = configHolder.getValue(IMG_U);
        v = configHolder.getValue(IMG_V);
        imgRes = configHolder.getValue(IMAGE);
        margin_top = configHolder.getValue(MARGIN_TOP);
        tmpMarginLeft = configHolder.getValue(MARGIN_LEFT);
        margin_bottom = configHolder.getValue(MARGIN_BOTTOM);
        tmpMarginRight = configHolder.getValue(MARGIN_RIGHT);
        align = configHolder.getValue(ALIGN_CENTER);
    }

    @Override
    public int getMinWidth(int height) {
        return Math.max(tmpMarginLeft, 0) + Math.max(tmpMarginRight, 0) + (tmpWidth <= 0 ? imgWidth : tmpWidth);
    }

    @Override
    public int getMinHeight(int width) {
        return margin_top + margin_bottom + (tmpHeight <= 0 ? imgHeight : tmpHeight);
    }

    @Override
    public void setWidth(int width) {
        int minWidth = getMinWidth(0);

        if (minWidth < width) {
            switch (align) {
                case CENTER:
                    if (tmpMarginLeft < 0) {
                        if (tmpMarginRight < 0) {
                            margin_left = (width - minWidth) / 2;
                        } else {
                            margin_left = (width - minWidth - tmpMarginRight);
                        }
                    } else {
                        margin_left = tmpMarginLeft;
                    }
                    break;
                case RIGHT:
                    if (tmpMarginLeft < 0) {
                        margin_left = width - minWidth;
                    } else {
                        margin_left = tmpMarginLeft;
                    }
                    break;
                case LEFT:
                    margin_left = Math.max(tmpMarginLeft, 0);
                    break;
            }
        }

        super.setWidth(width);
    }

    @Override
    public void drawBackgroundLayer(Screen screen, int mx, int my) {
        mc.getTextureManager().bindTexture(imgRes);

        GlStateManager.pushMatrix();
        GlStateManager.translatef(getX() + margin_left, getY() + margin_top, 0.0f);
        GlStateManager.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        AbstractGui.blit(0, 0, 0, u, v, tmpWidth, tmpHeight, imgWidth, imgHeight);
        GlStateManager.popMatrix();
    }
}
