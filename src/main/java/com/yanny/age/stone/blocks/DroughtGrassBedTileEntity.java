package com.yanny.age.stone.blocks;

import com.yanny.age.stone.subscribers.TileEntitySubscriber;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntity;

public class DroughtGrassBedTileEntity extends TileEntity {
    public DroughtGrassBedTileEntity() {
        //noinspection ConstantConditions
        super(TileEntitySubscriber.drought_grass_bed);
    }

    @Override
    public SUpdateTileEntityPacket getUpdatePacket() {
        return new SUpdateTileEntityPacket(this.pos, 11, this.getUpdateTag());
    }
}
