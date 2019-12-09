package com.yanny.age.stone.handlers;

import com.google.common.collect.Sets;
import com.yanny.age.stone.Reference;
import com.yanny.ages.api.capability.fluid.GenericFluidEntityHandler;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Set;

import static com.yanny.ages.api.capability.fluid.GenericFluidEntityHandler.Setup;

public class AquaductHandler {
    private static final int VERSION = 1;
    private static final String ID = Reference.MODID + "_aquaduct_handler";
    private static final Setup SETUP = new AquaductSetup();
    private static final HashMap<World, GenericFluidEntityHandler> INSTANCE = new HashMap<>();

    @Nonnull
    public static GenericFluidEntityHandler getInstance(@Nonnull World world) {
        if (world.isRemote) {
            throw new IllegalStateException(ID + " is only server-side");
        }

        GenericFluidEntityHandler handler = INSTANCE.get(world);

        if (handler == null) {
            handler = new GenericFluidEntityHandler(world, SETUP);
            INSTANCE.put(world, handler);
        }

        if (world instanceof ServerWorld) {
            ServerWorld server = (ServerWorld) world;
            server.getSavedData().getOrCreate(() -> INSTANCE.get(world), ID);
        }

        return handler;
    }

    private static final class AquaductSetup extends Setup {
        private static final Set<Direction> DIRECTIONS = Sets.newHashSet(Direction.Plane.HORIZONTAL);

        AquaductSetup() {
            super(500, ID, DIRECTIONS, VERSION);
        }

        @Override
        public boolean checkConnection(@Nonnull World world, @Nonnull BlockPos pos, @Nonnull Direction posFacing, @Nonnull BlockPos pos2) {
            return true;
        }
    }
}
