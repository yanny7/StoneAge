package com.yanny.age.stone.manual;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

public class ButtonWidget extends Widget {
    public static final ResourceLocation WIDGETS_LOCATION = new ResourceLocation("textures/gui/widgets.png");

    final String text;
    final OnClick onClick;
    boolean isHovered = false;
    boolean isActive = true;

    ButtonWidget(@Nonnull String text, @Nonnull OnClick onClick) {
        this.text = text;
        this.onClick = onClick;
    }

    @Override
    public void drawBackgroundLayer(Screen screen, int mx, int my) {
        int i = getYImage();

        mc.getTextureManager().bindTexture(WIDGETS_LOCATION);
        GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0f);
        GlStateManager.enableBlend();
        GlStateManager.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        screen.blit(getX(), getY(), 0, 46 + i * 20, getWidth() / 2, getHeight());
        screen.blit(getX() + getWidth() / 2, getY(), 200 - getWidth() / 2, 46 + i * 20, getWidth() / 2, getHeight());
        screen.drawCenteredString(mc.fontRenderer, text, getX() + getWidth() / 2, getY() + (getHeight() - 8) / 2, -1);
    }

    @Override
    public boolean mouseClicked(int mx, int my, int key) {
        if (inBounds(mx, my)) {
            onClick.onClick();
            return true;
        }

        return false;
    }

    @Override
    public void mouseMoved(int mx, int my) {
        isHovered = inBounds(mx, my);
    }

    protected int getYImage() {
        int i = 1;

        if (!isActive) {
            i = 0;
        } else if (isHovered) {
            i = 2;
        }

        return i;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    interface OnClick {
        void onClick();
    }
}
