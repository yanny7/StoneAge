package com.yanny.age.stone.blocks;

import com.yanny.age.stone.recipes.FlintWorkbenchRecipe;
import com.yanny.age.stone.subscribers.TileEntitySubscriber;
import com.yanny.age.stone.subscribers.ToolSubscriber;
import com.yanny.age.stone.utils.ItemStackUtils;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
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
import java.util.Optional;

public class FlintWorkbenchTileEntity extends TileEntity implements IInventoryInterface {
    private final NonNullList<ItemStack> stacks = NonNullList.withSize(9, ItemStack.EMPTY);
    private final IItemHandlerModifiable nonSidedItemHandler = createNonSidedInventoryHandler(stacks);
    private final LazyOptional<IItemHandlerModifiable> nonSidedInventoryHandler = LazyOptional.of(() -> nonSidedItemHandler);
    private final RecipeWrapper inventoryWrapper = new RecipeWrapper(nonSidedItemHandler);
    private ItemStack recipeOutput = ItemStack.EMPTY;

    public FlintWorkbenchTileEntity() {
        //noinspection ConstantConditions
        super(TileEntitySubscriber.flint_workbench);
    }

    @Override
    public void read(CompoundNBT tag) {
        CompoundNBT invTag = tag.getCompound("inv");
        CompoundNBT outTag = tag.getCompound("output");
        ItemStackUtils.deserializeStacks(invTag, stacks);
        recipeOutput = ItemStack.read(outTag);
        super.read(tag);
    }

    @Override
    @Nonnull
    public CompoundNBT write(CompoundNBT tag) {
        tag.put("inv", ItemStackUtils.serializeStacks(stacks));
        CompoundNBT outTag = new CompoundNBT();
        recipeOutput.write(outTag);
        tag.put("output", outTag);
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

    @Nonnull
    public NonNullList<ItemStack> getStacks() {
        return stacks;
    }

    public ItemStack getRecipeOutput() {
        return recipeOutput;
    }

    boolean blockActivated(PlayerEntity player, BlockRayTraceResult hit) {
        assert world != null;
        ItemStack itemStack = player.getHeldItemMainhand();

        //noinspection ConstantConditions
        if (itemStack.getItem().equals(ToolSubscriber.flint_knife)) {
            findMatchingRecipe().ifPresent(flintWorkbenchRecipe -> {
                ItemStack result = flintWorkbenchRecipe.getCraftingResult(getInventory());
                NonNullList<ItemStack> itemStacks = NonNullList.create();
                itemStacks.add(result);
                InventoryHelper.dropItems(world, getPos(), itemStacks);

                for (int i = 0; i < stacks.size(); i++) {
                    stacks.set(i, ItemStack.EMPTY);
                }

                recipeOutput = ItemStack.EMPTY;
                itemStack.damageItem(1, player, playerEntity -> playerEntity.sendBreakAnimation(EquipmentSlotType.MAINHAND));
                world.playSound(null, getPos(), SoundEvents.BLOCK_DISPENSER_DISPENSE, SoundCategory.BLOCKS, 1.0f, 1.0f);
            });
        } else {
            if (hit.getFace() == Direction.UP) {
                Direction dir = getBlockState().get(HorizontalBlock.HORIZONTAL_FACING);
                int x = 0;
                int y = 0;

                // handle rotation
                switch (dir) {
                    case NORTH:
                        x = (int) Math.floor(FlintWorkbenchRecipe.MAX_WIDTH - (hit.getHitVec().x - hit.getPos().getX()) * FlintWorkbenchRecipe.MAX_WIDTH);
                        y = (int) Math.floor(FlintWorkbenchRecipe.MAX_HEIGHT - (hit.getHitVec().z - hit.getPos().getZ()) * FlintWorkbenchRecipe.MAX_HEIGHT);
                        break;
                    case SOUTH:
                        x = (int) Math.floor((hit.getHitVec().x - hit.getPos().getX()) * FlintWorkbenchRecipe.MAX_WIDTH);
                        y = (int) Math.floor((hit.getHitVec().z - hit.getPos().getZ()) * FlintWorkbenchRecipe.MAX_HEIGHT);
                        break;
                    case EAST:
                        x = (int) Math.floor(FlintWorkbenchRecipe.MAX_HEIGHT - (hit.getHitVec().z - hit.getPos().getZ()) * FlintWorkbenchRecipe.MAX_HEIGHT);
                        y = (int) Math.floor((hit.getHitVec().x - hit.getPos().getX()) * FlintWorkbenchRecipe.MAX_WIDTH);
                        break;
                    case WEST:
                        x = (int) Math.floor((hit.getHitVec().z - hit.getPos().getZ()) * FlintWorkbenchRecipe.MAX_HEIGHT);
                        y = (int) Math.floor(FlintWorkbenchRecipe.MAX_WIDTH - (hit.getHitVec().x - hit.getPos().getX()) * FlintWorkbenchRecipe.MAX_WIDTH);
                        break;
                }

                ItemStack stack = stacks.get(y * FlintWorkbenchRecipe.MAX_WIDTH + x);

                if (!itemStack.isEmpty() && stack.isEmpty()) {
                    stacks.set(y * FlintWorkbenchRecipe.MAX_WIDTH + x, itemStack.split(1));
                    Optional<FlintWorkbenchRecipe> recipe = findMatchingRecipe();

                    if (recipe.isPresent()) {
                        recipe.ifPresent(flintWorkbenchRecipe -> recipeOutput = flintWorkbenchRecipe.getRecipeOutput().copy());
                    } else {
                        recipeOutput = ItemStack.EMPTY;
                    }

                    return true;
                }

                if (itemStack.isEmpty() && !stacks.get(y * FlintWorkbenchRecipe.MAX_WIDTH + x).isEmpty()) {
                    NonNullList<ItemStack> itemStacks = NonNullList.create();
                    itemStacks.add(stack);
                    InventoryHelper.dropItems(world, getPos(), itemStacks);
                    stacks.set(y * FlintWorkbenchRecipe.MAX_WIDTH + x, ItemStack.EMPTY);
                    world.playSound(null, getPos(), SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.BLOCKS, 1.0f, 1.0f);
                    Optional<FlintWorkbenchRecipe> recipe = findMatchingRecipe();

                    if (recipe.isPresent()) {
                        recipe.ifPresent(flintWorkbenchRecipe -> recipeOutput = flintWorkbenchRecipe.getRecipeOutput().copy());
                    } else {
                        recipeOutput = ItemStack.EMPTY;
                    }

                    return true;
                }
            }
        }

        return false;
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

    private Optional<FlintWorkbenchRecipe> findMatchingRecipe() {
        assert this.world != null;
        return stacks.stream().allMatch(ItemStack::isEmpty) ? Optional.empty()
                : this.world.getRecipeManager().getRecipe(FlintWorkbenchRecipe.flint_workbench, inventoryWrapper, this.world);
    }
}
