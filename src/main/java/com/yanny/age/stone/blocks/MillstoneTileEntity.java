package com.yanny.age.stone.blocks;

import com.yanny.age.stone.recipes.MillstoneRecipe;
import com.yanny.age.stone.subscribers.TileEntitySubscriber;
import com.yanny.ages.api.utils.ItemStackUtils;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
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

public class MillstoneTileEntity extends TileEntity implements IInventoryInterface, ITickableTileEntity, INamedContainerProvider {
    static final int ITEMS = 3;
    private static final double PI2 = Math.PI * 2;

    private final NonNullList<ItemStack> stacks = NonNullList.withSize(ITEMS, ItemStack.EMPTY);
    private final IItemHandlerModifiable nonSidedItemHandler = createNonSidedInventoryHandler(stacks);
    private final LazyOptional<IItemHandlerModifiable> sidedInventoryHandler = LazyOptional.of(() -> createSidedInventoryHandler(stacks));
    private final LazyOptional<IItemHandlerModifiable> nonSidedInventoryHandler = LazyOptional.of(() -> nonSidedItemHandler);
    private final RecipeWrapper inventoryWrapper = new RecipeWrapper(nonSidedItemHandler);
    private final IItemHandlerModifiable tmpItemHandler = new ItemStackHandler(1);
    private final RecipeWrapper tmpItemHandlerWrapper = new RecipeWrapper(tmpItemHandler);
    private final IIntArray data = getData();

    private float rotation = 0f;
    private boolean active = false;
    private int activateTicks = 0;
    private int ticks = 0;
    private double secondChance = 0.0D;
    private ItemStack result = ItemStack.EMPTY;
    private ItemStack secondResult = ItemStack.EMPTY;

    public MillstoneTileEntity() {
        //noinspection ConstantConditions
        super(TileEntitySubscriber.millstone);
    }

    @Override
    public void tick() {
        assert world != null;

        if (active) {
            if (world.rand.nextInt(5) == 0) {
                double d0 = pos.getX() + world.rand.nextFloat() / 2 + 0.25;
                double d1 = pos.getY() + 7 / 16f + 0.025D;
                double d2 = pos.getZ() + world.rand.nextFloat() / 2 + 0.25;
                world.addParticle(ParticleTypes.CRIT, d0, d1, d2, 0, world.rand.nextFloat(), 0);
            }

            rotation += PI2 / 80;
            rotation = (float) (rotation % PI2);
            ticks++;

            if (ticks % 20 == 0) {
                active = false;

                if (!world.isRemote && (ticks == activateTicks)) {
                    if (stacks.get(1).isEmpty()) {
                        stacks.set(1, result);
                    } else {
                        stacks.get(1).grow(result.getCount());
                    }

                    if (world.rand.nextDouble() < secondChance) {
                        if (stacks.get(2).isEmpty()) {
                            stacks.set(2, secondResult);
                        } else {
                            stacks.get(2).grow(secondResult.getCount());
                        }
                    }

                    ticks = 0;
                    result = ItemStack.EMPTY;
                    world.notifyBlockUpdate(getPos(), getBlockState(), getBlockState(), 3);
                }
            }
        }
    }

