package com.yanny.age.zero.compatibility.jei;

import com.google.common.collect.ImmutableList;
import com.yanny.age.zero.Reference;
import com.yanny.age.zero.recipes.FlintWorkbenchRecipe;
import com.yanny.age.zero.subscribers.BlockSubscriber;
import com.yanny.age.zero.subscribers.ToolSubscriber;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableStatic;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class FlintWorkbenchRecipeCategory implements IRecipeCategory<FlintWorkbenchRecipe> {
    static final ResourceLocation UID = new ResourceLocation(Reference.MODID, "flint_workbench_recipe_category");

    private final String localizedName;
    private final IDrawableStatic background;
    private final IDrawable icon;

    FlintWorkbenchRecipeCategory(IGuiHelper guiHelper) {
        ResourceLocation location = new ResourceLocation(Reference.MODID, "textures/gui/jei/flint_workbench.png");
        background = guiHelper.createDrawable(location, 26, 13, 122, 60);
        localizedName = I18n.format("block.age_zero.flint_workbench");
        icon = guiHelper.createDrawableIngredient(new ItemStack(BlockSubscriber.flint_workbench));
    }

    @Nonnull
    @Override
    public ResourceLocation getUid() {
        return UID;
    }

    @Nonnull
    @Override
    public Class<? extends FlintWorkbenchRecipe> getRecipeClass() {
        return FlintWorkbenchRecipe.class;
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
    public void setIngredients(@Nonnull FlintWorkbenchRecipe flintWorkbenchRecipe, @Nonnull IIngredients ingredients) {
        ImmutableList.Builder<List<ItemStack>> inputBuilder = ImmutableList.builder();
        ImmutableList.Builder<ItemStack> outputBuilder = ImmutableList.builder();
        int width = flintWorkbenchRecipe.getWidth();
        int height = flintWorkbenchRecipe.getHeight();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                inputBuilder.add(Arrays.asList(flintWorkbenchRecipe.getIngredients().get(x + y * width).getMatchingStacks()));
            }
        }

        outputBuilder.add(flintWorkbenchRecipe.getRecipeOutput());
        ingredients.setInputLists(VanillaTypes.ITEM, inputBuilder.build());
        ingredients.setOutputLists(VanillaTypes.ITEM, ImmutableList.of(outputBuilder.build()));
    }

    @Override
    public void setRecipe(@Nonnull IRecipeLayout recipeLayout, @Nonnull FlintWorkbenchRecipe flintWorkbenchRecipe, @Nonnull IIngredients ingredients) {
        int width = flintWorkbenchRecipe.getWidth();
        int height = flintWorkbenchRecipe.getHeight();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                recipeLayout.getItemStacks().init(x + y * width, true, 3 + x * 18, 3 + y * 18);
                recipeLayout.getItemStacks().set(x + y * width, ingredients.getInputs(VanillaTypes.ITEM).get(x + y * width));
            }
        }

        recipeLayout.getItemStacks().init(width * height, false, 97, 21);
        recipeLayout.getItemStacks().set(width * height, ingredients.getOutputs(VanillaTypes.ITEM).get(0));

        recipeLayout.getItemStacks().init(width * height + 1, false, 66, 20);
        //noinspection ConstantConditions
        recipeLayout.getItemStacks().set(width * height + 1, Collections.singletonList(ToolSubscriber.flint_knife.getDefaultInstance()));
    }
}
