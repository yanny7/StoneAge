package com.yanny.age.stone.subscribers;

import com.yanny.age.stone.blocks.*;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;

import static com.yanny.age.stone.Reference.MODID;

@ObjectHolder(MODID)
@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class TileEntitySubscriber {
    public static final TileEntityType<FlintWorkbenchTileEntity> flint_workbench = null;
    public static final TileEntityType<DryingRackTileEntity> drying_rack = null;
    public static final TileEntityType<TanningRackTileEntity> tanning_rack = null;
    public static final TileEntityType<StoneChestTileEntity> stone_chest = null;
    public static final TileEntityType<TreeStumpTileEntity> tree_stump = null;
    public static final TileEntityType<AquaductTileEntity> aquaduct = null;
    public static final TileEntityType<DroughtGrassBedTileEntity> drought_grass_bed = null;
    public static final TileEntityType<FeederTileEntity> feeder = null;
    public static final TileEntityType<MillstoneTileEntity> millstone = null;
    public static final TileEntityType<FishingNetTileEntity> fishing_net = null;

    @SuppressWarnings("ConstantConditions")
    @SubscribeEvent
    public static void registerTileEntity(RegistryEvent.Register<TileEntityType<?>> event) {
        IForgeRegistry<TileEntityType<?>> registry = event.getRegistry();
        registry.register(TileEntityType.Builder.create(FlintWorkbenchTileEntity::new, BlockSubscriber.flint_workbench)
                .build(null).setRegistryName("flint_workbench"));
        registry.register(TileEntityType.Builder.create(DryingRackTileEntity::new, BlockSubscriber.drying_rack)
                .build(null).setRegistryName("drying_rack"));
        registry.register(TileEntityType.Builder.create(TanningRackTileEntity::new, BlockSubscriber.tanning_rack)
                .build(null).setRegistryName("tanning_rack"));
        registry.register(TileEntityType.Builder.create(StoneChestTileEntity::new, BlockSubscriber.stone_chest)
                .build(null).setRegistryName("stone_chest"));
        registry.register(TileEntityType.Builder.create(TreeStumpTileEntity::new, BlockSubscriber.tree_stump)
                .build(null).setRegistryName("tree_stump"));
        registry.register(TileEntityType.Builder.create(AquaductTileEntity::new, BlockSubscriber.aquaduct)
                .build(null).setRegistryName("aquaduct"));
        registry.register(TileEntityType.Builder.create(DroughtGrassBedTileEntity::new, BlockSubscriber.drought_grass_bed)
                .build(null).setRegistryName("drought_grass_bed"));
        registry.register(TileEntityType.Builder.create(FeederTileEntity::new, BlockSubscriber.feeder)
                .build(null).setRegistryName("feeder"));
        registry.register(TileEntityType.Builder.create(MillstoneTileEntity::new, BlockSubscriber.millstone)
                .build(null).setRegistryName("millstone"));
        registry.register(TileEntityType.Builder.create(FishingNetTileEntity::new, BlockSubscriber.fishing_net)
                .build(null).setRegistryName("fishing_net"));
    }
}
