package com.yanny.age.stone.recipes.handlers;

import com.yanny.age.stone.Reference;
import com.yanny.age.stone.recipes.MillstoneRecipe;
import com.yanny.ages.api.manual.IRecipeHandler;
import com.yanny.ages.api.manual.RecipeBackground;
import com.yanny.ages.api.manual.RecipeIngredient;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class MillstoneRecipeHandler implements IRecipeHandler {
    @Nonnull
    @Override
    public List<RecipeIngredient> getRecipeIngredients(IRecipe<?> recipe) {
        MillstoneRecipe millstoneRecipe = (MillstoneRecipe) recipe;
        List<RecipeIngredient> list = new ArrayList<>();

        list.add(new RecipeIngredient(millstoneRecipe.getIngredients().get(0), 22, 22));
        list.add(new RecipeIngredient(Ingredient.fromItems(ForgeRegistries.ITEMS.getValue(new ResourceLocation(Reference.MODID, "millstone"))), 52, 22));
        list.add(new RecipeIngredient(Ingredient.fromStacks(millstoneRecipe.getRecipeOutput()), 85, 23));
        return list;
    }

    @Nonnull
    @Override
    public RecipeBackground getRecipeBackground() {
        return new RecipeBackground(new ResourceLocation(Reference.MODID, "textures/gui/jei/gui_layouts.png"), 0, 61, 120, 60, 256, 256);
    }
}
