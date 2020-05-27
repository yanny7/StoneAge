package com.yanny.age.stone.blocks;

import com.yanny.age.stone.compatibility.top.TopBlockInfoProvider;
import mcjty.theoneprobe.api.IProbeHitData;
import mcjty.theoneprobe.api.IProbeInfo;
import mcjty.theoneprobe.api.ProbeMode;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.InventoryHelper;
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
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class MillstoneBlock extends HorizontalBlock implements TopBlockInfoProvider {
    private static final VoxelShape SHAPE = VoxelShapes.or(Block.makeCuboidShape(0, 0, 0, 16, 3, 16),
            Block.makeCuboidShape(2.5, 3, 2.5, 13.5, 7, 13.5),
            Block.makeCuboidShape(3, 7.05, 3, 13, 11, 13),
            Block.makeCuboidShape(7, 7, 7, 9, 12, 9));

    public MillstoneBlock() {
        super(Properties.create(Material.ROCK).hardnessAndResistance(4.0f));
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new MillstoneTileEntity();
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
        return SHAPE;
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onReplaced(BlockState state, @Nonnull World worldIn, @Nonnull BlockPos pos, BlockState newState, boolean isMoving) {
        if (state.getBlock() != newState.getBlock()) {
            TileEntity tileentity = worldIn.getTileEntity(pos);

            if (tileentity instanceof MillstoneTileEntity) {
                InventoryHelper.dropInventoryItems(worldIn, pos, ((MillstoneTileEntity)tileentity).getInventory());
            }

            super.onReplaced(state, worldIn, pos, newState, isMoving);
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        MillstoneTileEntity tile = (MillstoneTileEntity) worldIn.getTileEntity(pos);

        if (tile != null) {
            if (!worldIn.isRemote) {
                if (player.isSneaking()) {
                    NetworkHooks.openGui((ServerPlayerEntity) player, tile, tile.getPos());
                } else {
                    tile.onActivated();
                }
            }
            return true;
        } else {
            throw new IllegalStateException("Named container provider is missing");
        }
    }

    @Override
    public void addProbeInfo(ProbeMode probeMode, IProbeInfo iProbeInfo, PlayerEntity playerEntity, World world, BlockState blockState, IProbeHitData iProbeHitData) {
        TileEntity te = world.getTileEntity(iProbeHitData.getPos());

        if (te instanceof MillstoneTileEntity) {
            MillstoneTileEntity millstone = (MillstoneTileEntity) te;

            if (!millstone.getResult().isEmpty()) {
                iProbeInfo.horizontal().item(millstone.getResult()).progress(millstone.getPerc(), 100, iProbeInfo.defaultProgressStyle().suffix("%"));
            }
        }
    }
}
