package com.yanny.age.zero.blocks;

import com.yanny.age.zero.recipes.TreeStumpRecipe;
import com.yanny.age.zero.subscribers.TileEntitySubscriber;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.RecipeWrapper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class TreeStumpTileEntity extends TileEntity implements IInventoryInterface {
    private final NonNullList<ItemStack> stacks = NonNullList.withSize(1, ItemStack.EMPTY);
    private final IItemHandlerModifiable nonSidedItemHandler = createNonSidedInventoryHandler(stacks);
    private final LazyOptional<IItemHandlerModifiable> sidedInventoryHandler = LazyOptional.of(() -> createSidedInventoryHandler(stacks));
    private final LazyOptional<IItemHandlerModifiable> nonSidedInventoryHandler = LazyOptional.of(() -> nonSidedItemHandler);
    private final RecipeWrapper inventoryWrapper = new RecipeWrapper(nonSidedItemHandler);
    private final IItemHandlerModifiable tmpItemHandler = new ItemStackHandler(1);
    private final RecipeWrapper tmpItemHandlerWrapper = new RecipeWrapper(tmpItemHandler);

    private int chopLeft = 0;
    private ItemStack recipeResult = ItemStack.EMPTY;
    private List<Ingredient> tools = new ArrayList<>();

    public TreeStumpTileEntity() {
        //noinspection ConstantConditions
        super(TileEntitySubscriber.tree_stump);
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
        chopLeft = tag.getInt("chopLeft");
        recipeResult = ItemStack.read(tag);
        CompoundNBT toolTag = tag.getCompound("tool");
        ItemStackUtils.deserializeIngredients(toolTag, tools);
        super.read(tag);
    }

    @Override
    @Nonnull
    public CompoundNBT write(CompoundNBT tag) {
        tag.put("inv", ItemStackUtils.serializeStacks(stacks));
        tag.putInt("chopLeft", chopLeft);
        recipeResult.write(tag);
        tag.put("tool", ItemStackUtils.serializeIngredients(tools));
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

    void onBlockRightClicked(PlayerEntity player) {
        assert world != null;

        if (hasTool(player.getHeldItemMainhand())) {
            chopLeft--;

            if (chopLeft == 0) {
                NonNullList<ItemStack> itemStacks = NonNullList.create();
                itemStacks.add(recipeResult);
                stacks.set(0, ItemStack.EMPTY);
                InventoryHelper.dropItems(world, getPos(), itemStacks);
                recipeResult = ItemStack.EMPTY;
                tools.clear();
                player.getHeldItemMainhand().damageItem(1, player, playerEntity -> playerEntity.sendBreakAnimation(EquipmentSlotType.MAINHAND));

                world.playSound(null, getPos(), SoundEvents.BLOCK_WOOD_BREAK, SoundCategory.BLOCKS, 1.0f, 1.0f);
                world.notifyBlockUpdate(getPos(), getBlockState(), getBlockState(), 3);
            } else {
                world.playSound(null, getPos(), SoundEvents.BLOCK_WOOD_HIT, SoundCategory.BLOCKS, 1.0f, 1.0f);
            }
        } else {
            world.playSound(null, getPos(), SoundEvents.ITEM_SHIELD_BLOCK, SoundCategory.BLOCKS, 1.0f, 1.0f);
        }
    }

    void blockActivated(PlayerEntity player) {
        assert world != null;
        ItemStack itemStack = player.getHeldItemMainhand();
        TreeStumpRecipe recipe = getRecipe(itemStack);

        if (stacks.get(0).isEmpty() && recipe != null) {
            stacks.set(0, new ItemStack(itemStack.getItem(), 1));
            chopLeft = recipe.getChopTimes();
            recipeResult = recipe.getCraftingResult(null);
            tools.addAll(recipe.getTools());

            if (itemStack.getCount() > 1) {
                itemStack.setCount(itemStack.getCount() - 1);
            } else {
                player.setHeldItem(Hand.MAIN_HAND, ItemStack.EMPTY);
            }

            world.notifyBlockUpdate(getPos(), getBlockState(), getBlockState(), 3);
            return;
        }

        if (itemStack.isEmpty() && !stacks.get(0).isEmpty()) {
            NonNullList<ItemStack> itemStacks = NonNullList.create();
            itemStacks.add(stacks.get(0).copy());
            stacks.set(0, ItemStack.EMPTY);
            InventoryHelper.dropItems(world, getPos(), itemStacks);
            recipeResult = ItemStack.EMPTY;
            tools.clear();

            world.notifyBlockUpdate(getPos(), getBlockState(), getBlockState(), 3);
            world.playSound(null, getPos(), SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.BLOCKS, 1.0f, 1.0f);
        }
    }

    boolean hasTool(ItemStack toolInHand) {
        return tools.stream().anyMatch(ingredient -> ingredient.test(toolInHand));
    }

    @Nullable
    private TreeStumpRecipe getRecipe(ItemStack item) {
        assert world != null;
        tmpItemHandler.setStackInSlot(0, item);
        return world.getRecipeManager().getRecipe(TreeStumpRecipe.tree_stump, tmpItemHandlerWrapper, world).orElse(null);
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
                return ItemStack.EMPTY;
            }

            @Nonnull
            @Override
            public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
                if (getStackInSlot(slot).isEmpty() && world != null) {
                    TreeStumpRecipe recipe = getRecipe(stack);

                    if (recipe != null) {
                        return super.insertItem(slot, stack, simulate);
                    }
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
}
