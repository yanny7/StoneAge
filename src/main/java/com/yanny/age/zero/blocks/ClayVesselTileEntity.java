package com.yanny.age.zero.blocks;

import com.yanny.age.zero.config.Config;
import com.yanny.age.zero.subscribers.TileEntitySubscriber;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ClayVesselTileEntity extends TileEntity {
    private static final ItemStack WATER = new ItemStack(Items.WATER_BUCKET);
    private final LazyOptional<FluidTank> fluidHandler = LazyOptional.of(() -> new FluidTank(Config.clayVesselCapacity, fluidStack -> fluidStack.isFluidEqual(WATER)));

    public ClayVesselTileEntity() {
        //noinspection ConstantConditions
        super(TileEntitySubscriber.clay_vessel);
    }

    @Override
    public void read(CompoundNBT tag) {
        fluidHandler.ifPresent(tank -> tank.readFromNBT(tag));
        super.read(tag);
    }

    @Override
    @Nonnull
    public CompoundNBT write(CompoundNBT tag) {
        fluidHandler.ifPresent(tank -> tank.writeToNBT(tag));
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

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
            return fluidHandler.cast();
        }

        return super.getCapability(cap, side);
    }

    @Override
    public void remove() {
        fluidHandler.invalidate();
        super.remove();
    }
}
