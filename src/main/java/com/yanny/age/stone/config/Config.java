package com.yanny.age.stone.config;

import net.minecraft.world.biome.Biome;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.EnumSet;
import java.util.Set;
import java.util.stream.Collectors;

import static net.minecraft.world.biome.Biome.Category.*;

public class Config {
    static final Set<Biome> DEFAULT_DEER_BIOMES;
    static final Set<Biome> DEFAULT_BOAR_BIOMES;
    static final Set<Biome> DEFAULT_AUROCH_BIOMES;
    static final Set<Biome> DEFAULT_FOWL_BIOMES;
    static final Set<Biome> DEFAULT_MOUFLON_BIOMES;
    static final Set<Biome> DEFAULT_MAMMOTH_BIOMES;
    static final Set<Biome> DEFAULT_TIGER_BIOMES;
    static final Set<Biome> DEFAULT_RHINO_BIOMES;

    static final Set<Biome> DEFAULT_STRUCTURE_BIOMES;

    static {
        DEFAULT_DEER_BIOMES = ForgeRegistries.BIOMES.getValues().stream().filter(biome -> EnumSet.of(FOREST, PLAINS, TAIGA, EXTREME_HILLS, SAVANNA, BEACH, SWAMP, JUNGLE, MESA, ICY)
                .contains(biome.getCategory())).collect(Collectors.toSet());
        DEFAULT_BOAR_BIOMES = ForgeRegistries.BIOMES.getValues().stream().filter(biome -> !EnumSet.of(FOREST, PLAINS, TAIGA, EXTREME_HILLS, SAVANNA, SWAMP, JUNGLE)
                .contains(biome.getCategory())).collect(Collectors.toSet());
        DEFAULT_AUROCH_BIOMES = ForgeRegistries.BIOMES.getValues().stream().filter(biome -> !EnumSet.of(FOREST, PLAINS, TAIGA, EXTREME_HILLS, SAVANNA, BEACH)
                .contains(biome.getCategory())).collect(Collectors.toSet());
        DEFAULT_FOWL_BIOMES = ForgeRegistries.BIOMES.getValues().stream().filter(biome -> !EnumSet.of(FOREST, PLAINS, TAIGA, EXTREME_HILLS, SAVANNA, SWAMP, JUNGLE, BEACH, MESA)
                .contains(biome.getCategory())).collect(Collectors.toSet());
        DEFAULT_MOUFLON_BIOMES = ForgeRegistries.BIOMES.getValues().stream().filter(biome -> !EnumSet.of(FOREST, PLAINS, TAIGA, EXTREME_HILLS, SWAMP, MESA)
                .contains(biome.getCategory())).collect(Collectors.toSet());
        DEFAULT_MAMMOTH_BIOMES = ForgeRegistries.BIOMES.getValues().stream().filter(biome -> !EnumSet.of(PLAINS, SAVANNA, ICY, TAIGA, EXTREME_HILLS, DESERT, SAVANNA)
                .contains(biome.getCategory())).collect(Collectors.toSet());
        DEFAULT_TIGER_BIOMES = ForgeRegistries.BIOMES.getValues().stream().filter(biome -> !EnumSet.of(PLAINS, SAVANNA, ICY, TAIGA, DESERT, FOREST, BEACH, JUNGLE, MUSHROOM)
                .contains(biome.getCategory())).collect(Collectors.toSet());
        DEFAULT_RHINO_BIOMES = ForgeRegistries.BIOMES.getValues().stream().filter(biome -> !EnumSet.of(PLAINS, SAVANNA, ICY, TAIGA, BEACH, SAVANNA, MUSHROOM, RIVER, SWAMP)
                .contains(biome.getCategory())).collect(Collectors.toSet());

        DEFAULT_STRUCTURE_BIOMES = ForgeRegistries.BIOMES.getValues().stream().filter(biome -> !EnumSet.of(OCEAN, RIVER, THEEND, NETHER).contains(biome.getCategory())).collect(Collectors.toSet());
    }

    public static boolean removeVanillaRecipes = true;
    public static boolean removeVanillaGeneratedAnimals = true;
    public static int domesticateAfterGenerations = 3;
    public static double tanningRackFinishChance = 0.1;
    public static boolean changeMiningLevelForVanillaBlocks = true;
    public static int aquaductTickChanceBoneMealEffect = 200;
    public static int aquaductEffectRange = 4;
    public static int aquaductFillPerTick = 4;
    public static int aquaductUsePerTick = 1;
    public static int feederTickChanceBreedAnimalEffect = 200;
    public static int feederEffectRange = 4;
    public static boolean forceToolForWood = true;
    public static boolean givePlayerManualOnFirstConnect = true;
    public static int fishingNetChance = 3600;

    public static double abandonedCampSpawnChance = 0.009f;
    public static double burialPlaceSpawnChance = 0.01f;

    public static Set<Biome> abandonedCampAllowedBiomes = DEFAULT_STRUCTURE_BIOMES;
    public static Set<Biome> burialPlaceAllowedBiomes = DEFAULT_STRUCTURE_BIOMES;

    public static boolean spawnDeerEnable = true;
    public static int spawnDeerWeight = 20;
    public static int spawnDeerMinCount = 4;
    public static int spawnDeerMaxCount = 10;
    public static Set<Biome> spawnDeerAllowedBiomes = DEFAULT_DEER_BIOMES;

    public static boolean spawnBoarEnable = true;
    public static int spawnBoarWeight = 10;
    public static int spawnBoarMinCount = 4;
    public static int spawnBoarMaxCount = 6;
    public static Set<Biome> spawnBoarAllowedBiomes = DEFAULT_BOAR_BIOMES;

    public static boolean spawnAurochEnable = true;
    public static int spawnAurochWeight = 10;
    public static int spawnAurochMinCount = 4;
    public static int spawnAurochMaxCount = 8;
    public static Set<Biome> spawnAurochAllowedBiomes = DEFAULT_AUROCH_BIOMES;

    public static boolean spawnFowlEnable = true;
    public static int spawnFowlWeight = 10;
    public static int spawnFowlMinCount = 6;
    public static int spawnFowlMaxCount = 8;
    public static Set<Biome> spawnFowlAllowedBiomes = DEFAULT_FOWL_BIOMES;

    public static boolean spawnMouflonEnable = true;
    public static int spawnMouflonWeight = 10;
    public static int spawnMouflonMinCount = 4;
    public static int spawnMouflonMaxCount = 8;
    public static Set<Biome> spawnMouflonAllowedBiomes = DEFAULT_MOUFLON_BIOMES;

    public static boolean spawnMammothEnable = true;
    public static int spawnMammothWeight = 10;
    public static int spawnMammothMinCount = 2;
    public static int spawnMammothMaxCount = 4;
    public static Set<Biome> spawnMammothAllowedBiomes = DEFAULT_MAMMOTH_BIOMES;

    public static boolean spawnSaberToothTigerEnable = true;
    public static int spawnSaberToothTigerWeight = 5;
    public static int spawnSaberToothTigerMinCount = 1;
    public static int spawnSaberToothTigerMaxCount = 2;
    public static Set<Biome> spawnSaberToothTigerAllowedBiomes = DEFAULT_TIGER_BIOMES;

    public static boolean spawnWoollyRhinoEnable = true;
    public static int spawnWoollyRhinoWeight = 10;
    public static int spawnWoollyRhinoMinCount = 2;
    public static int spawnWoollyRhinoMaxCount = 6;
    public static Set<Biome> spawnWoollyRhinoAllowedBiomes = DEFAULT_RHINO_BIOMES;
}
