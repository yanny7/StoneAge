package com.yanny.age.zero.subscribers;

import com.yanny.age.zero.client.renderer.RendererSubscriber;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import static com.yanny.age.zero.Reference.*;

@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModSubscriber {
    @SubscribeEvent
    public static void clientRegistries(FMLClientSetupEvent event) {
        RendererSubscriber.registerRenderer();
    }
}
