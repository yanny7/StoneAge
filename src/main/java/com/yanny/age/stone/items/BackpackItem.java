package com.yanny.age.stone.items;

import com.yanny.ages.api.group.ModItemGroup;
import com.yanny.ages.api.utils.ItemStackUtils;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.*;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Objects;

public class BackpackItem extends Item implements INamedContainerProvider {
    public static final int INVENTORY_WIDTH = 5;
    public static final int INVENTORY_HEIGHT = 3;
    public static final int ITEMS = INVENTORY_WIDTH * INVENTORY_HEIGHT;

    public BackpackItem() {
        super(new Properties().group(ModItemGroup.AGES).maxStackSize(1));
    }

    @Nonnull
    public static NonNullList<ItemStack> getBackpackItems(@Nonnull ItemStack backpack) {
        NonNullList<ItemStack> list = NonNullList.withSize(ITEMS, ItemStack.EMPTY);

        if (backpack.getChildTag("backpack") != null) {
            ItemStackUtils.deserializeStacks(backpack.getOrCreateChildTag("backpack").getCompound("inv"), list);
        } else {
            saveBackpackItems(backpack, list);
        }

        return list;
    }

    public static void saveBackpackItems(@Nonnull ItemStack backpack, NonNullList<ItemStack> list) {
        backpack.getOrCreateChildTag("backpack").put("inv", ItemStackUtils.serializeStacks(list));
    }

    @Nonnull
    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, @Nonnull Hand handIn) {
        ItemStack stack = playerIn.getHeldItem(handIn);

        getBackpackItems(stack); // create backpack

        if (!worldIn.isRemote && playerIn instanceof ServerPlayerEntity) {
            playerIn.openContainer(this);
            return ActionResult.func_226248_a_(stack);
        }

        return super.onItemRightClick(worldIn, playerIn, handIn);
    }

    @Nonnull
    @Override
    public ActionResultType onItemUse(ItemUseContext context) {
        ItemStack stack = context.getItem();

        getBackpackItems(stack); // create backpack

        if (!context.getWorld().isRemote && context.getPlayer() instanceof ServerPlayerEntity) {
            context.getPlayer().openContainer(this);
        }

        return ActionResultType.SUCCESS;
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundNBT nbts) {
        NonNullList<ItemStack> list = getBackpackItems(stack);

        return new ICapabilityProvider() {
            @Nonnull
            @Override
            public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
                if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
                    return  LazyOptional.of(() -> new ItemStackHandler(list) {
                        @Override
                        protected void onContentsChanged(int slot) {
                            super.onContentsChanged(slot);
                            saveBackpackItems(stack, list);
                        }
                    }).cast();
                }

                return LazyOptional.empty();
            }
        };
    }

    @Nonnull
    @Override
    public ITextComponent getDisplayName() {
        return new StringTextComponent(Objects.requireNonNull(getRegistryName()).getPath());
    }

    @Nullable
    @Override
    public Container createMenu(int id, @Nonnull PlayerInventory inventory, @Nonnull PlayerEntity entity) {
        return new BackpakcContainer(id, inventory, inventory.getCurrentItem());
    }
}
