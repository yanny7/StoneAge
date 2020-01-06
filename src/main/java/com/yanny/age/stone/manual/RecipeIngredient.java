package com.yanny.age.stone.manual;

import net.minecraft.item.ItemStack;

public class RecipeIngredient {
    final ItemStack item;
    final int x;
    final int y;

    public RecipeIngredient(ItemStack item, int x, int y) {
        this.item = item;
        this.x = x;
        this.y = y;
    }
}
