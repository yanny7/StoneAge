package com.yanny.age.stone.manual;

import com.google.gson.JsonObject;
import com.mojang.blaze3d.platform.GLX;
import com.mojang.blaze3d.platform.GlStateManager;
import com.yanny.age.stone.ExampleMod;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.fml.client.config.GuiUtils;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static com.yanny.age.stone.manual.ConfigHolder.RECIPE;
import static com.yanny.age.stone.manual.ConfigHolder.SCALE;

public class RecipeWidget extends MarginWidget {
    public static final String TYPE = "recipe";
    private static final int ITEM_WIDTH = 16;

    protected final float scale;
    protected final IRecipe<?>[] recipes;
    protected final List<IRecipeHandler> recipeHandlers = new ArrayList<>();
    protected final List<RecipeBackground> backgrounds = new ArrayList<>();
    protected final List<List<RecipeIngredient>> recipeIngredients = new ArrayList<>();

    public RecipeWidget(JsonObject object, IManual manual) {
        super(object, manual, RECIPE, SCALE);

        scale = configHolder.getValue(SCALE);
        recipes = configHolder.getValue(RECIPE);

        for (IRecipe<?> recipe : recipes) {
            IRecipeHandler recipeHandler = manual.getRecipeHandler(recipe);

            recipeHandlers.add(recipeHandler);
            backgrounds.add(recipeHandler.getRecipeBackground());
            recipeIngredients.add(recipeHandler.getRecipeIngredients(recipe));
        }
    }

    @Override
    int getRawWidth() {
        return Collections.max(backgrounds, Comparator.comparing(b -> b.width * scale)).width + Math.max(getRawMarginLeft(), 0) + Math.max(getRawMarginRight(), 0);
    }

    @Override
    public int getMinWidth(int height) {
        return getRawWidth();
    }

    @Override
    public int getMinHeight(int width) {
        return Collections.max(backgrounds, Comparator.comparing(b -> b.height * scale)).height + getMarginTop() + getMarginBottom();
    }

    @Override
    public void drawBackgroundLayer(Screen screen, int mx, int my) {
        int tmp = (int) (System.currentTimeMillis() / 2000);
        int recipeIndex = tmp % recipeIngredients.size();

        mc.getTextureManager().bindTexture(backgrounds.get(recipeIndex).image);

        GlStateManager.pushMatrix();
        GlStateManager.translatef(getX() + getMarginLeft(), getY() + getMarginTop(), 0.0f);
        GlStateManager.scalef(scale, scale, scale);
        GlStateManager.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        AbstractGui.blit(0, 0, 0, backgrounds.get(recipeIndex).u, backgrounds.get(recipeIndex).v, backgrounds.get(recipeIndex).width, backgrounds.get(recipeIndex).height, backgrounds.get(recipeIndex).imgW, backgrounds.get(recipeIndex).imgH);
        GlStateManager.popMatrix();
    }

    @Override
    public void render(Screen screen, int mx, int my) {
        int tmp = (int) (System.currentTimeMillis() / 2000);
        int recipeIndex = tmp % recipeIngredients.size();

        GlStateManager.pushMatrix();
        GlStateManager.translatef(getX() + getMarginLeft(), getY() + getMarginTop(), 0.0f);
        GlStateManager.scalef(scale, scale, scale);
        RenderHelper.disableStandardItemLighting();
        RenderHelper.enableGUIStandardItemLighting();

        List<RecipeIngredient> ingredients = recipeIngredients.get(recipeIndex);

        for (RecipeIngredient ingredient : ingredients) {
            ItemStack[] stacks = ingredient.item.getMatchingStacks();

            if (stacks.length > 0) {
                mc.getItemRenderer().renderItemAndEffectIntoGUI(stacks[tmp % stacks.length], ingredient.x, ingredient.y);
                mc.getItemRenderer().renderItemOverlays(mc.fontRenderer, stacks[tmp % stacks.length], ingredient.x, ingredient.y);
            }
        }

        RenderHelper.enableStandardItemLighting();
        GlStateManager.popMatrix();

        mc.getTextureManager().bindTexture(backgrounds.get(recipeIndex).image);

        if (inBounds(mx, my)) {
            for (RecipeIngredient ingredient : ingredients) {
                ItemStack[] stacks = ingredient.item.getMatchingStacks();

                if (stacks.length > 0) {
                    if (((getX() + getMarginLeft() + ingredient.x * scale) < mx) && (mx < (getX() + getMarginLeft() + (ingredient.x + ITEM_WIDTH) * scale)) &&
                        ((getY() + getMarginTop() + ingredient.y * scale) < my) && (my < (getY() + getMarginTop() + (ingredient.y + ITEM_WIDTH) * scale))) {
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
