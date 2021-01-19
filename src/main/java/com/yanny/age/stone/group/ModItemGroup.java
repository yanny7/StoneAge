package com.yanny.age.stone.group;

import com.yanny.age.stone.Reference;
import com.yanny.age.stone.subscribers.ToolSubscriber;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

public class ModItemGroup extends ItemGroup {
    public static final ItemGroup AGES = new ModItemGroup();

    ModItemGroup() {
        super(Reference.MODID);
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    @Nonnull
    public ItemStack createIcon() {
        return new ItemStack(ToolSubscriber.stone_hammer);
    }
}
