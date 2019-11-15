package com.yanny.age.zero.compatibility.jei;

import com.google.common.collect.ImmutableList;
import com.yanny.age.zero.Reference;
import com.yanny.age.zero.blocks.TreeStumpBlock;
import com.yanny.age.zero.recipes.TreeStumpRecipe;
import com.yanny.age.zero.subscribers.BlockSubscriber;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableStatic;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TreeStumpRecipeCategory implements IRecipeCategory<TreeStumpRecipe> {
    static final ResourceLocation UID = new ResourceLocation(Reference.MODID, "tree_stump_recipe_category");

    private final String localizedName;
    private final IDrawableStatic background;
    private final IDrawable icon;

    TreeStumpRecipeCategory(IGuiHelper guiHelper) {
        ResourceLocation location = new ResourceLocation(Reference.MODID, "textures/gui/jei/gui_layouts.png");
        background = guiHelper.createDrawable(location, 0, 61, 120, 60);
        localizedName = I18n.format("block.age_zero.tree_stump");
        icon = guiHelper.createDrawableIngredient(new ItemStack(BlockSubscriber.tree_stump));
    }

    @Nonnull
    @Override
    public ResourceLocation getUid() {
        return UID;
    }

    @Nonnull
    @Override
    public Class<? extends TreeStumpRecipe> getRecipeClass() {
        return TreeStumpRecipe.class;
    }

    @Nonnull
    @Override
    public String getTitle() {
        return localizedName;
    }

    @Nonnull
    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Nonnull
    @Override
    public IDrawable getIcon() {
        return icon;
    }

    @Override
    public void setIngredients(@Nonnull TreeStumpRecipe treeStumpRecipe, @Nonnull IIngredients ingredients) {
        ImmutableList.Builder<List<ItemStack>> inputBuilder = ImmutableList.builder();
        ImmutableList.Builder<ItemStack> outputBuilder = ImmutableList.builder();

        inputBuilder.add(Arrays.asList(treeStumpRecipe.getIngredients().get(0).getMatchingStacks()));
        inputBuilder.add(Arrays.asList(Ingredient.fromTag(TreeStumpBlock.AXES).getMatchingStacks()));
        outputBuilder.add(treeStumpRecipe.getRecipeOutput());

        ingredients.setInputLists(VanillaTypes.ITEM, inputBuilder.build());
        ingredients.setOutputLists(VanillaTypes.ITEM, ImmutableList.of(outputBuilder.build()));
    }

    @Override
    public void setRecipe(@Nonnull IRecipeLayout recipeLayout, @Nonnull TreeStumpRecipe treeStumpRecipe, @Nonnull IIngredients ingredients) {
        recipeLayout.getItemStacks().init(0, true, 21, 21);
        recipeLayout.getItemStacks().set(0, ingredients.getInputs(VanillaTypes.ITEM).get(0));

        recipeLayout.getItemStacks().init(1, false, 84, 22);
        recipeLayout.getItemStacks().set(1, ingredients.getOutputs(VanillaTypes.ITEM).get(0));

        recipeLayout.getItemStacks().init(2, false, 51, 13);
        recipeLayout.getItemStacks().set(2, ingredients.getInputs(VanillaTypes.ITEM).get(1));

        recipeLayout.getItemStacks().init(3, false, 51, 29);
        recipeLayout.getItemStacks().set(3, Collections.singletonList(new ItemStack(BlockSubscriber.tree_stump)));
    }
}
