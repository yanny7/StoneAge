package com.yanny.age.stone.manual;

import javax.annotation.Nonnull;
import java.util.List;

public interface IRecipeWidget {
    int getRecipeWidth();
    int getRecipeHeight();

    @Nonnull
    List<RecipeIngredient> getRecipeIngredients();

    @Nonnull
    RecipeBackground getRecipeBackground();
}
