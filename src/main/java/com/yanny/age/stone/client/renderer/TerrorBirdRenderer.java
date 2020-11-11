package com.yanny.age.stone.client.renderer;

import com.yanny.age.stone.Reference;
import com.yanny.age.stone.client.models.TerrorBirdModel;
import com.yanny.age.stone.entities.TerrorBirdEntity;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.registry.IRenderFactory;

import javax.annotation.Nonnull;

@OnlyIn(Dist.CLIENT)
public class TerrorBirdRenderer extends MobRenderer<TerrorBirdEntity, TerrorBirdModel> {
    private static final ResourceLocation TERROR_BIRD_TEXTURE = new ResourceLocation(Reference.MODID, "textures/entity/terror_bird.png");

    private TerrorBirdRenderer(@Nonnull EntityRendererManager rendererManager) {
        super(rendererManager, new TerrorBirdModel(), 0.3f);
    }

    @Nonnull
    @Override
    public ResourceLocation getEntityTexture(@Nonnull TerrorBirdEntity entity) {
        return TERROR_BIRD_TEXTURE;
    }

    @Override
    protected float handleRotationFloat(TerrorBirdEntity livingBase, float partialTicks) {
        float f = MathHelper.lerp(partialTicks, livingBase.oFlap, livingBase.wingRotation);
        float f1 = MathHelper.lerp(partialTicks, livingBase.oFlapSpeed, livingBase.destPos);
        return (MathHelper.sin(f) + 1.0F) * f1;
    }

    @Override
    protected boolean canRenderName(TerrorBirdEntity entity) {
        return entity.hasCustomName();
    }

    public static class RenderFactory implements IRenderFactory<TerrorBirdEntity> {

        @Override
        public EntityRenderer<? super TerrorBirdEntity> createRenderFor(EntityRendererManager manager) {
            return new TerrorBirdRenderer(manager);
        }
    }
}
