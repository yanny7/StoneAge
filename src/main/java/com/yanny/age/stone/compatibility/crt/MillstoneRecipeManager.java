package com.yanny.age.stone.compatibility.crt;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.item.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.managers.IRecipeManager;
import com.blamejared.crafttweaker.impl.actions.recipes.ActionAddRecipe;
import com.yanny.age.stone.recipes.MillstoneRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.ResourceLocation;

import static org.openzen.zencode.java.ZenCodeType.*;

@ZenRegister
@Name("mods.stone_age.MillstoneManager")
public class MillstoneRecipeManager implements IRecipeManager {
    @SuppressWarnings("unused")
    public static final MillstoneRecipeManager INSTANCE = new MillstoneRecipeManager();

    @SuppressWarnings("rawtypes")
    @Override
    public IRecipeType getRecipeType() {
        return MillstoneRecipe.millstone;
    }

    @Method
    public void addRecipe(String name, IItemStack output, IIngredient input, @OptionalString String group) {
        MillstoneRecipe recipe = new MillstoneRecipe(new ResourceLocation("crafttweaker", name), group, input.asVanillaIngredient(), output.getInternal());
        CraftTweakerAPI.apply(new ActionAddRecipe(this, recipe, ""));
    }
}