    @Nullable
    @Override
    public Container createMenu(int id, @Nonnull PlayerInventory inventory, @Nonnull PlayerEntity entity) {
        assert world != null;
        return new MillstoneContainer(id, pos, world, inventory, entity, data);
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
    public void read(@Nonnull BlockState blockState, CompoundNBT tag) {
        CompoundNBT invTag = tag.getCompound("inv");
        ItemStackUtils.deserializeStacks(invTag, stacks);
        active = tag.getBoolean("active");
        rotation = tag.getFloat("rotation");
        ticks = tag.getInt("ticks");
        result = ItemStack.read(tag.getCompound("result"));
        secondResult = ItemStack.read(tag.getCompound("secondResult"));
        secondChance = tag.getDouble("secondChance");
        activateTicks = tag.getInt("activateTicks");
        super.read(blockState, tag);
    }

    @Override
    @Nonnull
    public CompoundNBT write(CompoundNBT tag) {
        tag.put("inv", ItemStackUtils.serializeStacks(stacks));
        tag.putBoolean("active", active);
        tag.putFloat("rotation", rotation);
        tag.putInt("ticks", ticks);
        tag.put("result", result.write(new CompoundNBT()));
        tag.put("secondResult", secondResult.write(new CompoundNBT()));
        tag.putDouble("secondChance", secondChance);
        tag.putInt("activateTicks", activateTicks);
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
        read(getBlockState(), pkt.getNbtCompound());
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

    @Nonnull
    ItemStack getResult() {
        return result;
    }

    int getCraftingProgress() {
        return Math.round(ticks / (float) activateTicks * 100);
    }

    boolean isItemValid(@Nonnull ItemStack itemStack) {
        return getRecipe(itemStack).isPresent();
    }

    @Nonnull
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

    @Nonnull
    private IItemHandlerModifiable createSidedInventoryHandler(@Nonnull NonNullList<ItemStack> stacks) {
        return new ItemStackHandler(stacks) {
            @Nonnull
            @Override
            public ItemStack extractItem(int slot, int amount, boolean simulate) {
                if (slot > 0) {
                    return super.extractItem(slot, amount, simulate);
                }

                return ItemStack.EMPTY;
            }

            @Nonnull
            @Override
            public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
                if (slot == 0 && MillstoneTileEntity.this.isItemValid(stack)) {
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

    public void onActivated() {
        assert world != null;

        if (!active) {
            ItemStack inputStack = stacks.get(0);

            if (result.isEmpty() && !inputStack.isEmpty()) {
                getRecipe(inputStack).ifPresent(millstoneRecipe -> {
                    if (canCraft(millstoneRecipe)) {
                        inputStack.shrink(millstoneRecipe.getIngredients().get(0).getMatchingStacks()[0].getCount());
                        result = millstoneRecipe.getCraftingResult(null);
                        secondResult = millstoneRecipe.getCraftingSecondResult();
                        secondChance = millstoneRecipe.getSecondChance();
                        active = true;
                        activateTicks = millstoneRecipe.getActivateCount() * 20;
                        ticks = 0;

                        world.playSound(null, getPos(), SoundEvents.BLOCK_GRINDSTONE_USE, SoundCategory.BLOCKS, 0.5f, 1.0f);
                        world.notifyBlockUpdate(getPos(), getBlockState(), getBlockState(), 3);
                    }
                });
            } else if (!result.isEmpty() || !secondResult.isEmpty()) {
                active = true;

                world.playSound(null, getPos(), SoundEvents.BLOCK_GRINDSTONE_USE, SoundCategory.BLOCKS, 0.5f, 1.0f);
                world.notifyBlockUpdate(getPos(), getBlockState(), getBlockState(), 3);
            }
        }
    }

    @Nonnull
    private Optional<MillstoneRecipe> getRecipe(@Nonnull ItemStack item) {
        assert world != null;
        tmpItemHandler.setStackInSlot(0, item);
        return world.getRecipeManager().getRecipe(MillstoneRecipe.millstone, tmpItemHandlerWrapper, world);
    }

    @Nonnull
    private IIntArray getData() {
        return new IIntArray() {
            @Override
            public int get(int index) {
                return Math.round(ticks / (float) activateTicks * 100);
            }

            @Override
            public void set(int index, int value) {

            }

            @Override
            public int size() {
                return 1;
            }
        };
    }

    public boolean canCraft(@Nonnull MillstoneRecipe recipe) {
        ItemStack inputStack = stacks.get(0);
        ItemStack outputStack = stacks.get(1);
        ItemStack outputSecondStack = stacks.get(2);
        ItemStack recipeInput = recipe.getIngredients().get(0).getMatchingStacks()[0];
        ItemStack recipeOutput = recipe.getRecipeOutput();
        ItemStack recipeSecondOutput = recipe.getRecipeSecondOutput();
        boolean emptyOutput = outputStack.isEmpty();
        boolean emptyRecipe = recipeOutput.isEmpty();
        boolean emptySecondOutput = outputSecondStack.isEmpty();
        boolean emptySecondRecipe = recipeSecondOutput.isEmpty();
        int inputCount = inputStack.getCount();
        int inputRecipeCount = recipeInput.getCount();
        int outputCount = outputStack.getCount();
        int outputMaxCount = outputStack.getMaxStackSize();
        int outputSecondCount = outputSecondStack.getCount();
        int outputSecondMaxCount = outputSecondStack.getMaxStackSize();
        int outputRecipeCount = recipeOutput.getCount();
        int outputRecipeSecondCount = recipeSecondOutput.getCount();

        if (inputCount >= inputRecipeCount) {
            return (emptyOutput || emptyRecipe || (outputStack.isItemEqual(recipeOutput) && outputCount <= outputMaxCount - outputRecipeCount)) &&
                    (emptySecondOutput || emptySecondRecipe || (outputSecondStack.isItemEqual(recipeSecondOutput) && outputSecondCount <= outputSecondMaxCount - outputRecipeSecondCount));
        }

        return false;
    }
}
