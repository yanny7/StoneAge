package com.yanny.age.stone.recipes;

import com.yanny.age.stone.subscribers.BlockSubscriber;
import com.yanny.age.stone.subscribers.RecipeSubscriber;
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
import javax.annotation.Nullable;

public class TreeStumpRecipe implements IRecipe<IInventory> {
    public static final IRecipeType<TreeStumpRecipe> tree_stump = IRecipeType.register("tree_stump");

    private final IRecipeType<?> type;
    private final ResourceLocation id;
    final String group;
    final Ingredient ingredient;
    final Ingredient tool;
    final ItemStack result;
    final int chopTimes;

    public TreeStumpRecipe(ResourceLocation resourceLocation, String group, Ingredient ingredient, Ingredient tool, ItemStack result, int chopTimes) {
        type = tree_stump;
        id = resourceLocation;
        this.group = group;
        this.ingredient = ingredient;
        this.tool = tool;
        this.result = result;
        this.chopTimes = chopTimes;
    }

    @Override
    public boolean matches(IInventory inv, @Nonnull World worldIn) {
        return this.ingredient.test(inv.getStackInSlot(0));
    }

    @Override
    @Nonnull
    public ItemStack getCraftingResult(@Nullable IInventory inv) {
        return this.result.copy();
    }

    @Override
    public boolean canFit(int width, int height) {
        return true;
    }

    @Override
    @Nonnull
    public ItemStack getRecipeOutput() {
        return result;
    }

    @Override
    @Nonnull
    public ResourceLocation getId() {
        return id;
    }

    @Override
    @Nonnull
    public IRecipeSerializer<?> getSerializer() {
        //noinspection ConstantConditions
        return RecipeSubscriber.tree_stump;
    }

    @Override
    @Nonnull
    public IRecipeType<?> getType() {
        return type;
    }

    @Override
    @Nonnull
    public NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> nonNullList = NonNullList.create();
        nonNullList.add(this.ingredient);
        return nonNullList;
    }

    public NonNullList<Ingredient> getTools() {
        NonNullList<Ingredient> nonNullList = NonNullList.create();
        nonNullList.add(this.tool);
        return nonNullList;
    }

    @Override
    @Nonnull
    public ItemStack getIcon() {
        return new ItemStack(BlockSubscriber.tree_stump);
    }

    public int getChopTimes() {
        return chopTimes;
    }
}
