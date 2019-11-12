package com.yanny.age.zero.client.renderer;

import com.mojang.blaze3d.platform.GlStateManager;
import com.yanny.age.zero.Reference;
import com.yanny.age.zero.blocks.StoneChestBlock;
import com.yanny.age.zero.blocks.StoneChestTileEntity;
import com.yanny.age.zero.client.models.StoneChestModel;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.tileentity.IChestLid;
import net.minecraft.util.ResourceLocation;

public class StoneChestRenderer extends TileEntityRenderer<StoneChestTileEntity> {
    private static final ResourceLocation TEXTURE_NORMAL = new ResourceLocation(Reference.MODID, "textures/entity/stone_chest.png");
    private final StoneChestModel model = new StoneChestModel();

    @Override
    public void render(StoneChestTileEntity tileEntityIn, double x, double y, double z, float partialTicks, int destroyStage) {
        GlStateManager.enableDepthTest();
        GlStateManager.depthFunc(515);
        GlStateManager.depthMask(true);

        BlockState blockstate = tileEntityIn.getBlockState();
        StoneChestModel chestModel = this.getChestModel(destroyStage);

        if (destroyStage >= 0) {
            GlStateManager.matrixMode(5890);
            GlStateManager.pushMatrix();
            GlStateManager.scalef(4.0F, 4.0F, 1.0F);
            GlStateManager.translatef(0.0625F, 0.0625F, 0.0625F);
            GlStateManager.matrixMode(5888);
        } else {
            GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        }

        GlStateManager.pushMatrix();
        GlStateManager.enableRescaleNormal();
        GlStateManager.translatef((float)x, (float)y + 1.0F, (float)z + 1.0F);
        GlStateManager.scalef(1.0F, -1.0F, -1.0F);

        float f = blockstate.get(StoneChestBlock.HORIZONTAL_FACING).getHorizontalAngle();

        if ((double)Math.abs(f) > 1.0E-5D) {
            GlStateManager.translatef(0.5F, 0.5F, 0.5F);
            GlStateManager.rotatef(f, 0.0F, 1.0F, 0.0F);
            GlStateManager.translatef(-0.5F, -0.5F, -0.5F);
        }

        GlStateManager.translatef(0.5f, -0.5f, 0.5f);

        this.applyLidRotation(tileEntityIn, partialTicks, chestModel);
        chestModel.renderAll();
        GlStateManager.disableRescaleNormal();
        GlStateManager.popMatrix();
        GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);

        if (destroyStage >= 0) {
            GlStateManager.matrixMode(5890);
            GlStateManager.popMatrix();
            GlStateManager.matrixMode(5888);
        }
    }

    private StoneChestModel getChestModel(int destroyStage) {
        ResourceLocation resourcelocation;

        if (destroyStage >= 0) {
            resourcelocation = DESTROY_STAGES[destroyStage];
        } else {
            resourcelocation = TEXTURE_NORMAL;
        }

        this.bindTexture(resourcelocation);
        return model;
    }

    private void applyLidRotation(StoneChestTileEntity tileEntity, float angle, StoneChestModel model) {
        float f = ((IChestLid)tileEntity).getLidAngle(angle);
        f = 1.0F - f;
        f = 1.0F - f * f * f;
        model.getLid().rotateAngleY = -(f * ((float)Math.PI / 2F));
    }
}
