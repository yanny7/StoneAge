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

public class TanningRackRecipeSerializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<TanningRackRecipe> {
    private final TanningRackRecipeSerializer.IFactory<TanningRackRecipe> factory;

    public TanningRackRecipeSerializer(@Nonnull TanningRackRecipeSerializer.IFactory<TanningRackRecipe> factory) {
        this.factory = factory;
    }

    @Override
    @Nonnull
    public TanningRackRecipe read(@Nonnull ResourceLocation recipeId, @Nonnull JsonObject json) {
        String s = JSONUtils.getString(json, "group", "");
        JsonElement jsonelement = JSONUtils.isJsonArray(json, "ingredient")
                ? JSONUtils.getJsonArray(json, "ingredient")
                : JSONUtils.getJsonObject(json, "ingredient");
        Ingredient ingredient = Ingredient.deserialize(jsonelement);
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

        JsonElement toolElement = JSONUtils.isJsonArray(json, "tool")
                ? JSONUtils.getJsonArray(json, "tool")
                : JSONUtils.getJsonObject(json, "tool");
        Ingredient tool = Ingredient.deserialize(toolElement);

        return this.factory.create(recipeId, s, ingredient, itemstack, tool);
    }

    @Nullable
    @Override
    public TanningRackRecipe read(@Nonnull ResourceLocation recipeId, PacketBuffer buffer) {
        String s = buffer.readString(32767);
        Ingredient ingredient = Ingredient.read(buffer);
        ItemStack itemstack = buffer.readItemStack();
        Ingredient tool = Ingredient.read(buffer);

        return this.factory.create(recipeId, s, ingredient, itemstack, tool);
    }

    @Override
    public void write(PacketBuffer buffer, TanningRackRecipe recipe) {
        buffer.writeString(recipe.group);
        recipe.ingredient.write(buffer);
        buffer.writeItemStack(recipe.result);
        recipe.tool.write(buffer);
    }

    public interface IFactory<T extends TanningRackRecipe> {
        T create(@Nonnull ResourceLocation resourceLocation, @Nonnull String group, @Nonnull Ingredient ingredient, @Nonnull ItemStack result, @Nonnull Ingredient tool);
    }
}
