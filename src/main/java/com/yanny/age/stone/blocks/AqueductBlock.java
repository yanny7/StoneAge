package com.yanny.age.stone.blocks;

import com.google.common.collect.Maps;
import com.yanny.age.stone.compatibility.top.ITopBlockProvider;
import mcjty.theoneprobe.api.IProbeHitData;
import mcjty.theoneprobe.api.IProbeInfo;
import mcjty.theoneprobe.api.ProbeMode;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.fluid.IFluidState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.BucketItem;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static net.minecraft.state.properties.BlockStateProperties.WATERLOGGED;

public class AqueductBlock extends Block implements ITopBlockProvider {
    private static final Map<Integer, VoxelShape> SHAPES = new HashMap<>();

    static {
        SHAPES.put(0, getBaseShape());
        SHAPES.put(1, VoxelShapes.or(getBaseShape(), getNorthShape()));
        SHAPES.put(2, VoxelShapes.or(getBaseShape(), getEastShape()));
        SHAPES.put(3, VoxelShapes.or(getBaseShape(), getNorthShape(), getEastShape()));
        SHAPES.put(4, VoxelShapes.or(getBaseShape(), getSouthShape()));
        SHAPES.put(5, VoxelShapes.or(getBaseShape(), getNorthShape(), getSouthShape()));
        SHAPES.put(6, VoxelShapes.or(getBaseShape(), getEastShape(), getSouthShape()));
        SHAPES.put(7, VoxelShapes.or(getBaseShape(), getNorthShape(), getEastShape(), getSouthShape()));
        SHAPES.put(8, VoxelShapes.or(getBaseShape(), getWestShape()));
        SHAPES.put(9, VoxelShapes.or(getBaseShape(), getNorthShape(), getWestShape()));
        SHAPES.put(10, VoxelShapes.or(getBaseShape(), getEastShape(), getWestShape()));
        SHAPES.put(11, VoxelShapes.or(getBaseShape(), getNorthShape(), getEastShape(), getWestShape()));
        SHAPES.put(12, VoxelShapes.or(getBaseShape(), getSouthShape(), getWestShape()));
        SHAPES.put(13, VoxelShapes.or(getBaseShape(), getNorthShape(), getSouthShape(), getWestShape()));
        SHAPES.put(14, VoxelShapes.or(getBaseShape(), getEastShape(), getSouthShape(), getWestShape()));
        SHAPES.put(15, VoxelShapes.or(getBaseShape(), getNorthShape(), getEastShape(), getSouthShape(), getWestShape()));
    }

    private static final BooleanProperty NORTH = BooleanProperty.create("north");
    private static final BooleanProperty EAST = BooleanProperty.create("east");
    private static final BooleanProperty SOUTH = BooleanProperty.create("south");
    private static final BooleanProperty WEST = BooleanProperty.create("west");

    private static final Map<Direction, BooleanProperty> FACING_TO_PROPERTY_MAP = Util.make(Maps.newEnumMap(Direction.class), (map) -> {
        map.put(Direction.NORTH, NORTH);
        map.put(Direction.EAST, EAST);
        map.put(Direction.SOUTH, SOUTH);
        map.put(Direction.WEST, WEST);
    });

    public AqueductBlock() {
        super(Properties.create(Material.ROCK).hardnessAndResistance(2.0f));
        setDefaultState(getStateContainer().getBaseState()
                .with(NORTH, false)
                .with(SOUTH, false)
                .with(EAST, false)
                .with(WEST, false)
                .with(WATERLOGGED, false));
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new AqueductTileEntity();
    }

