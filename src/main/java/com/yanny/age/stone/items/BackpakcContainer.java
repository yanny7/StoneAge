package com.yanny.age.stone.items;

import com.yanny.age.stone.subscribers.ContainerSubscriber;
import com.yanny.age.stone.utils.ContainerUtils;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.NonNullList;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;

import javax.annotation.Nonnull;

import static com.yanny.age.stone.items.BackpackItem.*;

public class BackpakcContainer extends Container {
    private final ItemStack backpack;
    private final NonNullList<ItemStack> backpackItems;

    public BackpakcContainer(int windowId, @Nonnull PlayerInventory inv, @SuppressWarnings("unused") @Nonnull PacketBuffer extraData) {
        this(windowId, inv, inv.getCurrentItem());
    }

    BackpakcContainer(int id, @Nonnull PlayerInventory inventory, @Nonnull ItemStack current) {
        super(ContainerSubscriber.backpack, id);
        IItemHandler inv = new InvWrapper(inventory);

        backpack = current;
        backpackItems = BackpackItem.getBackpackItems(backpack);

        ItemStackHandler handler = new ItemStackHandler(backpackItems) {
            @Override
            protected void onContentsChanged(int slot) {
                super.onContentsChanged(slot);
                BackpackItem.saveBackpackItems(backpack, backpackItems);
            }

            @Override
            public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
                return !(stack.isItemEqual(backpack));
            }
        };

        for (int y = 0; y < INVENTORY_HEIGHT; y++) {
            for (int x = 0; x < INVENTORY_WIDTH; x++) {
                addSlot(new SlotItemHandler(handler, x + y * INVENTORY_WIDTH, 44 + x * 18, 17 + y * 18));
            }
        }

        ContainerUtils.layoutPlayerInventorySlots((slot, x, y) -> addSlot(new LimitedSlotItemHandler(inv, slot, x, y)), 8, 84);
    }

    @Override
    public boolean canInteractWith(@Nonnull PlayerEntity playerIn) {
        return true;
    }

    @Nonnull
    @Override
    public ItemStack transferStackInSlot(@Nonnull PlayerEntity playerIn, int index) {
        Slot slot = inventorySlots.get(index);

        if (slot != null && slot.getHasStack()) {
            ItemStack stack = slot.getStack();
            ItemStack itemstack = stack.copy();

            if (stack == backpack) {
                return ItemStack.EMPTY;
            }

            if (index < ITEMS) {
                if (!mergeItemStack(stack, ITEMS + 1, ITEMS + 36, true)) {
                    return ItemStack.EMPTY;
                }

                slot.onSlotChange(stack, itemstack);
            } else {
                if (!mergeItemStack(stack, 0, ITEMS, false)) {
                    return ItemStack.EMPTY;
                }
            }

            if (stack.isEmpty()) {
                slot.putStack(ItemStack.EMPTY);
            } else {
                slot.onSlotChanged();
            }

            if (stack.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(playerIn, stack);
        }

        BackpackItem.saveBackpackItems(backpack, backpackItems);
        return ItemStack.EMPTY;
    }

    class LimitedSlotItemHandler extends SlotItemHandler {
        public LimitedSlotItemHandler(@Nonnull IItemHandler itemHandler, int index, int xPosition, int yPosition) {
            super(itemHandler, index, xPosition, yPosition);
        }

        @Override
        public boolean canTakeStack(PlayerEntity playerIn) {
            return !getStack().isItemEqual(backpack);
        }
    }
}
