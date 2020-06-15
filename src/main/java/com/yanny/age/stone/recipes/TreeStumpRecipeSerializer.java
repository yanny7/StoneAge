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
import net.minecraftforge.registries.ForgeRegistryEntry;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class TreeStumpRecipeSerializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<TreeStumpRecipe> {
    private final TreeStumpRecipeSerializer.IFactory<TreeStumpRecipe> factory;

    public TreeStumpRecipeSerializer(@Nonnull TreeStumpRecipeSerializer.IFactory<TreeStumpRecipe> factory) {
        this.factory = factory;
    }

    @Override
    @Nonnull
    public TreeStumpRecipe read(@Nonnull ResourceLocation recipeId, @Nonnull JsonObject json) {
        String s = JSONUtils.getString(json, "group", "");
        JsonElement ingredientJsonElement = JSONUtils.isJsonArray(json, "ingredient")
                ? JSONUtils.getJsonArray(json, "ingredient")
                : JSONUtils.getJsonObject(json, "ingredient");
        JsonElement toolJsonElement = JSONUtils.isJsonArray(json, "tool")
                ? JSONUtils.getJsonArray(json, "tool")
                : JSONUtils.getJsonObject(json, "tool");
        Ingredient ingredient = Ingredient.deserialize(ingredientJsonElement);
        Ingredient tool = Ingredient.deserialize(toolJsonElement);
        ItemStack itemstack;

        if (!json.has("result")) {
            throw new com.google.gson.JsonSyntaxException("Missing result, expected to find a string or object");
        }

        if (json.get("result").isJsonObject()) {
            itemstack = CraftingHelper.getItemStack(JSONUtils.getJsonObject(json, "result"), true);
        } else {
            String s1 = JSONUtils.getString(json, "result");
            ResourceLocation resourcelocation = new ResourceLocation(s1);
            //noinspection deprecation
            itemstack = new ItemStack(Registry.ITEM.getValue(resourcelocation).orElseThrow(() -> new IllegalStateException("Item: " + s1 + " does not exist")));
        }

        int i = JSONUtils.getInt(json, "chopTimes", 1);

        return this.factory.create(recipeId, s, ingredient, tool, itemstack, i);
    }

    @Nullable
    @Override
    public TreeStumpRecipe read(@Nonnull ResourceLocation recipeId, PacketBuffer buffer) {
        String s = buffer.readString(32767);
        Ingredient ingredient = Ingredient.read(buffer);
        Ingredient tool = Ingredient.read(buffer);
        ItemStack itemstack = buffer.readItemStack();

        int i = buffer.readVarInt();

        return this.factory.create(recipeId, s, ingredient, tool, itemstack, i);
    }

    @Override
    public void write(PacketBuffer buffer, TreeStumpRecipe recipe) {
        buffer.writeString(recipe.group);
        recipe.ingredient.write(buffer);
        recipe.tool.write(buffer);
        buffer.writeItemStack(recipe.result);
        buffer.writeVarInt(recipe.chopTimes);
    }

    public interface IFactory<T extends TreeStumpRecipe> {
        T create(@Nonnull ResourceLocation resourceLocation, @Nonnull String group, @Nonnull Ingredient ingredient, @Nonnull Ingredient tool,
                 @Nonnull ItemStack result, int chopTimes);
    }
}
