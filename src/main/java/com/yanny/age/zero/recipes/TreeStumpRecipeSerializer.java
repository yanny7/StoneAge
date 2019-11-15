package com.yanny.age.zero.recipes;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipe;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class TreeStumpRecipeSerializer<T extends TreeStumpRecipe>
        extends net.minecraftforge.registries.ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<T> {
    private final TreeStumpRecipeSerializer.IFactory<T> factory;

    public TreeStumpRecipeSerializer(TreeStumpRecipeSerializer.IFactory<T> factory) {
        this.factory = factory;
    }

    @Override
    @Nonnull
    public T read(@Nonnull ResourceLocation recipeId, @Nonnull JsonObject json) {
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
            itemstack = ShapedRecipe.deserializeItem(JSONUtils.getJsonObject(json, "result"));
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
    public T read(@Nonnull ResourceLocation recipeId, PacketBuffer buffer) {
        String s = buffer.readString(32767);
        Ingredient ingredient = Ingredient.read(buffer);
        Ingredient tool = Ingredient.read(buffer);
        ItemStack itemstack = buffer.readItemStack();

        int i = buffer.readVarInt();

        return this.factory.create(recipeId, s, ingredient, tool, itemstack, i);
    }

    @Override
    public void write(PacketBuffer buffer, T recipe) {
        buffer.writeString(recipe.group);
        recipe.ingredient.write(buffer);
        recipe.tool.write(buffer);
        buffer.writeItemStack(recipe.result);
        buffer.writeVarInt(recipe.chopTimes);
    }

    public interface IFactory<T extends TreeStumpRecipe> {
        T create(ResourceLocation resourceLocation, String group, Ingredient ingredient, Ingredient tool, ItemStack result, int chopTimes);
    }
}
