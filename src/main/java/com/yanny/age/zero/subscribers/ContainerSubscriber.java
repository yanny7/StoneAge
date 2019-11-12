package com.yanny.age.zero.subscribers;

import com.yanny.age.zero.blocks.StoneChestContainer;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;

import static com.yanny.age.zero.Reference.*;

@ObjectHolder(MODID)
@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ContainerSubscriber {
    public static ContainerType<StoneChestContainer> stone_chest = IForgeContainerType.create(StoneChestContainer::new);

    @SubscribeEvent
    public static void registerContainer(RegistryEvent.Register<ContainerType<?>> event) {
        IForgeRegistry<ContainerType<?>> registry = event.getRegistry();
        registry.register(stone_chest.setRegistryName(MODID, "stone_chest"));
    }
}