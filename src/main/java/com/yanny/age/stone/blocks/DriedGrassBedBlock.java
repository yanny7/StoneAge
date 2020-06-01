package com.yanny.age.stone.blocks;

import net.minecraft.block.BedBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.item.DyeColor;
import net.minecraft.state.properties.BedPart;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class DriedGrassBedBlock extends BedBlock {
    private static final VoxelShape NORTH = Block.makeCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 3.0D, 16.0D);
    private static final VoxelShape SOUTH = Block.makeCuboidShape(2.0D, 0.0D, 0.0D, 14.0D, 3.0D, 14.0D);
    private static final VoxelShape WEST = Block.makeCuboidShape(2.0D, 0.0D, 2.0D, 16.0D, 3.0D, 14.0D);
    private static final VoxelShape EAST = Block.makeCuboidShape(0.0D, 0.0D, 2.0D, 14.0D, 3.0D, 14.0D);

    public DriedGrassBedBlock() {
        super(DyeColor.BLACK, Properties.create(Material.WOOL).hardnessAndResistance(2.0f));
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new DriedGrassBedTileEntity();
    }

    @Override
    public TileEntity createNewTileEntity(@Nonnull IBlockReader worldIn) {
        return new DriedGrassBedTileEntity();
    }

    @Nonnull
    @Override
    public VoxelShape getShape(BlockState state, @Nonnull IBlockReader worldIn, @Nonnull BlockPos pos, @Nonnull ISelectionContext context) {
        Direction direction = state.get(HORIZONTAL_FACING);
        Direction direction1 = state.get(PART) == BedPart.HEAD ? direction : direction.getOpposite();
        switch(direction1) {
            case NORTH:
                return NORTH;
            case SOUTH:
                return SOUTH;
            case WEST:
                return WEST;
            case EAST:
                return EAST;
        }

        throw new IllegalStateException();
    }
}
