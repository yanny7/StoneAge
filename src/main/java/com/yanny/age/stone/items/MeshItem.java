package com.yanny.age.stone.items;

import net.minecraft.item.Item;

import javax.annotation.Nonnull;

public class MeshItem extends Item {

    public MeshItem(@Nonnull Properties properties) {
        super(properties);
    }

    @Override
    public int getItemEnchantability() {
        return 1;
    }
}
