package com.yanny.age.stone.structures;

import com.yanny.age.stone.Reference;
import net.minecraft.block.Blocks;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.structure.IStructurePieceType;
import net.minecraft.world.gen.feature.template.TemplateManager;

import javax.annotation.Nonnull;
import java.util.Random;

public class BurialPlacePiece extends BasePiece {
    public static IStructurePieceType BURIAL_PLACE;

    public BurialPlacePiece(Random rand, int xIn, int zIn) {
        super(BURIAL_PLACE, rand, xIn, 0, zIn, 9, 5, 9);
        setCoordBaseMode(Direction.Plane.HORIZONTAL.random(rand));
        LOGGER.info("Generated BurialPlace at x:" + xIn + " z:" + zIn);
    }

    @SuppressWarnings("unused")
    public BurialPlacePiece(TemplateManager templateManager, CompoundNBT compoundNBT) {
        super(BURIAL_PLACE, compoundNBT);
    }

    @Override
    public boolean create(@Nonnull IWorld worldIn, @Nonnull ChunkGenerator<?> chunkGenerator, @Nonnull Random randomIn,
                                  @Nonnull MutableBoundingBox structureBoundingBoxIn, @Nonnull ChunkPos chunkPos) {
        if (!isInsideBounds(worldIn, structureBoundingBoxIn, 0)) {
            return false;
        }

        generateStoneChest(worldIn, structureBoundingBoxIn, randomIn, 4, -2, 4, new ResourceLocation(Reference.MODID, "chests/stone_chest"), Direction.Plane.HORIZONTAL.random(randomIn));

        replaceAirAndLiquidDownwards(worldIn, structureBoundingBoxIn, 0, 2, 4, Blocks.STONE.getDefaultState());
        replaceAirAndLiquidDownwards(worldIn, structureBoundingBoxIn, 1, 2, 1, Blocks.STONE.getDefaultState());
        replaceAirAndLiquidDownwards(worldIn, structureBoundingBoxIn, 1, 2, 7, Blocks.STONE.getDefaultState());
        replaceAirAndLiquidDownwards(worldIn, structureBoundingBoxIn, 4, 2, 0, Blocks.STONE.getDefaultState());
        replaceAirAndLiquidDownwards(worldIn, structureBoundingBoxIn, 4, 2, 8, Blocks.STONE.getDefaultState());
        replaceAirAndLiquidDownwards(worldIn, structureBoundingBoxIn, 7, 2, 1, Blocks.STONE.getDefaultState());
        replaceAirAndLiquidDownwards(worldIn, structureBoundingBoxIn, 7, 2, 7, Blocks.STONE.getDefaultState());
        replaceAirAndLiquidDownwards(worldIn, structureBoundingBoxIn, 8, 2, 4, Blocks.STONE.getDefaultState());

        return true;
    }
}
