package com.yanny.age.stone.manual;

import com.google.common.collect.Lists;
import com.google.gson.JsonObject;
import com.mojang.blaze3d.platform.GlStateManager;
import com.yanny.age.stone.ExampleMod;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.fml.client.config.GuiUtils;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;

import static com.yanny.age.stone.manual.ConfigHolder.*;
import static com.yanny.age.stone.manual.ConfigHolder.HEIGHT;

public class ItemWidget extends Widget {
    public static final String TYPE = "item";
    private static final int ITEM_WIDTH = 16;

    protected final float scale;
    protected final List<String> text;
    protected final int tmpWidth;
    protected final int tmpHeight;
    protected final int margin;
    protected final ItemStack item;

    public ItemWidget(JsonObject object, IPage page, IManual manual) {
        ConfigHolder holder = new ConfigHolder(SCALE, WIDTH, HEIGHT, ITEM);
        holder.Load(object, manual);

        scale = holder.getValue(SCALE);
        tmpWidth = holder.getValue(WIDTH);
        tmpHeight = holder.getValue(HEIGHT);
        margin = Utils.get(Integer.class, manual, object, "margin", 0, true);
        item = holder.getValue(ITEM);

        text = Lists.newArrayList();
        List<ITextComponent> list = item.getTooltip(ExampleMod.proxy.getClientPlayer(), ITooltipFlag.TooltipFlags.NORMAL);
        for(ITextComponent itextcomponent : list) {
            text.add(itextcomponent.getFormattedText());
        }
    }

    @Override
    public int getMinWidth(int height) {
        return Math.max(tmpWidth, Math.round(ITEM_WIDTH * scale)) + (2 * margin);
    }

    @Override
    public int getMinHeight(int width) {
        return Math.max(tmpHeight, Math.round(ITEM_WIDTH * scale)) + (2 * margin);
    }

    @Override
    public void drawBackgroundLayer(Screen screen, int mx, int my) {
        GlStateManager.pushTextureAttributes();
        GlStateManager.pushLightingAttributes();
        GlStateManager.pushMatrix();
        GlStateManager.translatef(x + (width - ITEM_WIDTH - margin) / 2f, y + (height - ITEM_WIDTH - margin) / 2f, 0.0f);
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
