package com.yanny.age.zero.blocks;

import com.yanny.age.zero.ExampleMod;
import com.yanny.age.zero.subscribers.BlockSubscriber;
import com.yanny.age.zero.subscribers.ContainerSubscriber;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;

import javax.annotation.Nonnull;

import static com.yanny.age.zero.blocks.StoneChestTileEntity.*;

public class StoneChestContainer extends Container {
    private StoneChestTileEntity tile;
    private PlayerEntity player;
    private IItemHandler inventory;

    public StoneChestContainer(int windowId, PlayerInventory inv, PacketBuffer extraData) {
        this(windowId, extraData.readBlockPos(), ExampleMod.proxy.getClientWorld(), inv, ExampleMod.proxy.getClientPlayer());
    }

    StoneChestContainer(int id, BlockPos pos, World world, PlayerInventory inventory, PlayerEntity player) {
        super(ContainerSubscriber.stone_chest, id);
        tile = (StoneChestTileEntity) world.getTileEntity(pos);
        this.player = player;
        this.inventory = new InvWrapper(inventory);

        if (tile == null) {
            throw new IllegalStateException("TileEntity does not exists!");
        }

        tile.openInventory(player);
        tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(h -> {
            for (int y = 0; y < INVENTORY_HEIGHT; y++) {
                for (int x = 0; x < INVENTORY_WIDTH; x++) {
                    addSlot(new SlotItemHandler(h, x + y * INVENTORY_WIDTH, 62 + x * 18, 17 + y * 18));
                }
            }
        });
        layoutPlayerInventorySlots(8, 84);
    }

    @Override
    public boolean canInteractWith(@Nonnull PlayerEntity playerIn) {
        if (tile == null || tile.getWorld() == null) {
            throw new IllegalStateException("Null pointer");
        }
        return isWithinUsableDistance(IWorldPosCallable.of(tile.getWorld(), tile.getPos()), player, BlockSubscriber.stone_chest);
    }

    @Nonnull
    @Override
    public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
        Slot slot = inventorySlots.get(index);

        if (slot != null && slot.getHasStack()) {
            ItemStack stack = slot.getStack();
            ItemStack itemstack = stack.copy();
            if (index < INVENTORY_WIDTH * INVENTORY_HEIGHT) {
                if (!mergeItemStack(stack, INVENTORY_WIDTH * INVENTORY_HEIGHT + 1, 38, true)) {
                    return ItemStack.EMPTY;
                }
                slot.onSlotChange(stack, itemstack);
            } else {
                if (!mergeItemStack(stack, 0, INVENTORY_WIDTH * INVENTORY_HEIGHT, false)) {
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

        return ItemStack.EMPTY;
    }

    @Override
    public void onContainerClosed(PlayerEntity playerIn) {
        super.onContainerClosed(playerIn);
        tile.closeInventory(playerIn);
    }

    private int addSlotRange(IItemHandler handler, int index, int x, int y, int amount, int dx) {
        for (int i = 0 ; i < amount ; i++) {
            addSlot(new SlotItemHandler(handler, index, x, y));
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

    IInventory getIInventory() {
        return tile.getInventory();
    }
}
