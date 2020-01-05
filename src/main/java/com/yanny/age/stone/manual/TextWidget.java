package com.yanny.age.stone.manual;

import com.google.gson.JsonObject;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.gui.screen.Screen;

import java.util.ArrayList;
import java.util.List;

import static com.yanny.age.stone.manual.ConfigHolder.*;
import static com.yanny.age.stone.manual.ConfigHolder.HEIGHT;

public class TextWidget extends Widget {
    public static final String TYPE = "text";

    protected final IManual manual;
    protected final String text;
    protected final List<CustomFontRenderer.Link> links = new ArrayList<>();
    protected final CustomFontRenderer customFontRenderer;

    protected final int color;
    protected final float scale;
    protected final int tmpWidth;
    protected final int tmpHeight;
    protected final int margin_top;
    protected final int margin_left;
    protected final int margin_bottom;
    protected final int margin_right;
    protected final Align align;
    protected final boolean justify;

    public TextWidget(JsonObject object, IManual manual) {
        ConfigHolder holder = new ConfigHolder(TEXT, SCALE, COLOR, WIDTH, HEIGHT, MARGIN_TOP, MARGIN_LEFT, MARGIN_BOTTOM, MARGIN_RIGHT, ALIGN_LEFT, JUSTIFY);
        holder.loadConfig(object, manual);
        this.manual = manual;

        text = holder.getValue(TEXT);
        color = holder.getValue(COLOR);
        scale = holder.getValue(SCALE);
        tmpWidth = holder.getValue(WIDTH);
        tmpHeight = holder.getValue(HEIGHT);
        margin_top = holder.getValue(MARGIN_TOP);
        margin_left = holder.getValue(MARGIN_LEFT);
        margin_bottom = holder.getValue(MARGIN_BOTTOM);
        margin_right = holder.getValue(MARGIN_RIGHT);
        align = holder.getValue(ALIGN_LEFT);
        justify = holder.getValue(JUSTIFY);

        customFontRenderer = new CustomFontRenderer(mc.fontRenderer);
    }

    @Override
    public int getMinWidth(int height) {
        return Math.max(tmpWidth, DYNAMIC);
    }

    @Override
    public int getMinHeight(int width) {
        return Math.max(tmpHeight, Math.round(getTextHeight(Math.round((width - margin_left - margin_right) / scale)) * scale) + margin_top + margin_bottom);
    }

    @Override
    public void setPos(int x, int y) {
        super.setPos(x, y);
        links.clear();
        links.addAll(customFontRenderer.analyseSplitStringLinks(text, 0, 0, Math.round((width - margin_left - margin_right) / scale), align));
    }

    @Override
    public void drawBackgroundLayer(Screen screen, int mx, int my) {
        GlStateManager.pushMatrix();
        GlStateManager.translatef(x + margin_left, y + margin_top, 0.0f);
        GlStateManager.scalef(scale, scale, 1.0f);
        customFontRenderer.drawSplitString(text, 0, 0, Math.round((width - margin_left - margin_right) / scale), color, align);
        GlStateManager.popMatrix();

        GlStateManager.pushMatrix();
        GlStateManager.translatef(x + margin_left, y + margin_top, 0.0f);
        GlStateManager.scalef(scale, scale, 1.0f);
        links.forEach(link -> {
            if (link.inArea(mx - (x + margin_left), my - (y + margin_top), scale)) {
                link.rects.forEach(rect -> Screen.fill((int) rect.x1, (int) rect.y1, (int) rect.x2, (int) rect.y2, 0x66000000));
            }
        });
        GlStateManager.popMatrix();
    }

    @Override
    public boolean mouseClicked(int mx, int my, int key) {
        for (CustomFontRenderer.Link link : links) {
            if (link.inArea(mx - (x + margin_left), my - (y + margin_top), scale)) {
                manual.changePage(link.key);
                return true;
            }
        }

        return false;
    }

    private int getTextHeight(int width) {
        return customFontRenderer.getWordWrappedHeight(text, width);
    }
}
