package com.yanny.age.zero.client.renderer;

import com.yanny.age.zero.entities.BoarEntity;
import com.yanny.age.zero.entities.DeerEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import static com.yanny.age.zero.Reference.*;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class RendererSubscriber {
    @SubscribeEvent
    public static void clientRegistries(FMLClientSetupEvent event) {
        RendererSubscriber.registerRenderer();
    }

    private static void registerRenderer() {
        RenderingRegistry.registerEntityRenderingHandler(DeerEntity.class, new DeerRenderer.RenderFactory());
        RenderingRegistry.registerEntityRenderingHandler(BoarEntity.class, new BoarRenderer.RenderFactory());
    }
}