    @Nonnull
    @Override
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT_MIPPED;
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return this.makeConnections(context.getWorld(), context.getPos());
    }

    @Override
    public void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(NORTH, EAST, SOUTH, WEST);
        builder.add(WATERLOGGED);
    }

    @SuppressWarnings("deprecation")
    @Override
    @Nonnull
    public BlockState updatePostPlacement(@Nonnull BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn,
                                          BlockPos currentPos, BlockPos facingPos) {
        if (facing != Direction.DOWN && facing != Direction.UP) {
            return stateIn.with(FACING_TO_PROPERTY_MAP.get(facing), checkSideConnection(worldIn.getWorld(), currentPos, facingPos, facing));
        }

        return super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
    }

    @SuppressWarnings("deprecation")
    @Nonnull
    @Override
    public IFluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getFlowingFluidState(1, false) : super.getFluidState(state);
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        AqueductTileEntity tile = (AqueductTileEntity) worldIn.getTileEntity(pos);

        if (tile != null) {
            ItemStack heldItem = player.getHeldItem(handIn);

            if (heldItem.getItem() instanceof BucketItem) {
                LazyOptional<IFluidHandler> fluidHandler = tile.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null);
                fluidHandler.ifPresent(fluid -> FluidUtil.interactWithFluidHandler(player, handIn, fluid));
                return true;
            }

            if (heldItem.isEmpty()) {
                tile.changedState();
                return true;
            }

            return super.onBlockActivated(state, worldIn, pos, player, handIn, hit);
        } else {
            throw new IllegalStateException("Named container provider is missing");
        }
    }

    @OnlyIn(Dist.CLIENT)
    public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand) {
        if (rand.nextInt(2) == 0 && stateIn.get(WATERLOGGED)) {
            BlockPos blockpos = pos.up();

            if (worldIn.isAirBlock(blockpos)) {
                AqueductTileEntity tileEntity = (AqueductTileEntity) worldIn.getTileEntity(pos);

                if (tileEntity != null && tileEntity.getCapacity() > 0.01) {
                    double d0 = pos.getX() + rand.nextFloat() / 2 + 0.25;
                    double d1 = pos.getY() + 4 / 16f + 0.05D;
                    double d2 = pos.getZ() + rand.nextFloat() / 2 + 0.25;
                    worldIn.addParticle(ParticleTypes.BUBBLE, d0, d1, d2, 0, rand.nextFloat(), 0);
                }
            }
        }
    }

    @SuppressWarnings("deprecation")
    @Nonnull
    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        int flag = 0;
        flag |= !state.get(NORTH) ? 1 : 0;
        flag |= !state.get(EAST) ? 2 : 0;
        flag |= !state.get(SOUTH) ? 4 : 0;
        flag |= !state.get(WEST) ? 8 : 0;
        return SHAPES.get(flag);
    }

    @Override
    public void addProbeInfo(ProbeMode probeMode, IProbeInfo iProbeInfo, PlayerEntity playerEntity, World world, BlockState blockState, IProbeHitData iProbeHitData) {
        TileEntity te = world.getTileEntity(iProbeHitData.getPos());

        if (te instanceof AqueductTileEntity) {
            AqueductTileEntity aqueductTileEntity = (AqueductTileEntity) te;
            iProbeInfo.horizontal().progress(aqueductTileEntity.getFilled(), aqueductTileEntity.getFullCapacity(), iProbeInfo.defaultProgressStyle().suffix("mB"));
        }
    }

    static boolean isWater(Block block, IFluidState fluidBlockState) {
        return block.equals(Blocks.WATER) && (fluidBlockState.getLevel() == 8);
    }

    private BlockState makeConnections(@Nonnull World world, @Nonnull BlockPos pos) {
        return getDefaultState()
                .with(NORTH, checkSideConnection(world, pos, pos.north(), Direction.NORTH))
                .with(EAST, checkSideConnection(world, pos, pos.east(), Direction.EAST))
                .with(SOUTH, checkSideConnection(world, pos, pos.south(), Direction.SOUTH))
                .with(WEST, checkSideConnection(world, pos, pos.west(), Direction.WEST));
    }

    private boolean checkSideConnection(@Nonnull World world, @Nonnull BlockPos pos, @Nonnull BlockPos sidePos, Direction direction) {
        AqueductTileEntity aqueductTileEntity = (AqueductTileEntity) world.getTileEntity(pos);
        BlockState facingBlockState = world.getBlockState(sidePos);
        Block block = facingBlockState.getBlock();
        boolean isWater = isWater(block, world.getFluidState(sidePos));

        if (aqueductTileEntity != null) {
            aqueductTileEntity.setSource(direction, isWater);
        }

        return block == this || isWater;
    }

    static private VoxelShape getBaseShape() {
        VoxelShape base = Block.makeCuboidShape(0, 0, 0, 16, 4, 16);
        VoxelShape b1 = Block.makeCuboidShape(0, 4, 0, 4, 16, 4);
        VoxelShape b2 = Block.makeCuboidShape(12, 4, 0, 16, 16, 4);
        VoxelShape b3 = Block.makeCuboidShape(0, 4, 12, 4, 16, 16);
        VoxelShape b4 = Block.makeCuboidShape(12, 4, 12, 16, 16, 16);
        return VoxelShapes.or(base, b1, b2, b3, b4);
    }

    static private VoxelShape getNorthShape() {
        return Block.makeCuboidShape(4, 4, 0, 12, 16, 4);
    }

    static private VoxelShape getEastShape() {
        return Block.makeCuboidShape(12, 4, 4, 16, 16, 12);
    }

    static private VoxelShape getSouthShape() {
        return Block.makeCuboidShape(4, 4, 12, 12, 16, 16);
    }

    static private VoxelShape getWestShape() {
        return Block.makeCuboidShape(0, 4, 4, 4, 16, 12);
    }
}
