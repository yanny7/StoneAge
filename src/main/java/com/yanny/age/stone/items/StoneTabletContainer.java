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

public class StoneTabletContainer extends Container {
    public StoneTabletContainer(int windowId, @SuppressWarnings("unused") PlayerInventory inv, @SuppressWarnings("unused") PacketBuffer extraData) {
        this(windowId);
    }

    StoneTabletContainer(int id) {
        super(ContainerSubscriber.stone_tablet, id);
    }

    @Override
    public boolean canInteractWith(@Nonnull PlayerEntity playerIn) {
        return true;
    }
}
