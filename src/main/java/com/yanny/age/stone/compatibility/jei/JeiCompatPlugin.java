package com.yanny.age.stone.compatibility.jei;

import com.yanny.age.stone.Reference;
import com.yanny.age.stone.recipes.*;
import com.yanny.age.stone.subscribers.BlockSubscriber;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.RecipeManager;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.stream.Collectors;

@JeiPlugin
public class JeiCompatPlugin implements IModPlugin {

    @Nonnull
    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(Reference.MODID, "main");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(
                new DryingRackRecipeCategory(registration.getJeiHelpers().getGuiHelper()),
                new TanningRackRecipeCategory(registration.getJeiHelpers().getGuiHelper()),
                new FlintWorkbenchRecipeCategory(registration.getJeiHelpers().getGuiHelper()),
                new TreeStumpRecipeCategory(registration.getJeiHelpers().getGuiHelper()),
                new MillstoneRecipeCategory(registration.getJeiHelpers().getGuiHelper())
        );
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        assert Minecraft.getInstance().world != null;
        RecipeManager recipeManager = Minecraft.getInstance().world.getRecipeManager();
        List<IRecipe<?>> dryingRackRecipes = recipeManager.getRecipes().stream().filter(recipe -> recipe instanceof DryingRackRecipe).collect(Collectors.toList());
        List<IRecipe<?>> tanningRackRecipes = recipeManager.getRecipes().stream().filter(recipe -> recipe instanceof TanningRackRecipe).collect(Collectors.toList());
        List<IRecipe<?>> flintWorkbenchRecipes = recipeManager.getRecipes().stream().filter(recipe -> recipe instanceof FlintWorkbenchRecipe).collect(Collectors.toList());
        List<IRecipe<?>> treeStumpRecipes = recipeManager.getRecipes().stream().filter(recipe -> recipe instanceof TreeStumpRecipe).collect(Collectors.toList());
        List<IRecipe<?>> millstoneRecipes = recipeManager.getRecipes().stream().filter(recipe -> recipe instanceof MillstoneRecipe).collect(Collectors.toList());

        registration.addRecipes(dryingRackRecipes, DryingRackRecipeCategory.UID);
        registration.addRecipes(tanningRackRecipes, TanningRackRecipeCategory.UID);
        registration.addRecipes(flintWorkbenchRecipes, FlintWorkbenchRecipeCategory.UID);
        registration.addRecipes(treeStumpRecipes, TreeStumpRecipeCategory.UID);
        registration.addRecipes(millstoneRecipes, MillstoneRecipeCategory.UID);
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(new ItemStack(BlockSubscriber.drying_rack), DryingRackRecipeCategory.UID);
        registration.addRecipeCatalyst(new ItemStack(BlockSubscriber.tanning_rack), TanningRackRecipeCategory.UID);
        registration.addRecipeCatalyst(new ItemStack(BlockSubscriber.flint_workbench), FlintWorkbenchRecipeCategory.UID);
        registration.addRecipeCatalyst(new ItemStack(BlockSubscriber.tree_stump), TreeStumpRecipeCategory.UID);
        registration.addRecipeCatalyst(new ItemStack(BlockSubscriber.millstone), MillstoneRecipeCategory.UID);
    }
}
