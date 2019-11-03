package com.yanny.age.zero.subscribers;

import com.google.common.collect.Sets;
import com.yanny.age.zero.Reference;
import com.yanny.age.zero.config.Config;
import com.yanny.age.zero.entities.BoarEntity;
import com.yanny.age.zero.entities.DeerEntity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;

import java.util.EnumSet;
import java.util.Set;

import static net.minecraft.entity.EntityType.*;
import static net.minecraft.world.biome.Biome.Category.*;

@SuppressWarnings({"unused", "unchecked"})
@ObjectHolder(Reference.MODID)
@Mod.EventBusSubscriber(modid = Reference.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class EntitySubscriber {
    public static final EntityType<DeerEntity> deer = (EntityType<DeerEntity>) EntityType.Builder.create(DeerEntity::new, EntityClassification.CREATURE)
            .size(0.9f, 1.5f).build("").setRegistryName(Reference.MODID,"deer");
    public static final EntityType<BoarEntity> boar = (EntityType<BoarEntity>) EntityType.Builder.create(BoarEntity::new, EntityClassification.CREATURE)
            .size(0.9f, 0.9f).build("").setRegistryName(Reference.MODID,"boar");

    public static final Item deer_spawn_egg = null;
    public static final Item boar_spawn_egg = null;

    private static final EnumSet<Biome.Category> deer_biomes = EnumSet.of(FOREST, PLAINS, TAIGA, EXTREME_HILLS, SAVANNA);
    private static final EnumSet<Biome.Category> boar_biomes = EnumSet.of(FOREST, PLAINS, TAIGA, EXTREME_HILLS, SAVANNA, SWAMP, JUNGLE);

    private static final Set<EntityType> vanillaAnimals = Sets.newHashSet(COW, SHEEP, PIG);

    @SubscribeEvent
    public static void registerEntities(RegistryEvent.Register<EntityType<?>> event) {
        IForgeRegistry<EntityType<?>> registry = event.getRegistry();
        registry.register(deer);
        registry.register(boar);

        for (Biome biome : ForgeRegistries.BIOMES) {
            if (deer_biomes.contains(biome.getCategory())) {
                biome.getSpawns(deer.getClassification()).add(new Biome.SpawnListEntry(deer, 10, 4, 8));
            }
            if (boar_biomes.contains(biome.getCategory())) {
                biome.getSpawns(boar.getClassification()).add(new Biome.SpawnListEntry(boar, 10, 4, 6));
            }

            if (Config.removeVanillaGeneratedAnimals) {
                biome.getSpawns(EntityClassification.CREATURE).removeIf(entry -> vanillaAnimals.contains(entry.entityType));
            }
        }
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        IForgeRegistry<Item> registry = event.getRegistry();
        registry.register(new SpawnEggItem(deer, 0x00ffff, 0xff00ff,
                new Item.Properties().group(ItemGroup.MISC)).setRegistryName(Reference.MODID, "deer_spawn_egg"));
        registry.register(new SpawnEggItem(boar, 0xc0c0ff, 0xff00ff,
                new Item.Properties().group(ItemGroup.MISC)).setRegistryName(Reference.MODID, "boar_spawn_egg"));
    }
}
