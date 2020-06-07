package com.yanny.age.stone.compatibility.crt;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.item.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.managers.IRecipeManager;
import com.blamejared.crafttweaker.impl.actions.recipes.ActionAddRecipe;
import com.yanny.age.stone.recipes.TanningRackRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.ResourceLocation;
import org.openzen.zencode.java.ZenCodeType;

import static org.openzen.zencode.java.ZenCodeType.*;

@ZenRegister
@Name("mods.stone_age.TanningRackManager")
public class TanningRackManager implements IRecipeManager {
    @SuppressWarnings("unused")
    @ZenCodeType.Field
    public static final TanningRackManager INSTANCE = new TanningRackManager();

    @SuppressWarnings("rawtypes")
    @Override
    public IRecipeType getRecipeType() {
        return TanningRackRecipe.tanning_rack;
    }

    @Method
    public void addRecipe(String name, IItemStack output, IIngredient input, IIngredient tool, @OptionalString String group) {
        TanningRackRecipe recipe = new TanningRackRecipe(new ResourceLocation("crafttweaker", name), group,
                input.asVanillaIngredient(), output.getInternal(), tool.asVanillaIngredient());
        CraftTweakerAPI.apply(new ActionAddRecipe(this, recipe, ""));
    }
}
