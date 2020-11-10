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
import org.joml.Matrix3f;
import org.joml.Vector3f;

import javax.annotation.Nonnull;
import java.util.Random;

import static com.yanny.age.stone.structures.FeatureUtils.*;
import static net.minecraft.state.properties.BlockStateProperties.LIT;

public class AbandonedCampFeature extends Feature<ProbabilityConfig> {

    public AbandonedCampFeature(Codec<ProbabilityConfig> configCodec) {
        super(configCodec);
    }

    public boolean func_241855_a(@Nonnull ISeedReader seedReader, @Nonnull ChunkGenerator chunkGenerator, @Nonnull Random random, @Nonnull BlockPos pos, @Nonnull ProbabilityConfig featureConfig) {
        if (random.nextFloat() < 0.5) {
            fillWithAir(seedReader, pos, -2, 0, -2, 2, 3, 2);
            fillWithBlocks(seedReader, pos, -2, -1, -2, 2, -1, 2, Blocks.GRASS_BLOCK.getDefaultState(), Blocks.GRASS_BLOCK.getDefaultState(), false);

            Direction direction = Direction.Plane.HORIZONTAL.random(random);

            Matrix3f normal = new Matrix3f();
            normal.identity();
            normal.rotate((float) Math.toRadians(direction.getHorizontalAngle()), new Vector3f(0, 1, 0));

            for (int i = -2; i <= 2; i++) {
                if (random.nextDouble() < 0.5) {
                    generateRandomRack(seedReader, getRotatedPos(pos, i, 0, 2, normal), random, direction.getOpposite());
                }
            }

            seedReader.setBlockState(pos, Blocks.CAMPFIRE.getDefaultState().with(LIT, false), 2);
            generateBed(seedReader, getRotatedPos(pos, 2, 0, 0, normal), direction);
            generateFlintWorkbench(seedReader, getRotatedPos(pos, -2, 0, 0, normal), random, direction.getOpposite().rotateY());
            generateStoneChest(seedReader, getRotatedPos(pos, -2, 0, -1, normal), random, new ResourceLocation(Reference.MODID, "chests/stone_chest"), direction.getOpposite().rotateY());

            return true;
        }

        return false;
    }
}
