package com.yanny.age.stone.proxy;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

public class ServerProxy implements IProxy {
    @Override
    public void init() {

    }

    @Override
    public World getClientWorld() {
        throw new IllegalStateException("No client world on server");
    }

    @Override
    public PlayerEntity getClientPlayer() {
        throw new IllegalStateException("No client player on server");
    }
}
