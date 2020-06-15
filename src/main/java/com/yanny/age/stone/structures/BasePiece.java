package com.yanny.age.stone.structures;

import com.yanny.age.stone.blocks.DryingRackTileEntity;
import com.yanny.age.stone.blocks.FlintWorkbenchTileEntity;
import com.yanny.age.stone.blocks.StoneChestTileEntity;
import com.yanny.age.stone.blocks.TanningRackTileEntity;
import com.yanny.age.stone.subscribers.BlockSubscriber;
import com.yanny.age.stone.subscribers.ItemSubscriber;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.feature.structure.IStructurePieceType;
import net.minecraft.world.gen.feature.structure.ScatteredStructurePiece;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nonnull;
import java.util.Random;

import static net.minecraft.state.properties.BlockStateProperties.HORIZONTAL_FACING;

public abstract class BasePiece extends ScatteredStructurePiece {
    protected static final Logger LOGGER = LogManager.getLogger();

    protected BasePiece(@Nonnull IStructurePieceType structurePieceTypeIn, @Nonnull Random rand, int xIn, int yIn, int zIn, int widthIn, int heightIn, int depthIn) {
        super(structurePieceTypeIn, rand, xIn, yIn, zIn, widthIn, heightIn, depthIn);
    }

    protected BasePiece(@Nonnull IStructurePieceType structurePieceTypeIn, @Nonnull CompoundNBT nbt) {
        super(structurePieceTypeIn, nbt);
    }

    protected void generateStoneChest(@Nonnull IWorld worldIn, MutableBoundingBox boundsIn, @Nonnull Random rand, int x, int y, int z, @Nonnull ResourceLocation lootResource, Direction direction) {
        BlockPos blockpos = new BlockPos(getXWithOffset(x, z), getYWithOffset(y), getZWithOffset(x, z));
        if (boundsIn.isVecInside(blockpos)) {
            //noinspection ConstantConditions
            worldIn.setBlockState(blockpos, BlockSubscriber.stone_chest.getDefaultState().with(BlockStateProperties.HORIZONTAL_FACING, direction), 2);
            TileEntity tileentity = worldIn.getTileEntity(blockpos);

            if (tileentity instanceof StoneChestTileEntity) {
                ((StoneChestTileEntity)tileentity).setLootTable(lootResource, rand.nextLong());
            }
        }
    }

    @SuppressWarnings("SameParameterValue")
    protected void generateFlintWorkbench(@Nonnull IWorld worldIn, @Nonnull MutableBoundingBox boundsIn, @Nonnull Random rand, int x, int y, int z, @Nonnull Direction direction) {
        BlockPos blockpos = new BlockPos(getXWithOffset(x, z), getYWithOffset(y), getZWithOffset(x, z));

        if (boundsIn.isVecInside(blockpos)) {
            //noinspection ConstantConditions
            setBlockState(worldIn, BlockSubscriber.flint_workbench.getDefaultState().with(BlockStateProperties.HORIZONTAL_FACING, direction), x, y, z, boundsIn);
            TileEntity tileentity = worldIn.getTileEntity(blockpos);

            if (tileentity instanceof FlintWorkbenchTileEntity) {
                NonNullList<ItemStack> stacks = ((FlintWorkbenchTileEntity)tileentity).getStacks();

                for (int i = 0; i < stacks.size(); i++) {
                    if (rand.nextDouble() < 0.25) {
                        stacks.set(i, new ItemStack(ItemSubscriber.antler));
                    }
                }
            }
        }
    }

    @SuppressWarnings("SameParameterValue")
    protected void generateRandomRack(@Nonnull IWorld worldIn, @Nonnull MutableBoundingBox boundsIn, @Nonnull Random random, int x, int y, int z, @Nonnull Direction direction) {
        BlockPos blockpos = new BlockPos(getXWithOffset(x, z), getYWithOffset(y), getZWithOffset(x, z));

        if (boundsIn.isVecInside(blockpos)) {
            if (random.nextDouble() < 0.5) {
                //noinspection ConstantConditions
                setBlockState(worldIn, BlockSubscriber.drying_rack.getDefaultState().with(HORIZONTAL_FACING, direction), x, y, z, boundsIn);
                TileEntity tileentity = worldIn.getTileEntity(blockpos);

                if (tileentity instanceof DryingRackTileEntity) {
                    NonNullList<ItemStack> stacks = ((DryingRackTileEntity)tileentity).getStacks();

                    for (int i = stacks.size() / 2; i < stacks.size(); i++) {
                        if (random.nextDouble() < 0.5) {
                            stacks.set(i, new ItemStack(ItemSubscriber.dried_grass));
                        }
                    }
                }
            } else {
                //noinspection ConstantConditions
                setBlockState(worldIn, BlockSubscriber.tanning_rack.getDefaultState().with(HORIZONTAL_FACING, direction), x, y, z, boundsIn);
                TileEntity tileentity = worldIn.getTileEntity(blockpos);

                if (tileentity instanceof TanningRackTileEntity) {
                    NonNullList<ItemStack> stacks = ((TanningRackTileEntity)tileentity).getStacks();

                    for (int i = 0; i < stacks.size() / 2; i++) {
                        if (random.nextDouble() < 0.7) {
                            stacks.set(i, new ItemStack(ItemSubscriber.raw_hide));
                        }
                    }
                }
            }
        }
    }

    @SuppressWarnings("SameParameterValue")
    protected void replaceAirAndLiquidDownwards(@Nonnull IWorld worldIn, @Nonnull MutableBoundingBox boundingboxIn, int x, int y, int z, @Nonnull BlockState blockstateIn) {
        int i = getXWithOffset(x, z);
        int j = getYWithOffset(y);
        int k = getZWithOffset(x, z);
        if (boundingboxIn.isVecInside(new BlockPos(i, j, k))) {
            while(isNotSolid(worldIn, new BlockPos(i, j, k)) && j > 1) {
                worldIn.setBlockState(new BlockPos(i, j, k), blockstateIn, 2);
                --j;
            }
        }
    }

    private boolean isNotSolid(@Nonnull IWorld worldIn, @Nonnull BlockPos pos) {
        BlockState state = worldIn.getBlockState(pos);
        return worldIn.isAirBlock(pos) || state.getMaterial().isLiquid() || state.getMaterial().isReplaceable() || state.getMaterial().equals(Material.SAND)
                || state.getMaterial().equals(Material.EARTH) || state.getMaterial().equals(Material.ORGANIC);
    }
}
