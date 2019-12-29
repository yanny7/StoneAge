package com.yanny.age.stone.manual;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;

public abstract class Widget {
    protected Minecraft mc = Minecraft.getInstance();

    protected int x, y, width, height;
    protected boolean enabled;
    protected boolean visible;

    public Widget(int width, int height) {
        this.width = width;
        this.height = height;
        this.enabled = true;
        this.visible = true;
    }

    public abstract void draw(AbstractGui screen, int mx, int my);

    public boolean mouseClicked(int mx, int my, int key) {
        return false;
    }

    public void mouseMoved(int mx, int my) {}

    public void keyTyped(char c, int code) {}

    public void setPos(int x, int y) {
        this.x = x;
        this.y = y;
    }

    final boolean inBounds(int mx, int my) {
        return mx >= x && my >= y && mx < x + width && my < y + height;
    }
}
