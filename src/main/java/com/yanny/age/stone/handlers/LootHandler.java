package com.yanny.age.stone.handlers;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mojang.datafixers.util.Pair;
import com.yanny.ages.api.utils.Tags;
import net.minecraft.advancements.criterion.ItemPredicate;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.tags.Tag;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.*;
import net.minecraft.world.storage.loot.conditions.ILootCondition;
import net.minecraft.world.storage.loot.conditions.MatchTool;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

import static com.yanny.age.stone.Reference.MODID;

@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class LootHandler {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final Map<String, Pair<String, Item>> REMOVE_LOOT = Maps.newHashMap();
    private static final Map<String, String> INJECT_POOL = Maps.newHashMap();
    private static final Map<String, Pair<Block, Tag<Item>>> INJECT_ITEM_POOL = Maps.newHashMap();
    private static final Map<String, Pair<Block, Tag<Item>>> INJECT_ALTERNATE_ENTRIES = Maps.newHashMap();
    private static final Map<String, Pair<Block, Tag<Item>>> CHANGE_ALTERNATE_ENTRIES = Maps.newHashMap();

    private static Field lootEntries;
    private static Field entryItem;
    private static Field alternativeEntries;
    private static Field alternativeConditions;
    private static Constructor<AlternativesLootEntry> constructAlternative;

    static {
        lootEntries = ObfuscationReflectionHelper.findField(LootPool.class, "field_186453_a");
        lootEntries.setAccessible(true);
        alternativeEntries = ObfuscationReflectionHelper.findField(ParentedLootEntry.class, "field_216147_c");
        alternativeEntries.setAccessible(true);
        alternativeConditions = ObfuscationReflectionHelper.findField(LootEntry.class, "field_216144_d");
        alternativeConditions.setAccessible(true);
        constructAlternative = ObfuscationReflectionHelper.findConstructor(AlternativesLootEntry.class, LootEntry[].class, ILootCondition[].class);
        constructAlternative.setAccessible(true);
        entryItem = ObfuscationReflectionHelper.findField(ItemLootEntry.class, "field_186368_a");
        entryItem.setAccessible(true);

        REMOVE_LOOT.put("minecraft:entities/horse", new Pair<>("main", Items.LEATHER));
        REMOVE_LOOT.put("minecraft:entities/cow", new Pair<>("main", Items.LEATHER));

        INJECT_POOL.put("minecraft:blocks/dirt", "stone_age:inject/blocks/bones_and_sticks");
        INJECT_POOL.put("minecraft:blocks/coarse_dirt", "stone_age:inject/blocks/bones_and_sticks");
        INJECT_POOL.put("minecraft:blocks/gravel", "stone_age:inject/blocks/bones_and_sticks");
        INJECT_POOL.put("minecraft:blocks/sand", "stone_age:inject/blocks/bones_and_sticks");
        INJECT_POOL.put("minecraft:blocks/red_sand", "stone_age:inject/blocks/bones_and_sticks");
        INJECT_POOL.put("minecraft:blocks/grass_block", "stone_age:inject/blocks/saplings");
        INJECT_POOL.put("minecraft:entities/cod", "stone_age:inject/entities/fish_bone");
        INJECT_POOL.put("minecraft:entities/salmon", "stone_age:inject/entities/fish_bone");
        INJECT_POOL.put("minecraft:entities/tropical_fish", "stone_age:inject/entities/fish_bone");
        INJECT_POOL.put("minecraft:entities/dolphin", "stone_age:inject/entities/fish_bone");
        INJECT_POOL.put("minecraft:entities/cow", "stone_age:inject/entities/bone_fat_hide");
        INJECT_POOL.put("minecraft:entities/horse", "stone_age:inject/entities/bone_fat_hide");
        INJECT_POOL.put("minecraft:entities/pig", "stone_age:inject/entities/bone_fat");
        INJECT_POOL.put("minecraft:entities/sheep", "stone_age:inject/entities/bone_fat");

        INJECT_ITEM_POOL.put("minecraft:blocks/seagrass", new Pair<>(Blocks.SEAGRASS, Tags.Items.SHEARS));
        INJECT_ITEM_POOL.put("minecraft:blocks/tall_seagrass", new Pair<>(Blocks.SEAGRASS, Tags.Items.SHEARS));
        INJECT_ITEM_POOL.put("minecraft:blocks/vine", new Pair<>(Blocks.VINE, Tags.Items.SHEARS));
        INJECT_ITEM_POOL.put("minecraft:blocks/large_fern", new Pair<>(Blocks.FERN, Tags.Items.SHEARS));

        INJECT_ALTERNATE_ENTRIES.put("minecraft:blocks/stone", new Pair<>(Blocks.GRAVEL, Tags.Items.HAMMERS));
        INJECT_ALTERNATE_ENTRIES.put("minecraft:blocks/acacia_leaves", new Pair<>(Blocks.ACACIA_LEAVES, Tags.Items.SHEARS));
        INJECT_ALTERNATE_ENTRIES.put("minecraft:blocks/birch_leaves", new Pair<>(Blocks.BIRCH_LEAVES, Tags.Items.SHEARS));
        INJECT_ALTERNATE_ENTRIES.put("minecraft:blocks/cobweb", new Pair<>(Blocks.COBWEB, Tags.Items.SHEARS));
        INJECT_ALTERNATE_ENTRIES.put("minecraft:blocks/dark_oak_leaves", new Pair<>(Blocks.DARK_OAK_LEAVES, Tags.Items.SHEARS));
        INJECT_ALTERNATE_ENTRIES.put("minecraft:blocks/dead_bush", new Pair<>(Blocks.DEAD_BUSH, Tags.Items.SHEARS));
        INJECT_ALTERNATE_ENTRIES.put("minecraft:blocks/fern", new Pair<>(Blocks.FERN, Tags.Items.SHEARS));
        INJECT_ALTERNATE_ENTRIES.put("minecraft:blocks/grass", new Pair<>(Blocks.GRASS, Tags.Items.SHEARS));
        INJECT_ALTERNATE_ENTRIES.put("minecraft:blocks/jungle_leaves", new Pair<>(Blocks.JUNGLE_LEAVES, Tags.Items.SHEARS));
        INJECT_ALTERNATE_ENTRIES.put("minecraft:blocks/oak_leaves", new Pair<>(Blocks.OAK_LEAVES, Tags.Items.SHEARS));
        INJECT_ALTERNATE_ENTRIES.put("minecraft:blocks/spruce_leaves", new Pair<>(Blocks.SPRUCE_LEAVES, Tags.Items.SHEARS));
        INJECT_ALTERNATE_ENTRIES.put("minecraft:blocks/tall_grass", new Pair<>(Blocks.GRASS, Tags.Items.SHEARS));

        CHANGE_ALTERNATE_ENTRIES.put("minecraft:blocks/sandstone", new Pair<>(Blocks.SAND, Tags.Items.HAMMERS));
        CHANGE_ALTERNATE_ENTRIES.put("minecraft:blocks/chiseled_sandstone", new Pair<>(Blocks.SAND, Tags.Items.HAMMERS));
        CHANGE_ALTERNATE_ENTRIES.put("minecraft:blocks/cut_sandstone", new Pair<>(Blocks.SAND, Tags.Items.HAMMERS));
        CHANGE_ALTERNATE_ENTRIES.put("minecraft:blocks/smooth_sandstone", new Pair<>(Blocks.SAND, Tags.Items.HAMMERS));
        CHANGE_ALTERNATE_ENTRIES.put("minecraft:blocks/red_sandstone", new Pair<>(Blocks.RED_SAND, Tags.Items.HAMMERS));
        CHANGE_ALTERNATE_ENTRIES.put("minecraft:blocks/chiseled_red_sandstone", new Pair<>(Blocks.RED_SAND, Tags.Items.HAMMERS));
        CHANGE_ALTERNATE_ENTRIES.put("minecraft:blocks/cut_red_sandstone", new Pair<>(Blocks.RED_SAND, Tags.Items.HAMMERS));
        CHANGE_ALTERNATE_ENTRIES.put("minecraft:blocks/smooth_red_sandstone", new Pair<>(Blocks.RED_SAND, Tags.Items.HAMMERS));
    }

    @SubscribeEvent
    public static void lootLoad(LootTableLoadEvent event) {
        String name = event.getName().toString();

        if (REMOVE_LOOT.containsKey(name)) {
            removeLootFromTable(event.getTable(), REMOVE_LOOT.get(name));
        }
        if (INJECT_POOL.containsKey(name)) {
            event.getTable().addPool(getInjectPool(INJECT_POOL.get(name)));
        }
        if (INJECT_ITEM_POOL.containsKey(name)) {
            injectItemPool(event.getTable(), INJECT_ITEM_POOL.get(name));
        }
        if (INJECT_ALTERNATE_ENTRIES.containsKey(name)) {
            injectMainAlternativeEntries(event.getTable(), INJECT_ALTERNATE_ENTRIES.get(name));
        }
        if (CHANGE_ALTERNATE_ENTRIES.containsKey(name)) {
            changeToMainAlternativeEntries(event.getTable(), CHANGE_ALTERNATE_ENTRIES.get(name));
        }
    }

    private static LootPool getInjectPool(String entryName) {
        return LootPool.builder().addEntry(getInjectEntry(entryName)).bonusRolls(0, 1).name("stone_age:inject").build();
    }

    private static LootEntry.Builder<?> getInjectEntry(String name) {
        ResourceLocation table = new ResourceLocation(name);
        return TableLootEntry.builder(table).weight(1);
    }

    private static void injectItemPool(LootTable table, Pair<Block, Tag<Item>> data) {
        ItemPredicate.Builder predicate = ItemPredicate.Builder.create().tag(data.getSecond());
        LootEntry.Builder<?> itemLootEntry = ItemLootEntry.builder(data.getFirst()).acceptCondition(MatchTool.builder(predicate)).weight(1);
        LootPool.Builder pool = LootPool.builder().addEntry(itemLootEntry).bonusRolls(0, 1).name("stone_age:inject");
        table.addPool(pool.build());
    }

    @SuppressWarnings("unchecked")
    private static void injectMainAlternativeEntries(LootTable table, Pair<Block, Tag<Item>> data) {
        try {
            LootPool pool = table.getPool("main");
            //noinspection ConstantConditions
            if (pool != null) {
                List<LootEntry> entries = (List<LootEntry>) lootEntries.get(pool);

                if (entries != null && entries.size() > 0 && entries.get(0) instanceof AlternativesLootEntry) {
                    ItemPredicate.Builder predicate = ItemPredicate.Builder.create().tag(data.getSecond());
                    LootEntry itemLootEntry = ItemLootEntry.builder(data.getFirst()).acceptCondition(MatchTool.builder(predicate)).build();
                    LootEntry[] aEntries = (LootEntry[]) alternativeEntries.get(entries.get(0));
                    ILootCondition[] aConditions = (ILootCondition[]) alternativeConditions.get(entries.get(0));

                    if (aEntries != null && aConditions != null) {
                        List<LootEntry> list = Lists.newArrayList(itemLootEntry);
                        list.addAll(Arrays.asList(aEntries));
                        AlternativesLootEntry newAlternative = constructAlternative.newInstance(list.toArray(new LootEntry[0]), aConditions);
                        entries.set(0, newAlternative);
                    } else {
                        LOGGER.warn("Fields not available! {}", data.toString());
                    }
                } else {
                    LOGGER.warn("Alternatives not available! {}", data.toString());
                }
            } else {
                LOGGER.warn("Main pool not available! {}", data.toString());
            }
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    private static void changeToMainAlternativeEntries(LootTable table, Pair<Block, Tag<Item>> data) {
        try {
            LootPool pool = table.getPool("main");
            //noinspection ConstantConditions
            if (pool != null) {
                List<LootEntry> entries = (List<LootEntry>) lootEntries.get(pool);

                if (entries != null && entries.size() > 0) {
                    ItemPredicate.Builder predicate = ItemPredicate.Builder.create().tag(data.getSecond());
                    LootEntry.Builder<?> itemLootEntry = ItemLootEntry.builder(data.getFirst()).acceptCondition(MatchTool.builder(predicate));
                    List<LootEntry> list = Lists.newArrayList(itemLootEntry.build());
                    list.addAll(entries);
                    AlternativesLootEntry a = constructAlternative.newInstance(list.toArray(new LootEntry[0]), new ILootCondition[0]);
                    entries.set(0, a);
                } else {
                    LOGGER.warn("Alternatives not available! {}", data.toString());
                }
            } else {
                LOGGER.warn("Main pool not available! {}", data.toString());
            }
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }


    @SuppressWarnings("unchecked")
    public static void removeLootFromTable(LootTable table, Pair<String, Item> data) {
        try {
            LootPool pool = table.getPool(data.getFirst());
            List<LootEntry> entries = (List<LootEntry>) lootEntries.get(pool);
            Iterator<LootEntry> it = entries.iterator();

            while(it.hasNext()) {
                LootEntry entry = it.next();

                if(entry instanceof ItemLootEntry) {
                    ItemLootEntry lootEntry = (ItemLootEntry)entry;
                    Item i = (Item) entryItem.get(lootEntry);

                    if(i == data.getSecond()) {
                        it.remove();
                        return;
                    }
                }
            }
        } catch (IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
