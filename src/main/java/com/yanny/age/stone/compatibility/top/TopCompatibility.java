package com.yanny.age.stone.compatibility.top;

import com.yanny.age.stone.Reference;
import mcjty.theoneprobe.api.*;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import net.minecraftforge.fml.InterModComms;

import java.util.function.Function;
import java.util.function.Supplier;

public class TopCompatibility {
    public static final String ID = Reference.MODID + ":default";
    private static boolean registered;

    public static void register() {
        if (registered) {
            return;
        }

        registered = true;
        InterModComms.sendTo("theoneprobe", "getTheOneProbe",
                (Supplier<Function<ITheOneProbe, Void>>)(() -> TopCompatibility::setProbe));
    }

    public static Void setProbe(ITheOneProbe probe) {
        probe.registerProvider(new IProbeInfoProvider() {
            @Override
            public String getID() {
                return ID;
            }

            @Override
            public void addProbeInfo(ProbeMode mode, IProbeInfo probeInfo, PlayerEntity player, World world, BlockState blockState, IProbeHitData data) {
                if (blockState.getBlock() instanceof IProbeInfoProvider) {
                    IProbeInfoProvider provider = (IProbeInfoProvider) blockState.getBlock();
                    provider.addProbeInfo(mode, probeInfo, player, world, blockState, data);
                }

            }
        });

        return null;
    }
}
