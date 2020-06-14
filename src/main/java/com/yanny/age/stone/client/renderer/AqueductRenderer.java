package com.yanny.age.stone.client.renderer;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.yanny.age.stone.blocks.AqueductTileEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.world.ILightReader;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.MinecraftForgeClient;

import javax.annotation.Nonnull;

import static com.yanny.age.stone.utils.RendererUtils.add;

@OnlyIn(Dist.CLIENT)
public class AqueductRenderer extends TileEntityRenderer<AqueductTileEntity> {

    public AqueductRenderer(TileEntityRendererDispatcher p_i226006_1_) {
        super(p_i226006_1_);
    }

    @Override
    public void render(AqueductTileEntity tileEntityIn, float partialTicks, @Nonnull MatrixStack matrixStack,
                       @Nonnull IRenderTypeBuffer renderTypeBuffer, int overlayUV, int lightmapUV) {
        if (!tileEntityIn.hasWorld() || tileEntityIn.getCapacity() < 0.01) {
            return;
        }

        float v = tileEntityIn.getCapacity() * (10 / 16f) + 4 / 16f;

        matrixStack.push();

        Fluid fluid = Fluids.WATER.getFluid();
        ILightReader lightReader =  MinecraftForgeClient.getRegionRenderCache(tileEntityIn.getWorld(), tileEntityIn.getPos());
        //noinspection deprecation
        TextureAtlasSprite sprite = Minecraft.getInstance().getAtlasSpriteGetter(AtlasTexture.LOCATION_BLOCKS_TEXTURE)
                .apply(fluid.getAttributes().getStillTexture(tileEntityIn.getWorld(), tileEntityIn.getPos()));
        IVertexBuilder builder = renderTypeBuffer.getBuffer(RenderType.getTranslucent());

        add(fluid, lightReader, tileEntityIn.getPos(), builder, matrixStack, 0.0f, v, 1.0f, sprite.getMinU(), sprite.getMaxV());
        add(fluid, lightReader, tileEntityIn.getPos(),builder, matrixStack, 1.0f, v, 1.0f, sprite.getMaxU(), sprite.getMaxV());
        add(fluid, lightReader, tileEntityIn.getPos(),builder, matrixStack, 1.0f, v, 0.0f, sprite.getMaxU(), sprite.getMinV());
        add(fluid, lightReader, tileEntityIn.getPos(),builder, matrixStack, 0.0f, v, 0.0f, sprite.getMinU(), sprite.getMinV());

        matrixStack.pop();
    }
}
