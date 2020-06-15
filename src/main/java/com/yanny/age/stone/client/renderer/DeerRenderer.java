package com.yanny.age.stone.client.renderer;

import com.yanny.age.stone.Reference;
import com.yanny.age.stone.client.models.DeerModel;
import com.yanny.age.stone.entities.DeerEntity;
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
public class DeerRenderer extends MobRenderer<DeerEntity, DeerModel> {
    private static final ResourceLocation DEER_TEXTURE = new ResourceLocation(Reference.MODID, "textures/entity/deer.png");

    private DeerRenderer(@Nonnull EntityRendererManager rendererManager) {
        super(rendererManager, new DeerModel(), 0.5f);
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(@Nonnull DeerEntity entity) {
        return DEER_TEXTURE;
    }

    @Override
    protected boolean canRenderName(DeerEntity entity) {
        return entity.hasCustomName();
    }

    public static class RenderFactory implements IRenderFactory<DeerEntity> {

        @Override
        public EntityRenderer<? super DeerEntity> createRenderFor(EntityRendererManager manager) {
            return new DeerRenderer(manager);
        }
    }
}
