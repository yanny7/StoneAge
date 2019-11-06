package com.yanny.age.zero.proxy;

import com.yanny.age.zero.client.renderer.FlintWorkbenchRenderer;
import com.yanny.age.zero.blocks.FlintWorkbenchTileEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.registry.ClientRegistry;


public class ClientProxy implements IProxy {

    @Override
    public void init() {
        ClientRegistry.bindTileEntitySpecialRenderer(FlintWorkbenchTileEntity.class, new FlintWorkbenchRenderer());
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
