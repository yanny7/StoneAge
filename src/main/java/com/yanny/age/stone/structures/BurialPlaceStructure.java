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

public class BurialPlaceStructure extends Structure<ProbabilityConfig> {
    public BurialPlaceStructure(Function<Dynamic<?>, ? extends ProbabilityConfig> configFactoryIn) {
        super(configFactoryIn);
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public boolean func_225558_a_(@Nonnull BiomeManager biomeManager, @Nonnull ChunkGenerator<?> chunkGen, @Nonnull Random rand, int chunkPosX, int chunkPosZ, @Nonnull Biome biome) {
        ((SharedSeedRandom)rand).setLargeFeatureSeedWithSalt(chunkGen.getSeed(), chunkPosX, chunkPosZ, 121381454);

        if (chunkGen.hasStructure(biome, FeatureSubscriber.burial_place_structure)) {
            ProbabilityConfig config = chunkGen.getStructureConfig(biome, FeatureSubscriber.burial_place_structure);
            return rand.nextDouble() < config.probability;
        }

        return false;
    }

    @Nonnull
    @Override
    public IStartFactory getStartFactory() {
        return BurialPlaceStructure.Start::new;
    }

    @Nonnull
    @Override
    public String getStructureName() {
        return "BurialPlace";
    }

    @Override
    public int getSize() {
        return 9;
    }

    public static class Start extends StructureStart {
        public Start(Structure<?> structure, int chunkX, int chunkZ, MutableBoundingBox bounds, int reference, long seed) {
            super(structure, chunkX, chunkZ, bounds, reference, seed);
        }

        public void init(@Nonnull ChunkGenerator<?> generator, @Nonnull TemplateManager templateManagerIn, int chunkX, int chunkZ, @Nonnull Biome biomeIn) {
            BurialPlacePiece campPiece = new BurialPlacePiece(this.rand, (chunkX << 4) + 2, (chunkZ << 4) + 2);
            this.components.add(campPiece);
            campPiece.buildComponent(campPiece, this.components, this.rand);
            this.recalculateStructureSize();
        }
    }
}
