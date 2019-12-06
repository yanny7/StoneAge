package com.yanny.age.stone.blocks;

import com.yanny.age.stone.recipes.MillstoneRecipe;
import com.yanny.age.stone.subscribers.TileEntitySubscriber;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.RecipeWrapper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

public class MillstoneTileEntity extends TileEntity implements IInventoryInterface, ITickableTileEntity, INamedContainerProvider {
    private static final Direction[] DIRECTIONS = new Direction[]{Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST, Direction.DOWN};

    static final int ITEMS = 1;
    private static final double PI2 = Math.PI * 2;
    private static final int TICKS_TO_FINISH = 100;

    private final NonNullList<ItemStack> stacks = NonNullList.withSize(ITEMS, ItemStack.EMPTY);
    private final IItemHandlerModifiable nonSidedItemHandler = createNonSidedInventoryHandler(stacks);
    private final LazyOptional<IItemHandlerModifiable> sidedInventoryHandler = LazyOptional.of(() -> createSidedInventoryHandler(stacks));
    private final LazyOptional<IItemHandlerModifiable> nonSidedInventoryHandler = LazyOptional.of(() -> nonSidedItemHandler);
    private final RecipeWrapper inventoryWrapper = new RecipeWrapper(nonSidedItemHandler);
    private final IItemHandlerModifiable tmpItemHandler = new ItemStackHandler(1);
    private final RecipeWrapper tmpItemHandlerWrapper = new RecipeWrapper(tmpItemHandler);

    private float rotation = 0f;
    private boolean active = false;
    private int partCnt = 0;
    private ItemStack result = ItemStack.EMPTY;

    public MillstoneTileEntity() {
        //noinspection ConstantConditions
        super(TileEntitySubscriber.millstone);
    }

    @Override
    public void tick() {
        assert world != null;

        if (active) {
            float oldRot = rotation;

            rotation += PI2 / TICKS_TO_FINISH;
            rotation = (float) (rotation % PI2);

            if (partCnt > 0) {
                partCnt--;
            }

            if (rotation < oldRot) {
                active = false;
                partCnt = 0;

                if (!world.isRemote) {
                    AtomicBoolean found = new AtomicBoolean(false);
                    for (Direction direction : DIRECTIONS) {
                        TileEntity entity = world.getTileEntity(pos.offset(direction));
                        if (entity != null) {
                            entity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(handler -> {
                                for (int i = 0; i < handler.getSlots(); i++) {
                                    if (!handler.insertItem(i, result, true).isEmpty()) {
                                        handler.insertItem(i, result, false);
                                        break;
                                    }
                                }
                                found.set(true);
                            });
                            if (found.get()) {
                                break;
                            }
                        }
                    }

                    if (!found.get()) {
                        NonNullList<ItemStack> itemStacks = NonNullList.create();
                        itemStacks.add(result);
                        InventoryHelper.dropItems(world, getPos(), itemStacks);
                    }

                    result = ItemStack.EMPTY;
                    world.notifyBlockUpdate(getPos(), getBlockState(), getBlockState(), 3);
                }
            } else {
                if (partCnt == 0) {
                    active = false;

                    if (!world.isRemote) {
                        world.notifyBlockUpdate(getPos(), getBlockState(), getBlockState(), 3);
                    }
                }
            }
        }
    }

    @Nullable
    @Override
    public Container createMenu(int id, @Nonnull PlayerInventory inventory, @Nonnull PlayerEntity entity) {
        assert world != null;
        return new MillstoneContainer(id, pos, world, inventory, entity);
    }

    @Nonnull
    @Override
    public ITextComponent getDisplayName() {
        assert getType().getRegistryName() != null;
        return new StringTextComponent(getType().getRegistryName().getPath());
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
        active = tag.getBoolean("active");
        rotation = tag.getFloat("rotation");
        partCnt = tag.getInt("partCnt");
        super.read(tag);
    }

    @Override
    @Nonnull
    public CompoundNBT write(CompoundNBT tag) {
        tag.put("inv", ItemStackUtils.serializeStacks(stacks));
        tag.putBoolean("active", active);
        tag.putFloat("rotation", rotation);
        tag.putInt("partCnt", partCnt);
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
                if (side == Direction.UP) {
                    return sidedInventoryHandler.cast();
                }
            } else {
                return nonSidedInventoryHandler.cast();
            }
        }
        return super.getCapability(cap, side);
    }

    @Override
    public void validate() {
        super.validate();
    }

    @Override
    public void remove() {
        sidedInventoryHandler.invalidate();
        nonSidedInventoryHandler.invalidate();
        super.remove();
    }

    boolean isItemValid(ItemStack itemStack) {
        return getRecipe(itemStack).isPresent();
    }

    private IItemHandlerModifiable createNonSidedInventoryHandler(@Nonnull NonNullList<ItemStack> stacks) {
        return new ItemStackHandler(stacks) {

            @Nonnull
            @Override
            public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
                if (MillstoneTileEntity.this.isItemValid(stack)) {
                    return super.insertItem(slot, stack, simulate);
                }

                return stack;
            }

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
                return ItemStack.EMPTY;
            }

            @Nonnull
            @Override
            public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
                if (MillstoneTileEntity.this.isItemValid(stack)) {
                    return super.insertItem(slot, stack, simulate);
                }

                return stack;
            }

            @Override
            protected void onContentsChanged(int slot) {
                assert world != null;
                markDirty();
                world.notifyBlockUpdate(getPos(), getBlockState(), getBlockState(), 3);
            }
        };
    }

    public float rotateAngle() {
        return rotation;
    }

    void onActivated() {
        assert world != null;

        if (!active) {
            if (result.isEmpty() && !stacks.get(0).isEmpty()) {
                Optional<MillstoneRecipe> recipe = getRecipe(stacks.get(0));
                recipe.ifPresent(millstoneRecipe -> {
                    result = millstoneRecipe.getRecipeOutput().copy();
                    stacks.get(0).shrink(1);
                });
                active = true;
                partCnt = TICKS_TO_FINISH / 4;
            } else if (!result.isEmpty()) {
                active = true;
                partCnt = TICKS_TO_FINISH / 4;
            }

            world.notifyBlockUpdate(getPos(), getBlockState(), getBlockState(), 3);
        }
    }

    @Nonnull
    private Optional<MillstoneRecipe> getRecipe(@Nonnull ItemStack item) {
        assert world != null;
        tmpItemHandler.setStackInSlot(0, item);
        return world.getRecipeManager().getRecipe(MillstoneRecipe.millstone, tmpItemHandlerWrapper, world);
    }
}
