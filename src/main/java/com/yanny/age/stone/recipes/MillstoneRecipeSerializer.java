package com.yanny.age.stone.recipes;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.IngredientNBT;
import net.minecraftforge.registries.ForgeRegistryEntry;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class MillstoneRecipeSerializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<MillstoneRecipe> {
    private final MillstoneRecipeSerializer.IFactory<MillstoneRecipe> factory;

    public MillstoneRecipeSerializer(@Nonnull MillstoneRecipeSerializer.IFactory<MillstoneRecipe> factory) {
        this.factory = factory;
    }

    @Override
    @Nonnull
    public MillstoneRecipe read(@Nonnull ResourceLocation recipeId, @Nonnull JsonObject json) {
        String group = JSONUtils.getString(json, "group", "");
        JsonElement jsonelement = JSONUtils.isJsonArray(json, "ingredient")
                ? JSONUtils.getJsonArray(json, "ingredient")
                : JSONUtils.getJsonObject(json, "ingredient");
        Ingredient ingredient = IngredientNBT.deserialize(jsonelement);
        ItemStack result;
        ItemStack secondResult = ItemStack.EMPTY;
        int activateCount = JSONUtils.getInt(json, "activateCount", 1);
        double secondChance = JSONUtils.getFloat(json, "secondChance", 1.0f);

        if (!json.has("result")) {
            throw new com.google.gson.JsonSyntaxException("Missing result, expected to find a string or object");
        }

        if (json.get("result").isJsonObject()) {
            result = CraftingHelper.getItemStack(JSONUtils.getJsonObject(json, "result"), true);
        } else {
            String s1 = JSONUtils.getString(json, "result");
            ResourceLocation resourcelocation = new ResourceLocation(s1);
            //noinspection deprecation
            result = new ItemStack(Registry.ITEM.getValue(resourcelocation).orElseThrow(() -> new IllegalStateException("Item: " + s1 + " does not exist")));
        }

        if (json.has("secondResult")) {
            if (json.get("secondResult").isJsonObject()) {
                secondResult = CraftingHelper.getItemStack(JSONUtils.getJsonObject(json, "secondResult"), true);
            } else {
                String string = JSONUtils.getString(json, "secondResult");
                ResourceLocation resourceLocation = new ResourceLocation(string);
                //noinspection deprecation
                secondResult = new ItemStack(Registry.ITEM.getValue(resourceLocation).orElseThrow(() -> new IllegalStateException("Item: " + string + " does not exist")));
            }
        } else {
            secondChance = 0;
        }

        return this.factory.create(recipeId, group, ingredient, result, secondResult, secondChance, activateCount);
    }

    @Nullable
    @Override
    public MillstoneRecipe read(@Nonnull ResourceLocation recipeId, PacketBuffer buffer) {
        String group = buffer.readString(32767);
        Ingredient ingredient = Ingredient.read(buffer);
        ItemStack result = buffer.readItemStack();
        ItemStack secondResult = buffer.readItemStack();
        double secondChance = buffer.readDouble();
        int activateCount = buffer.readInt();

        return this.factory.create(recipeId, group, ingredient, result, secondResult, secondChance, activateCount);
    }

    @Override
    public void write(PacketBuffer buffer, MillstoneRecipe recipe) {
        buffer.writeString(recipe.group);
        recipe.ingredient.write(buffer);
        buffer.writeItemStack(recipe.result);
        buffer.writeItemStack(recipe.secondResult);
        buffer.writeDouble(recipe.secondChance);
        buffer.writeInt(recipe.activateCount);
    }

    public interface IFactory<T extends MillstoneRecipe> {
        T create(ResourceLocation resourceLocation, String group, Ingredient ingredient, ItemStack result, ItemStack secondResult, double secondChance, int activateCount);
    }
}
