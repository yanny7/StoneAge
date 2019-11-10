package com.yanny.age.zero.subscribers;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.yanny.age.zero.blocks.FlintWorkbenchTileEntity;
import com.yanny.age.zero.config.Config;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementList;
import net.minecraft.advancements.AdvancementManager;
import net.minecraft.block.Block;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.RecipeManager;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootTable;
import net.minecraft.world.storage.loot.LootTableManager;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static com.yanny.age.zero.Reference.MODID;
import static net.minecraft.block.Blocks.*;

@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ForgeEventSubscriber {
    private static final Set<ResourceLocation> RECIPES_TO_REMOVE = Sets.newHashSet(
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
            new ResourceLocation("minecraft", "oak_planks"),
            new ResourceLocation("minecraft", "birch_planks"),
            new ResourceLocation("minecraft", "acacia_planks"),
            new ResourceLocation("minecraft", "jungle_planks"),
            new ResourceLocation("minecraft", "spruce_planks"),
            new ResourceLocation("minecraft", "dark_oak_planks")
    );
    //private static final Set<ResourceLocation> RECIPES_TO_ADD = Sets.newHashSet();

    private static final Set<ResourceLocation> LOOTS_TO_REMOVE = Sets.newHashSet(
            new ResourceLocation("minecraft", "entities/cow"),
            new ResourceLocation("minecraft", "entities/pig"),
            new ResourceLocation("minecraft", "entities/sheep"),
            new ResourceLocation("minecraft", "blocks/acacia_leaves"),
            new ResourceLocation("minecraft", "blocks/birch_leaves"),
            new ResourceLocation("minecraft", "blocks/dark_oak_leaves"),
            new ResourceLocation("minecraft", "blocks/jungle_leaves"),
            new ResourceLocation("minecraft", "blocks/oak_leaves"),
            new ResourceLocation("minecraft", "blocks/spruce_leaves"),
            new ResourceLocation("minecraft", "blocks/gravel")
    );
    private static final Set<ResourceLocation> LOOTS_TO_ADD = Sets.newHashSet(
            new ResourceLocation(MODID, "entities/cow"),
            new ResourceLocation(MODID, "entities/pig"),
            new ResourceLocation(MODID, "entities/sheep"),
            new ResourceLocation(MODID, "blocks/acacia_leaves"),
            new ResourceLocation(MODID, "blocks/birch_leaves"),
            new ResourceLocation(MODID, "blocks/dark_oak_leaves"),
            new ResourceLocation(MODID, "blocks/jungle_leaves"),
            new ResourceLocation(MODID, "blocks/oak_leaves"),
            new ResourceLocation(MODID, "blocks/spruce_leaves"),
            new ResourceLocation(MODID, "blocks/gravel")
    );

    private static final Set<ResourceLocation> ADVANCEMENTS_TO_REMOVE = Sets.newHashSet(
            new ResourceLocation("minecraft", "recipes/building_blocks/oak_planks"),
            new ResourceLocation("minecraft", "recipes/building_blocks/birch_planks"),
            new ResourceLocation("minecraft", "recipes/building_blocks/acacia_planks"),
            new ResourceLocation("minecraft", "recipes/building_blocks/jungle_planks"),
            new ResourceLocation("minecraft", "recipes/building_blocks/spruce_planks"),
            new ResourceLocation("minecraft", "recipes/building_blocks/dark_oak_planks")
    );

    @SuppressWarnings("unchecked")
    @SubscribeEvent
    public static void FMLServerStartingEvent(FMLServerStartingEvent event) {
        if (Config.removeVanillaRecipes) {
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
                    RECIPES_TO_REMOVE.forEach(map1::remove);
                /*RECIPES_TO_ADD.forEach(resourceLocation -> {
                    IRecipe<?> recipe = map1.remove(resourceLocation);
                    map1.put(new ResourceLocation("minecraft", resourceLocation.getPath()), recipe);
                });*/
                });
                recipes.set(recipeManager, ImmutableMap.copyOf(map));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

            AdvancementManager advancementManager = event.getServer().getAdvancementManager();
            Class advancementManagerClass = advancementManager.getClass();
            Field advancements = advancementManagerClass.getDeclaredFields()[2];
            advancements.setAccessible(true);

            try {
                AdvancementList advancementList = (AdvancementList) advancements.get(advancementManager);
                Class list = advancementList.getClass();
                Field listField = list.getDeclaredFields()[1];
                listField.setAccessible(true);
                Map<ResourceLocation, Advancement> map = (Map<ResourceLocation, Advancement>) listField.get(advancementList);
                ADVANCEMENTS_TO_REMOVE.forEach(map::remove);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

            LootTableManager lootTableManager = event.getServer().getLootTableManager();
            Class lootTableManagerClass = lootTableManager.getClass();
            Field lootTable = lootTableManagerClass.getDeclaredFields()[2];
            lootTable.setAccessible(true);

            try {
                Map<ResourceLocation, LootTable> lootTableMap = (Map<ResourceLocation, LootTable>) lootTable.get(lootTableManager);
                HashMap<ResourceLocation, LootTable> map = Maps.newHashMap();
                lootTableMap.forEach(map::put);
                LOOTS_TO_REMOVE.forEach(map::remove);
                LOOTS_TO_ADD.forEach(resourceLocation -> {
                LootTable loot = map.remove(resourceLocation);
                map.put(new ResourceLocation("minecraft", resourceLocation.getPath()), loot);
            });

                lootTable.set(lootTableManager, ImmutableMap.copyOf(map));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        if (Config.changeMiningLevelForVanillaBlocks) {
            Set<Block> set = ImmutableSet.of(COAL_ORE, COBBLESTONE, ICE, MOSSY_COBBLESTONE, NETHERRACK, PACKED_ICE, BLUE_ICE, SANDSTONE, CHISELED_SANDSTONE,
                    CUT_SANDSTONE, CHISELED_RED_SANDSTONE, CUT_RED_SANDSTONE, RED_SANDSTONE, STONE, GRANITE, POLISHED_GRANITE, DIORITE, POLISHED_DIORITE, ANDESITE,
                    POLISHED_ANDESITE, STONE_SLAB, SMOOTH_STONE_SLAB, SANDSTONE_SLAB, PETRIFIED_OAK_SLAB, COBBLESTONE_SLAB, BRICK_SLAB, STONE_BRICK_SLAB,
                    NETHER_BRICK_SLAB, RED_SANDSTONE_SLAB, SMOOTH_RED_SANDSTONE, SMOOTH_SANDSTONE, SMOOTH_STONE, STONE_BUTTON, STONE_PRESSURE_PLATE,
                    POLISHED_GRANITE_SLAB, SMOOTH_RED_SANDSTONE_SLAB, MOSSY_STONE_BRICK_SLAB, POLISHED_DIORITE_SLAB, MOSSY_COBBLESTONE_SLAB, SMOOTH_SANDSTONE_SLAB,
                    GRANITE_SLAB, ANDESITE_SLAB, RED_NETHER_BRICK_SLAB, POLISHED_ANDESITE_SLAB, DIORITE_SLAB, SHULKER_BOX, BLACK_SHULKER_BOX, BLUE_SHULKER_BOX,
                    BROWN_SHULKER_BOX, CYAN_SHULKER_BOX, GRAY_SHULKER_BOX, GREEN_SHULKER_BOX, LIGHT_BLUE_SHULKER_BOX, LIGHT_GRAY_SHULKER_BOX, LIME_SHULKER_BOX,
                    MAGENTA_SHULKER_BOX, ORANGE_SHULKER_BOX, PINK_SHULKER_BOX, PURPLE_SHULKER_BOX, RED_SHULKER_BOX, WHITE_SHULKER_BOX, YELLOW_SHULKER_BOX);
            set.forEach(block -> setHarvestLevel(block, ToolSubscriber.Tiers.BONE_TIER.getHarvestLevel()));
        }
    }

    @SubscribeEvent
    public static void rightClickWithFlint(PlayerInteractEvent.RightClickBlock event) {
        TileEntity entity = event.getWorld().getTileEntity(event.getPos());

        if (entity instanceof FlintWorkbenchTileEntity) {
            ((FlintWorkbenchTileEntity) entity).blockClicked(event.getPlayer());
        }
    }

    private static void setHarvestLevel(Block block, @SuppressWarnings("SameParameterValue") int harvestLevel) {
        Class clazz = Block.class;
        Field harvestLevelField = clazz.getDeclaredFields()[24];
        harvestLevelField.setAccessible(true);
        try {
            harvestLevelField.set(block, harvestLevel);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
