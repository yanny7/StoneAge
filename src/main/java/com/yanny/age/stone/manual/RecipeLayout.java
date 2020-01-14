package com.yanny.age.stone.manual;

import net.minecraft.item.ItemStack;

import java.util.List;

public class RecipeLayout {
    final List<RecipeIngredient> ingredients;
    final ItemStack result;

    RecipeLayout(List<RecipeIngredient> ingredients, ItemStack result) {
        this.ingredients = ingredients;
        this.result = result;
    }
}
