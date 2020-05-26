package com.yanny.age.stone.client.renderer;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.yanny.age.stone.Reference;
import com.yanny.age.stone.client.models.FlintSpearModel;
import com.yanny.age.stone.entities.FlintSpearEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.Vector3f;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.registry.IRenderFactory;

import javax.annotation.Nonnull;

import static net.minecraft.util.math.MathHelper.lerp;

@OnlyIn(Dist.CLIENT)
public class FlintSpearRenderer extends EntityRenderer<FlintSpearEntity> {
    static final ResourceLocation TEXTURE = new ResourceLocation(Reference.MODID, "textures/entity/flint_spear.png");
    private final FlintSpearModel model = new FlintSpearModel();

    private FlintSpearRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn);
    }

    @Override
    public void render(FlintSpearEntity entity, float entityYaw, float partialTicks, @Nonnull MatrixStack matrixStack, @Nonnull IRenderTypeBuffer renderTypeBuffer, int packedLightIn) {
        matrixStack.push();
        matrixStack.rotate(Vector3f.YP.rotationDegrees(lerp(partialTicks, entity.prevRotationYaw, entity.rotationYaw) - 90.0F));
        matrixStack.rotate(Vector3f.ZP.rotationDegrees(lerp(partialTicks, entity.prevRotationPitch, entity.rotationPitch) + 90.0F));
        matrixStack.scale(1, -1, 1);
        matrixStack.translate(0, -1.5, 0);
        RenderSystem.color4f(1, 1, 1, 1);
        int i = OverlayTexture.getPackedUV(OverlayTexture.getU(0), OverlayTexture.getV(false));
        IVertexBuilder vertexBuilder = ItemRenderer.getBuffer(renderTypeBuffer, model.getRenderType(this.getEntityTexture(entity)), false, false);
        model.render(matrixStack, vertexBuilder, packedLightIn, i, 1.0F, 1.0F, 1.0F, 1.0F);
        matrixStack.pop();
        super.render(entity, entityYaw, partialTicks, matrixStack, renderTypeBuffer, packedLightIn);
    }

    @Nonnull
    @Override
    public ResourceLocation getEntityTexture(@Nonnull FlintSpearEntity entity) {
        return TEXTURE;
    }

    public static class RenderFactory implements IRenderFactory<FlintSpearEntity> {
        @Override
        public EntityRenderer<? super FlintSpearEntity> createRenderFor(EntityRendererManager manager) {
            return new FlintSpearRenderer(manager);
        }
    }
}
