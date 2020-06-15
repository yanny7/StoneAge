package com.yanny.age.stone.items;

import com.yanny.age.stone.subscribers.ContainerSubscriber;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.network.PacketBuffer;

import javax.annotation.Nonnull;

public class StoneTabletContainer extends Container {
    public StoneTabletContainer(int windowId, @SuppressWarnings("unused") @Nonnull PlayerInventory inv, @SuppressWarnings("unused") @Nonnull PacketBuffer extraData) {
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
