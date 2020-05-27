package com.yanny.age.stone.compatibility.top;

import mcjty.theoneprobe.api.*;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.function.Function;

import static com.yanny.age.stone.compatibility.top.TopCompatibility.ID;

public class TopRegistration implements Function<ITheOneProbe, Void> {

    @Nullable
    @Override
    public Void apply(ITheOneProbe theOneProbe) {
        theOneProbe.registerProvider(new IProbeInfoProvider() {
            @Override
            public String getID() {
                return ID;
            }

            @Override
            public void addProbeInfo(ProbeMode mode, IProbeInfo probeInfo, PlayerEntity player, World world, BlockState blockState, IProbeHitData data) {
                if (blockState.getBlock() instanceof TopBlockInfoProvider) {
                    TopBlockInfoProvider provider = (TopBlockInfoProvider) blockState.getBlock();
                    provider.addProbeInfo(mode, probeInfo, player, world, blockState, data);
                }
            }
        });
        theOneProbe.registerEntityProvider(new IProbeInfoEntityProvider() {
            @Override
            public String getID() {
                return ID;
            }

            @Override
            public void addProbeEntityInfo(ProbeMode probeMode, IProbeInfo iProbeInfo, PlayerEntity playerEntity, World world, Entity entity, IProbeHitEntityData iProbeHitEntityData) {
                if (entity instanceof TopEntityInfoProvider) {
                    TopEntityInfoProvider provider = (TopEntityInfoProvider) entity;
                    provider.addProbeInfo(probeMode, iProbeInfo, playerEntity, world, entity, iProbeHitEntityData);
                }
            }
        });

        return null;
    }
}
