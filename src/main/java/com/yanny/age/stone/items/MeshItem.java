package com.yanny.age.stone.items;

import com.yanny.ages.api.group.ModItemGroup;
import net.minecraft.item.Item;

public class MeshItem extends Item {

    public MeshItem(Properties properties) {
        super(properties);
    }

    @Override
    public int getItemEnchantability() {
        return 1;
    }
}
