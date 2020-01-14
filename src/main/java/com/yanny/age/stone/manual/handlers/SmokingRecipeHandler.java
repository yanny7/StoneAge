package com.yanny.age.stone.manual.handlers;

import com.yanny.age.stone.Reference;
import com.yanny.age.stone.manual.IRecipeHandler;
import com.yanny.age.stone.manual.RecipeBackground;
import com.yanny.age.stone.manual.RecipeIngredient;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.SmokingRecipe;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

import static net.minecraft.item.Items.*;

public class SmokingRecipeHandler implements IRecipeHandler {
    @Nonnull
    @Override
    public List<RecipeIngredient> getRecipeIngredients(IRecipe<?> recipe) {
        SmokingRecipe smokingRecipe = (SmokingRecipe) recipe;
        List<RecipeIngredient> list = new ArrayList<>();

        list.add(new RecipeIngredient(smokingRecipe.getIngredients().get(0), 22, 4));
        list.add(new RecipeIngredient(Ingredient.fromItems(COAL, CHARCOAL, COAL_BLOCK, OAK_PLANKS, STICK, OAK_SLAB), 22, 40));
        list.add(new RecipeIngredient(Ingredient.fromStacks(smokingRecipe.getRecipeOutput()), 82, 22));
        return list;
    }

    @Nonnull
    @Override
    public RecipeBackground getRecipeBackground() {
        return new RecipeBackground(new ResourceLocation(Reference.MODID, "textures/gui/manual/default_recipes.png"), 0, 0, 120,60, 256, 256);
    }
}
