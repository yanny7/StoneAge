package com.yanny.age.stone.manual;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;

public abstract class Widget {
    public static final int DYNAMIC = -1;

    protected final Minecraft mc = Minecraft.getInstance();

    private int x, y, width, height;
    protected final boolean enabled;
    protected final boolean visible;

    public Widget() {
        this.enabled = true;
        this.visible = true;
        x = 0;
        y = 0;
        width = DYNAMIC;
        height = DYNAMIC;
    }

    public void drawBackgroundLayer(Screen screen, int mx, int my) {}

    public void render(Screen screen, int mx, int my) {}

    public boolean mouseClicked(int mx, int my, int key) {
        return false;
    }

    public void mouseMoved(int mx, int my) {}

    public void setPos(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setSize(int width, int height) {
        setWidth(width);
        setHeight(height);
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getMinWidth(int height) {
        return DYNAMIC;
    }

    public int getMinHeight(int width) {
        return DYNAMIC;
    }

    public boolean inBounds(int mx, int my) {
        return mx >= x && my >= y && mx <= x + width && my <= y + height;
    }
}
