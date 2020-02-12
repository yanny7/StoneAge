package com.yanny.age.stone.client.renderer;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.yanny.age.stone.Reference;
import com.yanny.age.stone.client.models.MammothModel;
import com.yanny.age.stone.entities.MammothEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.registry.IRenderFactory;

import javax.annotation.Nonnull;

@OnlyIn(Dist.CLIENT)
public class MammothRenderer extends MobRenderer<MammothEntity, MammothModel> {
    private static final ResourceLocation MAMMOTH_TEXTURE = new ResourceLocation(Reference.MODID, "textures/entity/mammoth.png");

    private MammothRenderer(EntityRendererManager rendererManager) {
        super(rendererManager, new MammothModel(), 1.0f);
    }

    @Override
    public void render(@Nonnull MammothEntity entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, @Nonnull IRenderTypeBuffer bufferIn, int packedLightIn) {
        matrixStackIn.scale(1.5f, 1.5f, 1.5f);
        super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
    }

    @Nonnull
    @Override
    public ResourceLocation getEntityTexture(@Nonnull MammothEntity entity) {
        return MAMMOTH_TEXTURE;
    }

    @Override
    protected boolean canRenderName(MammothEntity entity) {
        return entity.hasCustomName();
    }

    public static class RenderFactory implements IRenderFactory<MammothEntity> {

        @Override
        public EntityRenderer<? super MammothEntity> createRenderFor(EntityRendererManager manager) {
            return new MammothRenderer(manager);
        }
    }
}
