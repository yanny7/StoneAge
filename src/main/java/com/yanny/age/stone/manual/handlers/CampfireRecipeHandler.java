package com.yanny.age.stone.manual.handlers;

import com.yanny.age.stone.Reference;
import com.yanny.age.stone.manual.IRecipeHandler;
import com.yanny.age.stone.manual.RecipeBackground;
import com.yanny.age.stone.manual.RecipeIngredient;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.CampfireCookingRecipe;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class CampfireRecipeHandler implements IRecipeHandler {
    @Nonnull
    @Override
    public List<RecipeIngredient> getRecipeIngredients(IRecipe<?> recipe) {
        CampfireCookingRecipe cookingRecipe = (CampfireCookingRecipe) recipe;
        List<RecipeIngredient> list = new ArrayList<>();

        list.add(new RecipeIngredient(cookingRecipe.getIngredients().get(0), 22, 22));
        list.add(new RecipeIngredient(Ingredient.fromItems(Items.CAMPFIRE), 52, 22));
        list.add(new RecipeIngredient(Ingredient.fromStacks(cookingRecipe.getRecipeOutput()), 82, 22));
        return list;
    }

    @Nonnull
    @Override
    public RecipeBackground getRecipeBackground() {
        return new RecipeBackground(new ResourceLocation(Reference.MODID, "textures/gui/manual/default_recipes.png"), 0, 122, 120,60, 256, 256);
    }
}
