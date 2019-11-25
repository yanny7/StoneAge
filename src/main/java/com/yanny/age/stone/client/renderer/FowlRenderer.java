package com.yanny.age.stone.client.renderer;

import com.yanny.age.stone.Reference;
import com.yanny.age.stone.client.models.FowlModel;
import com.yanny.age.stone.entities.FowlEntity;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.registry.IRenderFactory;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@OnlyIn(Dist.CLIENT)
public class FowlRenderer extends LivingRenderer<FowlEntity, FowlModel> {
    private static final ResourceLocation FOWL_TEXTURE = new ResourceLocation(Reference.MODID, "textures/entity/fowl.png");

    private FowlRenderer(EntityRendererManager rendererManager) {
        super(rendererManager, new FowlModel(), 0.3f);
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(@Nonnull FowlEntity entity) {
        return FOWL_TEXTURE;
    }

    @Override
    protected float handleRotationFloat(FowlEntity livingBase, float partialTicks) {
        float f = MathHelper.lerp(partialTicks, livingBase.oFlap, livingBase.wingRotation);
        float f1 = MathHelper.lerp(partialTicks, livingBase.oFlapSpeed, livingBase.destPos);
        return (MathHelper.sin(f) + 1.0F) * f1;
    }

    @Override
    protected boolean canRenderName(FowlEntity entity) {
        return entity.hasCustomName();
    }

    public static class RenderFactory implements IRenderFactory<FowlEntity> {

        @Override
        public EntityRenderer<? super FowlEntity> createRenderFor(EntityRendererManager manager) {
            return new FowlRenderer(manager);
        }
    }
}
