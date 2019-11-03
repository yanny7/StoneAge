package com.yanny.age.zero.client.renderer;

import com.yanny.age.zero.Reference;
import com.yanny.age.zero.client.models.BoarModel;
import com.yanny.age.zero.entities.AurochEntity;
import com.yanny.age.zero.entities.BoarEntity;
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
public class BoarRenderer extends LivingRenderer<BoarEntity, BoarModel> {
    private static final ResourceLocation BOAR_TEXTURE = new ResourceLocation(Reference.MODID, "textures/entity/boar.png");

    private BoarRenderer(EntityRendererManager rendererManager) {
        super(rendererManager, new BoarModel(), 0.5f);
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(@Nonnull BoarEntity entity) {
        return BOAR_TEXTURE;
    }

    @Override
    protected boolean canRenderName(BoarEntity entity) {
        return false;
    }

    public static class RenderFactory implements IRenderFactory<BoarEntity> {

        @Override
        public EntityRenderer<? super BoarEntity> createRenderFor(EntityRendererManager manager) {
            return new BoarRenderer(manager);
        }
    }
}
