package com.yanny.age.stone.blocks;

import com.google.common.collect.Maps;
import com.yanny.age.stone.config.Config;
import com.yanny.age.stone.handlers.AquaductHandler;
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
import net.minecraft.world.World;
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

public class AquaductTileEntity extends TileEntity implements ITickableTileEntity {
    private static final FluidStack WATER = new FluidStack(Fluids.WATER, 0);

    private float capacity = 0f;
    private boolean activated = false;
    private Map<Direction, Boolean> sources = Maps.newHashMap();

    private int tick = 0;
    private int tmpTick = 0;
    private int level = 0;
    private boolean initialized = false;

    public AquaductTileEntity() {
        //noinspection ConstantConditions
        super(TileEntitySubscriber.aquaduct);
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
                setSource(direction, AquaductBlock.isWater(world.getBlockState(pos).getBlock(), world.getFluidState(pos)));
            });
        }

        if (world.isRemote) {
            tmpTick++;
            if (tmpTick % 2 == 0) {
                tick++;
            }
        } else {
            LazyOptional<FluidTank> fluidTank = AquaductHandler.getInstance(world).getCapability(pos);
            fluidTank.ifPresent(tank -> {
                int oldLevel = level;
                level = Math.round((float)tank.getFluidAmount() / tank.getCapacity() * 20);

                if (oldLevel != level) {
                    capacity = level / 20f;
                    world.notifyBlockUpdate(pos, getBlockState(), getBlockState(), 3);
                }

                if (sources.containsValue(true)) {
                    WATER.setAmount(4);
                    tank.fill(WATER, IFluidHandler.FluidAction.EXECUTE);
                } else {
                    if (activated) {
                        FluidStack fluidStack = tank.drain(1, IFluidHandler.FluidAction.EXECUTE);

                        if (fluidStack.isEmpty()) {
                            if (world.getBlockState(pos).get(WATERLOGGED)) {
                                world.setBlockState(pos, world.getBlockState(pos).with(WATERLOGGED, false));
                            }
                        } else {
                            if (!world.getBlockState(pos).get(WATERLOGGED)) {
                                world.setBlockState(pos, world.getBlockState(pos).with(WATERLOGGED, true));
                            }

                            if (world.rand.nextInt(Config.aquaductTickChanceBoneMealEffect) == 0) {
                                boneMealEffect(pos, world);
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
            return AquaductHandler.getInstance(world).getCapability(pos);
        }

        return super.getCapability(cap, side);
    }

    @Override
    public void read(CompoundNBT tag) {
        capacity = tag.getFloat("capacity");
        activated = tag.getBoolean("activated");
        super.read(tag);
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
        read(pkt.getNbtCompound());
    }

    @Override
    public void validate() {
        super.validate();

        if (world != null && !world.isRemote) {
            AquaductHandler.getInstance(world).register(pos);
        }
    }

    @Override
    public void remove() {
        if (world != null && !world.isRemote) {
            AquaductHandler.getInstance(world).remove(pos);
        }

        super.remove();
    }

    public float getCapacity() {
        return capacity;
    }

    public int getTick() {
        return tick;
    }

    void setSource(Direction direction, boolean isSource) {
        sources.replace(direction, isSource);
    }

    void changedState() {
        assert world != null;
        activated = !activated;
    }

    private static void boneMealEffect(BlockPos pos, World world) {
        BlockPos cropPos = pos.up().north(world.rand.nextInt(9) - 4).east(world.rand.nextInt(9) - 4);
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
