package com.yanny.age.stone.manual;

import com.google.common.collect.Lists;
import com.google.gson.JsonObject;
import com.mojang.blaze3d.platform.GlStateManager;
import com.yanny.age.stone.ExampleMod;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.fml.client.config.GuiUtils;

import java.util.List;

import static com.yanny.age.stone.manual.ConfigHolder.*;
import static com.yanny.age.stone.manual.ConfigHolder.HEIGHT;

public class ItemWidget extends ConfigurableWidget {
    public static final String TYPE = "item";
    private static final int ITEM_WIDTH = 16;

    protected final float scale;
    protected final List<String> text;
    protected final ItemStack item;
    protected final int margin_top;
    protected final int margin_bottom;
    protected final int tmpMarginLeft;
    protected final int tmpMarginRight;
    protected final Align align;

    protected int margin_left;

    public ItemWidget(JsonObject object, IManual manual) {
        super(object, manual, SCALE, WIDTH, HEIGHT, ITEM, MARGIN_TOP, MARGIN_LEFT, MARGIN_BOTTOM, MARGIN_RIGHT, ALIGN_CENTER);

        scale = configHolder.getValue(SCALE);
        item = configHolder.getValue(ITEM);
        margin_top = configHolder.getValue(MARGIN_TOP);
        tmpMarginLeft = configHolder.getValue(MARGIN_LEFT);
        margin_bottom = configHolder.getValue(MARGIN_BOTTOM);
        tmpMarginRight = configHolder.getValue(MARGIN_RIGHT);
        align = configHolder.getValue(ALIGN_CENTER);

        text = Lists.newArrayList();
        List<ITextComponent> list = item.getTooltip(ExampleMod.proxy.getClientPlayer(), ITooltipFlag.TooltipFlags.NORMAL);
        for(ITextComponent itextcomponent : list) {
            text.add(itextcomponent.getFormattedText());
        }
    }

    @Override
    public int getMinWidth(int height) {
        return Math.round(ITEM_WIDTH * scale) + Math.max(tmpMarginLeft, 0) + Math.max(tmpMarginRight, 0);
    }

    @Override
    public int getMinHeight(int width) {
        return Math.round(ITEM_WIDTH * scale) + margin_top + margin_bottom;
    }

    @Override
    public void setWidth(int width) {
        int minWidth = getMinWidth(0);

        if (minWidth < width) {
            switch (align) {
                case CENTER:
                    if (tmpMarginLeft < 0) {
                        if (tmpMarginRight < 0) {
                            margin_left = (width - minWidth) / 2;
                        } else {
                            margin_left = (width - minWidth - tmpMarginRight);
                        }
                    } else {
                        margin_left = tmpMarginLeft;
                    }
                    break;
                case RIGHT:
                    if (tmpMarginLeft < 0) {
                        margin_left = width - minWidth;
                    } else {
                        margin_left = tmpMarginLeft;
                    }
                    break;
                case LEFT:
                    margin_left = Math.max(tmpMarginLeft, 0);
                    break;
            }
        }

        super.setWidth(width);
    }

    @Override
    public void drawBackgroundLayer(Screen screen, int mx, int my) {
        GlStateManager.pushTextureAttributes();
        GlStateManager.pushLightingAttributes();
        GlStateManager.pushMatrix();
        GlStateManager.translatef(getX() + margin_left, getY() + margin_top, 0.0f);
        GlStateManager.scalef(scale, scale, 1.0f);
        GlStateManager.enableRescaleNormal();
        RenderHelper.enableGUIStandardItemLighting();
        mc.getItemRenderer().renderItemIntoGUI(item, 0, 0);
        RenderHelper.disableStandardItemLighting();
        GlStateManager.disableRescaleNormal();
        GlStateManager.popMatrix();
        GlStateManager.popAttributes();
        GlStateManager.popAttributes();
    }

    @Override
    public void render(Screen screen, int mx, int my) {
        if (inBounds(mx, my)) {
            GuiUtils.drawHoveringText(text, mx, my, screen.width, screen.height, -1, mc.fontRenderer);
        }
    }
}
