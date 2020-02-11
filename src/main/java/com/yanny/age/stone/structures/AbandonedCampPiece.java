package com.yanny.age.stone.structures;

import com.yanny.age.stone.Reference;
import com.yanny.age.stone.subscribers.BlockSubscriber;
import net.minecraft.block.Blocks;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.state.properties.BedPart;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.feature.structure.IStructurePieceType;
import net.minecraft.world.gen.feature.template.TemplateManager;

import javax.annotation.Nonnull;
import java.util.Random;

import static net.minecraft.state.properties.BlockStateProperties.*;

public class AbandonedCampPiece extends BasePiece {
    public static IStructurePieceType CAMP;

    public AbandonedCampPiece(Random rand, int xIn, int zIn) {
        super(CAMP, new Random(), xIn, 0, zIn, 7, 5, 7);
        setCoordBaseMode(Direction.Plane.HORIZONTAL.random(rand));
        LOGGER.info("Generated AbandonedCamp at x:" + xIn + " z:" + zIn);
    }

    @SuppressWarnings("unused")
    public AbandonedCampPiece(TemplateManager templateManager, CompoundNBT compoundNBT) {
        super(CAMP, compoundNBT);
    }

    @Override
    public boolean addComponentParts(@Nonnull IWorld worldIn, @Nonnull Random randomIn, @Nonnull MutableBoundingBox structureBoundingBoxIn, @Nonnull ChunkPos chunkPosIn) {
        if (!isInsideBounds(worldIn, structureBoundingBoxIn, 0)) {
            return false;
        }

        fillWithAir(worldIn, structureBoundingBoxIn, 0, 0, 0, 6, 3, 6);
        fillWithBlocks(worldIn, structureBoundingBoxIn, 0, -1, 0, 6, -1, 6, Blocks.GRASS_BLOCK.getDefaultState(), Blocks.GRASS_BLOCK.getDefaultState(), false);

        for (int i = 1; i < 6; i++) {
            if (randomIn.nextDouble() < 0.5) {
                generateRandomRack(worldIn, structureBoundingBoxIn, randomIn, 6, 0, i, Direction.WEST);
            }
        }

        setBlockState(worldIn, Blocks.CAMPFIRE.getDefaultState().with(LIT, false), 3, 0, 3, structureBoundingBoxIn);
        //noinspection ConstantConditions
        setBlockState(worldIn, BlockSubscriber.dried_grass_bed.getDefaultState().with(HORIZONTAL_FACING, Direction.EAST).with(BED_PART, BedPart.HEAD), 3, 0, 5, structureBoundingBoxIn);
        //noinspection ConstantConditions
        setBlockState(worldIn, BlockSubscriber.dried_grass_bed.getDefaultState().with(HORIZONTAL_FACING, Direction.EAST).with(BED_PART, BedPart.FOOT), 2, 0, 5, structureBoundingBoxIn);
        generateFlintWorkbench(worldIn, structureBoundingBoxIn, randomIn, 0, 0, 3, Direction.EAST);
        generateStoneChest(worldIn, structureBoundingBoxIn, randomIn, 0, 0, 2, new ResourceLocation(Reference.MODID, "chests/stone_chest"), Direction.EAST);

        return true;
    }
}
