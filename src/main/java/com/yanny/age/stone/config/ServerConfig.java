package com.yanny.age.stone.config;

import com.yanny.age.stone.Reference;
import net.minecraftforge.common.ForgeConfigSpec;

import javax.annotation.Nonnull;

class ServerConfig {
    final ForgeConfigSpec.BooleanValue removeVanillaRecipes;
    final ForgeConfigSpec.BooleanValue removeVanillaGeneratedAnimals;
    final ForgeConfigSpec.IntValue domesticateAfterGenerations;
    final ForgeConfigSpec.DoubleValue tanningRackFinishChance;
    final ForgeConfigSpec.BooleanValue changeMiningLevelForVanillaBlocks;
    final ForgeConfigSpec.IntValue aquaductTickChanceBoneMealEffect;

    final ForgeConfigSpec.IntValue clayVesselCapacity;

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
                .defineInRange("domesticateAfterGenerations", 3, 1, Integer.MAX_VALUE);
        tanningRackFinishChance = builder
                .comment("Chance of finishing recipe in tanning rack")
                .translation(Reference.MODID + ".config.tanning_rack_finish_chance")
                .defineInRange("tanningRackFinishChance", 0.1, 0.001, 1.0);
        changeMiningLevelForVanillaBlocks = builder
                .comment("Change mining level for vanilla items (change it to -1, to be mineable by antler pickaxe)")
                .translation(Reference.MODID + ".config.change_mining_level_for_vanilla_blocks")
                .define("changeMiningLevelForVanillaBlocks", true);
        aquaductTickChanceBoneMealEffect = builder
                .comment("Chance of bonemeal efect from aquaduct every X ticks (randomly)")
                .translation(Reference.MODID + ".config.aquaduct_tich_chance_bone_meal_effect")
                .defineInRange("aquaductTickChanceBoneMealEffect", 200, 1, Integer.MAX_VALUE);
        clayVesselCapacity = builder
                .comment("Fluid capacity in mB of clay vessel")
                .translation(Reference.MODID + ".config.clay_vessel_capaicty")
                .defineInRange("clayVesselCapacity", 8000, 1, Integer.MAX_VALUE);
        builder.pop();
    }
}
