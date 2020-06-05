package com.yanny.age.stone.compatibility.crt;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.item.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.managers.IRecipeManager;
import com.blamejared.crafttweaker.impl.actions.recipes.ActionAddRecipe;
import com.yanny.age.stone.recipes.DryingRackRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.ResourceLocation;
import org.openzen.zencode.java.ZenCodeType;

import static org.openzen.zencode.java.ZenCodeType.*;

@ZenRegister
@Name("mods.stone_age.DryingRackManager")
public class DryingRackRecipeManager implements IRecipeManager {
    @SuppressWarnings("unused")
    @ZenCodeType.Field
    public static final DryingRackRecipeManager INSTANCE = new DryingRackRecipeManager();

    @SuppressWarnings("rawtypes")
    @Override
    public IRecipeType getRecipeType() {
        return DryingRackRecipe.drying_rack;
    }

    @Method
    public void addRecipe(String name, IItemStack output, IIngredient input, int dryingTime, @OptionalString String group) {
        DryingRackRecipe recipe = new DryingRackRecipe(new ResourceLocation("crafttweaker", name), group, input.asVanillaIngredient(), output.getInternal(), dryingTime);
        CraftTweakerAPI.apply(new ActionAddRecipe(this, recipe, ""));
    }
}
