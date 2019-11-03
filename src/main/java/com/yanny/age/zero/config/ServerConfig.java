package com.yanny.age.zero.config;

import com.yanny.age.zero.Reference;
import net.minecraftforge.common.ForgeConfigSpec;

import javax.annotation.Nonnull;

class ServerConfig {
    final ForgeConfigSpec.BooleanValue removeVanillaRecipes;
    final ForgeConfigSpec.BooleanValue removeVanillaGeneratedAnimals;

    ServerConfig(@Nonnull final ForgeConfigSpec.Builder builder) {
        builder.push("general");
        removeVanillaRecipes = builder
                .comment("Remove vanilla recipes that are changed by mod or break mod gameplay")
                .translation(Reference.MODID + ".config.remove_vanilla_recipes")
                .define("removeVanillaRecipes", true);
        removeVanillaGeneratedAnimals = builder
                .comment("Remove spawning of vanilla animals like cows, pigs, sheeps")
                .translation(Reference.MODID + ".config.remove_vanilla_generated_animals")
                .define("removeVanillaGeneratedAnimals", true);
        builder.pop();
    }
}
