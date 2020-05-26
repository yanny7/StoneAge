package com.yanny.age.stone.structures;

import com.mojang.datafixers.Dynamic;
import com.yanny.age.stone.subscribers.FeatureSubscriber;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeManager;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.ProbabilityConfig;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.structure.StructureStart;
import net.minecraft.world.gen.feature.template.TemplateManager;

import javax.annotation.Nonnull;
import java.util.Random;
import java.util.function.Function;

public class AbandonedCampStructure extends Structure<ProbabilityConfig> {
    public AbandonedCampStructure(Function<Dynamic<?>, ? extends ProbabilityConfig> configFactoryIn) {
        super(configFactoryIn);
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public boolean canBeGenerated(@Nonnull BiomeManager biomeManager, @Nonnull ChunkGenerator<?> chunkGen, @Nonnull Random rand, int chunkPosX, int chunkPosZ, @Nonnull Biome biome) {
        ((SharedSeedRandom)rand).setLargeFeatureSeedWithSalt(chunkGen.getSeed(), chunkPosX, chunkPosZ, 605684042);

        if (chunkGen.hasStructure(biome, FeatureSubscriber.abandoned_camp_structure)) { // check if structure is valid in given biome
            ProbabilityConfig config = chunkGen.getStructureConfig(biome, FeatureSubscriber.abandoned_camp_structure);
            return rand.nextDouble() < config.probability;
        }

        return false;
    }

    @Nonnull
    @Override
    public IStartFactory getStartFactory() {
        return AbandonedCampStructure.Start::new;
    }

    @Nonnull
    @Override
    public String getStructureName() {
        return "AbandonedCamp";
    }

    @Override
    public int getSize() {
        return 7;
    }

    public static class Start extends StructureStart {
        public Start(Structure<?> structure, int chunkX, int chunkZ, MutableBoundingBox bounds, int reference, long seed) {
            super(structure, chunkX, chunkZ, bounds, reference, seed);
        }

        public void init(@Nonnull ChunkGenerator<?> generator, @Nonnull TemplateManager templateManagerIn, int chunkX, int chunkZ, @Nonnull Biome biomeIn) {
            AbandonedCampPiece campPiece = new AbandonedCampPiece(this.rand, (chunkX << 4) + 2, (chunkZ << 4) + 2);
            this.components.add(campPiece);
            campPiece.buildComponent(campPiece, this.components, this.rand);
            this.recalculateStructureSize();
        }
    }
}
