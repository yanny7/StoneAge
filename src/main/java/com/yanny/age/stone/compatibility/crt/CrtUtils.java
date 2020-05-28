package com.yanny.age.stone.compatibility.crt;

import com.blamejared.crafttweaker.api.item.IIngredient;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import org.apache.commons.lang3.tuple.Triple;

class CrtUtils {
    public static Triple<NonNullList<Ingredient>, Integer, Integer> convert(IIngredient[][] input) {
        int height = input.length;
        int tempWidth = input[0].length;

        for (IIngredient[] iIngredients : input) {
            tempWidth = Math.max(iIngredients.length, tempWidth);
        }

        int width = tempWidth;

        NonNullList<Ingredient> ingredients = NonNullList.withSize(height * width, Ingredient.EMPTY);

        for(int row = 0; row < input.length; ++row) {
            IIngredient[] ingredientRow = input[row];

            for(int column = 0; column < ingredientRow.length; ++column) {
                ingredients.set(row * width + column, ingredientRow[column].asVanillaIngredient());
            }
        }

        //noinspection SuspiciousNameCombination
        return Triple.of(ingredients, width, height);
    }
}
