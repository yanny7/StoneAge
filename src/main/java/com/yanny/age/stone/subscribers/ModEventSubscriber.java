package com.yanny.age.stone.subscribers;

import com.yanny.age.stone.ExampleMod;
import com.yanny.age.stone.compatibility.top.TopCompatibility;
import com.yanny.age.stone.config.Config;
import com.yanny.age.stone.config.ConfigHelper;
import com.yanny.age.stone.config.ConfigHolder;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.ProbabilityConfig;
import net.minecraft.world.gen.placement.IPlacementConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.registries.ForgeRegistries;

import static com.yanny.age.stone.Reference.MODID;

@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEventSubscriber {

    @SubscribeEvent
    public static void init(FMLCommonSetupEvent event) {
        ExampleMod.proxy.init();
    }

    @SubscribeEvent
    public static void registerTOP(InterModEnqueueEvent event) {
        TopCompatibility.register();
    }

    @SubscribeEvent
    public static void onModConfigEvent(ModConfig.ModConfigEvent event) {
        final ModConfig config = event.getConfig();

        if (!config.getModId().equals(MODID)) {
            return;
        }

        // Rebake the configs when they change
        if (config.getSpec() == ConfigHolder.CLIENT_SPEC) {
            ConfigHelper.bakeClient();
        } else if (config.getSpec() == ConfigHolder.SERVER_SPEC) {
            ConfigHelper.bakeServer();
        }
    }

    @SuppressWarnings({"ConstantConditions"})
    @SubscribeEvent
    public static void FMLLoadCompleteEvent(FMLLoadCompleteEvent event) {
        for (Biome biome : ForgeRegistries.BIOMES) {
            if (Config.abandonedCampAllowedBiomes.contains(biome)) {
                biome.addStructure(FeatureSubscriber.abandoned_camp_structure, new ProbabilityConfig((float) Config.abandonedCampSpawnChance));
                biome.addFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, Biome.createDecoratedFeature(FeatureSubscriber.abandoned_camp_structure,
                        new ProbabilityConfig((float) Config.abandonedCampSpawnChance), Placement.NOPE, IPlacementConfig.NO_PLACEMENT_CONFIG));
            }

            if (Config.burialPlaceAllowedBiomes.contains(biome)) {
                biome.addStructure(FeatureSubscriber.burial_place_structure, new ProbabilityConfig((float) Config.burialPlaceSpawnChance));
                biome.addFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, Biome.createDecoratedFeature(FeatureSubscriber.burial_place_structure,
                        new ProbabilityConfig((float) Config.burialPlaceSpawnChance), Placement.NOPE, IPlacementConfig.NO_PLACEMENT_CONFIG));
            }
        }
    }
}
