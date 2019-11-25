package com.yanny.age.stone.subscribers;

import com.yanny.age.stone.blocks.*;
import com.yanny.age.stone.client.renderer.StoneChestItemRenderer;
import com.yanny.ages.api.group.ModItemGroup;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;

import static com.yanny.age.stone.Reference.MODID;

@SuppressWarnings("WeakerAccess")
@ObjectHolder(MODID)
@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class BlockSubscriber {
    public static final Block flint_workbench = null;
    public static final Block drying_rack = null;
    public static final Block tanning_rack = null;
    public static final Block stone_chest = null;
    public static final Block tree_stump = null;
    public static final Block clay_vessel = null;
    public static final Block unfired_clay_vessel = null;
    public static final Block aquaduct = null;

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        IForgeRegistry<Block> registry = event.getRegistry();
        registry.register(new FlintWorkbenchBlock().setRegistryName(MODID, "flint_workbench"));
        registry.register(new DryingRackBlock().setRegistryName(MODID, "drying_rack"));
        registry.register(new TanningRackBlock().setRegistryName(MODID, "tanning_rack"));
        registry.register(new StoneChestBlock().setRegistryName(MODID, "stone_chest"));
        registry.register(new TreeStumpBlock().setRegistryName(MODID, "tree_stump"));
        registry.register(new ClayVesselBlock().setRegistryName(MODID, "clay_vessel"));
        registry.register(new UnfiredClayVesselBlock().setRegistryName(MODID, "unfired_clay_vessel"));
        registry.register(new AquaductBlock().setRegistryName(MODID, "aquaduct"));
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
        registry.register(new BlockItem(stone_chest, new Item.Properties().group(ModItemGroup.TOOMANYORES).setTEISR(() -> StoneChestItemRenderer::new))
                .setRegistryName(MODID, "stone_chest"));
        registry.register(new BlockItem(tree_stump, new Item.Properties().group(ModItemGroup.TOOMANYORES))
                .setRegistryName(MODID, "tree_stump"));
        registry.register(new BlockItem(clay_vessel, new Item.Properties().group(ModItemGroup.TOOMANYORES))
                .setRegistryName(MODID, "clay_vessel"));
        registry.register(new BlockItem(unfired_clay_vessel, new Item.Properties().group(ModItemGroup.TOOMANYORES))
                .setRegistryName(MODID, "unfired_clay_vessel"));
        registry.register(new BlockItem(aquaduct, new Item.Properties().group(ModItemGroup.TOOMANYORES))
                .setRegistryName(MODID, "aquaduct"));
    }
}
