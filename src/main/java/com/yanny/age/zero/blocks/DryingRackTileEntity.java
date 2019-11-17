package com.yanny.age.zero.blocks;

import com.yanny.age.zero.recipes.DryingRackRecipe;
import com.yanny.age.zero.subscribers.TileEntitySubscriber;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.RecipeWrapper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class DryingRackTileEntity extends TileEntity implements IInventoryInterface, ITickableTileEntity {
    public static final int ITEMS = 4;

    private final NonNullList<ItemStack> stacks = NonNullList.withSize(ITEMS * 2, ItemStack.EMPTY);
    private final IItemHandlerModifiable nonSidedItemHandler = createNonSidedInventoryHandler(stacks);
    private final LazyOptional<IItemHandlerModifiable> sidedInventoryHandler = LazyOptional.of(() -> createSidedInventoryHandler(stacks));
    private final LazyOptional<IItemHandlerModifiable> nonSidedInventoryHandler = LazyOptional.of(() -> nonSidedItemHandler);
    private final RecipeWrapper inventoryWrapper = new RecipeWrapper(nonSidedItemHandler);
    private final IItemHandlerModifiable tmpItemHandler = new ItemStackHandler(1);
    private final RecipeWrapper tmpItemHandlerWrapper = new RecipeWrapper(tmpItemHandler);
    private final DryingItem[] items = new DryingItem[ITEMS];

    public DryingRackTileEntity() {
        //noinspection ConstantConditions
        super(TileEntitySubscriber.drying_rack);
        for (int i = 0; i < ITEMS; i++) {
            items[i] = new DryingItem();
        }
    }

    @Override
    public void tick() {
        assert world != null;

        if (!world.isRemote && world.isDaytime()) {
            for (int i = 0; i < ITEMS; i++) {
                if (items[i].active) {
                    if (items[i].isDried()) {
                        stacks.set(i + ITEMS, items[i].result);
                        stacks.set(i, ItemStack.EMPTY);
                        items[i].reset();
                        world.notifyBlockUpdate(getPos(), getBlockState(), getBlockState(), 3);
                    } else {
                        items[i].remaining--;
                    }
                }
            }
        }
    }

    @Nonnull
    @Override
    public IInventory getInventory() {
        return inventoryWrapper;
    }

    @Override
    public void read(CompoundNBT tag) {
        CompoundNBT invTag = tag.getCompound("inv");
        ItemStackUtils.deserializeStacks(invTag, stacks);
        for (int i = 0; i < ITEMS; i++) {
            items[i].read(tag.getCompound("items" + i));
        }
        super.read(tag);
    }

    @Override
    @Nonnull
    public CompoundNBT write(CompoundNBT tag) {
        tag.put("inv", ItemStackUtils.serializeStacks(stacks));
        for (int i = 0; i < ITEMS; i++) {
            tag.put("items" + i, items[i].write());
        }
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
            if (side != null) {
                return sidedInventoryHandler.cast();
            } else {
                return nonSidedInventoryHandler.cast();
            }
        }
        return super.getCapability(cap, side);
    }

    @Override
    public void remove() {
        sidedInventoryHandler.invalidate();
        nonSidedInventoryHandler.invalidate();
        super.remove();
    }

    boolean blockActivated(PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        assert world != null;

        if (!player.isSneaking() && !world.isRemote && hit.getFace() != Direction.UP && hit.getFace() != Direction.DOWN && handIn == Hand.MAIN_HAND) {
            Direction direction = getBlockState().get(HorizontalBlock.HORIZONTAL_FACING);
            ItemStack itemStack = player.getHeldItem(handIn);
            DryingRackRecipe recipe = getRecipe(itemStack);

            int pos;
            double x = (hit.getHitVec().x - hit.getPos().getX());
            double z = (hit.getHitVec().z - hit.getPos().getZ());

            switch (direction) {
                case SOUTH:
                    if (x < 0.5) {
                        pos = (hit.getFace() == direction) ? 2 : 0;
                    } else {
                        pos = (hit.getFace() == direction) ? 3 : 1;
                    }
                    break;
                case NORTH:
                    if (x < 0.5) {
                        pos = (hit.getFace() == direction) ? 0 : 2;
                    } else {
                        pos = (hit.getFace() == direction) ? 1 : 3;
                    }
                    break;
                case EAST:
                    if (z < 0.5) {
                        pos = (hit.getFace() == direction) ? 2 : 0;
                    } else {
                        pos = (hit.getFace() == direction) ? 3 : 1;
                    }
                    break;
                case WEST:
                    if (z < 0.5) {
                        pos = (hit.getFace() == direction) ? 0 : 2;
                    } else {
                        pos = (hit.getFace() == direction) ? 1 : 3;
                    }
                    break;
                default:
                    return false;
            }

            if (stacks.get(pos).isEmpty() && stacks.get(pos + ITEMS).isEmpty() && recipe != null) {
                DryingItem item = items[pos];

                stacks.set(pos, new ItemStack(itemStack.getItem(), 1));
                item.setup(true, recipe.getDryingTime(), recipe.getCraftingResult(null));

                if (itemStack.getCount() > 1) {
                    itemStack.setCount(itemStack.getCount() - 1);
                } else {
                    player.setHeldItem(handIn, ItemStack.EMPTY);
                }

                world.notifyBlockUpdate(getPos(), getBlockState(), getBlockState(), 3);
                return true;
            }

            if (itemStack.isEmpty() && !stacks.get(pos + ITEMS).isEmpty()) {
                NonNullList<ItemStack> itemStacks = NonNullList.create();
                itemStacks.add(stacks.get(pos + ITEMS).copy());
                stacks.set(pos + ITEMS, ItemStack.EMPTY);
                stacks.set(pos, ItemStack.EMPTY);
                InventoryHelper.dropItems(world, getPos(), itemStacks);

                world.notifyBlockUpdate(getPos(), getBlockState(), getBlockState(), 3);
                world.playSound(null, getPos(), SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.BLOCKS, 1.0f, 1.0f);
                return true;
            }
        }
        return false;
    }

    @Nullable
    private DryingRackRecipe getRecipe(ItemStack item) {
        assert world != null;
        tmpItemHandler.setStackInSlot(0, item);
        return world.getRecipeManager().getRecipe(DryingRackRecipe.drying_rack, tmpItemHandlerWrapper, world).orElse(null);
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

    private IItemHandlerModifiable createSidedInventoryHandler(@Nonnull NonNullList<ItemStack> stacks) {
        return new ItemStackHandler(stacks) {
            @Nonnull
            @Override
            public ItemStack extractItem(int slot, int amount, boolean simulate) {
                if (slot >= ITEMS) {
                    return super.extractItem(slot, amount, simulate);
                } else {
                    return ItemStack.EMPTY;
                }
            }

            @Nonnull
            @Override
            public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
                if (slot < ITEMS) {
                    if (getStackInSlot(slot).isEmpty() && world != null) {
                        DryingRackRecipe recipe = getRecipe(stack);
                        if (recipe != null) {
                            items[slot].setup(true, recipe.getDryingTime(), recipe.getCraftingResult(null));
                            return super.insertItem(slot, stack, simulate);
                        }
                    }

                    return stack;
                } else {
                    return stack;
                }
            }

            @Override
            protected void onContentsChanged(int slot) {
                assert world != null;
                markDirty();
                world.notifyBlockUpdate(getPos(), getBlockState(), getBlockState(), 3);
            }
        };
    }

    private static class DryingItem {
        boolean active;
        int dryingTime;
        int remaining;
        ItemStack result;

        DryingItem() {
            setup(false, 0, ItemStack.EMPTY);
        }

        boolean isDried() {
            return active && remaining <= 0;
        }

        void setup(boolean active, int dryingTime, ItemStack result) {
            this.active = active;
            this.dryingTime = this.remaining = dryingTime;
            this.result = result;
        }

        void reset() {
            this.active = false;
            this.result = ItemStack.EMPTY;
        }

        CompoundNBT write() {
            CompoundNBT nbt = new CompoundNBT();
            nbt.putBoolean("active", active);
            nbt.putInt("dryingTime", dryingTime);
            nbt.putInt("remaining", remaining);
            CompoundNBT item = new CompoundNBT();
            result.write(item);
            nbt.put("item", item);
            return nbt;
        }

        void read(CompoundNBT nbt) {
            active = nbt.getBoolean("active");
            dryingTime = nbt.getInt("dryingTime");
            remaining = nbt.getInt("remaining");
            result = ItemStack.read(nbt.getCompound("item"));
        }
    }
}
