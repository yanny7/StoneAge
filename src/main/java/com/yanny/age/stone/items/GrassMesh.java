package com.yanny.age.stone.items;

import com.yanny.ages.api.group.ModItemGroup;
import net.minecraft.item.Item;

public class GrassMesh extends Item {

    public GrassMesh() {
        super(new Item.Properties().group(ModItemGroup.AGES).maxDamage(15));
    }

    @Override
    public int getItemEnchantability() {
        return 1;
    }
}
