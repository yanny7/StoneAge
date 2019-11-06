package com.yanny.age.zero.recipes;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public class FlintWorkbenchRecipe implements IRecipe<IInventory> {
    public static final IRecipeType<FlintWorkbenchRecipe> flint_workbench = IRecipeType.register("flint_workbench");
    private static int MAX_WIDTH = 3;
    private static int MAX_HEIGHT = 3;

    private final int recipeWidth;
    private final int recipeHeight;
    private final NonNullList<Ingredient> recipeItems;
    private final ItemStack recipeOutput;
    private final ResourceLocation id;
    private final String group;

    FlintWorkbenchRecipe(ResourceLocation id, String group, int recipeWidth, int recipeHeight, NonNullList<Ingredient> ingredients, ItemStack output) {
        this.id = id;
        this.group = group;
        this.recipeWidth = recipeWidth;
        this.recipeHeight = recipeHeight;
        this.recipeItems = ingredients;
        this.recipeOutput = output;
    }

    @Nonnull
    @Override
    public ResourceLocation getId() {
        return this.id;
    }

    @Nonnull
    @Override
    public IRecipeSerializer<?> getSerializer() {
        return IRecipeSerializer.CRAFTING_SHAPED;
    }

    @Nonnull
    @Override
    public IRecipeType<?> getType() {
        return flint_workbench;
    }

    @Nonnull
    @Override
    public String getGroup() {
        return this.group;
    }

    @Nonnull
    @Override
    public ItemStack getRecipeOutput() {
        return this.recipeOutput;
    }

    @Nonnull
    @Override
    public NonNullList<Ingredient> getIngredients() {
        return this.recipeItems;
    }

    @Override
    public boolean canFit(int width, int height) {
        return width >= this.recipeWidth && height >= this.recipeHeight;
    }

    @Override
    public boolean matches(@Nonnull IInventory inv, @Nonnull World worldIn) {
        for(int i = 0; i < MAX_WIDTH; ++i) {
            for(int j = 0; j < MAX_HEIGHT; ++j) {
                if (this.checkMatch(inv, i, j, true)) {
                    return true;
                }

                if (this.checkMatch(inv, i, j, false)) {
                    return true;
                }
            }
        }

        return false;
    }

    @Nonnull
    @Override
    public ItemStack getCraftingResult(@Nonnull IInventory inv) {
        return this.getRecipeOutput().copy();
    }

    int getWidth() {
        return this.recipeWidth;
    }

    int getHeight() {
        return this.recipeHeight;
    }

    /**
     * Checks if the region of a crafting inventory is match for the recipe.
     */
    private boolean checkMatch(IInventory inventory, int x, int y, boolean reversed) {
        for(int i = 0; i < MAX_WIDTH; ++i) {
            for(int j = 0; j < MAX_HEIGHT; ++j) {
                int k = i - x;
                int l = j - y;
                Ingredient ingredient = Ingredient.EMPTY;
                if (k >= 0 && l >= 0 && k < this.recipeWidth && l < this.recipeHeight) {
                    if (reversed) {
                        ingredient = this.recipeItems.get(this.recipeWidth - k - 1 + l * this.recipeWidth);
                    } else {
                        ingredient = this.recipeItems.get(k + l * this.recipeWidth);
                    }
                }

                if (!ingredient.test(inventory.getStackInSlot(i + j * MAX_WIDTH))) {
                    return false;
                }
            }
        }

        return true;
    }

/*
    FlintWorkbenchRecipe(final ResourceLocation id, final String group, final int recipeWidth, final int recipeHeight,
                         final NonNullList<Ingredient> ingredients, final ItemStack recipeOutput) {
        super(id, group, recipeWidth, recipeHeight, ingredients, recipeOutput);
    }

    @SuppressWarnings("ConstantConditions")
    @Nonnull
    @Override
    public IRecipeSerializer<?> getSerializer() {
        return RecipeSubscriber.flint_workbench_serializer;
    }*/
}
