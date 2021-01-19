package com.yanny.age.stone.structures;

import com.yanny.age.stone.blocks.DryingRackTileEntity;
import com.yanny.age.stone.blocks.FlintWorkbenchTileEntity;
import com.yanny.age.stone.blocks.StoneChestTileEntity;
import com.yanny.age.stone.blocks.TanningRackTileEntity;
import com.yanny.age.stone.subscribers.BlockSubscriber;
import com.yanny.age.stone.subscribers.ItemSubscriber;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import net.minecraft.state.properties.BedPart;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import org.joml.Matrix3f;
import org.joml.Vector3f;

import javax.annotation.Nonnull;
import java.util.Random;

import static net.minecraft.state.properties.BlockStateProperties.BED_PART;
import static net.minecraft.state.properties.BlockStateProperties.HORIZONTAL_FACING;

public class FeatureUtils {

    public static void fillWithAir(ISeedReader seedReader, BlockPos pos, int minX, int minY, int minZ, int maxX, int maxY, int maxZ) {
        for(int i = minY; i <= maxY; ++i) {
            for(int j = minX; j <= maxX; ++j) {
                for(int k = minZ; k <= maxZ; ++k) {
                    seedReader.setBlockState(pos.add(j, i, k), Blocks.AIR.getDefaultState(), 2);
                }
            }
        }

    }

    public static void fillWithBlocks(ISeedReader seedReader, BlockPos pos, int xMin, int yMin, int zMin, int xMax, int yMax, int zMax, BlockState boundaryBlockState, BlockState insideBlockState, boolean existingOnly) {
        for(int i = yMin; i <= yMax; ++i) {
            for(int j = xMin; j <= xMax; ++j) {
                for(int k = zMin; k <= zMax; ++k) {
                    //noinspection deprecation
                    if (!existingOnly || !seedReader.getBlockState(pos.add(j, i, k)).isAir()) {
                        if (i != yMin && i != yMax && j != xMin && j != xMax && k != zMin && k != zMax) {
                            seedReader.setBlockState(pos.add(j, i, k), insideBlockState, 2);
                        } else {
                            seedReader.setBlockState(pos.add(j, i, k), boundaryBlockState, 2);
                        }
                    }
                }
            }
        }
    }

    @SuppressWarnings("ConstantConditions")
    public static void generateRandomRack(@Nonnull ISeedReader seedReader, @Nonnull BlockPos pos, @Nonnull Random random, @Nonnull Direction direction) {
        if (random.nextDouble() < 0.5) {
            seedReader.setBlockState(pos, BlockSubscriber.drying_rack.getDefaultState().with(HORIZONTAL_FACING, direction), 2);
            TileEntity tileentity = seedReader.getTileEntity(pos);

            if (tileentity instanceof DryingRackTileEntity) {
                NonNullList<ItemStack> stacks = ((DryingRackTileEntity)tileentity).getStacks();

                for (int i = stacks.size() / 2; i < stacks.size(); i++) {
                    if (random.nextDouble() < 0.5) {
                        stacks.set(i, new ItemStack(ItemSubscriber.dried_grass));
                    }
                }
            }
        } else {
            seedReader.setBlockState(pos, BlockSubscriber.tanning_rack.getDefaultState().with(HORIZONTAL_FACING, direction), 2);
            TileEntity tileentity = seedReader.getTileEntity(pos);

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

    @SuppressWarnings("ConstantConditions")
    public static void generateBed(@Nonnull ISeedReader seedReader, @Nonnull BlockPos pos, Direction direction) {
        Direction bedDirection = direction.getAxisDirection() == Direction.AxisDirection.NEGATIVE ? direction : direction.getOpposite();
        seedReader.setBlockState(pos, BlockSubscriber.dried_grass_bed.getDefaultState().with(HORIZONTAL_FACING, bedDirection).with(BED_PART, BedPart.HEAD), 2);
        seedReader.setBlockState(pos.func_241872_a(direction.getAxis(), 1), BlockSubscriber.dried_grass_bed.getDefaultState().with(HORIZONTAL_FACING, bedDirection).with(BED_PART, BedPart.FOOT), 2);
    }

    private static final Vector3f vector = new Vector3f();
    public static BlockPos getRotatedPos(BlockPos pos, int x, int y, int z, Matrix3f normal) {
        vector.set(x, y, z);
        vector.mulTranspose(normal);
        return pos.add(Math.round(vector.x), Math.round(vector.y), Math.round(vector.z));
    }

    @SuppressWarnings("ConstantConditions")
    public static void generateFlintWorkbench(@Nonnull ISeedReader seedReader, @Nonnull BlockPos pos, @Nonnull Random rand, @Nonnull Direction direction) {
        seedReader.setBlockState(pos, BlockSubscriber.flint_workbench.getDefaultState().with(BlockStateProperties.HORIZONTAL_FACING, direction), 2);
        TileEntity tileentity = seedReader.getTileEntity(pos);

        if (tileentity instanceof FlintWorkbenchTileEntity) {
            NonNullList<ItemStack> stacks = ((FlintWorkbenchTileEntity)tileentity).getStacks();

            for (int i = 0; i < stacks.size(); i++) {
                if (rand.nextDouble() < 0.25) {
                    stacks.set(i, new ItemStack(ItemSubscriber.antler));
                }
            }
        }
    }

    @SuppressWarnings("ConstantConditions")
    public static void generateStoneChest(@Nonnull ISeedReader seedReader, BlockPos pos, @Nonnull Random rand, @Nonnull ResourceLocation lootResource, Direction direction) {
        seedReader.setBlockState(pos, BlockSubscriber.stone_chest.getDefaultState().with(BlockStateProperties.HORIZONTAL_FACING, direction), 2);
        TileEntity tileentity = seedReader.getTileEntity(pos);

        if (tileentity instanceof StoneChestTileEntity) {
            ((StoneChestTileEntity)tileentity).setLootTable(lootResource, rand.nextLong());
        }
    }

    public static void replaceAirAndLiquidDownwards(@Nonnull ISeedReader seedReader, @Nonnull BlockPos pos, @Nonnull BlockState blockState) {
        int i = pos.getY();
        BlockPos p = pos;

        while (isNotSolid(seedReader, p) && i > 1) {
            seedReader.setBlockState(p, blockState, 2);
            p = p.down();
        }
    }

    private static boolean isNotSolid(@Nonnull ISeedReader seedReader, @Nonnull BlockPos pos) {
        BlockState state = seedReader.getBlockState(pos);
        return seedReader.isAirBlock(pos) || state.getMaterial().isLiquid() || state.getMaterial().isReplaceable() || state.getMaterial().equals(Material.SAND)
                || state.getMaterial().equals(Material.EARTH) || state.getMaterial().equals(Material.ORGANIC);
    }
}
