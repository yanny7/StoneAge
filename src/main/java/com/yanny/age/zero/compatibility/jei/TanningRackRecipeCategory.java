package com.yanny.age.zero.compatibility.jei;

import com.google.common.collect.ImmutableList;
import com.yanny.age.zero.Reference;
import com.yanny.age.zero.recipes.DryingRackRecipe;
import com.yanny.age.zero.recipes.TanningRackRecipe;
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
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.List;

public class TanningRackRecipeCategory implements IRecipeCategory<TanningRackRecipe> {
    static final ResourceLocation UID = new ResourceLocation(Reference.MODID, "tanning_rack_recipe_category");

    private final String localizedName;
    private final IDrawableStatic background;
    private final IDrawable icon;

    TanningRackRecipeCategory(IGuiHelper guiHelper) {
        ResourceLocation location = new ResourceLocation("minecraft", "textures/gui/demo_background.png");
        background = guiHelper.createDrawable(location, 50, 22, 95, 42);
        localizedName = I18n.format("block.age_zero.tanning_rack");
        icon = guiHelper.createDrawableIngredient(new ItemStack(BlockSubscriber.tanning_rack));
    }

    @Nonnull
    @Override
    public ResourceLocation getUid() {
        return UID;
    }

    @Nonnull
    @Override
    public Class<? extends TanningRackRecipe> getRecipeClass() {
        return TanningRackRecipe.class;
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
    public void setIngredients(@Nonnull TanningRackRecipe tanningRackRecipe, @Nonnull IIngredients ingredients) {
        ImmutableList.Builder<List<ItemStack>> inputBuilder = ImmutableList.builder();
        ImmutableList.Builder<ItemStack> outputBuilder = ImmutableList.builder();

        inputBuilder.add(Arrays.asList(tanningRackRecipe.getIngredients().get(0).getMatchingStacks()));
        outputBuilder.add(tanningRackRecipe.getRecipeOutput());

        ingredients.setInputLists(VanillaTypes.ITEM, inputBuilder.build());
        ingredients.setOutputLists(VanillaTypes.ITEM, ImmutableList.of(outputBuilder.build()));
    }

    @Override
    public void setRecipe(@Nonnull IRecipeLayout recipeLayout, @Nonnull TanningRackRecipe tanningRackRecipe, @Nonnull IIngredients ingredients) {
        recipeLayout.getItemStacks().init(0, true, 8, 11);
        recipeLayout.getItemStacks().set(0, ingredients.getInputs(VanillaTypes.ITEM).get(0));

        recipeLayout.getItemStacks().init(1, false, 65, 12);
        recipeLayout.getItemStacks().set(1, ingredients.getOutputs(VanillaTypes.ITEM).get(0));
    }
}
