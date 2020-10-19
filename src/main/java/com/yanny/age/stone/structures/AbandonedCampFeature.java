package com.yanny.age.stone.structures;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.serialization.Codec;
import com.yanny.age.stone.Reference;
import net.minecraft.block.Blocks;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Matrix3f;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.ProbabilityConfig;

import javax.annotation.Nonnull;
import java.util.Random;

import static com.yanny.age.stone.structures.FeatureUtils.*;
import static net.minecraft.state.properties.BlockStateProperties.*;

public class AbandonedCampFeature extends Feature<ProbabilityConfig> {

    public AbandonedCampFeature(Codec<ProbabilityConfig> configCodec) {
        super(configCodec);
    }

    public boolean func_241855_a(@Nonnull ISeedReader seedReader, @Nonnull ChunkGenerator chunkGenerator, @Nonnull Random random, @Nonnull BlockPos pos, @Nonnull ProbabilityConfig featureConfig) {
        if (random.nextFloat() < featureConfig.probability && seedReader.getBlockState(pos.down()).isSolid()) {
            fillWithAir(seedReader, pos, -2, 0, -2, 2, 3, 2);
            fillWithBlocks(seedReader, pos, -2, -1, -2, 2, -1, 2, Blocks.GRASS_BLOCK.getDefaultState(), Blocks.GRASS_BLOCK.getDefaultState(), false);

            Direction direction = Direction.Plane.HORIZONTAL.random(random);
            MatrixStack matrixStack = new MatrixStack();
            matrixStack.rotate(Vector3f.YP.rotationDegrees(direction.getHorizontalAngle()));
            Matrix3f normal = matrixStack.getLast().getNormal();

            for (int i = -2; i <= 2; i++) {
                if (random.nextDouble() < 0.5) {
                    generateRandomRack(seedReader, getRotatedPos(pos, i, 0, 2, normal), random, direction.getAxis() == Direction.Axis.X ? direction : direction.getOpposite());
                }
            }

            seedReader.setBlockState(pos, Blocks.CAMPFIRE.getDefaultState().with(LIT, false), 2);
            generateBed(seedReader, getRotatedPos(pos, 2, 0, 0, normal), direction);
            generateFlintWorkbench(seedReader, getRotatedPos(pos, -2, 0, 0, normal), random, Direction.EAST);
            generateStoneChest(seedReader, getRotatedPos(pos, -2, 0, -1, normal), random, new ResourceLocation(Reference.MODID, "chests/stone_chest"), Direction.EAST);

            return true;
        }

        return false;
    }
}
