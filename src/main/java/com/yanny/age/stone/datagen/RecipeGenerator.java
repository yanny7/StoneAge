package com.yanny.age.stone.datagen;

import com.yanny.age.stone.Reference;
import com.yanny.age.stone.subscribers.BlockSubscriber;
import com.yanny.age.stone.subscribers.FoodSubscriber;
import com.yanny.age.stone.subscribers.ItemSubscriber;
import com.yanny.age.stone.subscribers.ToolSubscriber;
import net.minecraft.advancements.criterion.ItemPredicate;
import net.minecraft.data.*;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.Tags;
import org.lwjgl.system.NonnullDefault;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

@SuppressWarnings("ConstantConditions")
@NonnullDefault
public class RecipeGenerator extends RecipeProvider {
    private static final Set<WoodItemHolder> WOOD_ITEMS = new HashSet<>();
    private static final Set<SlabHolder> SLABS = new HashSet<>();
    private static final Set<TerracottaHolder> TERRACOTTA = new HashSet<>();
    private static final Set<FoodHolder> FOODS = new HashSet<>();
    private static final Set<ToolHolder> TOOLS = new HashSet<>();
    private static final Set<OneItemHolder> ONE_ITEMS = new HashSet<>();

    static {
        WOOD_ITEMS.add(new WoodItemHolder(Items.ACACIA_BOAT, Items.ACACIA_FENCE, Items.ACACIA_FENCE_GATE, Items.ACACIA_PLANKS, Items.ACACIA_SLAB, Items.ACACIA_LOG));
        WOOD_ITEMS.add(new WoodItemHolder(Items.BIRCH_BOAT, Items.BIRCH_FENCE, Items.BIRCH_FENCE_GATE, Items.BIRCH_PLANKS, Items.BIRCH_SLAB, Items.BIRCH_LOG));
        WOOD_ITEMS.add(new WoodItemHolder(Items.DARK_OAK_BOAT, Items.DARK_OAK_FENCE, Items.DARK_OAK_FENCE_GATE, Items.DARK_OAK_PLANKS, Items.DARK_OAK_SLAB, Items.DARK_OAK_LOG));
        WOOD_ITEMS.add(new WoodItemHolder(Items.JUNGLE_BOAT, Items.JUNGLE_FENCE, Items.JUNGLE_FENCE_GATE, Items.JUNGLE_PLANKS, Items.JUNGLE_SLAB, Items.JUNGLE_LOG));
        WOOD_ITEMS.add(new WoodItemHolder(Items.OAK_BOAT, Items.OAK_FENCE, Items.OAK_FENCE_GATE, Items.OAK_PLANKS, Items.OAK_SLAB, Items.OAK_LOG));
        WOOD_ITEMS.add(new WoodItemHolder(Items.SPRUCE_BOAT, Items.SPRUCE_FENCE, Items.SPRUCE_FENCE_GATE, Items.SPRUCE_PLANKS, Items.SPRUCE_SLAB, Items.SPRUCE_LOG));

        SLABS.add(new SlabHolder(Items.ANDESITE, Items.ANDESITE_SLAB));
        SLABS.add(new SlabHolder(Items.BRICKS, Items.BRICK_SLAB));
        SLABS.add(new SlabHolder(Items.COBBLESTONE, Items.COBBLESTONE_SLAB));
        SLABS.add(new SlabHolder(Items.CUT_RED_SANDSTONE, Items.CUT_RED_SANDSTONE_SLAB));
        SLABS.add(new SlabHolder(Items.CUT_SANDSTONE, Items.CUT_SANDSTONE_SLAB));
        SLABS.add(new SlabHolder(Items.DIORITE, Items.DIORITE_SLAB));
        SLABS.add(new SlabHolder(Items.GRANITE, Items.GRANITE_SLAB));
        SLABS.add(new SlabHolder(Items.MOSSY_COBBLESTONE, Items.MOSSY_COBBLESTONE_SLAB));
        SLABS.add(new SlabHolder(Items.MOSSY_STONE_BRICKS, Items.MOSSY_STONE_BRICK_SLAB));
        SLABS.add(new SlabHolder(Items.POLISHED_ANDESITE, Items.POLISHED_ANDESITE_SLAB));
        SLABS.add(new SlabHolder(Items.POLISHED_DIORITE, Items.POLISHED_DIORITE_SLAB));
        SLABS.add(new SlabHolder(Items.POLISHED_GRANITE, Items.POLISHED_GRANITE_SLAB));
        SLABS.add(new SlabHolder(Items.RED_SANDSTONE, Items.RED_SANDSTONE_SLAB));
        SLABS.add(new SlabHolder(Items.SANDSTONE, Items.SANDSTONE_SLAB));
        SLABS.add(new SlabHolder(Items.SMOOTH_RED_SANDSTONE, Items.SMOOTH_RED_SANDSTONE_SLAB));
        SLABS.add(new SlabHolder(Items.SMOOTH_SANDSTONE, Items.SMOOTH_SANDSTONE_SLAB));
        SLABS.add(new SlabHolder(Items.SMOOTH_STONE, Items.SMOOTH_STONE_SLAB));
        SLABS.add(new SlabHolder(Items.STONE_BRICKS, Items.STONE_BRICK_SLAB));
        SLABS.add(new SlabHolder(Items.STONE, Items.STONE_SLAB));

        TERRACOTTA.add(new TerracottaHolder(Items.BLACK_TERRACOTTA, Tags.Items.DYES_BLACK));
        TERRACOTTA.add(new TerracottaHolder(Items.BLUE_TERRACOTTA, Tags.Items.DYES_BLUE));
        TERRACOTTA.add(new TerracottaHolder(Items.BROWN_TERRACOTTA, Tags.Items.DYES_BROWN));
        TERRACOTTA.add(new TerracottaHolder(Items.CYAN_TERRACOTTA, Tags.Items.DYES_CYAN));
        TERRACOTTA.add(new TerracottaHolder(Items.GRAY_TERRACOTTA, Tags.Items.DYES_GRAY));
        TERRACOTTA.add(new TerracottaHolder(Items.GREEN_TERRACOTTA, Tags.Items.DYES_GREEN));
        TERRACOTTA.add(new TerracottaHolder(Items.LIGHT_BLUE_TERRACOTTA, Tags.Items.DYES_LIGHT_BLUE));
        TERRACOTTA.add(new TerracottaHolder(Items.LIGHT_GRAY_TERRACOTTA, Tags.Items.DYES_LIGHT_GRAY));
        TERRACOTTA.add(new TerracottaHolder(Items.LIME_TERRACOTTA, Tags.Items.DYES_LIME));
        TERRACOTTA.add(new TerracottaHolder(Items.MAGENTA_TERRACOTTA, Tags.Items.DYES_MAGENTA));
        TERRACOTTA.add(new TerracottaHolder(Items.ORANGE_TERRACOTTA, Tags.Items.DYES_ORANGE));
        TERRACOTTA.add(new TerracottaHolder(Items.PINK_TERRACOTTA, Tags.Items.DYES_PINK));
        TERRACOTTA.add(new TerracottaHolder(Items.PURPLE_TERRACOTTA, Tags.Items.DYES_PURPLE));
        TERRACOTTA.add(new TerracottaHolder(Items.RED_TERRACOTTA, Tags.Items.DYES_RED));
        TERRACOTTA.add(new TerracottaHolder(Items.WHITE_TERRACOTTA, Tags.Items.DYES_WHITE));
        TERRACOTTA.add(new TerracottaHolder(Items.YELLOW_TERRACOTTA, Tags.Items.DYES_YELLOW));

        FOODS.add(new FoodHolder(FoodSubscriber.auroch_meat, FoodSubscriber.cooked_auroch_meat));
        FOODS.add(new FoodHolder(FoodSubscriber.boar_meat, FoodSubscriber.cooked_boar_meat));
        FOODS.add(new FoodHolder(FoodSubscriber.fat, FoodSubscriber.cooked_fat));
        FOODS.add(new FoodHolder(FoodSubscriber.fowl_meat, FoodSubscriber.cooked_fowl_meat));
        FOODS.add(new FoodHolder(FoodSubscriber.mammoth_meat, FoodSubscriber.cooked_mammoth_meat));
        FOODS.add(new FoodHolder(FoodSubscriber.mouflon_meat, FoodSubscriber.cooked_mouflon_meat));
        FOODS.add(new FoodHolder(FoodSubscriber.rhino_meat, FoodSubscriber.cooked_rhino_meat));
        FOODS.add(new FoodHolder(FoodSubscriber.tiger_meat, FoodSubscriber.cooked_tiger_meat));
        FOODS.add(new FoodHolder(FoodSubscriber.venison, FoodSubscriber.cooked_venison));
        FOODS.add(new FoodHolder(ItemSubscriber.raw_bread, Items.BREAD));

        TOOLS.add(new ToolHolder(ToolSubscriber.bone_axe, ToolSubscriber.bone_axe_head, ItemSubscriber.grass_lead));
        TOOLS.add(new ToolHolder(ToolSubscriber.bone_hoe, ToolSubscriber.bone_hoe_head, ItemSubscriber.grass_lead));
        TOOLS.add(new ToolHolder(ToolSubscriber.bone_pickaxe, ToolSubscriber.bone_pickaxe_head, ItemSubscriber.grass_lead));
        TOOLS.add(new ToolHolder(ToolSubscriber.bone_shovel, ToolSubscriber.bone_shovel_head, ItemSubscriber.grass_lead));
        TOOLS.add(new ToolHolder(ToolSubscriber.bone_sword, ToolSubscriber.bone_sword_head, ItemSubscriber.grass_lead));
        TOOLS.add(new ToolHolder(ToolSubscriber.stone_axe, ToolSubscriber.stone_axe_head, ItemSubscriber.leather_strip));
        TOOLS.add(new ToolHolder(ToolSubscriber.stone_hoe, ToolSubscriber.stone_hoe_head, ItemSubscriber.leather_strip));
        TOOLS.add(new ToolHolder(ToolSubscriber.stone_pickaxe, ToolSubscriber.stone_pickaxe_head, ItemSubscriber.leather_strip));
        TOOLS.add(new ToolHolder(ToolSubscriber.stone_shovel, ToolSubscriber.stone_shovel_head, ItemSubscriber.leather_strip));
        TOOLS.add(new ToolHolder(ToolSubscriber.stone_sword, ToolSubscriber.stone_sword_head, ItemSubscriber.leather_strip));

        ONE_ITEMS.add(new OneItemHolder(ItemSubscriber.bone_arrow_head, com.yanny.ages.api.utils.Tags.Items.BONES, com.yanny.ages.api.utils.Tags.Items.BONES, new String[]{"#"}));
        ONE_ITEMS.add(new OneItemHolder(ToolSubscriber.bone_axe_head, com.yanny.ages.api.utils.Tags.Items.BONES, com.yanny.ages.api.utils.Tags.Items.BONES, new String[]{"##", "# "}));
        ONE_ITEMS.add(new OneItemHolder(ToolSubscriber.bone_hoe_head, com.yanny.ages.api.utils.Tags.Items.BONES, com.yanny.ages.api.utils.Tags.Items.BONES, new String[]{"##"}));
        ONE_ITEMS.add(new OneItemHolder(ToolSubscriber.bone_pickaxe_head, com.yanny.ages.api.utils.Tags.Items.BONES, com.yanny.ages.api.utils.Tags.Items.BONES, new String[]{"###"}));
        ONE_ITEMS.add(new OneItemHolder(ToolSubscriber.bone_shears, com.yanny.ages.api.utils.Tags.Items.BONES, com.yanny.ages.api.utils.Tags.Items.BONES, new String[]{" #", "# "}));
        ONE_ITEMS.add(new OneItemHolder(ToolSubscriber.bone_shovel_head, com.yanny.ages.api.utils.Tags.Items.BONES, com.yanny.ages.api.utils.Tags.Items.BONES, new String[]{"#", "#"}));
        ONE_ITEMS.add(new OneItemHolder(ToolSubscriber.bone_sword_head, com.yanny.ages.api.utils.Tags.Items.BONES, com.yanny.ages.api.utils.Tags.Items.BONES, new String[]{"#", "#", "#"}));
        ONE_ITEMS.add(new OneItemHolder(ItemSubscriber.cobweb_mesh, Items.STRING, Items.STRING, new String[]{"##", "##"}));
        ONE_ITEMS.add(new OneItemHolder(BlockSubscriber.aqueduct, Items.BRICK, Items.BRICK, new String[]{"# #", "# #", "###"}));
        ONE_ITEMS.add(new OneItemHolder(BlockSubscriber.drying_rack, Items.STICK, Items.STICK, new String[]{"# #", " # ", "# #"}));
        ONE_ITEMS.add(new OneItemHolder(BlockSubscriber.tanning_rack, Items.STICK, Items.STICK, new String[]{"# #", " # ", "###"}));
        ONE_ITEMS.add(new OneItemHolder(Items.FLOWER_POT, Items.BRICK, Items.BRICK, new String[]{"# #", " # "}));
        ONE_ITEMS.add(new OneItemHolder(ItemSubscriber.grass_lead, ItemSubscriber.dried_grass, ItemSubscriber.dried_grass, new String[]{"###"}));
        ONE_ITEMS.add(new OneItemHolder(ItemSubscriber.grass_mesh, ItemSubscriber.dried_grass, ItemSubscriber.dried_grass, new String[]{"##", "##"}));
        ONE_ITEMS.add(new OneItemHolder(Items.HAY_BLOCK, Items.WHEAT, Items.WHEAT, new String[]{"###", "###", "###"}));
        ONE_ITEMS.add(new OneItemHolder(Items.LEAD, ItemSubscriber.leather_strip, ItemSubscriber.leather_strip, new String[]{"## ", "# #", " ##"}));
        ONE_ITEMS.add(new OneItemHolder(ToolSubscriber.stone_axe_head, Tags.Items.STONE, Items.STONE, new String[]{"##", "# "}));
        ONE_ITEMS.add(new OneItemHolder(ToolSubscriber.stone_hoe_head, Tags.Items.STONE, Items.STONE, new String[]{"##"}));
        ONE_ITEMS.add(new OneItemHolder(ToolSubscriber.stone_pickaxe_head, Tags.Items.STONE, Items.STONE, new String[]{"###"}));
        ONE_ITEMS.add(new OneItemHolder(ToolSubscriber.stone_shovel_head, Tags.Items.STONE, Items.STONE, new String[]{"#", "#"}));
        ONE_ITEMS.add(new OneItemHolder(ToolSubscriber.stone_sword_head, Tags.Items.STONE, Items.STONE, new String[]{"#", "#", "#"}));
        ONE_ITEMS.add(new OneItemHolder(BlockSubscriber.thatch_block, ItemSubscriber.dried_grass, ItemSubscriber.dried_grass, new String[]{"###", "###", "###"}));
        ONE_ITEMS.add(new OneItemHolder(BlockSubscriber.thatch_stairs, ItemSubscriber.dried_grass, ItemSubscriber.dried_grass, new String[]{"#  ", "## ", "###"}));
        ONE_ITEMS.add(new OneItemHolder(ItemSubscriber.leather_strip, Items.LEATHER, Items.LEATHER, 4, new String[]{"#"}));
    }

