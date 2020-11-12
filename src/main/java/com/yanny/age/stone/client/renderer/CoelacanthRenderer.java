package com.yanny.age.stone.client.renderer;

import com.yanny.age.stone.Reference;
import com.yanny.age.stone.client.models.CoelacanthModel;
import com.yanny.age.stone.entities.CoelacanthEntity;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.registry.IRenderFactory;

import javax.annotation.Nonnull;

@OnlyIn(Dist.CLIENT)
public class CoelacanthRenderer extends MobRenderer<CoelacanthEntity, CoelacanthModel> {
    private static final ResourceLocation COELACANT_TEXTURE = new ResourceLocation(Reference.MODID, "textures/entity/coelacanth.png");

    private CoelacanthRenderer(@Nonnull EntityRendererManager rendererManager) {
        super(rendererManager, new CoelacanthModel(), 0.3f);
    }

    @Nonnull
    @Override
    public ResourceLocation getEntityTexture(@Nonnull CoelacanthEntity entity) {
        return COELACANT_TEXTURE;
    }

    @Override
    protected float handleRotationFloat(@Nonnull CoelacanthEntity livingBase, float partialTicks) {
        return 1.0f;
    }

    @Override
    protected boolean canRenderName(CoelacanthEntity entity) {
        return entity.hasCustomName();
    }

    public static class RenderFactory implements IRenderFactory<CoelacanthEntity> {

        @Override
        public EntityRenderer<? super CoelacanthEntity> createRenderFor(EntityRendererManager manager) {
            return new CoelacanthRenderer(manager);
        }
    }
}
