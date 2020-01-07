package com.yanny.age.stone.manual;

import com.google.common.collect.Lists;
import com.google.gson.JsonObject;
import com.mojang.blaze3d.platform.GlStateManager;
import com.yanny.age.stone.ExampleMod;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.fml.client.config.GuiUtils;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

import static com.yanny.age.stone.manual.ConfigHolder.*;

public class RecipeWidget extends ConfigurableWidget {
    public static final String TYPE = "recipe";
    private static final int ITEM_WIDTH = 16;

    protected int margin_left;

    protected final int margin_top;
    protected final int margin_bottom;
    protected final int tmpMarginLeft;
    protected final int tmpMarginRight;
    protected final Align align;
    protected final IRecipeWidget recipe;
    protected final RecipeBackground background;
    protected final List<RecipeIngredient> recipeIngredients;
    protected final List<Ingredient> texts;

    public RecipeWidget(JsonObject object, IManual manual) {
        super(object, manual, MARGIN_TOP, MARGIN_LEFT_AUTO, MARGIN_BOTTOM, MARGIN_RIGHT_AUTO, RECIPE, ALIGN_CENTER);

        margin_top = configHolder.getValue(MARGIN_TOP);
        tmpMarginLeft = configHolder.getValue(MARGIN_LEFT_AUTO);
        margin_bottom = configHolder.getValue(MARGIN_BOTTOM);
        tmpMarginRight = configHolder.getValue(MARGIN_RIGHT);
        recipe = configHolder.getValue(RECIPE);
        align = configHolder.getValue(ALIGN_CENTER);

        background = recipe.getRecipeBackground();
        recipeIngredients = recipe.getRecipeIngredients();

        texts = Lists.newArrayList();
        for (RecipeIngredient ingredient : recipeIngredients) {
            texts.add(ingredient.item);
        }
    }

    @Override
    public int getMinWidth(int height) {
        return recipe.getRecipeWidth() + tmpMarginLeft + tmpMarginRight;
    }

    @Override
    public int getMinHeight(int width) {
        return recipe.getRecipeHeight() + margin_top + margin_bottom;
    }

    @Override
    public void setWidth(int width) {
        int minWidth = (recipe.getRecipeWidth() + Math.max(tmpMarginLeft, 0) + Math.max(tmpMarginRight, 0));

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
        int tmp = (int) (System.currentTimeMillis() / 2000);
        mc.getTextureManager().bindTexture(background.image);

        GlStateManager.pushMatrix();
        GlStateManager.translatef(getX() + margin_left, getY() + margin_top, 0.0f);
        GlStateManager.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        AbstractGui.blit(0, 0, 0, background.u, background.v, recipe.getRecipeWidth(), recipe.getRecipeHeight(), background.imgW, background.imgH);
        GlStateManager.popMatrix();

        GlStateManager.pushTextureAttributes();
        GlStateManager.pushLightingAttributes();
        GlStateManager.pushMatrix();
        GlStateManager.translatef(getX() + margin_left, getY() + margin_top, 0.0f);
        GlStateManager.enableRescaleNormal();
        RenderHelper.enableGUIStandardItemLighting();

        for (RecipeIngredient ingredient : recipeIngredients) {
            ItemStack[] stacks = ingredient.item.getMatchingStacks();

            if (stacks.length > 0) {
                mc.getItemRenderer().renderItemIntoGUI(stacks[tmp % stacks.length], ingredient.x, ingredient.y);
            }
        }

        RenderHelper.disableStandardItemLighting();
        GlStateManager.disableRescaleNormal();
        GlStateManager.popMatrix();
        GlStateManager.popAttributes();
        GlStateManager.popAttributes();
    }

    @Override
    public void render(Screen screen, int mx, int my) {
        int tmp = (int) (System.currentTimeMillis() / 2000);
        mc.getTextureManager().bindTexture(background.image);

        if (inBounds(mx, my)) {
            for (RecipeIngredient ingredient : recipeIngredients) {
                ItemStack[] stacks = ingredient.item.getMatchingStacks();

                if (stacks.length > 0) {
                    if (((getX() + tmpMarginLeft + ingredient.x) < mx) && (mx < (getX() + tmpMarginLeft + ingredient.x + ITEM_WIDTH)) &&
                        ((getY() + margin_top + ingredient.y) < my) && (my < (getY() + margin_top + ingredient.y + ITEM_WIDTH))) {
                        GuiUtils.drawHoveringText(getText(stacks[tmp % stacks.length]), mx, my, screen.width, screen.height, -1, mc.fontRenderer);
                    }
                }
            }
        }
    }

    @Nonnull
    private List<String> getText(@Nonnull ItemStack ingredient) {
        List<ITextComponent> list = ingredient.getTooltip(ExampleMod.proxy.getClientPlayer(), ITooltipFlag.TooltipFlags.NORMAL);
        List<String> text = new ArrayList<>();

        for (ITextComponent itextcomponent : list) {
            text.add(itextcomponent.getFormattedText());
        }

        return text;
    }
}
