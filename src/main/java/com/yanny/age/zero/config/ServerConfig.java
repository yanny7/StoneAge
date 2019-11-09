package com.yanny.age.zero.config;

import com.yanny.age.zero.Reference;
import net.minecraftforge.common.ForgeConfigSpec;

import javax.annotation.Nonnull;

class ServerConfig {
    final ForgeConfigSpec.BooleanValue removeVanillaRecipes;
    final ForgeConfigSpec.BooleanValue removeVanillaGeneratedAnimals;
    final ForgeConfigSpec.IntValue domesticateAfterGenerations;
    final ForgeConfigSpec.DoubleValue tanningRackFinishChance;

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
        domesticateAfterGenerations = builder
                .comment("Domesticate wild animal after given generations")
                .translation(Reference.MODID + ".config.domesticate_after_generations")
                .defineInRange("domesticateAfterGenerations", 3, 1, 10);
        tanningRackFinishChance = builder
                .comment("Chance of finishing recipe in tanning rack")
                .translation(Reference.MODID + ".config.tanning_rack_finish_chance")
                .defineInRange("tanningRackFinishChance", 0.1, 0.001, 1.0);
        builder.pop();
    }
}
