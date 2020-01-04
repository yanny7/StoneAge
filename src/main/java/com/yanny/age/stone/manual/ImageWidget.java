package com.yanny.age.stone.manual;

import com.google.gson.JsonObject;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.ResourceLocation;

import static com.yanny.age.stone.manual.ConfigHolder.*;

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
        ConfigHolder holder = new ConfigHolder(SCALE, WIDTH, HEIGHT, ITEM, IMG_WIDTH, IMG_HEIGHT, IMG_U, IMG_V, IMAGE);
        holder.Load(object, manual);

        scale = holder.getValue(SCALE);
        tmpWidth = holder.getValue(WIDTH);
        tmpHeight = holder.getValue(HEIGHT);
        imgWidth = holder.getValue(IMG_WIDTH);
        imgHeight = holder.getValue(IMG_HEIGHT);
        u = holder.getValue(IMG_U);
        v = holder.getValue(IMG_V);

        margin = Utils.get(Integer.class, manual, object, "margin", 0, true);

        imgRes = new ResourceLocation(holder.getValue(IMAGE));
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
