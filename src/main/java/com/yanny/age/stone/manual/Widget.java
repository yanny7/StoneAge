package com.yanny.age.stone.manual;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;

public abstract class Widget {
    public static final int DYNAMIC = -1;

    protected final Minecraft mc = Minecraft.getInstance();

    protected int x, y, width, height;
    protected boolean enabled;
    protected boolean visible;

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

    public void keyTyped(char c, int code) {}

    public void setPos(int x, int y) {
        this.x = x;
        this.y = y;
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
