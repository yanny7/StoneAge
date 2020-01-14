package com.yanny.age.stone.manual;

import net.minecraft.item.crafting.IRecipe;

import javax.annotation.Nonnull;
import java.util.List;

public interface IRecipeHandler {

    @Nonnull
    List<RecipeIngredient> getRecipeIngredients(IRecipe<?> recipe);

    @Nonnull
    RecipeBackground getRecipeBackground();
}
