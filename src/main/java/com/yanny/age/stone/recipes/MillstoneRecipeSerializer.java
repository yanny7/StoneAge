package com.yanny.age.stone.recipes;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.NBTIngredient;
import net.minecraftforge.registries.ForgeRegistries;
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
        Ingredient ingredient = NBTIngredient.deserialize(jsonelement);
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
            result = new ItemStack(ForgeRegistries.ITEMS.getValue(resourcelocation));
        }

        if (json.has("secondResult")) {
            if (json.get("secondResult").isJsonObject()) {
                secondResult = CraftingHelper.getItemStack(JSONUtils.getJsonObject(json, "secondResult"), true);
            } else {
                String string = JSONUtils.getString(json, "secondResult");
                ResourceLocation resourceLocation = new ResourceLocation(string);
                secondResult = new ItemStack(ForgeRegistries.ITEMS.getValue(resourceLocation));
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
        T create(@Nonnull ResourceLocation resourceLocation, @Nonnull String group, @Nonnull Ingredient ingredient, @Nonnull ItemStack result,
                 @Nonnull ItemStack secondResult, double secondChance, int activateCount);
    }
}
