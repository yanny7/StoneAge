package com.yanny.age.stone.proxy;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public interface IProxy {
    void init();
    @Nullable
    World getClientWorld();
    @Nullable
    PlayerEntity getClientPlayer();
}
