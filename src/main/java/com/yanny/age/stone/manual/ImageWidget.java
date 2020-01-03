package com.yanny.age.stone.manual;

import com.google.gson.JsonObject;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.ResourceLocation;

public class ImageWidget extends Widget {
    public static final String TYPE = "image";

    protected final ResourceLocation imgRes;
    protected final float scale;
    protected final int tmpWidth;
    protected final int tmpHeight;
    protected final int imgWidth;
    protected final int imgHeight;
    protected final int u;
    protected final int v;
    protected final int margin;

    public ImageWidget(JsonObject object, IPage page, IManual manual) {
        scale = Utils.get(Float.class, manual, object, "scale", 1.0f, true);
        tmpWidth = Utils.get(Integer.class, manual, object, "width", 16, false);
        tmpHeight = Utils.get(Integer.class, manual, object, "height", 16, false);
        imgWidth = Utils.get(Integer.class, manual, object, "w", 16, false);
        imgHeight = Utils.get(Integer.class, manual, object, "h", 16, false);
        u = Utils.get(Integer.class, manual, object, "u", 0, true);
        v = Utils.get(Integer.class, manual, object, "v", 0, true);
        margin = Utils.get(Integer.class, manual, object, "margin", 0, true);

        imgRes = new ResourceLocation(Utils.get(String.class, manual, object, "path", "minecraft:textures/block/stone.png", false));
    }

    @Override
    public void drawBackgroundLayer(Screen screen, int mx, int my) {
        mc.getTextureManager().bindTexture(imgRes);

        GlStateManager.pushMatrix();
        GlStateManager.translatef(x + margin, y + margin, 0.0f);
        GlStateManager.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        AbstractGui.blit(0, 0, 0, u, v, tmpWidth, tmpHeight, imgWidth, imgHeight);
        GlStateManager.popMatrix();
    }
}
