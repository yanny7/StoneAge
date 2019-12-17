package com.yanny.age.stone.items;

import com.yanny.age.stone.subscribers.ContainerSubscriber;
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
    private final IItemHandler inventory;
    private final ItemStack backpack;
    private final NonNullList<ItemStack> backpackItems;

    public BackpakcContainer(int windowId, PlayerInventory inv, @SuppressWarnings("unused") PacketBuffer extraData) {
        this(windowId, inv, inv.getCurrentItem());
    }

    BackpakcContainer(int id, PlayerInventory inventory, ItemStack current) {
        super(ContainerSubscriber.backpack, id);
        this.inventory = new InvWrapper(inventory);

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

        layoutPlayerInventorySlots(8, 84);
    }

    @Override
    public boolean canInteractWith(@Nonnull PlayerEntity playerIn) {
        return true;
    }

    @Nonnull
    @Override
    public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
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

    private int addSlotRange(IItemHandler handler, int index, int x, int y, int amount, int dx) {
        for (int i = 0 ; i < amount ; i++) {
            addSlot(new LimitedSlotItemHandler(handler, index, x, y));
            x += dx;
            index++;
        }

        return index;
    }

    @SuppressWarnings("SameParameterValue")
    private void addSlotBox(IItemHandler handler, int index, int x, int y, int horAmount, int dx, int verAmount, int dy) {
        for (int j = 0 ; j < verAmount ; j++) {
            index = addSlotRange(handler, index, x, y, horAmount, dx);
            y += dy;
        }
    }

    @SuppressWarnings("SameParameterValue")
    private void layoutPlayerInventorySlots(int leftCol, int topRow) {
        addSlotBox(inventory, 9, leftCol, topRow, 9, 18, 3, 18);

        // Hotbar offset
        topRow += 58;
        addSlotRange(inventory, 0, leftCol, topRow, 9, 18);
    }

    class LimitedSlotItemHandler extends SlotItemHandler {
        public LimitedSlotItemHandler(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
            super(itemHandler, index, xPosition, yPosition);
        }

        @Override
        public boolean canTakeStack(PlayerEntity playerIn) {
            return !getStack().isItemEqual(backpack);
        }
    }
}
