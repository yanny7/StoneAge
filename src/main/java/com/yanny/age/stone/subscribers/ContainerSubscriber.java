package com.yanny.age.stone.subscribers;

import com.yanny.age.stone.blocks.FeederContainer;
import com.yanny.age.stone.blocks.MillstoneContainer;
import com.yanny.age.stone.blocks.StoneChestContainer;
import com.yanny.age.stone.items.BackpakcContainer;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;

import static com.yanny.age.stone.Reference.*;

@ObjectHolder(MODID)
@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ContainerSubscriber {
    public static final ContainerType<StoneChestContainer> stone_chest = IForgeContainerType.create(StoneChestContainer::new);
    public static final ContainerType<FeederContainer> feeder = IForgeContainerType.create(FeederContainer::new);
    public static final ContainerType<MillstoneContainer> millstone = IForgeContainerType.create(MillstoneContainer::new);
    public static final ContainerType<BackpakcContainer> backpack = IForgeContainerType.create(BackpakcContainer::new);

    @SubscribeEvent
    public static void registerContainer(RegistryEvent.Register<ContainerType<?>> event) {
        IForgeRegistry<ContainerType<?>> registry = event.getRegistry();
        registry.register(stone_chest.setRegistryName(MODID, "stone_chest"));
        registry.register(feeder.setRegistryName(MODID, "feeder"));
        registry.register(millstone.setRegistryName(MODID, "millstone"));
        registry.register(backpack.setRegistryName(MODID, "backpack"));
    }
}