package com.yanny.age.stone.manual;

import com.google.gson.JsonObject;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.gui.screen.Screen;

import static com.yanny.age.stone.manual.ConfigHolder.*;

public class TitleWidget extends Widget {
    public static final String TYPE = "title";

    protected final String text;
    protected final int color;
    protected final float scale;
    protected final int tmpWidth;
    protected final int tmpHeight;
    protected final int margin_top;
    protected final int margin_left;
    protected final int margin_bottom;
    protected final int margin_right;
    protected final Align align;

    public TitleWidget(JsonObject object, IManual manual) {
        ConfigHolder holder = new ConfigHolder(TEXT, SCALE, COLOR, WIDTH, HEIGHT, MARGIN_TOP, MARGIN_LEFT, MARGIN_BOTTOM, MARGIN_RIGHT, ALIGN_CENTER);
        holder.loadConfig(object, manual);

        text = holder.getValue(TEXT);
        color = holder.getValue(COLOR);
        scale = holder.getValue(SCALE);
        tmpWidth = holder.getValue(WIDTH);
        tmpHeight = holder.getValue(HEIGHT);
        margin_top = holder.getValue(MARGIN_TOP);
        margin_left = holder.getValue(MARGIN_LEFT);
        margin_bottom = holder.getValue(MARGIN_BOTTOM);
        margin_right = holder.getValue(MARGIN_RIGHT);
        align = holder.getValue(ALIGN_CENTER);
    }

    @Override
    public int getMinWidth(int height) {
        return Math.max(tmpWidth, DYNAMIC);
    }

    @Override
    public int getMinHeight(int width) {
        return Math.max(tmpHeight, Math.round(mc.fontRenderer.FONT_HEIGHT * scale) + margin_top + margin_bottom);
    }

    @Override
    public void drawBackgroundLayer(Screen screen, int mx, int my) {
        GlStateManager.pushMatrix();
        GlStateManager.translatef(getX() + margin_left, getY() + margin_top, 0.0f);
        GlStateManager.scalef(scale, scale, 1.0f);
        switch (align) {
            case LEFT:
                screen.drawString(mc.fontRenderer, text, 0, 0, color);
                break;
            case RIGHT:
                screen.drawRightAlignedString(mc.fontRenderer, text, Math.round((getWidth() - margin_left - margin_right) / scale), 0, color);
                break;
            case CENTER:
                screen.drawCenteredString(mc.fontRenderer, text, Math.round(((getWidth() - margin_left - margin_right) / scale) / 2f), 0, color);
                break;
        }
        GlStateManager.popMatrix();
    }
}
