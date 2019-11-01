package com.yanny.age.zero.subscribers;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.yanny.age.zero.config.Config;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementList;
import net.minecraft.advancements.AdvancementManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.RecipeManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Set;

import static com.yanny.age.zero.Reference.MODID;

@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ForgeEventSubscriber {
    private static final Set<ResourceLocation> RESOURCES_TO_REMOVE = Sets.newHashSet(
            // tools
            new ResourceLocation("minecraft", "wooden_axe"),
            new ResourceLocation("minecraft", "wooden_pickaxe"),
            new ResourceLocation("minecraft", "wooden_hoe"),
            new ResourceLocation("minecraft", "wooden_shovel"),
            new ResourceLocation("minecraft", "wooden_sword"),
            new ResourceLocation("minecraft", "stone_axe"),
            new ResourceLocation("minecraft", "stone_pickaxe"),
            new ResourceLocation("minecraft", "stone_hoe"),
            new ResourceLocation("minecraft", "stone_shovel"),
            new ResourceLocation("minecraft", "stone_sword"),
            //planks
            new ResourceLocation("minecraft", "oak_planks"),
            new ResourceLocation("minecraft", "birch_planks"),
            new ResourceLocation("minecraft", "acacia_planks"),
            new ResourceLocation("minecraft", "jungle_planks"),
            new ResourceLocation("minecraft", "spruce_planks"),
            new ResourceLocation("minecraft", "dark_oak_planks")
    );

    @SuppressWarnings("unchecked")
    @SubscribeEvent
    public static void FMLServerStartingEvent(FMLServerStartingEvent event) {
        if (!Config.removeVanillaRecipes) {
            return;
        }

        RecipeManager recipeManager = event.getServer().getRecipeManager();
        Class recipeManagerClass = recipeManager.getClass();

        try {
            Field recipes = recipeManagerClass.getDeclaredFields()[2];
            recipes.setAccessible(true);
            Map<IRecipeType<?>, Map<ResourceLocation, IRecipe<?>>> recipesMap = (Map<IRecipeType<?>, Map<ResourceLocation, IRecipe<?>>>) recipes.get(recipeManager);
            Map<IRecipeType<?>, Map<ResourceLocation, IRecipe<?>>> map = Maps.newHashMap();
            recipesMap.forEach((iRecipeType, resourceLocationIRecipeMap) -> {
                Map<ResourceLocation, IRecipe<?>> map1 = map.computeIfAbsent(iRecipeType, (recipeType) -> Maps.newHashMap());
                resourceLocationIRecipeMap.forEach(map1::put);
                RESOURCES_TO_REMOVE.forEach(map1::remove);
            });
            recipes.set(recipeManager, ImmutableMap.copyOf(map));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        AdvancementManager advancementManager = event.getServer().getAdvancementManager();
        Class advancementManagerClass = advancementManager.getClass();

        try {
            Field advancements = advancementManagerClass.getDeclaredFields()[2];
            advancements.setAccessible(true);
            AdvancementList advancementList = (AdvancementList) advancements.get(advancementManager);

            Class list = advancementList.getClass();
            Field listField = list.getDeclaredFields()[1];
            listField.setAccessible(true);
            Map<ResourceLocation, Advancement> map = (Map<ResourceLocation, Advancement>) listField.get(advancementList);
            RESOURCES_TO_REMOVE.forEach(map::remove);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
