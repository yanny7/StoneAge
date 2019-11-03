package com.yanny.age.zero.subscribers;

import com.google.common.collect.Sets;
import com.yanny.age.zero.Reference;
import com.yanny.age.zero.config.Config;
import com.yanny.age.zero.entities.*;
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
    public static final EntityType<AurochEntity> auroch = (EntityType<AurochEntity>) EntityType.Builder.create(AurochEntity::new, EntityClassification.CREATURE)
            .size(1.1f, 1.5f).build("").setRegistryName(Reference.MODID,"auroch");
    public static final EntityType<FowlEntity> fowl = (EntityType<FowlEntity>) EntityType.Builder.create(FowlEntity::new, EntityClassification.CREATURE)
            .size(0.7f, 0.7f).build("").setRegistryName(Reference.MODID,"fowl");
    public static final EntityType<MouflonEntity> mouflon = (EntityType<MouflonEntity>) EntityType.Builder.create(MouflonEntity::new, EntityClassification.CREATURE)
            .size(0.9f, 1.2f).build("").setRegistryName(Reference.MODID,"mouflon");

    public static final Item deer_spawn_egg = null;
    public static final Item boar_spawn_egg = null;
    public static final Item auroch_spawn_egg = null;
    public static final Item fowl_spawn_egg = null;
    public static final Item mouflon_spawn_egg = null;

    private static final EnumSet<Biome.Category> deer_biomes = EnumSet.of(FOREST, PLAINS, TAIGA, EXTREME_HILLS, SAVANNA);
    private static final EnumSet<Biome.Category> boar_biomes = EnumSet.of(FOREST, PLAINS, TAIGA, EXTREME_HILLS, SAVANNA, SWAMP, JUNGLE);
    private static final EnumSet<Biome.Category> auroch_biomes = EnumSet.of(FOREST, PLAINS, TAIGA, EXTREME_HILLS, SAVANNA, BEACH);
    private static final EnumSet<Biome.Category> fowl_biomes = EnumSet.of(FOREST, PLAINS, TAIGA, EXTREME_HILLS, SAVANNA, SWAMP, JUNGLE, BEACH, MESA);
    private static final EnumSet<Biome.Category> mouflon_biomes = EnumSet.of(FOREST, PLAINS, TAIGA, EXTREME_HILLS, SWAMP, MESA);

    private static final Set<EntityType> vanillaAnimals = Sets.newHashSet(COW, SHEEP, PIG, CHICKEN);

    @SubscribeEvent
    public static void registerEntities(RegistryEvent.Register<EntityType<?>> event) {
        IForgeRegistry<EntityType<?>> registry = event.getRegistry();
        registry.register(deer);
        registry.register(boar);
        registry.register(auroch);
        registry.register(fowl);
        registry.register(mouflon);

        for (Biome biome : ForgeRegistries.BIOMES) {
            if (deer_biomes.contains(biome.getCategory())) {
                biome.getSpawns(deer.getClassification()).add(new Biome.SpawnListEntry(deer, 10, 4, 8));
            }
            if (boar_biomes.contains(biome.getCategory())) {
                biome.getSpawns(boar.getClassification()).add(new Biome.SpawnListEntry(boar, 10, 4, 6));
            }
            if (auroch_biomes.contains(biome.getCategory())) {
                biome.getSpawns(auroch.getClassification()).add(new Biome.SpawnListEntry(auroch, 10, 2, 4));
            }
            if (fowl_biomes.contains(biome.getCategory())) {
                biome.getSpawns(fowl.getClassification()).add(new Biome.SpawnListEntry(fowl, 10, 2, 6));
            }
            if (mouflon_biomes.contains(biome.getCategory())) {
                biome.getSpawns(mouflon.getClassification()).add(new Biome.SpawnListEntry(mouflon, 10, 2, 4));
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
        registry.register(new SpawnEggItem(auroch, 0xffc0c0, 0xffc0ff,
                new Item.Properties().group(ItemGroup.MISC)).setRegistryName(Reference.MODID, "auroch_spawn_egg"));
        registry.register(new SpawnEggItem(fowl, 0xd666c0, 0x33c066,
                new Item.Properties().group(ItemGroup.MISC)).setRegistryName(Reference.MODID, "fowl_spawn_egg"));
        registry.register(new SpawnEggItem(mouflon, 0xec6699, 0xccc096,
                new Item.Properties().group(ItemGroup.MISC)).setRegistryName(Reference.MODID, "mouflon_spawn_egg"));
    }
}
