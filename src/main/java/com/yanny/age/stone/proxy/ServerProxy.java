package com.yanny.age.stone.proxy;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class ServerProxy implements IProxy {
    @Override
    public void init() {

    }

    @Override
    @Nullable
    public World getClientWorld() {
        throw new IllegalStateException("No client world on server");
    }

    @Override
    @Nullable
    public PlayerEntity getClientPlayer() {
        throw new IllegalStateException("No client player on server");
    }
}
