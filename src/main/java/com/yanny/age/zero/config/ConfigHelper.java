package com.yanny.age.zero.config;

import net.minecraftforge.fml.config.ModConfig;

import javax.annotation.Nonnull;

public class ConfigHelper {

    public static void bakeServer() {
        Config.removeVanillaRecipes = ConfigHolder.SERVER.removeVanillaRecipes.get();
    }

    public static void bakeClient() {
    }

    public static void setValueAndSave(@Nonnull final ModConfig modConfig, @Nonnull final String path, @Nonnull final Object newValue) {
        modConfig.getConfigData().set(path, newValue);
        modConfig.save();
    }
}
