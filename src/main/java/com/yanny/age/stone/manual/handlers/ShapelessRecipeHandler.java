package com.yanny.age.stone.manual.handlers;

import com.yanny.age.stone.Reference;
import com.yanny.age.stone.manual.IRecipeHandler;
import com.yanny.age.stone.manual.RecipeBackground;
import com.yanny.age.stone.manual.RecipeIngredient;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapelessRecipe;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class ShapelessRecipeHandler implements IRecipeHandler {
    @Nonnull
    @Override
    public List<RecipeIngredient> getRecipeIngredients(IRecipe<?> recipe) {
        ShapelessRecipe shapelessRecipe = (ShapelessRecipe) recipe;
        List<RecipeIngredient> list = new ArrayList<>();

        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 3; x++) {
                list.add(new RecipeIngredient(shapelessRecipe.getIngredients().get(y * 3 + x), 3 + x * 18, 4 + y * 18));
            }
        }

        list.add(new RecipeIngredient(Ingredient.fromItems(shapelessRecipe.getRecipeOutput().getItem()), 97, 22));

        return list;
    }

    @Nonnull
    @Override
    public RecipeBackground getRecipeBackground() {
        return new RecipeBackground(new ResourceLocation(Reference.MODID, "textures/gui/manual/default_recipes.png"), 0, 61, 120,60, 256, 256);
    }
}
