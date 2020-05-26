package com.yanny.age.stone.client.renderer;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.yanny.age.stone.Reference;
import com.yanny.age.stone.blocks.DriedGrassBedTileEntity;
import com.yanny.age.stone.client.models.DriedGrassBedModel;
import com.yanny.age.stone.subscribers.ItemSubscriber;
import net.minecraft.block.BedBlock;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Vector3f;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;
import net.minecraft.state.properties.BedPart;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;

import static net.minecraft.client.renderer.model.ItemCameraTransforms.TransformType;

@OnlyIn(Dist.CLIENT)
public class DriedGrassBedRenderer extends TileEntityRenderer<DriedGrassBedTileEntity> {
    private static final ResourceLocation TEXTURE_NORMAL = new ResourceLocation(Reference.MODID, "textures/entity/dried_grass_bed.png");
    private static final ItemStack GRASS = new ItemStack(ItemSubscriber.dried_grass);
    private final DriedGrassBedModel model = new DriedGrassBedModel();

    public DriedGrassBedRenderer(TileEntityRendererDispatcher p_i226006_1_) {
        super(p_i226006_1_);
    }

    @Override
    public void render(@Nonnull DriedGrassBedTileEntity tileEntityIn, float partialTicks, @Nonnull MatrixStack matrixStack,
                       @Nonnull IRenderTypeBuffer iRenderTypeBuffer, int overlayUV, int lightmapUV) {
        if (tileEntityIn.hasWorld()) {
            BlockState blockState = tileEntityIn.getBlockState();
            render(blockState.get(BedBlock.PART) == BedPart.HEAD, matrixStack, iRenderTypeBuffer, overlayUV, lightmapUV, blockState.get(BedBlock.HORIZONTAL_FACING));
        } else {
            matrixStack.translate(0, 0, 0.5);
            render(true, matrixStack, iRenderTypeBuffer, overlayUV, lightmapUV, Direction.SOUTH);
            matrixStack.translate(0, 0, -1);
            render(false, matrixStack, iRenderTypeBuffer, overlayUV, lightmapUV, Direction.SOUTH);
        }
    }

