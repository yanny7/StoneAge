package com.yanny.age.stone.manual.handlers;

import com.yanny.age.stone.Reference;
import com.yanny.age.stone.manual.IRecipeHandler;
import com.yanny.age.stone.manual.RecipeBackground;
import com.yanny.age.stone.manual.RecipeIngredient;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipe;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class ShapedRecipeHandler implements IRecipeHandler {

    @Nonnull
    @Override
    public List<RecipeIngredient> getRecipeIngredients(IRecipe<?> recipe) {
        ShapedRecipe shapedRecipe = (ShapedRecipe) recipe;
        List<RecipeIngredient> list = new ArrayList<>();

        for (int y = 0; y < shapedRecipe.getRecipeHeight(); y++) {
            for (int x = 0; x < shapedRecipe.getRecipeWidth(); x++) {
                list.add(new RecipeIngredient(shapedRecipe.getIngredients().get(y * shapedRecipe.getRecipeWidth() + x), 3 + x * 18, 4 + y * 18));
            }
        }

        list.add(new RecipeIngredient(Ingredient.fromStacks(shapedRecipe.getRecipeOutput()), 97, 22));

        return list;
    }

    @Nonnull
    @Override
    public RecipeBackground getRecipeBackground() {
        return new RecipeBackground(new ResourceLocation(Reference.MODID, "textures/gui/manual/default_recipes.png"), 0, 61, 120,60, 256, 256);
    }
}
