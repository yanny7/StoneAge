package com.yanny.age.stone.blocks;

import com.yanny.age.stone.subscribers.ToolSubscriber;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class TanningRackBlock extends HorizontalBlock {
    private static final VoxelShape SHAPE_N = VoxelShapes.or(
            Block.makeCuboidShape(0.0D, 13.5D, 7.0D, 16.0D, 14.5D, 8.0D),
            Block.makeCuboidShape(0.0D, 1.0D, 1.5D, 16.0D, 2.0D, 2.5D),
            Block.makeCuboidShape(0.0D, 0.0, 7.5D, 16.0D, 15.0D, 8.5D));
    private static final VoxelShape SHAPE_S = VoxelShapes.or(
            Block.makeCuboidShape(0.0D, 13.5D, 8.0D, 16.0D, 14.5D, 9.0D),
            Block.makeCuboidShape(0.0D, 1.0D, 13.5D, 16.0D, 2.0D, 14.5D),
            Block.makeCuboidShape(0.0D, 0.0, 7.5D, 16.0D, 15.0D, 8.5D));
    private static final VoxelShape SHAPE_W = VoxelShapes.or(
            Block.makeCuboidShape(7.0D, 13.5D, 0.0D, 8.0D, 14.5D, 16.0D),
            Block.makeCuboidShape(1.5D, 1.0D, 0.0D, 2.5D, 2.0D, 16.0D),
            Block.makeCuboidShape(7.5D, 0.0, 0.0D, 8.5D, 15.0D, 16D));
    private static final VoxelShape SHAPE_E = VoxelShapes.or(
            Block.makeCuboidShape(8.0D, 13.5D, 0.0D, 9.0D, 14.5D, 16.0D),
            Block.makeCuboidShape(13.5D, 1.0D, 0.0D, 14.5D, 2.0D, 16.0D),
            Block.makeCuboidShape(7.5D, 0.0, 0.0D, 8.5D, 15.0D, 16D));

    public TanningRackBlock() {
        super(Properties.create(Material.WOOD).harvestLevel(ToolSubscriber.Tiers.BONE_TIER.getHarvestLevel()).harvestTool(ToolType.AXE).hardnessAndResistance(2.0f));
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new TanningRackTileEntity();
    }

    @SuppressWarnings("deprecation")
    @Override
    @Nonnull
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return this.getDefaultState().with(HORIZONTAL_FACING, context.getPlacementHorizontalFacing().getOpposite());
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(HORIZONTAL_FACING);
    }

    @Override
    public boolean isVariableOpacity() {
        return true;
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        TileEntity tileentity = worldIn.getTileEntity(pos);

        if (tileentity instanceof TanningRackTileEntity && !worldIn.isRemote && (handIn == Hand.MAIN_HAND)) {
            return ((TanningRackTileEntity) tileentity).blockActivated(player);
        }

        return super.onBlockActivated(state, worldIn, pos, player, handIn, hit);
    }

    @Nonnull
    @Override
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT;
    }

    @SuppressWarnings("deprecation")
    @Nonnull
    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        switch (state.get(HORIZONTAL_FACING)) {
            case NORTH:
                return SHAPE_N;
            case SOUTH:
                return SHAPE_S;
            case WEST:
                return SHAPE_W;
            case EAST:
                return SHAPE_E;
        }

        return VoxelShapes.fullCube();
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onReplaced(BlockState state, @Nonnull World worldIn, @Nonnull BlockPos pos, BlockState newState, boolean isMoving) {
        if (state.getBlock() != newState.getBlock()) {
            TileEntity tileentity = worldIn.getTileEntity(pos);

            if (tileentity instanceof TanningRackTileEntity) {
                InventoryHelper.dropInventoryItems(worldIn, pos, ((TanningRackTileEntity)tileentity).getInventory());
            }

            super.onReplaced(state, worldIn, pos, newState, isMoving);
        }
    }
}