    private void render(boolean isHead, MatrixStack matrixStack, IRenderTypeBuffer renderTypeBuffer, int overlayUV, int lightmapUV, Direction direction) {
        if (isHead) {
            this.model.setVisibleHead();
        } else {
            this.model.setVisibleFoot();
        }

        matrixStack.push();
        matrixStack.translate(0.5, 0.5, 0.5);

        switch (direction) {
            case SOUTH:
                matrixStack.rotate(Vector3f.YP.rotationDegrees(180));
                break;
            case EAST:
                matrixStack.rotate(Vector3f.YP.rotationDegrees(270));
                break;
            case WEST:
                matrixStack.rotate(Vector3f.YP.rotationDegrees(90));
                break;
        }

        matrixStack.translate(-0.5, -0.5, -0.5);
        RenderSystem.enableRescaleNormal();

        matrixStack.push();
        matrixStack.translate(0, 0.09375, 0);
        model.render(matrixStack, renderTypeBuffer.getBuffer(RenderType.getEntityCutoutNoCull(TEXTURE_NORMAL)), overlayUV, lightmapUV, 1f, 1f, 1f, 1f);
        matrixStack.pop();

        if (isHead) {
            matrixStack.push();
            matrixStack.translate(0.5, 0.5, 0.5);
            matrixStack.scale(0.749f, 0.749f,0.749f);
            matrixStack.rotate(Vector3f.XP.rotationDegrees(-85));
            matrixStack.translate(-0.5, -0.5, -0.5);
            matrixStack.translate(0.5, 0.75, 0.0325);
            Minecraft.getInstance().getItemRenderer().renderItem(GRASS, TransformType.FIXED, overlayUV, lightmapUV, matrixStack, renderTypeBuffer);
            matrixStack.pop();

            matrixStack.push();
            matrixStack.translate(0.5, 0.5, 0.5);
            matrixStack.scale(0.749f, 0.749f, 0.749f);
            matrixStack.rotate(Vector3f.YP.rotationDegrees(90F));
            matrixStack.rotate(Vector3f.XP.rotationDegrees(-85));
            matrixStack.translate(-0.5, -0.5, -0.5);
            matrixStack.translate(0.5, 0.75, 0.0325);
            Minecraft.getInstance().getItemRenderer().renderItem(GRASS, TransformType.FIXED, overlayUV, lightmapUV, matrixStack, renderTypeBuffer);
            matrixStack.pop();

            matrixStack.push();
            matrixStack.translate(0.5, 0.5, 0.5);
            matrixStack.scale(0.749f, 0.749f, 0.749f);
            matrixStack.rotate(Vector3f.YP.rotationDegrees(-90F));
            matrixStack.rotate(Vector3f.XP.rotationDegrees(-85));
            matrixStack.translate(-0.5, -0.5, -0.5);
            matrixStack.translate(0.5, 0.75, 0.0325);
            Minecraft.getInstance().getItemRenderer().renderItem(GRASS, TransformType.FIXED, overlayUV, lightmapUV, matrixStack, renderTypeBuffer);
            matrixStack.pop();

            matrixStack.push();
            matrixStack.translate(0.5, 0.5, 0.5);
            matrixStack.scale(0.749f, 0.749f, 0.749f);
            matrixStack.rotate(Vector3f.YP.rotationDegrees(-90F));
            matrixStack.rotate(Vector3f.XP.rotationDegrees(-85));
            matrixStack.translate(-0.5, -0.5, -0.5);
            matrixStack.translate(1.1666666f, 0.75, 0.03251);
            Minecraft.getInstance().getItemRenderer().renderItem(GRASS, TransformType.FIXED, overlayUV, lightmapUV, matrixStack, renderTypeBuffer);
            matrixStack.pop();
        } else {
            matrixStack.push();
            matrixStack.translate(0.5, 0.5, 0.5);
            matrixStack.scale(0.749f, 0.749f,0.749f);
            matrixStack.rotate(Vector3f.XP.rotationDegrees(85));
            matrixStack.translate(-0.5, -0.5, -0.5);
            matrixStack.translate(0.5, 0.75, 0.9675);
            Minecraft.getInstance().getItemRenderer().renderItem(GRASS, TransformType.FIXED, overlayUV, lightmapUV, matrixStack, renderTypeBuffer);
            matrixStack.pop();

            matrixStack.push();
            matrixStack.translate(0.5, 0.5, 0.5);
            matrixStack.scale(0.749f, 0.749f, 0.749f);
            matrixStack.rotate(Vector3f.YP.rotationDegrees(90F));
            matrixStack.rotate(Vector3f.XP.rotationDegrees(85));
            matrixStack.translate(-0.5, -0.5, -0.5);
            matrixStack.translate(0.5, 0.75, 0.9675);
            Minecraft.getInstance().getItemRenderer().renderItem(GRASS, TransformType.FIXED, overlayUV, lightmapUV, matrixStack, renderTypeBuffer);
            matrixStack.pop();

            matrixStack.push();
            matrixStack.translate(0.5, 0.5, 0.5);
            matrixStack.scale(0.749f, 0.749f, 0.749f);
            matrixStack.rotate(Vector3f.YP.rotationDegrees(-90F));
            matrixStack.rotate(Vector3f.XP.rotationDegrees(85));
            matrixStack.translate(-0.5, -0.5, -0.5);
            matrixStack.translate(0.5, 0.75, 0.9675);
            Minecraft.getInstance().getItemRenderer().renderItem(GRASS, TransformType.FIXED, overlayUV, lightmapUV, matrixStack, renderTypeBuffer);
            matrixStack.pop();

            matrixStack.push();
            matrixStack.translate(0.5, 0.5, 0.5);
            matrixStack.scale(0.749f, 0.749f, 0.749f);
            matrixStack.rotate(Vector3f.YP.rotationDegrees(-90F));
            matrixStack.rotate(Vector3f.XP.rotationDegrees(85));
            matrixStack.translate(-0.5, -0.5, -0.5);
            matrixStack.translate(-0.1666666f, 0.75, 0.9675);
            Minecraft.getInstance().getItemRenderer().renderItem(GRASS, TransformType.FIXED, overlayUV, lightmapUV, matrixStack, renderTypeBuffer);
            matrixStack.pop();
        }

        RenderSystem.color4f(1, 1, 1, 1);
        matrixStack.pop();
    }
}
