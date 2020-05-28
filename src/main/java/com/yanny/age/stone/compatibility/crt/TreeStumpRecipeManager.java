package com.yanny.age.stone.compatibility.crt;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.item.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.managers.IRecipeManager;
import com.blamejared.crafttweaker.impl.actions.recipes.ActionAddRecipe;
import com.yanny.age.stone.recipes.TreeStumpRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.ResourceLocation;

import static org.openzen.zencode.java.ZenCodeType.*;

@ZenRegister
@Name("mods.stone_age.DryingRackManager")
public class TreeStumpRecipeManager implements IRecipeManager {
    @SuppressWarnings("unused")
    public static final TreeStumpRecipeManager INSTANCE = new TreeStumpRecipeManager();

    @SuppressWarnings("rawtypes")
    @Override
    public IRecipeType getRecipeType() {
        return TreeStumpRecipe.tree_stump;
    }

    @Method
    public void addRecipe(String name, IItemStack output, IIngredient tool, IIngredient input, int chopTimes, @OptionalString String group) {
        TreeStumpRecipe recipe = new TreeStumpRecipe(new ResourceLocation("crafttweaker", name), group, input.asVanillaIngredient(),
                tool.asVanillaIngredient(), output.getInternal(), chopTimes);
        CraftTweakerAPI.apply(new ActionAddRecipe(this, recipe, ""));
    }
}
