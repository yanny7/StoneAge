package com.yanny.age.stone.recipes.handlers;

import com.yanny.age.stone.Reference;
import com.yanny.age.stone.manual.IRecipeHandler;
import com.yanny.age.stone.manual.RecipeBackground;
import com.yanny.age.stone.manual.RecipeIngredient;
import com.yanny.age.stone.recipes.TanningRackRecipe;
import com.yanny.age.stone.subscribers.ToolSubscriber;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class TanningRackRecipeHandler implements IRecipeHandler {
    @Nonnull
    @Override
    public List<RecipeIngredient> getRecipeIngredients(IRecipe<?> recipe) {
        TanningRackRecipe tanningRackRecipe = (TanningRackRecipe) recipe;
        List<RecipeIngredient> list = new ArrayList<>();

        list.add(new RecipeIngredient(tanningRackRecipe.getIngredients().get(0), 22, 22));
        list.add(new RecipeIngredient(Ingredient.fromItems(ForgeRegistries.ITEMS.getValue(new ResourceLocation(Reference.MODID, "tanning_rack"))), 52, 14));
        list.add(new RecipeIngredient(Ingredient.fromItems(ToolSubscriber.flint_knife), 52, 30));
        list.add(new RecipeIngredient(Ingredient.fromStacks(tanningRackRecipe.getRecipeOutput()), 85, 23));
        return list;
    }

    @Nonnull
    @Override
    public RecipeBackground getRecipeBackground() {
        return new RecipeBackground(new ResourceLocation(Reference.MODID, "textures/gui/jei/gui_layouts.png"), 0, 61, 120, 60, 256, 256);
    }
}
