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

    public TitleWidget(Widget parent, JsonObject object) {
        text = Utils.getString(object, "text", "<UNSET>", false);
        scale = Utils.getReal(object, "scale", 1.0, true).floatValue();
        color = Utils.getInt(object, "color", -1, true);
        tmpWidth = Utils.getInt(object, "width", DYNAMIC, true);
        tmpHeight = Utils.getInt(object, "height", DYNAMIC, true);

        String key = Utils.getString(object, "key", null, true);
        if (key != null) {
            parent.addLink(key);
        }
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

    @Override
    public void render(Screen screen, int mx, int my) {

    }
}
