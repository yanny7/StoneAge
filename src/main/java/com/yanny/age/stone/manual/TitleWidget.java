package com.yanny.age.stone.manual;

import com.google.gson.JsonObject;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.gui.screen.Screen;

public class TitleWidget extends Widget {
    public static final String TYPE = "title";

    protected final String text;
    protected final int color;
    protected final float scale;
    protected final int tmpWidth;
    protected final int tmpHeight;

    public TitleWidget(JsonObject object, IPage page, IManual manual) {
        text = Utils.get(String.class, manual, object, "text", "<UNSET>", false);
        scale = Utils.get(Float.class, manual, object, "scale", 1.0f, true);
        color = Utils.get(Integer.class, manual, object, "color", -1, true);
        tmpWidth = Utils.get(Integer.class, manual, object, "width", DYNAMIC, true);
        tmpHeight = Utils.get(Integer.class, manual, object, "height", DYNAMIC, true);
    }

    @Override
    public int getMinWidth(int height) {
        return Math.max(tmpWidth, DYNAMIC);
    }

    @Override
    public int getMinHeight(int width) {
        return Math.max(tmpHeight, Math.round(mc.fontRenderer.FONT_HEIGHT * scale));
    }

    @Override
    public void drawBackgroundLayer(Screen screen, int mx, int my) {
        GlStateManager.pushMatrix();
        GlStateManager.translatef(x, y, 0.0f);
        GlStateManager.scalef(scale, scale, 1.0f);
        screen.drawCenteredString(mc.fontRenderer, text, Math.round((width / scale) / 2f), 0, color);
        GlStateManager.popMatrix();
    }
}
