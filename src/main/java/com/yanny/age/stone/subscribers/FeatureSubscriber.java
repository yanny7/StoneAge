package com.yanny.age.stone.subscribers;

import com.yanny.age.stone.structures.*;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.ProbabilityConfig;
import net.minecraft.world.gen.feature.structure.IStructurePieceType;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;

import javax.annotation.Nonnull;

import static com.yanny.age.stone.Reference.MODID;

@SuppressWarnings("WeakerAccess")
@ObjectHolder(MODID)
@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class FeatureSubscriber {
    public static final Structure<ProbabilityConfig> abandoned_camp_structure = null;
    public static final Structure<ProbabilityConfig> burial_place_structure = null;

    @SubscribeEvent
    public static void registerBlocks(@Nonnull RegistryEvent.Register<Feature<?>> event) {
        IForgeRegistry<Feature<?>> registry = event.getRegistry();
        registry.register(new AbandonedCampStructure(ProbabilityConfig::deserialize).setRegistryName(MODID, "abandoned_camp_structure"));
        registry.register(new BurialPlaceStructure(ProbabilityConfig::deserialize).setRegistryName(MODID, "burial_place_structure"));

        AbandonedCampPiece.CAMP = IStructurePieceType.register(AbandonedCampPiece::new, "AbandonedCamp");
        BurialPlacePiece.BURIAL_PLACE = IStructurePieceType.register(BurialPlacePiece::new, "BurialPlace");
    }
}
