package com.yanny.age.stone.items;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class SickleItem extends Item {
    public SickleItem(Properties properties) {
        super(properties);
    }

    @Override
    public boolean onBlockDestroyed(ItemStack stack, World worldIn, BlockState state, BlockPos pos, LivingEntity entityLiving) {
        Block block = state.getBlock();

        if (!worldIn.isRemote) {
            stack.damageItem(1, entityLiving, livingEntity -> livingEntity.sendBreakAnimation(EquipmentSlotType.MAINHAND));

            NonNullList<ItemStack> itemStacks = NonNullList.create();

            if (state.isIn(BlockTags.LEAVES) || block == Blocks.COBWEB || block == Blocks.GRASS || block == Blocks.FERN || block == Blocks.DEAD_BUSH ||
                    block == Blocks.VINE || block == Blocks.TRIPWIRE || block.isIn(BlockTags.WOOL)) {
                @SuppressWarnings("deprecation")
                ItemStack result = Item.getItemFromBlock(block).getDefaultInstance();

                if (!result.isEmpty()) {
                    itemStacks.add(result);
                }
            }

            InventoryHelper.dropItems(worldIn, pos, itemStacks);
        }

        return state.isIn(BlockTags.LEAVES) || block == Blocks.COBWEB || block == Blocks.GRASS || block == Blocks.FERN || block == Blocks.DEAD_BUSH ||
                block == Blocks.VINE || block == Blocks.TRIPWIRE || block.isIn(BlockTags.WOOL) || super.onBlockDestroyed(stack, worldIn, state, pos, entityLiving);
    }

    @Override
    public boolean canHarvestBlock(BlockState blockIn) {
        Block block = blockIn.getBlock();
        return block == Blocks.COBWEB || block == Blocks.REDSTONE_WIRE || block == Blocks.TRIPWIRE;
    }

    @Override
    public float getDestroySpeed(ItemStack stack, BlockState state) {
        Block block = state.getBlock();
        if (block != Blocks.COBWEB && !state.isIn(BlockTags.LEAVES)) {
            return block.isIn(BlockTags.WOOL) ? 5.0F : super.getDestroySpeed(stack, state);
        } else {
            return 15.0F;
        }
    }
}
