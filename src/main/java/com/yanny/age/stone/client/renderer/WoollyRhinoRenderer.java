package com.yanny.age.stone.client.renderer;

import com.yanny.age.stone.Reference;
import com.yanny.age.stone.client.models.WoollyRhinoModel;
import com.yanny.age.stone.entities.WoollyRhinoEntity;
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
public class WoollyRhinoRenderer extends MobRenderer<WoollyRhinoEntity, WoollyRhinoModel> {
    private static final ResourceLocation WOOLLY_RHINO_TEXTURE = new ResourceLocation(Reference.MODID, "textures/entity/woolly_rhino.png");

    private WoollyRhinoRenderer(@Nonnull EntityRendererManager rendererManager) {
        super(rendererManager, new WoollyRhinoModel(), 0.7f);
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(@Nonnull WoollyRhinoEntity entity) {
        return WOOLLY_RHINO_TEXTURE;
    }

    @Override
    protected boolean canRenderName(WoollyRhinoEntity entity) {
        return entity.hasCustomName();
    }

    public static class RenderFactory implements IRenderFactory<WoollyRhinoEntity> {

        @Override
        public EntityRenderer<? super WoollyRhinoEntity> createRenderFor(EntityRendererManager manager) {
            return new WoollyRhinoRenderer(manager);
        }
    }
}
