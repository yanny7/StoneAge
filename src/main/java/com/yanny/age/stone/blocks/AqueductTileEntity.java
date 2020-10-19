package com.yanny.age.stone.blocks;

import com.google.common.collect.Maps;
import com.yanny.age.stone.config.Config;
import com.yanny.age.stone.handlers.AqueductHandler;
import com.yanny.age.stone.subscribers.TileEntitySubscriber;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.block.IGrowable;
import net.minecraft.fluid.Fluids;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Map;

import static net.minecraft.state.properties.BlockStateProperties.WATERLOGGED;

public class AqueductTileEntity extends TileEntity implements ITickableTileEntity {
    private static final float LEVELS = 20f;
    private static final FluidStack WATER = new FluidStack(Fluids.WATER, 0);

    private final Map<Direction, Boolean> sources = Maps.newHashMap();

    private boolean activated = false;
    private boolean initialized = false;
    private float capacity = 0f;
    private int level = 0;
    private int filled = 0;
    private int fullCapacity = 0;

    public AqueductTileEntity() {
        //noinspection ConstantConditions
        super(TileEntitySubscriber.aqueduct);
        sources.put(Direction.NORTH, false);
        sources.put(Direction.SOUTH, false);
        sources.put(Direction.EAST, false);
        sources.put(Direction.WEST, false);
    }

    @Override
    public void tick() {
        assert world != null;

        if (!initialized) {
            initialized = true;

            HorizontalBlock.HORIZONTAL_FACING.getAllowedValues().forEach(direction -> {
                BlockPos pos = getPos().offset(direction);
                setSource(direction, AqueductBlock.isWater(world.getBlockState(pos).getBlock(), world.getFluidState(pos)));
            });
        }

        if (!world.isRemote) {
            LazyOptional<FluidTank> fluidTank = AqueductHandler.getInstance(world).getCapability(pos);
            fluidTank.ifPresent(tank -> {
                int oldLevel = level;

                filled = tank.getFluidAmount();
                fullCapacity = tank.getCapacity();
                level = Math.round((float)tank.getFluidAmount() / tank.getCapacity() * LEVELS);

                if (oldLevel != level) {
                    capacity = level / LEVELS;
                    world.notifyBlockUpdate(pos, getBlockState(), getBlockState(), 3);
                }

                LazyOptional<FluidTank> upperTank = AqueductHandler.getInstance(world).getCapability(pos.up());

                if (sources.containsValue(true)) {
                    activated = false;
                    WATER.setAmount(Config.aqueductFillPerTick);
                    tank.fill(WATER, IFluidHandler.FluidAction.EXECUTE);
                } else if (upperTank.isPresent()) {
                    upperTank.ifPresent(upTank -> {
                        activated = false;
                        if (tank.getSpace() >= Config.aqueductFillPerTick) {
                            tank.fill(upTank.drain(Config.aqueductFillPerTick, IFluidHandler.FluidAction.EXECUTE), IFluidHandler.FluidAction.EXECUTE);
                        }
                    });
                } else {
                    if (activated) {
                        FluidStack fluidStack = tank.drain(Config.aqueductUsePerTick, IFluidHandler.FluidAction.EXECUTE);

                        if (fluidStack.isEmpty()) {
                            if (world.getBlockState(pos).get(WATERLOGGED)) {
                                world.setBlockState(pos, world.getBlockState(pos).with(WATERLOGGED, false));
                            }
                        } else {
                            if (!world.getBlockState(pos).get(WATERLOGGED)) {
                                world.setBlockState(pos, world.getBlockState(pos).with(WATERLOGGED, true));
                            }

                            if (world.rand.nextInt(Config.aqueductTickChanceBoneMealEffect) == 0) {
                                boneMealEffect(pos, (ServerWorld)world);
                            }
                        }
                    } else {
                        if (world.getBlockState(pos).get(WATERLOGGED)) {
                            world.setBlockState(pos, world.getBlockState(pos).with(WATERLOGGED, false));
                        }
                    }
                }
            });
        }
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (world != null && !world.isRemote && cap == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
            return AqueductHandler.getInstance(world).getCapability(pos);
        }

        return super.getCapability(cap, side);
    }

    @Override
    public void func_230337_a_(@Nonnull BlockState blockState, CompoundNBT tag) {
        capacity = tag.getFloat("capacity");
        activated = tag.getBoolean("activated");
        super.func_230337_a_(blockState, tag);
    }

    @Override
    @Nonnull
    public CompoundNBT write(CompoundNBT tag) {
        tag.putFloat("capacity", capacity);
        tag.putBoolean("activated", activated);
        return super.write(tag);
    }

    @Nullable
    @Override
    public SUpdateTileEntityPacket getUpdatePacket() {
        return new SUpdateTileEntityPacket(getPos(), getType().hashCode(), getUpdateTag());
    }

    @Nonnull
    @Override
    public CompoundNBT getUpdateTag() {
        return write(new CompoundNBT());
    }

    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
        super.onDataPacket(net, pkt);
        func_230337_a_(getBlockState(), pkt.getNbtCompound());
    }

    @Override
    public void validate() {
        super.validate();

        if (world != null && !world.isRemote) {
            AqueductHandler.getInstance(world).register(pos);
        }
    }

    @Override
    public void remove() {
        if (world != null && !world.isRemote) {
            AqueductHandler.getInstance(world).remove(pos);
        }

        super.remove();
    }

    public float getCapacity() {
        return capacity;
    }

    public int getFilled() {
        return filled;
    }

    public int getFullCapacity() {
        return fullCapacity;
    }

    void setSource(@Nonnull Direction direction, boolean isSource) {
        sources.replace(direction, isSource);
    }

    void changedState() {
        assert world != null;
        activated = !activated;
    }

    private static void boneMealEffect(@Nonnull BlockPos pos, @Nonnull ServerWorld world) {
        int r = Config.aqueductEffectRange;
        BlockPos cropPos = pos.up().north(world.rand.nextInt(r * 2 + 1) - r).east(world.rand.nextInt(r * 2 + 1) - r);
        BlockState blockstate = world.getBlockState(cropPos);

        if (blockstate.getBlock() instanceof IGrowable) {
            IGrowable igrowable = (IGrowable)blockstate.getBlock();

            if (igrowable.canGrow(world, cropPos, blockstate, world.isRemote) && igrowable.canUseBonemeal(world, world.rand, cropPos, blockstate)) {
                igrowable.grow(world, world.rand, cropPos, blockstate);
                world.playEvent(2005, cropPos, 0);

                for(int i = 0; i < 5; ++i) {
                    double d0 = world.rand.nextGaussian() * 0.02D;
                    double d1 = world.rand.nextGaussian() * 0.02D;
                    double d2 = world.rand.nextGaussian() * 0.02D;
                    world.addParticle(ParticleTypes.HAPPY_VILLAGER,
                            cropPos.getX() + world.rand.nextFloat(),
                            cropPos.getY() + world.rand.nextFloat() * blockstate.getShape(world, cropPos).getEnd(Direction.Axis.Y),
                            cropPos.getZ() + world.rand.nextFloat(),
                            d0, d1, d2);
                }
            }
        }
    }
}
