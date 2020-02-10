package com.yanny.age.stone.config;

import com.yanny.age.stone.subscribers.ModEventSubscriber;
import net.minecraft.world.biome.Biome;

import java.util.List;
import java.util.Set;

public class Config {
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

    public static Set<Biome> abandonedCampAllowedBiomes = ModEventSubscriber.DEFAULT_BIOMES;
    public static Set<Biome> burialPlaceAllowedBiomes = ModEventSubscriber.DEFAULT_BIOMES;

    public static boolean spawnDeerEnable = true;
    public static int spawnDeerWeight = 20;
    public static int spawnDeerMinCount = 4;
    public static int spawnDeerMaxCount = 10;
    public static boolean spawnBoarEnable = true;
    public static int spawnBoarWeight = 10;
    public static int spawnBoarMinCount = 4;
    public static int spawnBoarMaxCount = 6;
    public static boolean spawnAurochEnable = true;
    public static int spawnAurochWeight = 10;
    public static int spawnAurochMinCount = 4;
    public static int spawnAurochMaxCount = 8;
    public static boolean spawnFowlEnable = true;
    public static int spawnFowlWeight = 10;
    public static int spawnFowlMinCount = 6;
    public static int spawnFowlMaxCount = 8;
    public static boolean spawnMouflonEnable = true;
    public static int spawnMouflonWeight = 10;
    public static int spawnMouflonMinCount = 4;
    public static int spawnMouflonMaxCount = 8;
    public static boolean spawnMammothEnable = true;
    public static int spawnMammothWeight = 10;
    public static int spawnMammothMinCount = 2;
    public static int spawnMammothMaxCount = 4;
    public static boolean spawnSaberToothTigerEnable = true;
    public static int spawnSaberToothTigerWeight = 5;
    public static int spawnSaberToothTigerMinCount = 1;
    public static int spawnSaberToothTigerMaxCount = 2;
    public static boolean spawnWoollyRhinoEnable = true;
    public static int spawnWoollyRhinoWeight = 10;
    public static int spawnWoollyRhinoMinCount = 2;
    public static int spawnWoollyRhinoMaxCount = 6;
}
