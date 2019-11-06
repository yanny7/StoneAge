package com.yanny.age.zero.recipes;

import com.yanny.age.zero.subscribers.RecipeSubscriber;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipe;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

public class FlintWorkbenchRecipe extends ShapedRecipe {

    FlintWorkbenchRecipe(final ResourceLocation id, final String group, final int recipeWidth, final int recipeHeight,
                         final NonNullList<Ingredient> ingredients, final ItemStack recipeOutput) {
        super(id, group, recipeWidth, recipeHeight, ingredients, recipeOutput);
    }

    @SuppressWarnings("ConstantConditions")
    @Nonnull
    @Override
    public IRecipeSerializer<?> getSerializer() {
        return RecipeSubscriber.flint_workbench_serializer;
    }
}
