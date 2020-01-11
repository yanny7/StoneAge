package com.yanny.age.stone.manual;

import com.google.gson.JsonElement;
import net.minecraft.item.crafting.IRecipe;

public interface IManual {
    void changePage(String key);
    void addLink(String key, int page);
    JsonElement getConstant(String key);
    IRecipeHandler getRecipeHandler(IRecipe<?> recipe);
}
