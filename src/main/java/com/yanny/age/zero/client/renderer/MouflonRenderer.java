package com.yanny.age.zero.client.renderer;

import com.yanny.age.zero.Reference;
import com.yanny.age.zero.client.models.MouflonModel;
import com.yanny.age.zero.entities.MouflonEntity;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.registry.IRenderFactory;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@OnlyIn(Dist.CLIENT)
public class MouflonRenderer extends LivingRenderer<MouflonEntity, MouflonModel> {
    private static final ResourceLocation MOUFLON_TEXTURE = new ResourceLocation(Reference.MODID, "textures/entity/mouflon.png");

    private MouflonRenderer(EntityRendererManager rendererManager) {
        super(rendererManager, new MouflonModel(), 0.5f);
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(@Nonnull MouflonEntity entity) {
        return MOUFLON_TEXTURE;
    }

    public static class RenderFactory implements IRenderFactory<MouflonEntity> {

        @Override
        public EntityRenderer<? super MouflonEntity> createRenderFor(EntityRendererManager manager) {
            return new MouflonRenderer(manager);
        }
    }
}
