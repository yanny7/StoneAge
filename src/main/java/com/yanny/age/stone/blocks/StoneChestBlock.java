package com.yanny.age.stone.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class StoneChestBlock extends HorizontalBlock {
    private static final VoxelShape SHAPE = Block.makeCuboidShape(1, 0, 1, 15, 14, 15);

    public StoneChestBlock() {
        super(Properties.create(Material.ROCK).hardnessAndResistance(2.0f));
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new StoneChestTileEntity();
    }

    @SuppressWarnings("deprecation")
    @Override
    @Nonnull
    public BlockRenderType getRenderType(@Nonnull BlockState state) {
        return BlockRenderType.ENTITYBLOCK_ANIMATED;
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
    @Nonnull
    @Override
    public VoxelShape getShape(@Nonnull BlockState state, @Nonnull IBlockReader worldIn, @Nonnull BlockPos pos, @Nonnull ISelectionContext context) {
        return SHAPE;
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onReplaced(BlockState state, @Nonnull World worldIn, @Nonnull BlockPos pos, BlockState newState, boolean isMoving) {
        if (state.getBlock() != newState.getBlock()) {
            TileEntity tileentity = worldIn.getTileEntity(pos);

            if (tileentity instanceof StoneChestTileEntity) {
                InventoryHelper.dropInventoryItems(worldIn, pos, ((StoneChestTileEntity)tileentity).getInventory());
            }

            super.onReplaced(state, worldIn, pos, newState, isMoving);
        }
    }

    @Nonnull
    @SuppressWarnings("deprecation")
    @Override
    public ActionResultType onBlockActivated(@Nonnull BlockState state, World worldIn, @Nonnull BlockPos pos, @Nonnull PlayerEntity player,
                                             @Nonnull Hand handIn, @Nonnull BlockRayTraceResult hit) {
        StoneChestTileEntity tile = (StoneChestTileEntity) worldIn.getTileEntity(pos);

        if (tile != null) {
            if (!worldIn.isRemote) {
                NetworkHooks.openGui((ServerPlayerEntity) player, tile, tile.getPos());
            }

            return ActionResultType.SUCCESS;
        } else {
            throw new IllegalStateException("Named container provider is missing");
        }
    }
}
