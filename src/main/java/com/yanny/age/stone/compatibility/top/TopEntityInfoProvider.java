package com.yanny.age.stone.compatibility.top;

import mcjty.theoneprobe.api.IProbeHitEntityData;
import mcjty.theoneprobe.api.IProbeInfo;
import mcjty.theoneprobe.api.ProbeMode;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public interface TopEntityInfoProvider {
    void addProbeInfo(@Nonnull ProbeMode mode, @Nonnull IProbeInfo probeInfo, @Nonnull PlayerEntity player, @Nonnull World world,
                      @Nonnull Entity entity, @Nonnull IProbeHitEntityData data);
}