    public RecipeGenerator(DataGenerator generatorIn) {
        super(generatorIn);
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    protected void registerRecipes(Consumer<IFinishedRecipe> consumer) {
        ShapedRecipeBuilder.shapedRecipe(ToolSubscriber.flint_knife)
                .patternLine("F")
                .patternLine("#")
                .key('F', Items.FLINT)
                .key('#', Items.STICK)
                .addCriterion("has_recipe", hasItem(Items.STICK))
                .build(consumer);
        ShapedRecipeBuilder.shapedRecipe(BlockSubscriber.flint_workbench)
                .patternLine("FG")
                .patternLine("##")
                .key('F', Items.FLINT)
                .key('G', Tags.Items.GRAVEL)
                .key('#', Items.STICK)
                .addCriterion("has_recipe", hasItem(Items.STICK))
                .build(consumer);
        ShapedRecipeBuilder.shapedRecipe(ItemSubscriber.unlit_torch)
                .patternLine("F")
                .patternLine("#")
                .key('F', FoodSubscriber.fat)
                .key('#', Items.STICK)
                .addCriterion("has_recipe", hasItem(Items.STICK))
                .build(consumer);

        ShapelessRecipeBuilder.shapelessRecipe(ItemSubscriber.raw_bread)
                .addIngredient(ItemSubscriber.flour, 3)
                .addIngredient(Items.SUGAR)
                .addCriterion("has_recipe", hasItem(ItemSubscriber.flour))
                .build(consumer, new ResourceLocation(Reference.MODID, ItemSubscriber.raw_bread.getRegistryName().getPath() + "_shapeless"));

        FlintWorkbenchRecipeBuilder.shapedRecipe(Items.ARROW)
                .patternLine("A")
                .patternLine("#")
                .patternLine("F")
                .key('A', ItemSubscriber.bone_arrow_head)
                .key('#', Items.STICK)
                .key('F', Items.FEATHER)
                .tool(com.yanny.ages.api.utils.Tags.Items.KNIVES)
                .addCriterion("has_recipe", hasItem(Items.STICK))
                .build(consumer);
        FlintWorkbenchRecipeBuilder.shapedRecipe(ItemSubscriber.backpack)
                .patternLine("GBG")
                .patternLine("SLS")
                .patternLine("GMG")
                .key('G', Items.LEATHER)
                .key('L', Items.LEAD)
                .key('B', ItemSubscriber.fish_bone)
                .key('S', ItemSubscriber.saber_teeth)
                .key('M', ItemSubscriber.mammoth_tusk)
                .tool(com.yanny.ages.api.utils.Tags.Items.KNIVES)
                .addCriterion("has_recipe", hasItem(Items.LEATHER))
                .build(consumer);
        FlintWorkbenchRecipeBuilder.shapedRecipe(Items.BOW)
                .patternLine(" #S")
                .patternLine("# S")
                .patternLine(" #S")
                .key('#', Items.STICK)
                .key('S', ItemSubscriber.leather_strip)
                .tool(com.yanny.ages.api.utils.Tags.Items.KNIVES)
                .addCriterion("has_recipe", hasItem(Items.STICK))
                .build(consumer);
        FlintWorkbenchRecipeBuilder.shapedRecipe(Items.CAMPFIRE)
                .patternLine(" S ")
                .patternLine("SCS")
                .patternLine("L#L")
                .key('#', Items.TORCH)
                .key('L', ItemTags.LOGS)
                .key('S', Items.STICK)
                .key('C', ItemTags.COALS)
                .tool(com.yanny.ages.api.utils.Tags.Items.KNIVES)
                .addCriterion("has_recipe", hasItem(ItemTags.COALS))
                .build(consumer);
        FlintWorkbenchRecipeBuilder.shapedRecipe(Items.FURNACE)
                .patternLine("###")
                .patternLine("#C#")
                .patternLine("###")
                .key('#', Items.COBBLESTONE)
                .key('C', Items.CAMPFIRE)
                .tool(com.yanny.ages.api.utils.Tags.Items.KNIVES)
                .addCriterion("has_recipe", hasItem(Items.COBBLESTONE))
                .build(consumer);
        FlintWorkbenchRecipeBuilder.shapedRecipe(Items.CRAFTING_TABLE)
                .patternLine("HWA")
                .patternLine("MBC")
                .patternLine("PPP")
                .key('H', ToolSubscriber.stone_pickaxe)
                .key('A', ToolSubscriber.stone_axe)
                .key('W', ItemTags.WOOL)
                .key('B', Items.BREAD)
                .key('M', BlockSubscriber.millstone)
                .key('C', BlockSubscriber.stone_chest)
                .key('P', ItemTags.WOODEN_SLABS)
                .tool(com.yanny.ages.api.utils.Tags.Items.KNIVES)
                .addCriterion("has_recipe", hasItem(BlockSubscriber.stone_chest))
                .build(consumer);
        FlintWorkbenchRecipeBuilder.shapedRecipe(BlockSubscriber.dried_grass_bed)
                .patternLine("###")
                .patternLine("###")
                .patternLine("CCC")
                .key('#', ItemSubscriber.dried_grass)
                .key('C', ItemTags.WOODEN_SLABS)
                .tool(com.yanny.ages.api.utils.Tags.Items.KNIVES)
                .addCriterion("has_recipe", hasItem(ItemSubscriber.dried_grass))
                .build(consumer);
        FlintWorkbenchRecipeBuilder.shapedRecipe(BlockSubscriber.feeder)
                .patternLine("WSW")
                .patternLine("LSL")
                .patternLine("WSW")
                .key('L', ItemTags.LOGS)
                .key('S', ItemTags.WOODEN_SLABS)
                .key('W', Items.WHEAT)
                .tool(com.yanny.ages.api.utils.Tags.Items.KNIVES)
                .addCriterion("has_recipe", hasItem(ItemTags.LOGS))
                .build(consumer);
        FlintWorkbenchRecipeBuilder.shapedRecipe(BlockSubscriber.fishing_net)
                .patternLine("SLS")
                .patternLine("L#L")
                .patternLine("SLS")
                .key('L', ItemSubscriber.leather_strip)
                .key('S', Items.STICK)
                .key('#', ItemTags.WOODEN_SLABS)
                .tool(com.yanny.ages.api.utils.Tags.Items.KNIVES)
                .addCriterion("has_recipe", hasItem(ItemSubscriber.leather_strip))
                .build(consumer);
        FlintWorkbenchRecipeBuilder.shapedRecipe(ToolSubscriber.flint_spear)
                .patternLine(" SF")
                .patternLine(" # ")
                .patternLine("#  ")
                .key('F', Items.FLINT)
                .key('S', ItemSubscriber.leather_strip)
                .key('#', Items.STICK)
                .tool(com.yanny.ages.api.utils.Tags.Items.KNIVES)
                .addCriterion("has_recipe", hasItem(ItemSubscriber.leather_strip))
                .build(consumer);
        FlintWorkbenchRecipeBuilder.shapedRecipe(Items.LEATHER_BOOTS)
                .patternLine("XSX")
                .patternLine("XBX")
                .key('B', ItemSubscriber.fish_bone)
                .key('S', ItemSubscriber.leather_strip)
                .key('X', Items.LEATHER)
                .tool(com.yanny.ages.api.utils.Tags.Items.KNIVES)
                .addCriterion("has_recipe", hasItem(ItemSubscriber.leather_strip))
                .build(consumer);
        FlintWorkbenchRecipeBuilder.shapedRecipe(Items.LEATHER_CHESTPLATE)
                .patternLine("XBX")
                .patternLine("XSX")
                .patternLine("XXX")
                .key('B', ItemSubscriber.fish_bone)
                .key('S', ItemSubscriber.leather_strip)
                .key('X', Items.LEATHER)
                .tool(com.yanny.ages.api.utils.Tags.Items.KNIVES)
                .addCriterion("has_recipe", hasItem(ItemSubscriber.leather_strip))
                .build(consumer);
        FlintWorkbenchRecipeBuilder.shapedRecipe(Items.LEATHER_HELMET)
                .patternLine("XXX")
                .patternLine("X X")
                .patternLine("S B")
                .key('B', ItemSubscriber.fish_bone)
                .key('S', ItemSubscriber.leather_strip)
                .key('X', Items.LEATHER)
                .tool(com.yanny.ages.api.utils.Tags.Items.KNIVES)
                .addCriterion("has_recipe", hasItem(ItemSubscriber.leather_strip))
                .build(consumer);
        FlintWorkbenchRecipeBuilder.shapedRecipe(Items.LEATHER_LEGGINGS)
                .patternLine("XXX")
                .patternLine("XSX")
                .patternLine("XBX")
                .key('B', ItemSubscriber.fish_bone)
                .key('S', ItemSubscriber.leather_strip)
                .key('X', Items.LEATHER)
                .tool(com.yanny.ages.api.utils.Tags.Items.KNIVES)
                .addCriterion("has_recipe", hasItem(ItemSubscriber.leather_strip))
                .build(consumer);
        FlintWorkbenchRecipeBuilder.shapedRecipe(ItemSubscriber.raw_bread)
                .patternLine("FFF")
                .patternLine("X  ")
                .key('F', ItemSubscriber.flour)
                .key('X', Items.SUGAR)
                .tool(com.yanny.ages.api.utils.Tags.Items.KNIVES)
                .addCriterion("has_recipe", hasItem(ItemSubscriber.flour))
                .build(consumer);
        FlintWorkbenchRecipeBuilder.shapedRecipe(BlockSubscriber.millstone)
                .patternLine("#A#")
                .patternLine("#G#")
                .patternLine("SSS")
                .key('#', Items.STICK)
                .key('A', Items.ANDESITE)
                .key('G', Items.GRANITE)
                .key('S', Items.SMOOTH_STONE_SLAB)
                .tool(com.yanny.ages.api.utils.Tags.Items.KNIVES)
                .addCriterion("has_recipe", hasItem(Items.ANDESITE))
                .build(consumer);
        FlintWorkbenchRecipeBuilder.shapedRecipe(BlockSubscriber.stone_chest)
                .patternLine("###")
                .patternLine("SCS")
                .patternLine("SSS")
                .key('#', Items.SMOOTH_STONE_SLAB)
                .key('C', Items.CLAY_BALL)
                .key('S', Items.STONE)
                .tool(com.yanny.ages.api.utils.Tags.Items.KNIVES)
                .addCriterion("has_recipe", hasItem(Items.STONE))
                .build(consumer);
        FlintWorkbenchRecipeBuilder.shapedRecipe(ToolSubscriber.stone_hammer)
                .patternLine(" XS")
                .patternLine(" #X")
                .patternLine("#  ")
                .key('#', Items.STICK)
                .key('X', Items.STONE)
                .key('S', ItemSubscriber.leather_strip)
                .tool(com.yanny.ages.api.utils.Tags.Items.KNIVES)
                .addCriterion("has_recipe", hasItem(ItemSubscriber.leather_strip))
                .build(consumer);
        FlintWorkbenchRecipeBuilder.shapedRecipe(BlockSubscriber.tree_stump)
                .patternLine(" # ")
                .patternLine("FFF")
                .key('#', ItemTags.LOGS)
                .key('F', Items.STONE)
                .tool(com.yanny.ages.api.utils.Tags.Items.KNIVES)
                .addCriterion("has_recipe", hasItem(Items.STONE))
                .build(consumer);
        FlintWorkbenchRecipeBuilder.shapedRecipe(Items.TORCH)
                .patternLine("F")
                .patternLine("#")
                .key('#', Items.STICK)
                .key('F', FoodSubscriber.fat)
                .tool(com.yanny.ages.api.utils.Tags.Items.KNIVES)
                .addCriterion("has_recipe", hasItem(Items.STICK))
                .build(consumer);

        TreeStumpRecipeBuilder.recipe(BlockSubscriber.thatch_slab, BlockSubscriber.thatch_block, 2)
                .chopTimes(2)
                .tool(com.yanny.ages.api.utils.Tags.Items.AXES)
                .addCriterion("has_recipe", hasItem(BlockSubscriber.thatch_block))
                .build(consumer);
        TreeStumpRecipeBuilder.recipe(Items.BONE_MEAL, ItemSubscriber.mammoth_tusk, 8)
                .chopTimes(3)
                .tool(com.yanny.ages.api.utils.Tags.Items.HAMMERS)
                .addCriterion("has_recipe", hasItem(ItemSubscriber.mammoth_tusk))
                .build(consumer, new ResourceLocation(Reference.MODID, Items.BONE_MEAL.getRegistryName().getPath() + "_from_mammoth_tusk"));
        TreeStumpRecipeBuilder.recipe(Items.BONE_MEAL, ItemSubscriber.rhino_tusk, 4)
                .chopTimes(1)
                .tool(com.yanny.ages.api.utils.Tags.Items.HAMMERS)
                .addCriterion("has_recipe", hasItem(ItemSubscriber.rhino_tusk))
                .build(consumer, new ResourceLocation(Reference.MODID, Items.BONE_MEAL.getRegistryName().getPath() + "_from_rhino_tusk"));
        TreeStumpRecipeBuilder.recipe(Items.BONE_MEAL, ItemSubscriber.saber_teeth, 3)
                .chopTimes(1)
                .tool(com.yanny.ages.api.utils.Tags.Items.HAMMERS)
                .addCriterion("has_recipe", hasItem(ItemSubscriber.saber_teeth))
                .build(consumer, new ResourceLocation(Reference.MODID, Items.BONE_MEAL.getRegistryName().getPath() + "_from_saber_teeth"));
        TreeStumpRecipeBuilder.recipe(Items.BONE_MEAL, Items.BONE, 1)
                .chopTimes(1)
                .tool(com.yanny.ages.api.utils.Tags.Items.HAMMERS)
                .addCriterion("has_recipe", hasItem(Items.BONE))
                .build(consumer, new ResourceLocation(Reference.MODID, Items.BONE_MEAL.getRegistryName().getPath() + "_from_bone"));

        MillstoneRecipeBuilder.recipe(ItemSubscriber.flour, Items.WHEAT, 2)
                .activateCount(2)
                .secondResult(ItemSubscriber.flour, 0.1f)
                .addCriterion("has_recipe", hasItem(Items.WHEAT))
                .build(consumer);
        MillstoneRecipeBuilder.recipe(Items.SUGAR, Items.SUGAR_CANE, 2)
                .activateCount(2)
                .secondResult(Items.SUGAR, 0.1f)
                .addCriterion("has_recipe", hasItem(Items.SUGAR_CANE))
                .build(consumer);

        DryingRackRecipeBuilder.recipe(ItemSubscriber.dried_grass, Items.GRASS)
                .dryingTime(1200)
                .addCriterion("has_recipe", hasItem(Items.GRASS))
                .build(consumer);

        TanningRackRecipeBuilder.recipe(Items.LEATHER, ItemSubscriber.raw_hide)
                .tool(com.yanny.ages.api.utils.Tags.Items.KNIVES)
                .addCriterion("has_recipe", hasItem(ItemSubscriber.raw_hide))
                .build(consumer);

        WOOD_ITEMS.forEach(item -> registerVanillaWoodRecipes(item, consumer));
        SLABS.forEach(item -> registerVanillaSlabRecipes(item, consumer));
        TERRACOTTA.forEach(item -> registerVanillaTerracottaRecipes(item, consumer));
        FOODS.forEach(item -> registerFoodRecipes(item, consumer));
        TOOLS.forEach(item -> registerToolRecipes(item, consumer));
        ONE_ITEMS.forEach(item -> registerOneItemFlintWorkbenchRecipes(item, consumer));
    }

    private static void registerOneItemFlintWorkbenchRecipes(OneItemHolder oneItemHolder, Consumer<IFinishedRecipe> consumer) {
        FlintWorkbenchRecipeBuilder recipe = FlintWorkbenchRecipeBuilder.shapedRecipe(oneItemHolder.result, oneItemHolder.count);

        for (String patternLine : oneItemHolder.patternLines) {
            recipe.patternLine(patternLine);
        }

        recipe.key('#', oneItemHolder.item)
                .tool(com.yanny.ages.api.utils.Tags.Items.KNIVES)
                .addCriterion("has_recipe", hasItem(oneItemHolder.criterion))
                .build(consumer);
    }

    private static void registerToolRecipes(ToolHolder toolHolder, Consumer<IFinishedRecipe> consumer) {
        FlintWorkbenchRecipeBuilder.shapedRecipe(toolHolder.output)
                .patternLine("HL")
                .patternLine("# ")
                .key('H', toolHolder.input)
                .key('L', toolHolder.lead)
                .key('#', Items.STICK)
                .tool(com.yanny.ages.api.utils.Tags.Items.KNIVES)
                .addCriterion("has_recipe", hasItem(Items.STICK))
                .build(consumer);
    }

    private static void registerFoodRecipes(FoodHolder foodHolder, Consumer<IFinishedRecipe> consumer) {
        ResourceLocation location = foodHolder.output.asItem().getRegistryName();

        if (location == null) {
            throw new IllegalStateException("Null resource location! " + foodHolder.output);
        }

        CookingRecipeBuilder.cookingRecipe(Ingredient.fromItems(foodHolder.input), foodHolder.output, 0.35f, 200, IRecipeSerializer.SMELTING)
                .addCriterion("has_recipe", hasItem(foodHolder.input))
                .build(consumer, new ResourceLocation(location.getNamespace(), location.getPath() + "_from_smelting"));
        CookingRecipeBuilder.cookingRecipe(Ingredient.fromItems(foodHolder.input), foodHolder.output, 0.35f, 600, IRecipeSerializer.CAMPFIRE_COOKING)
                .addCriterion("has_recipe", hasItem(foodHolder.input))
                .build(consumer, new ResourceLocation(location.getNamespace(), location.getPath() + "_from_campfire_cooking"));
        CookingRecipeBuilder.cookingRecipe(Ingredient.fromItems(foodHolder.input), foodHolder.output, 0.35f, 100, IRecipeSerializer.SMOKING)
                .addCriterion("has_recipe", hasItem(foodHolder.input))
                .build(consumer, new ResourceLocation(location.getNamespace(), location.getPath() + "_from_smoking"));
    }

    private static void registerVanillaTerracottaRecipes(TerracottaHolder terracottaHolder, Consumer<IFinishedRecipe> consumer) {
        FlintWorkbenchRecipeBuilder.shapedRecipe(terracottaHolder.item, 8)
                .patternLine("###")
                .patternLine("#G#")
                .patternLine("###")
                .key('G', terracottaHolder.color)
                .key('#', Items.TERRACOTTA)
                .tool(com.yanny.ages.api.utils.Tags.Items.KNIVES)
                .addCriterion("has_recipe", hasItem(Items.TERRACOTTA))
                .build(consumer);
    }

    private static void registerVanillaSlabRecipes(SlabHolder slabHolder, Consumer<IFinishedRecipe> consumer) {
        TreeStumpRecipeBuilder.recipe(slabHolder.slab, slabHolder.item, 2)
                .chopTimes(2)
                .tool(com.yanny.ages.api.utils.Tags.Items.HAMMERS)
                .addCriterion("has_recipe", hasItem(slabHolder.item))
                .build(consumer);
    }

    private static void registerVanillaWoodRecipes(WoodItemHolder woodItemHolder, Consumer<IFinishedRecipe> consumer) {
        FlintWorkbenchRecipeBuilder.shapedRecipe(woodItemHolder.boat)
                .patternLine("# #")
                .patternLine("###")
                .key('#', woodItemHolder.planks)
                .tool(com.yanny.ages.api.utils.Tags.Items.KNIVES)
                .addCriterion("has_recipe", hasItem(woodItemHolder.planks))
                .build(consumer);
        FlintWorkbenchRecipeBuilder.shapedRecipe(woodItemHolder.fence, 3)
                .patternLine("#S#")
                .patternLine("#S#")
                .key('#', woodItemHolder.planks)
                .key('S', Items.STICK)
                .tool(com.yanny.ages.api.utils.Tags.Items.KNIVES)
                .addCriterion("has_recipe", hasItem(woodItemHolder.planks))
                .build(consumer);
        FlintWorkbenchRecipeBuilder.shapedRecipe(woodItemHolder.fenceGate)
                .patternLine("S#S")
                .patternLine("S#S")
                .key('#', woodItemHolder.planks)
                .key('S', Items.STICK)
                .tool(com.yanny.ages.api.utils.Tags.Items.KNIVES)
                .addCriterion("has_recipe", hasItem(woodItemHolder.planks))
                .build(consumer);
        TreeStumpRecipeBuilder.recipe(woodItemHolder.planks, woodItemHolder.log, 2)
                .chopTimes(2)
                .tool(com.yanny.ages.api.utils.Tags.Items.AXES)
                .addCriterion("has_recipe", hasItem(woodItemHolder.log))
                .build(consumer);
        TreeStumpRecipeBuilder.recipe(woodItemHolder.slab, woodItemHolder.planks, 2)
                .chopTimes(2)
                .tool(com.yanny.ages.api.utils.Tags.Items.AXES)
                .addCriterion("has_recipe", hasItem(woodItemHolder.planks))
                .build(consumer);
    }

    private static class WoodItemHolder {
        final IItemProvider boat;
        final IItemProvider fence;
        final IItemProvider fenceGate;
        final IItemProvider planks;
        final IItemProvider slab;
        final IItemProvider log;

        WoodItemHolder(IItemProvider boat, IItemProvider fence, IItemProvider fenceGate, IItemProvider planks, IItemProvider slab, IItemProvider log) {
            this.boat = boat;
            this.fence = fence;
            this.fenceGate = fenceGate;
            this.planks = planks;
            this.slab = slab;
            this.log = log;
        }
    }

    private static class SlabHolder {
        final IItemProvider item;
        final IItemProvider slab;

        SlabHolder(IItemProvider item, IItemProvider slab) {
            this.item = item;
            this.slab = slab;
        }
    }

    private static class TerracottaHolder {
        final IItemProvider item;
        final ITag<Item> color;

        TerracottaHolder(IItemProvider item, ITag<Item> color) {
            this.item = item;
            this.color = color;
        }
    }

    private static class FoodHolder {
        final IItemProvider input;
        final IItemProvider output;

        FoodHolder(IItemProvider input, IItemProvider output) {
            this.input = input;
            this.output = output;
        }
    }

    private static class ToolHolder {
        final IItemProvider output;
        final IItemProvider input;
        final IItemProvider lead;

        ToolHolder(IItemProvider output, IItemProvider input, IItemProvider lead) {
            this.output = output;
            this.input = input;
            this.lead = lead;
        }
    }

    private static class OneItemHolder {
        final IItemProvider result;
        final Ingredient item;
        final String[] patternLines;
        final ItemPredicate criterion;
        final int count;

        OneItemHolder(IItemProvider result, ITag<Item> item, ITag<Item> criterion, String[] patternLines) {
            this.result = result;
            this.item = Ingredient.fromTag(item);
            this.criterion = ItemPredicate.Builder.create().tag(criterion).build();
            this.patternLines = patternLines;
            this.count = 1;
        }

        OneItemHolder(IItemProvider result, ITag<Item> item, IItemProvider criterion, String[] patternLines) {
            this.result = result;
            this.item = Ingredient.fromTag(item);
            this.criterion = ItemPredicate.Builder.create().item(criterion).build();
            this.patternLines = patternLines;
            this.count = 1;
        }

        OneItemHolder(IItemProvider result, IItemProvider item, IItemProvider criterion, String[] patternLines) {
            this.result = result;
            this.item = Ingredient.fromItems(item);
            this.criterion = ItemPredicate.Builder.create().item(criterion).build();
            this.patternLines = patternLines;
            this.count = 1;
        }

        OneItemHolder(IItemProvider result, IItemProvider item, IItemProvider criterion, int count, String[] patternLines) {
            this.result = result;
            this.item = Ingredient.fromItems(item);
            this.criterion = ItemPredicate.Builder.create().item(criterion).build();
            this.patternLines = patternLines;
            this.count = count;
        }
    }
}
