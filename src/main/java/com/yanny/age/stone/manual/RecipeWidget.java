package com.yanny.age.stone.manual;

import com.google.common.collect.Lists;
import com.google.gson.JsonObject;
import com.mojang.blaze3d.platform.GlStateManager;
import com.yanny.age.stone.ExampleMod;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.fml.client.config.GuiUtils;

import java.util.ArrayList;
import java.util.List;

import static com.yanny.age.stone.manual.ConfigHolder.*;
import static com.yanny.age.stone.manual.ConfigHolder.MARGIN_RIGHT;

public class RecipeWidget extends Widget {
    public static final String TYPE = "recipe";
    private static final int ITEM_WIDTH = 16;

    protected final int margin_top;
    protected final int margin_left;
    protected final int margin_bottom;
    protected final int margin_right;
    protected final IRecipeWidget recipe;
    protected final RecipeBackground background;
    protected final List<RecipeIngredient> recipeIngredients;
    protected final List<List<String>> texts;

    public RecipeWidget(JsonObject object, IManual manual) {
        ConfigHolder holder = new ConfigHolder(MARGIN_TOP, MARGIN_LEFT, MARGIN_BOTTOM, MARGIN_RIGHT, RECIPE);
        holder.loadConfig(object, manual);

        margin_top = holder.getValue(MARGIN_TOP);
        margin_left = holder.getValue(MARGIN_LEFT);
        margin_bottom = holder.getValue(MARGIN_BOTTOM);
        margin_right = holder.getValue(MARGIN_RIGHT);
        recipe = holder.getValue(RECIPE);
        background = recipe.getRecipeBackground();
        recipeIngredients = recipe.getRecipeIngredients();

        texts = Lists.newArrayList();
        for (RecipeIngredient ingredient : recipeIngredients) {
            List<String> text = new ArrayList<>();
            List<ITextComponent> list = ingredient.item.getTooltip(ExampleMod.proxy.getClientPlayer(), ITooltipFlag.TooltipFlags.NORMAL);

            for (ITextComponent itextcomponent : list) {
                text.add(itextcomponent.getFormattedText());
            }

            texts.add(text);
        }
    }

    @Override
    public int getMinWidth(int height) {
        return recipe.getRecipeWidth() + margin_left + margin_right;
    }

    @Override
    public int getMinHeight(int width) {
        return recipe.getRecipeHeight() + margin_top + margin_bottom;
    }

    @Override
    public void drawBackgroundLayer(Screen screen, int mx, int my) {
        mc.getTextureManager().bindTexture(background.image);

        GlStateManager.pushMatrix();
        GlStateManager.translatef(x + margin_left, y + margin_top, 0.0f);
        GlStateManager.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        AbstractGui.blit(0, 0, 0, background.u, background.v, recipe.getRecipeWidth(), recipe.getRecipeHeight(), background.imgW, background.imgH);
        GlStateManager.popMatrix();

        GlStateManager.pushTextureAttributes();
        GlStateManager.pushLightingAttributes();
        GlStateManager.pushMatrix();
        GlStateManager.translatef(x + margin_left, y + margin_top, 0.0f);
        GlStateManager.enableRescaleNormal();
        RenderHelper.enableGUIStandardItemLighting();

        for (RecipeIngredient ingredient : recipeIngredients) {
            mc.getItemRenderer().renderItemIntoGUI(ingredient.item, ingredient.x, ingredient.y);
        }

        RenderHelper.disableStandardItemLighting();
        GlStateManager.disableRescaleNormal();
        GlStateManager.popMatrix();
        GlStateManager.popAttributes();
        GlStateManager.popAttributes();
    }

    @Override
    public void render(Screen screen, int mx, int my) {
        int i = 0;

        if (inBounds(mx, my)) {
            for (RecipeIngredient ingredient : recipeIngredients) {
                if (((x + margin_left + ingredient.x) < mx) && (mx < (x + margin_left + ingredient.x + ITEM_WIDTH)) &&
                        ((y + margin_top + ingredient.y) < my) && (my < (y + margin_top + ingredient.y + ITEM_WIDTH))) {
                    GuiUtils.drawHoveringText(texts.get(i), mx, my, screen.width, screen.height, -1, mc.fontRenderer);
                }
                i++;
            }
        }
    }
}
