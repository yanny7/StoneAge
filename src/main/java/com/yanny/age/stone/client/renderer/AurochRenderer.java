package com.yanny.age.stone.client.renderer;

import com.yanny.age.stone.Reference;
import com.yanny.age.stone.client.models.AurochModel;
import com.yanny.age.stone.entities.AurochEntity;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.registry.IRenderFactory;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@OnlyIn(Dist.CLIENT)
public class AurochRenderer extends MobRenderer<AurochEntity, AurochModel> {
    private static final ResourceLocation AUROCH_TEXTURE = new ResourceLocation(Reference.MODID, "textures/entity/auroch.png");

    private AurochRenderer(@Nonnull EntityRendererManager rendererManager) {
        super(rendererManager, new AurochModel(), 0.5f);
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(@Nonnull AurochEntity entity) {
        return AUROCH_TEXTURE;
    }

    @Override
    protected boolean canRenderName(AurochEntity entity) {
        return entity.hasCustomName();
    }

    public static class RenderFactory implements IRenderFactory<AurochEntity> {

        @Override
        public EntityRenderer<? super AurochEntity> createRenderFor(EntityRendererManager manager) {
            return new AurochRenderer(manager);
        }
    }
}
