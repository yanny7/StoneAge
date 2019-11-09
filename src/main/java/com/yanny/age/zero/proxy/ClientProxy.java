package com.yanny.age.zero.proxy;

import com.yanny.age.zero.blocks.DryingRackTileEntity;
import com.yanny.age.zero.blocks.TanningRackTileEntity;
import com.yanny.age.zero.client.renderer.DryingRackRenderer;
import com.yanny.age.zero.client.renderer.FlintWorkbenchRenderer;
import com.yanny.age.zero.blocks.FlintWorkbenchTileEntity;
import com.yanny.age.zero.client.renderer.TanningRackRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.registry.ClientRegistry;


public class ClientProxy implements IProxy {

    @Override
    public void init() {
        ClientRegistry.bindTileEntitySpecialRenderer(FlintWorkbenchTileEntity.class, new FlintWorkbenchRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(DryingRackTileEntity.class, new DryingRackRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TanningRackTileEntity.class, new TanningRackRenderer());
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
