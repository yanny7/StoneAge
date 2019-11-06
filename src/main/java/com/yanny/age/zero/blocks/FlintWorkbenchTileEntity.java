package com.yanny.age.zero.blocks;

import com.yanny.age.zero.subscribers.TilesSubscriber;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.RecipeWrapper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class FlintWorkbenchTileEntity extends TileEntity implements IInventoryInterface {
    private final NonNullList<ItemStack> stacks = NonNullList.withSize(9, ItemStack.EMPTY);
    private final IItemHandlerModifiable nonSidedItemHandler = createNonSidedInventoryHandler(stacks);
    private final LazyOptional<IItemHandlerModifiable> nonSidedInventoryHandler = LazyOptional.of(() -> nonSidedItemHandler);
    private final RecipeWrapper inventoryWrapper = new RecipeWrapper(nonSidedItemHandler);

    public FlintWorkbenchTileEntity() {
        //noinspection ConstantConditions
        super(TilesSubscriber.flint_workbench);
    }

    boolean playerClicked(PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if (hit.getFace() == Direction.UP) {
            Direction dir = getBlockState().get(HorizontalBlock.HORIZONTAL_FACING);
            int x = 0;
            int y = 0;

            // handle rotation
            switch (dir) {
                case NORTH:
                    x = (int) Math.floor(3 - (hit.getHitVec().x - hit.getPos().getX()) * 3);
                    y = (int) Math.floor(3 - (hit.getHitVec().z - hit.getPos().getZ()) * 3);
                    break;
                case SOUTH:
                    x = (int) Math.floor((hit.getHitVec().x - hit.getPos().getX()) * 3);
                    y = (int) Math.floor((hit.getHitVec().z - hit.getPos().getZ()) * 3);
                    break;
                case EAST:
                    x = (int) Math.floor((hit.getHitVec().x - hit.getPos().getX()) * 3);
                    y = (int) Math.floor(3 - (hit.getHitVec().z - hit.getPos().getZ()) * 3);
                    break;
                case WEST:
                    x = (int) Math.floor(3 - (hit.getHitVec().x - hit.getPos().getX()) * 3);
                    y = (int) Math.floor((hit.getHitVec().z - hit.getPos().getZ()) * 3);
                    break;
            }

            ItemStack stack = stacks.get(x * 3 + y);

            if (!player.getHeldItem(handIn).isEmpty() && stack.isEmpty()) {
                stacks.set(x * 3 + y, new ItemStack(player.getHeldItem(handIn).getItem(), 1));
            }
            return true;
        }
        return false;
    }

    @Override
    public void read(CompoundNBT tag) {
        CompoundNBT invTag = tag.getCompound("inv");
        ItemStackUtils.deserializeStacks(invTag, stacks);
        super.read(tag);
    }

    @Override
    @Nonnull
    public CompoundNBT write(CompoundNBT tag) {
        tag.put("inv", ItemStackUtils.serializeStacks(stacks));
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
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            if (side == null) {
                return nonSidedInventoryHandler.cast();
            }
        }
        return super.getCapability(cap, side);
    }

    @Override
    public void remove() {
        nonSidedInventoryHandler.invalidate();
        super.remove();
    }

    @Nonnull
    public IInventory getInventory() {
        return inventoryWrapper;
    }

    private IItemHandlerModifiable createNonSidedInventoryHandler(@Nonnull NonNullList<ItemStack> stacks) {
        return new ItemStackHandler(stacks) {
            @Override
            protected void onContentsChanged(int slot) {
                assert world != null;
                markDirty();
                world.notifyBlockUpdate(getPos(), getBlockState(), getBlockState(), 3);
            }
        };
    }
}
