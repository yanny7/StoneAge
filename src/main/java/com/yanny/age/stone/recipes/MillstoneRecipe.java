package com.yanny.age.stone.recipes;

import com.yanny.age.stone.Reference;
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

public class MillstoneRecipe implements IRecipe<IInventory> {
    public static final IRecipeType<MillstoneRecipe> millstone = IRecipeType.register(Reference.MODID + ":millstone");

    private final IRecipeType<?> type;
    private final ResourceLocation id;
    final String group;
    final Ingredient ingredient;
    final ItemStack result;
    final ItemStack secondResult;
    final double secondChance;
    final int activateCount;

    public MillstoneRecipe(@Nonnull ResourceLocation resourceLocation, @Nonnull String group, @Nonnull Ingredient ingredient,
                           @Nonnull ItemStack result, @Nonnull ItemStack secondResult, double secondChance, int activateCount) {
        type = millstone;
        id = resourceLocation;
        this.group = group;
        this.ingredient = ingredient;
        this.result = result;
        this.secondResult = secondResult;
        this.secondChance = secondChance;
        this.activateCount = activateCount;
    }

    @Override
    public boolean matches(IInventory inv, @Nonnull World worldIn) {
        return this.ingredient.test(inv.getStackInSlot(0));
    }

    @Override
    @Nonnull
    public ItemStack getCraftingResult(@Nullable IInventory inv) {
        return result.copy();
    }

    @Nonnull
    public ItemStack getCraftingSecondResult() {
        return secondResult.copy();
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

    @Nonnull
    public ItemStack getRecipeSecondOutput() {
        return secondResult;
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
        return RecipeSubscriber.millstone;
    }

    @Override
    @Nonnull
    public IRecipeType<?> getType() {
        return type;
    }

    @Override
    @Nonnull
    public NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> nonnulllist = NonNullList.create();
        nonnulllist.add(this.ingredient);
        return nonnulllist;
    }

    @Override
    @Nonnull
    public ItemStack getIcon() {
        return new ItemStack(BlockSubscriber.millstone);
    }

    public double getSecondChance() {
        return secondChance;
    }

    public int getActivateCount() {
        return activateCount;
    }
}
