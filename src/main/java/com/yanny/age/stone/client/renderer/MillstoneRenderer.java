package com.yanny.age.stone.client.renderer;

import com.mojang.blaze3d.platform.GlStateManager;
import com.yanny.age.stone.Reference;
import com.yanny.age.stone.blocks.MillstoneTileEntity;
import com.yanny.age.stone.client.models.MillstoneModel;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MillstoneRenderer extends TileEntityRenderer<MillstoneTileEntity> {
    private static final ResourceLocation TEXTURE_NORMAL = new ResourceLocation(Reference.MODID, "textures/entity/millstone.png");
    private final MillstoneModel model = new MillstoneModel();

    @Override
    public void render(MillstoneTileEntity tileEntityIn, double x, double y, double z, float partialTicks, int destroyStage) {
        GlStateManager.pushMatrix();
        GlStateManager.enableRescaleNormal();
        GlStateManager.translatef((float)x + 0.5f, (float)y, (float)z + 0.5f);

        this.bindTexture(TEXTURE_NORMAL);
        model.rotate(tileEntityIn.rotateAngle());
        model.renderAll();

        GlStateManager.disableRescaleNormal();
        GlStateManager.popMatrix();
        GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
    }
}
