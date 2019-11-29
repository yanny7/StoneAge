package com.yanny.age.stone.client.renderer;

import com.mojang.blaze3d.platform.GlStateManager;
import com.yanny.age.stone.Reference;
import com.yanny.age.stone.blocks.DroughtGrassBedTileEntity;
import com.yanny.age.stone.client.models.DroughtGrassBedModel;
import com.yanny.age.stone.subscribers.ItemSubscriber;
import net.minecraft.block.BedBlock;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.state.properties.BedPart;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class DroughtGrassBedRenderer extends TileEntityRenderer<DroughtGrassBedTileEntity> {
    private static final ResourceLocation TEXTURE_NORMAL = new ResourceLocation(Reference.MODID, "textures/entity/drought_grass_bed.png");
    private static final ItemStack GRASS = new ItemStack(ItemSubscriber.drought_grass);
    private final DroughtGrassBedModel model = new DroughtGrassBedModel();

    @Override
    public void render(DroughtGrassBedTileEntity tileEntityIn, double x, double y, double z, float partialTicks, int destroyStage) {
        if (tileEntityIn.hasWorld()) {
            BlockState blockstate = tileEntityIn.getBlockState();
            this.render(blockstate.get(BedBlock.PART) == BedPart.HEAD, x, y, z, blockstate.get(BedBlock.HORIZONTAL_FACING));
        } else {
            this.render(true, x, y, z + 0.5D, Direction.SOUTH);
            this.render(false, x, y, z - 0.5D, Direction.SOUTH);
        }
    }

    @SuppressWarnings("deprecation")
    private void render(boolean isHead, double x, double y, double z, Direction direction) {
        if (isHead) {
            this.model.setVisibleHead();
        } else {
            this.model.setVisibleFoot();
        }

        GlStateManager.pushMatrix();
        GlStateManager.translatef((float)x, (float)y, (float)z);
        GlStateManager.translatef(0.5F, 0.5F, 0.5F);
        switch (direction) {
            case NORTH:
                GlStateManager.rotatef(0.0F, 0.0F, 1.0F, 0.0F);
                break;
            case SOUTH:
                GlStateManager.rotatef(180.0F, 0.0F, 1.0F, 0.0F);
                break;
            case EAST:
                GlStateManager.rotatef(270.0F, 0.0F, 1.0F, 0.0F);
                break;
            case WEST:
                GlStateManager.rotatef(90.0F, 0.0F, 1.0F, 0.0F);
                break;
        }
        GlStateManager.translatef(-0.5F, -0.5F, -0.5F);
        GlStateManager.enableRescaleNormal();

        GlStateManager.pushMatrix();
        GlStateManager.translatef(0F, 0.09375F, 0F);
        this.bindTexture(TEXTURE_NORMAL);
        this.model.render();
        GlStateManager.popMatrix();

        if (isHead) {
            GlStateManager.pushMatrix();
            GlStateManager.translatef(0.5F, 0.5F, 0.5F);
            GlStateManager.scalef(0.749f, 0.749f, 0.749f);
            GlStateManager.rotatef(-85F, 1, 0, 0F);
            GlStateManager.translatef(-0.5F, -0.5F, -0.5F);
            GlStateManager.translatef(0.5f, 0.75F, 0.0325f);
            Minecraft.getInstance().getItemRenderer().renderItem(GRASS, ItemCameraTransforms.TransformType.FIXED);
            GlStateManager.popMatrix();

            GlStateManager.pushMatrix();
            GlStateManager.translatef(0.5F, 0.5F, 0.5F);
            GlStateManager.scalef(0.749f, 0.749f, 0.749f);
            GlStateManager.rotatef(90F, 0, 1, 0F);
            GlStateManager.rotatef(-85F, 1, 0, 0F);
            GlStateManager.translatef(-0.5F, -0.5F, -0.5F);
            GlStateManager.translatef(0.5f, 0.75F, 0.0325f);
            Minecraft.getInstance().getItemRenderer().renderItem(GRASS, ItemCameraTransforms.TransformType.FIXED);
            GlStateManager.popMatrix();

            GlStateManager.pushMatrix();
            GlStateManager.translatef(0.5F, 0.5F, 0.5F);
            GlStateManager.scalef(0.749f, 0.749f, 0.749f);
            GlStateManager.rotatef(-90F, 0, 1, 0F);
            GlStateManager.rotatef(-85F, 1, 0, 0F);
            GlStateManager.translatef(-0.5F, -0.5F, -0.5F);
            GlStateManager.translatef(0.5f, 0.75F, 0.0325f);
            Minecraft.getInstance().getItemRenderer().renderItem(GRASS, ItemCameraTransforms.TransformType.FIXED);
            GlStateManager.popMatrix();

            GlStateManager.pushMatrix();
            GlStateManager.translatef(0.5F, 0.5F, 0.5F);
            GlStateManager.scalef(0.749f, 0.749f, 0.749f);
            GlStateManager.rotatef(-90F, 0, 1, 0F);
            GlStateManager.rotatef(-85F, 1, 0, 0F);
            GlStateManager.translatef(-0.5F, -0.5F, -0.5F);
            GlStateManager.translatef(1.1666666f, 0.75F, 0.03251f);
            Minecraft.getInstance().getItemRenderer().renderItem(GRASS, ItemCameraTransforms.TransformType.FIXED);
            GlStateManager.popMatrix();
        } else {
            GlStateManager.pushMatrix();
            GlStateManager.translatef(0.5F, 0.5F, 0.5F);
            GlStateManager.scalef(0.749f, 0.749f, 0.749f);
            GlStateManager.rotatef(85F, 1, 0, 0F);
            GlStateManager.translatef(-0.5F, -0.5F, -0.5F);
            GlStateManager.translatef(0.5f, 0.75F, 0.9675f);
            Minecraft.getInstance().getItemRenderer().renderItem(GRASS, ItemCameraTransforms.TransformType.FIXED);
            GlStateManager.popMatrix();

            GlStateManager.pushMatrix();
            GlStateManager.translatef(0.5F, 0.5F, 0.5F);
            GlStateManager.scalef(0.749f, 0.749f, 0.749f);
            GlStateManager.rotatef(90F, 0, 1, 0F);
            GlStateManager.rotatef(85F, 1, 0, 0F);
            GlStateManager.translatef(-0.5F, -0.5F, -0.5F);
            GlStateManager.translatef(0.5f, 0.75F, 0.9675f);
            Minecraft.getInstance().getItemRenderer().renderItem(GRASS, ItemCameraTransforms.TransformType.FIXED);
            GlStateManager.popMatrix();

            GlStateManager.pushMatrix();
            GlStateManager.translatef(0.5F, 0.5F, 0.5F);
            GlStateManager.scalef(0.749f, 0.749f, 0.749f);
            GlStateManager.rotatef(-90F, 0, 1, 0F);
            GlStateManager.rotatef(85F, 1, 0, 0F);
            GlStateManager.translatef(-0.5F, -0.5F, -0.5F);
            GlStateManager.translatef(0.5f, 0.75F, 0.9675f);
            Minecraft.getInstance().getItemRenderer().renderItem(GRASS, ItemCameraTransforms.TransformType.FIXED);
            GlStateManager.popMatrix();

            GlStateManager.pushMatrix();
            GlStateManager.translatef(0.5F, 0.5F, 0.5F);
            GlStateManager.scalef(0.749f, 0.749f, 0.749f);
            GlStateManager.rotatef(-90F, 0, 1, 0F);
            GlStateManager.rotatef(85F, 1, 0, 0F);
            GlStateManager.translatef(-0.5F, -0.5F, -0.5F);
            GlStateManager.translatef(-0.1666666f, 0.75F, 0.96751f);
            Minecraft.getInstance().getItemRenderer().renderItem(GRASS, ItemCameraTransforms.TransformType.FIXED);
            GlStateManager.popMatrix();
        }

        GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.popMatrix();
    }
}
