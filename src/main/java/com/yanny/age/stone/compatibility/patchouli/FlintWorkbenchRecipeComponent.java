package com.yanny.age.stone.compatibility.patchouli;

import com.mojang.blaze3d.systems.RenderSystem;
import com.yanny.age.stone.Reference;
import com.yanny.age.stone.recipes.FlintWorkbenchRecipe;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import vazkii.patchouli.api.IComponentRenderContext;

import javax.annotation.Nonnull;
import java.util.function.Function;

public class FlintWorkbenchRecipeComponent extends BaseComponent {

    private transient FlintWorkbenchRecipe recipe = null;
    private transient String title;

    @Override
    public void render(@Nonnull IComponentRenderContext context, float partialTicks, int mouseX, int mouseY) {
        if (recipe == null) {
            return;
        }

        mc.textureManager.bindTexture(TEXTURE);
        RenderSystem.enableBlend();
        AbstractGui.blit(x - 2, y - 2, 0, 0, 100, 62, 128, 128);

        drawCenteredStringNoShadow(title, PAGE_WIDTH / 2, y - 12, context.getHeaderColor(), mc.fontRenderer);
        context.renderItemStack(x + 79, y + 22, mouseX, mouseY, recipe.getRecipeOutput());
        context.renderIngredient(x + 62, y + 9, mouseX, mouseY, recipe.getTool());

        NonNullList<Ingredient> ingredients = recipe.getIngredients();
        int recipeWidth = recipe.getWidth();

        for (int i = 0; i < ingredients.size(); i++) {
            context.renderIngredient(x + (i % recipeWidth) * 19 + 3, y + (i / recipeWidth) * 19 + 3, mouseX, mouseY, ingredients.get(i));
        }
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public void onVariablesAvailable(@Nonnull Function<String, String> lookup) {
        String recipeName = lookup.apply("#recipe#");

        mc.world.getRecipeManager().getRecipe(new ResourceLocation(recipeName)).ifPresent((recipe) -> {
            this.recipe = (FlintWorkbenchRecipe) recipe;
            this.title = recipe.getRecipeOutput().getDisplayName().getFormattedText();
        });

    }
}
