package com.yanny.age.zero.proxy;

import com.yanny.age.zero.blocks.*;
import com.yanny.age.zero.client.renderer.DryingRackRenderer;
import com.yanny.age.zero.client.renderer.FlintWorkbenchRenderer;
import com.yanny.age.zero.client.renderer.StoneChestRenderer;
import com.yanny.age.zero.client.renderer.TanningRackRenderer;
import com.yanny.age.zero.subscribers.ContainerSubscriber;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.registry.ClientRegistry;


public class ClientProxy implements IProxy {

    @Override
    public void init() {
        ScreenManager.registerFactory(ContainerSubscriber.stone_chest, StoneChestGui::new);

        ClientRegistry.bindTileEntitySpecialRenderer(FlintWorkbenchTileEntity.class, new FlintWorkbenchRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(DryingRackTileEntity.class, new DryingRackRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TanningRackTileEntity.class, new TanningRackRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(StoneChestTileEntity.class, new StoneChestRenderer());
    }

    @Override
    public World getClientWorld() {
        return Minecraft.getInstance().world;
    }

    @Override
    public PlayerEntity getClientPlayer() {
        return Minecraft.getInstance().player;
    }
}
