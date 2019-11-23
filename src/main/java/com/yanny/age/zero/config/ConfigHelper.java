package com.yanny.age.zero.config;

import net.minecraftforge.fml.config.ModConfig;

import javax.annotation.Nonnull;

public class ConfigHelper {

    public static void bakeServer() {
        Config.removeVanillaRecipes = ConfigHolder.SERVER.removeVanillaRecipes.get();
        Config.removeVanillaGeneratedAnimals = ConfigHolder.SERVER.removeVanillaGeneratedAnimals.get();
        Config.domesticateAfterGenerations = ConfigHolder.SERVER.domesticateAfterGenerations.get();
        Config.tanningRackFinishChance = ConfigHolder.SERVER.tanningRackFinishChance.get();
        Config.changeMiningLevelForVanillaBlocks = ConfigHolder.SERVER.changeMiningLevelForVanillaBlocks.get();
        Config.aquaductTickChanceBoneMealEffect = ConfigHolder.SERVER.aquaductTickChanceBoneMealEffect.get();
        Config.clayVesselCapacity = ConfigHolder.SERVER.clayVesselCapacity.get();
    }

    public static void bakeClient() {
    }

    public static void setValueAndSave(@Nonnull final ModConfig modConfig, @Nonnull final String path, @Nonnull final Object newValue) {
        modConfig.getConfigData().set(path, newValue);
        modConfig.save();
    }
}
