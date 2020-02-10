package com.yanny.age.stone.subscribers;

import com.yanny.age.stone.ExampleMod;
import com.yanny.age.stone.compatibility.top.TopCompatibility;
import com.yanny.age.stone.config.Config;
import com.yanny.age.stone.config.ConfigHelper;
import com.yanny.age.stone.config.ConfigHolder;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.ProbabilityConfig;
import net.minecraft.world.gen.placement.IPlacementConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.EnumSet;

import static com.yanny.age.stone.Reference.MODID;
import static com.yanny.age.stone.subscribers.ToolSubscriber.*;
import static net.minecraft.world.biome.Biome.Category.*;

@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEventSubscriber {
    private static final EnumSet<Biome.Category> INVALID_BIOMES = EnumSet.of(OCEAN, RIVER, THEEND, NETHER);
    private static final int OVERLAY_BONE = 0xe8e5d2;

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

    @SuppressWarnings("ConstantConditions")
    @SubscribeEvent
    public static void onClientSetupEvent(FMLClientSetupEvent event) {
        RenderTypeLookup.setRenderLayer(BlockSubscriber.fishing_net, RenderType.cutoutMipped());
        RenderTypeLookup.setRenderLayer(BlockSubscriber.aquaduct, RenderType.cutoutMipped());
    }

    @SuppressWarnings({"ConstantConditions"})
    @SubscribeEvent
    public static void FMLLoadCompleteEvent(FMLLoadCompleteEvent event) {
        for (Biome biome : ForgeRegistries.BIOMES) {
            if (INVALID_BIOMES.contains(biome.getCategory())) {
                continue;
            }

            biome.addStructure(FeatureSubscriber.abandoned_camp_structure.withConfiguration(new ProbabilityConfig((float) Config.abandonedCampSpawnChance)));
            biome.addStructure(FeatureSubscriber.burial_place_structure.withConfiguration(new ProbabilityConfig((float) Config.burialPlaceSpawnChance)));

            biome.addFeature(GenerationStage.Decoration.SURFACE_STRUCTURES,
                    FeatureSubscriber.abandoned_camp_structure.withConfiguration(new ProbabilityConfig((float) Config.abandonedCampSpawnChance)).
                            func_227228_a_(Placement.NOPE.func_227446_a_(IPlacementConfig.NO_PLACEMENT_CONFIG)));
            biome.addFeature(GenerationStage.Decoration.SURFACE_STRUCTURES,
                    FeatureSubscriber.burial_place_structure.withConfiguration(new ProbabilityConfig((float) Config.burialPlaceSpawnChance)).
                            func_227228_a_(Placement.NOPE.func_227446_a_(IPlacementConfig.NO_PLACEMENT_CONFIG)));
        }
    }

    @SubscribeEvent
    public static void onColorRegister(ColorHandlerEvent.Item event) {
        event.getItemColors().register((itemStack, index) -> OVERLAY_BONE, bone_axe_head, bone_pickaxe_head, bone_hoe_head, bone_shovel_head, bone_sword_head);
        event.getItemColors().register((itemStack, index) -> {
            if (index == 0) {
                return OVERLAY_BONE;
            }

            return -1;
        }, bone_axe, bone_pickaxe, bone_hoe, bone_shovel, bone_sword);
    }
}
