package com.yanny.age.zero.blocks;

import com.yanny.age.zero.subscribers.TilesSubscriber;
import net.minecraft.tileentity.TileEntity;

public class TreeStumpTileEntity extends TileEntity {
    public TreeStumpTileEntity() {
        //noinspection ConstantConditions
        super(TilesSubscriber.tree_stump);
    }
}
