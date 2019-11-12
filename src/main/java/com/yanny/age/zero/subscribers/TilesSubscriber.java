package com.yanny.age.zero.subscribers;

import com.yanny.age.zero.blocks.DryingRackTileEntity;
import com.yanny.age.zero.blocks.FlintWorkbenchTileEntity;
import com.yanny.age.zero.blocks.StoneChestTileEntity;
import com.yanny.age.zero.blocks.TanningRackTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;

import static com.yanny.age.zero.Reference.MODID;

@ObjectHolder(MODID)
@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class TilesSubscriber {
    public static final TileEntityType<FlintWorkbenchTileEntity> flint_workbench = null;
    public static final TileEntityType<DryingRackTileEntity> drying_rack = null;
    public static final TileEntityType<TanningRackTileEntity> tanning_rack = null;
    public static final TileEntityType<StoneChestTileEntity> stone_chest = null;

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
    }
}
