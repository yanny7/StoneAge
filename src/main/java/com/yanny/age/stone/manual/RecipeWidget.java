package com.yanny.age.stone.manual;

import com.google.common.collect.Lists;
import com.google.gson.JsonObject;
import com.mojang.blaze3d.platform.GlStateManager;
import com.yanny.age.stone.ExampleMod;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.renderer.ItemModelMesher;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.fml.client.config.GuiUtils;
import org.lwjgl.opengl.GL11;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

import static com.yanny.age.stone.manual.ConfigHolder.RECIPE;
import static com.yanny.age.stone.manual.ConfigHolder.SCALE;

public class RecipeWidget extends MarginWidget {
    public static final String TYPE = "recipe";
    private static final int ITEM_WIDTH = 16;

    protected final float scale;
    protected final IRecipe<?> recipe;
    protected final IRecipeHandler recipeHandler;
    protected final RecipeBackground background;
    protected final List<RecipeIngredient> recipeIngredients;
    protected final List<Ingredient> texts;

    public RecipeWidget(JsonObject object, IManual manual) {
        super(object, manual, RECIPE, SCALE);

        scale = configHolder.getValue(SCALE);
        recipe = configHolder.getValue(RECIPE);

        recipeHandler = manual.getRecipeHandler(recipe);
        background = recipeHandler.getRecipeBackground();
        recipeIngredients = recipeHandler.getRecipeIngredients(recipe);

        texts = Lists.newArrayList();
        for (RecipeIngredient ingredient : recipeIngredients) {
            texts.add(ingredient.item);
        }
    }

    @Override
    int getRawWidth() {
        return Math.round(background.width * scale) + Math.max(getRawMarginLeft(), 0) + Math.max(getRawMarginRight(), 0);
    }

    @Override
    public int getMinWidth(int height) {
        return getRawWidth();
    }

    @Override
    public int getMinHeight(int width) {
        return Math.round(background.height * scale) + getMarginTop() + getMarginBottom();
    }

    @Override
    public void drawBackgroundLayer(Screen screen, int mx, int my) {
        int tmp = (int) (System.currentTimeMillis() / 2000);
        mc.getTextureManager().bindTexture(background.image);

        GlStateManager.pushMatrix();
        GlStateManager.translatef(getX() + getMarginLeft(), getY() + getMarginTop(), 0.0f);
        GlStateManager.scalef(scale, scale, 0);
        GlStateManager.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        AbstractGui.blit(0, 0, 0, background.u, background.v, background.width, background.height, background.imgW, background.imgH);
        GlStateManager.popMatrix();

        GlStateManager.pushMatrix();
        GlStateManager.translatef(getX() + getMarginLeft(), getY() + getMarginTop(), 0.0f);
        GlStateManager.scalef(scale, scale, 0);
        RenderHelper.enableStandardItemLighting();

        for (RecipeIngredient ingredient : recipeIngredients) {
            ItemStack[] stacks = ingredient.item.getMatchingStacks();

            if (stacks.length > 0) {
                mc.getItemRenderer().renderItemIntoGUI(stacks[tmp % stacks.length], ingredient.x, ingredient.y);
            }
        }

        RenderHelper.disableStandardItemLighting();
        GlStateManager.popMatrix();
    }

    @Override
    public void render(Screen screen, int mx, int my) {
        int tmp = (int) (System.currentTimeMillis() / 2000);
        mc.getTextureManager().bindTexture(background.image);

        if (inBounds(mx, my)) {
            for (RecipeIngredient ingredient : recipeIngredients) {
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
