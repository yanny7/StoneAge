package com.yanny.age.stone.manual;

import com.google.gson.JsonObject;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.gui.screen.Screen;

import java.util.ArrayList;
import java.util.List;

import static com.yanny.age.stone.manual.ConfigHolder.*;

public class ListWidget extends ConfigurableWidget {
    public static final String TYPE = "list";

    protected final CustomFontRenderer customFontRenderer;
    protected final List<CustomFontRenderer.Link> links = new ArrayList<>();

    protected final int color;
    protected final float scale;
    protected final int tmpWidth;
    protected final int tmpHeight;
    protected final int margin_top;
    protected final int margin_left;
    protected final int margin_bottom;
    protected final int margin_right;
    protected final int bulletWidth;
    protected final String bullet;
    protected final String[] list;
    protected final int[] heights;

    public ListWidget(JsonObject object, IManual manual) {
        super(object, manual, COLOR, SCALE, WIDTH, HEIGHT, MARGIN_TOP, MARGIN_LEFT, MARGIN_BOTTOM, MARGIN_RIGHT, LIST, BULLET);

        color = configHolder.getValue(COLOR);
        scale = configHolder.getValue(SCALE);
        tmpWidth = configHolder.getValue(WIDTH);
        tmpHeight = configHolder.getValue(HEIGHT);
        margin_top = configHolder.getValue(MARGIN_TOP);
        margin_left = configHolder.getValue(MARGIN_LEFT);
        margin_bottom = configHolder.getValue(MARGIN_BOTTOM);
        margin_right = configHolder.getValue(MARGIN_RIGHT);
        list = configHolder.getValue(LIST);
        bullet = configHolder.getValue(BULLET);
        bulletWidth = getBulletWidth(bullet + " ");
        heights = new int[list.length];

        customFontRenderer = new CustomFontRenderer(mc.fontRenderer);
    }

    @Override
    public int getMinWidth(int height) {
        return Math.max(tmpWidth, DYNAMIC);
    }

    @Override
    public int getMinHeight(int width) {
        return Math.max(tmpHeight, Math.round(getTextHeight(Math.round(width - bulletWidth - margin_left - margin_right)) * scale) + margin_top + margin_bottom);
    }

    @Override
    public void setWidth(int width) {
        super.setWidth(width);

        for (int i = 0; i < list.length ;i++) {
            heights[i] = customFontRenderer.getWordWrappedHeight(list[i], Math.round((width - bulletWidth - margin_left - margin_right) / scale));
        }
    }

    @Override
    public void drawBackgroundLayer(Screen screen, int mx, int my) {
        GlStateManager.pushMatrix();
        GlStateManager.translatef(getX() + margin_left, getY() + margin_top, 0.0f);
        GlStateManager.scalef(scale, scale, 1.0f);
        int height = 0;
        for (int i = 0; i < list.length; i++) {
            mc.fontRenderer.drawString(bullet, 0, height, color);
            customFontRenderer.drawSplitString(list[i], bulletWidth, height, Math.round((getWidth() - bulletWidth - margin_left - margin_right) / scale), color, Align.LEFT, false);
            height += heights[i];
        }
        GlStateManager.popMatrix();

        GlStateManager.pushMatrix();
        GlStateManager.translatef(getX() + margin_left, getY() + margin_top, 0.0f);
        GlStateManager.scalef(scale, scale, 1.0f);
        links.forEach(link -> {
            if (link.inArea(mx - (getX() + margin_left), my - (getY() + margin_top), scale)) {
                link.rects.forEach(rect -> Screen.fill((int) rect.x1, (int) rect.y1, (int) rect.x2, (int) rect.y2, 0x66000000));
            }
        });
        GlStateManager.popMatrix();
    }

    @Override
    public boolean mouseClicked(int mx, int my, int key) {
        for (CustomFontRenderer.Link link : links) {
            if (link.inArea(mx - (getX() + margin_left), my - (getY() + margin_top), scale)) {
                manual.changePage(link.key);
                return true;
            }
        }

        return false;
    }

    @Override
    public void setPos(int x, int y) {
        super.setPos(x, y);
        links.clear();

        int height = 0;
        for (int i = 0; i < list.length; i++) {
            links.addAll(customFontRenderer.analyseSplitStringLinks(list[i], bulletWidth, height, Math.round((getWidth() - bulletWidth - margin_left - margin_right) / scale), Align.LEFT, false));
            height += heights[i];
        }
    }

    private int getTextHeight(int width) {
        int height = 0;

        for (String text : list) {
            height += customFontRenderer.getWordWrappedHeight(text, Math.round((width - bulletWidth - margin_left - margin_right) / scale));
        }

        return height;
    }

    private int getBulletWidth(String bullet) {
        return Math.round(mc.fontRenderer.getStringWidth(bullet));
    }
}
