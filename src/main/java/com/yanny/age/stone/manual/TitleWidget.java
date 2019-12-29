package com.yanny.age.stone.manual;

import com.google.gson.JsonObject;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.gui.AbstractGui;

public class TitleWidget extends Widget {
    public static final String TYPE = "title";

    protected final PageWidget page;
    protected final String text;
    protected final int color;
    protected final float scale;

    public TitleWidget(PageWidget page, JsonObject object) {
        super(page.width, 0);
        this.page = page;

        text = Utils.getString(object, "text", "<UNSET>", false);
        scale = Utils.getReal(object, "scale", 1.0, true).floatValue();
        color = Utils.getInt(object, "color", -1, true);

        String key = Utils.getString(object, "key", null, true);
        if (key != null) {
            page.addLink(key);
        }

        height = Math.round(mc.fontRenderer.FONT_HEIGHT * scale);
    }

    @Override
    public void draw(AbstractGui screen, int mx, int my) {
        GlStateManager.pushMatrix();
        GlStateManager.translatef(x, y, 0.0f);
        GlStateManager.scalef(scale, scale, 1.0f);
        screen.drawCenteredString(mc.fontRenderer, text, Math.round((width / scale) / 2f), 0, color);
        GlStateManager.popMatrix();
    }
}
