package com.yanny.age.stone.config;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.stream.Collectors;

public class ConfigHelper {

    public static void bakeServer() {
        Config.removeVanillaRecipes = ConfigHolder.SERVER.removeVanillaRecipes.get();
        Config.removeVanillaGeneratedAnimals = ConfigHolder.SERVER.removeVanillaGeneratedAnimals.get();
        Config.domesticateAfterGenerations = ConfigHolder.SERVER.domesticateAfterGenerations.get();
        Config.tanningRackFinishChance = ConfigHolder.SERVER.tanningRackFinishChance.get();
        Config.aqueductTickChanceBoneMealEffect = ConfigHolder.SERVER.aqueductTickChanceBoneMealEffect.get();
        Config.aqueductEffectRange = ConfigHolder.SERVER.aqueductEffectRange.get();
        Config.aqueductFillPerTick = ConfigHolder.SERVER.aqueductFillPerTick.get();
        Config.aqueductUsePerTick = ConfigHolder.SERVER.aqueductUsePerTick.get();
        Config.feederTickChanceBreedAnimalEffect = ConfigHolder.SERVER.feederTickChanceBreedAnimalEffect.get();
        Config.feederEffectRange = ConfigHolder.SERVER.feederEffectRange.get();
        Config.forceToolForWood = ConfigHolder.SERVER.forceToolForWood.get();
        Config.givePlayerManualOnFirstConnect = ConfigHolder.SERVER.givePlayerManualOnFirstConnect.get();
        Config.fishingNetChance = ConfigHolder.SERVER.fishingNetChance.get();
        Config.disableVanillaCraftingTable = ConfigHolder.SERVER.disableVanillaCraftingTable.get();

        Config.abandonedCampSpawnChance = ConfigHolder.SERVER.abandonedCampSpawnChance.get();
        Config.burialPlaceSpawnChance = ConfigHolder.SERVER.burialPlaceSpawnChance.get();

        Config.abandonedCampAllowedBiomes.clear();
        Config.abandonedCampAllowedBiomes.addAll(ConfigHolder.SERVER.abandonedCampAllowedBiomes.get().stream()
                .map(value -> ForgeRegistries.BIOMES.getValue(new ResourceLocation(value))).collect(Collectors.toSet()));
        Config.burialPlaceAllowedBiomes.clear();
        Config.burialPlaceAllowedBiomes.addAll(ConfigHolder.SERVER.burialPlaceAllowedBiomes.get().stream()
                .map(value -> ForgeRegistries.BIOMES.getValue(new ResourceLocation(value))).collect(Collectors.toSet()));

        Config.spawnDeerEnable = ConfigHolder.SERVER.spawnDeerEnable.get();
        Config.spawnDeerWeight = ConfigHolder.SERVER.spawnDeerWeight.get();
        Config.spawnDeerMinCount = ConfigHolder.SERVER.spawnDeerMinCount.get();
        Config.spawnDeerMaxCount = ConfigHolder.SERVER.spawnDeerMaxCount.get();
        Config.spawnDeerAllowedBiomesBlacklist = ConfigHolder.SERVER.spawnDeerAllowedBiomesBlacklist.get();
        Config.spawnDeerAllowedBiomes.clear();
        Config.spawnDeerAllowedBiomes.addAll(ConfigHolder.SERVER.spawnDeerAllowedBiomes.get().stream()
                .map(value -> ForgeRegistries.BIOMES.getValue(new ResourceLocation(value))).collect(Collectors.toSet()));

        Config.spawnBoarEnable = ConfigHolder.SERVER.spawnBoarEnable.get();
        Config.spawnBoarWeight = ConfigHolder.SERVER.spawnBoarWeight.get();
        Config.spawnBoarMinCount = ConfigHolder.SERVER.spawnBoarMinCount.get();
        Config.spawnBoarMaxCount = ConfigHolder.SERVER.spawnBoarMaxCount.get();
        Config.spawnBoarAllowedBiomesBlacklist = ConfigHolder.SERVER.spawnBoarAllowedBiomesBlacklist.get();
        Config.boarBreedingResult = ConfigHolder.SERVER.boarBreedingResult.get();
        Config.spawnBoarAllowedBiomes.clear();
        Config.spawnBoarAllowedBiomes.addAll(ConfigHolder.SERVER.spawnBoarAllowedBiomes.get().stream()
                .map(value -> ForgeRegistries.BIOMES.getValue(new ResourceLocation(value))).collect(Collectors.toSet()));

        Config.spawnAurochEnable = ConfigHolder.SERVER.spawnAurochEnable.get();
        Config.spawnAurochWeight = ConfigHolder.SERVER.spawnAurochWeight.get();
        Config.spawnAurochMinCount = ConfigHolder.SERVER.spawnAurochMinCount.get();
        Config.spawnAurochMaxCount = ConfigHolder.SERVER.spawnAurochMaxCount.get();
        Config.spawnAurochAllowedBiomesBlacklist = ConfigHolder.SERVER.spawnAurochAllowedBiomesBlacklist.get();
        Config.aurochBreedingResult = ConfigHolder.SERVER.aurochBreedingResult.get();
        Config.spawnAurochAllowedBiomes.clear();
        Config.spawnAurochAllowedBiomes.addAll(ConfigHolder.SERVER.spawnAurochAllowedBiomes.get().stream()
                .map(value -> ForgeRegistries.BIOMES.getValue(new ResourceLocation(value))).collect(Collectors.toSet()));

        Config.spawnFowlEnable = ConfigHolder.SERVER.spawnFowlEnable.get();
        Config.spawnFowlWeight = ConfigHolder.SERVER.spawnFowlWeight.get();
        Config.spawnFowlMinCount = ConfigHolder.SERVER.spawnFowlMinCount.get();
        Config.spawnFowlMaxCount = ConfigHolder.SERVER.spawnFowlMaxCount.get();
        Config.spawnFowlAllowedBiomesBlacklist = ConfigHolder.SERVER.spawnFowlAllowedBiomesBlacklist.get();
        Config.fowlBreedingResult = ConfigHolder.SERVER.fowlBreedingResult.get();
        Config.spawnFowlAllowedBiomes.clear();
        Config.spawnFowlAllowedBiomes.addAll(ConfigHolder.SERVER.spawnFowlAllowedBiomes.get().stream()
                .map(value -> ForgeRegistries.BIOMES.getValue(new ResourceLocation(value))).collect(Collectors.toSet()));

        Config.spawnMouflonEnable = ConfigHolder.SERVER.spawnMouflonEnable.get();
        Config.spawnMouflonWeight = ConfigHolder.SERVER.spawnMouflonWeight.get();
        Config.spawnMouflonMinCount = ConfigHolder.SERVER.spawnMouflonMinCount.get();
        Config.spawnMouflonMaxCount = ConfigHolder.SERVER.spawnMouflonMaxCount.get();
        Config.spawnMouflonAllowedBiomesBlacklist = ConfigHolder.SERVER.spawnMouflonAllowedBiomesBlacklist.get();
        Config.mouflonBreedingResult = ConfigHolder.SERVER.mouflonBreedingResult.get();
        Config.spawnMouflonAllowedBiomes.clear();
        Config.spawnMouflonAllowedBiomes.addAll(ConfigHolder.SERVER.spawnMouflonAllowedBiomes.get().stream()
                .map(value -> ForgeRegistries.BIOMES.getValue(new ResourceLocation(value))).collect(Collectors.toSet()));

        Config.spawnMammothEnable = ConfigHolder.SERVER.spawnMammothEnable.get();
        Config.spawnMammothWeight = ConfigHolder.SERVER.spawnMammothWeight.get();
        Config.spawnMammothMinCount = ConfigHolder.SERVER.spawnMammothMinCount.get();
        Config.spawnMammothMaxCount = ConfigHolder.SERVER.spawnMammothMaxCount.get();
        Config.spawnMammothAllowedBiomesBlacklist = ConfigHolder.SERVER.spawnMammothAllowedBiomesBlacklist.get();
        Config.spawnMammothAllowedBiomes.clear();
        Config.spawnMammothAllowedBiomes.addAll(ConfigHolder.SERVER.spawnMammothAllowedBiomes.get().stream()
                .map(value -> ForgeRegistries.BIOMES.getValue(new ResourceLocation(value))).collect(Collectors.toSet()));

        Config.spawnSaberToothTigerEnable = ConfigHolder.SERVER.spawnSaberToothTigerEnable.get();
        Config.spawnSaberToothTigerWeight = ConfigHolder.SERVER.spawnSaberToothTigerWeight.get();
        Config.spawnSaberToothTigerMinCount = ConfigHolder.SERVER.spawnSaberToothTigerMinCount.get();
        Config.spawnSaberToothTigerMaxCount = ConfigHolder.SERVER.spawnSaberToothTigerMaxCount.get();
        Config.spawnSaberToothTigerAllowedBiomesBlacklist = ConfigHolder.SERVER.spawnSaberToothTigerAllowedBiomesBlacklist.get();
        Config.spawnSaberToothTigerAllowedBiomes.clear();
        Config.spawnSaberToothTigerAllowedBiomes.addAll(ConfigHolder.SERVER.spawnSaberToothTigerAllowedBiomes.get().stream()
                .map(value -> ForgeRegistries.BIOMES.getValue(new ResourceLocation(value))).collect(Collectors.toSet()));

        Config.spawnWoollyRhinoEnable = ConfigHolder.SERVER.spawnWoollyRhinoEnable.get();
        Config.spawnWoollyRhinoWeight = ConfigHolder.SERVER.spawnWoollyRhinoWeight.get();
        Config.spawnWoollyRhinoMinCount = ConfigHolder.SERVER.spawnWoollyRhinoMinCount.get();
        Config.spawnWoollyRhinoMaxCount = ConfigHolder.SERVER.spawnWoollyRhinoMaxCount.get();
        Config.spawnWoollyRhinoAllowedBiomesBlacklist = ConfigHolder.SERVER.spawnWoollyRhinoAllowedBiomesBlacklist.get();
        Config.spawnWoollyRhinoAllowedBiomes.clear();
        Config.spawnWoollyRhinoAllowedBiomes.addAll(ConfigHolder.SERVER.spawnWoollyRhinoAllowedBiomes.get().stream()
                .map(value -> ForgeRegistries.BIOMES.getValue(new ResourceLocation(value))).collect(Collectors.toSet()));
    }

    public static void bakeClient() {
    }
}
