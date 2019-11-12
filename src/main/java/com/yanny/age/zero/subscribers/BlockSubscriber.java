package com.yanny.age.zero.subscribers;

import com.yanny.age.zero.blocks.DryingRackBlock;
import com.yanny.age.zero.blocks.FlintWorkbenchBlock;
import com.yanny.age.zero.blocks.StoneChestBlock;
import com.yanny.age.zero.blocks.TanningRackBlock;
import com.yanny.ages.api.group.ModItemGroup;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;

import static com.yanny.age.zero.Reference.MODID;

@ObjectHolder(MODID)
@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class BlockSubscriber {
    public static final Block flint_workbench = null;
    public static final Block drying_rack = null;
    public static final Block tanning_rack = null;
    public static final Block stone_chest = null;

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        IForgeRegistry<Block> registry = event.getRegistry();
        registry.register(new FlintWorkbenchBlock().setRegistryName(MODID, "flint_workbench"));
        registry.register(new DryingRackBlock().setRegistryName(MODID, "drying_rack"));
        registry.register(new TanningRackBlock().setRegistryName(MODID, "tanning_rack"));
        registry.register(new StoneChestBlock().setRegistryName(MODID, "stone_chest"));
    }

    @SuppressWarnings("ConstantConditions")
    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        IForgeRegistry<Item> registry = event.getRegistry();
        registry.register(new BlockItem(flint_workbench, new Item.Properties().group(ModItemGroup.TOOMANYORES))
                .setRegistryName(MODID, "flint_workbench"));
        registry.register(new BlockItem(drying_rack, new Item.Properties().group(ModItemGroup.TOOMANYORES))
                .setRegistryName(MODID, "drying_rack"));
        registry.register(new BlockItem(tanning_rack, new Item.Properties().group(ModItemGroup.TOOMANYORES))
                .setRegistryName(MODID, "tanning_rack"));
        registry.register(new BlockItem(stone_chest, new Item.Properties().group(ModItemGroup.TOOMANYORES))
                .setRegistryName(MODID, "stone_chest"));
    }
}
