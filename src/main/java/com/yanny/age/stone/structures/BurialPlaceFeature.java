package com.yanny.age.stone.structures;

import com.mojang.serialization.Codec;
import com.yanny.age.stone.Reference;
import net.minecraft.block.Blocks;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.ProbabilityConfig;

import javax.annotation.Nonnull;
import java.util.Random;

import static com.yanny.age.stone.structures.FeatureUtils.generateStoneChest;
import static com.yanny.age.stone.structures.FeatureUtils.replaceAirAndLiquidDownwards;

public class BurialPlaceFeature extends Feature<ProbabilityConfig> {

    public BurialPlaceFeature(Codec<ProbabilityConfig> configCodec) {
        super(configCodec);
    }

    @Override
    public boolean func_241855_a(@Nonnull ISeedReader seedReader, @Nonnull ChunkGenerator chunkGenerator, @Nonnull Random random, @Nonnull BlockPos pos, @Nonnull ProbabilityConfig featureConfig) {
        if (random.nextFloat() < featureConfig.probability && seedReader.getBlockState(pos.down()).isSolid()) {
            generateStoneChest(seedReader, pos.add(0, -2, 0), random, new ResourceLocation(Reference.MODID, "chests/stone_chest"), Direction.Plane.HORIZONTAL.random(random));

            replaceAirAndLiquidDownwards(seedReader, pos.add(-4, 2, 0), Blocks.STONE.getDefaultState());
            replaceAirAndLiquidDownwards(seedReader, pos.add(-3, 2, -3), Blocks.STONE.getDefaultState());
            replaceAirAndLiquidDownwards(seedReader, pos.add(-3, 2, 3), Blocks.STONE.getDefaultState());
            replaceAirAndLiquidDownwards(seedReader, pos.add(0, 2, -4), Blocks.STONE.getDefaultState());
            replaceAirAndLiquidDownwards(seedReader, pos.add(0, 2, 4), Blocks.STONE.getDefaultState());
            replaceAirAndLiquidDownwards(seedReader, pos.add(3, 2, -3), Blocks.STONE.getDefaultState());
            replaceAirAndLiquidDownwards(seedReader, pos.add(3, 2, 3), Blocks.STONE.getDefaultState());
            replaceAirAndLiquidDownwards(seedReader, pos.add(4, 2, 0), Blocks.STONE.getDefaultState());

            return true;
        }

        return false;
    }
}
