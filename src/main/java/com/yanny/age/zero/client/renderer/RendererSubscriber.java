package com.yanny.age.zero.client.renderer;

import com.yanny.age.zero.entities.BoarEntity;
import com.yanny.age.zero.entities.DeerEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

@OnlyIn(Dist.CLIENT)
public class RendererSubscriber {

    public static void registerRenderer() {
        RenderingRegistry.registerEntityRenderingHandler(DeerEntity.class, new DeerRenderer.RenderFactory());
        RenderingRegistry.registerEntityRenderingHandler(BoarEntity.class, new BoarRenderer.RenderFactory());
    }
}
