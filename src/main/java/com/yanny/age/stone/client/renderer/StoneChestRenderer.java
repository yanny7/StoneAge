package com.yanny.age.stone.client.renderer;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.yanny.age.stone.Reference;
import com.yanny.age.stone.blocks.StoneChestBlock;
import com.yanny.age.stone.blocks.StoneChestTileEntity;
import com.yanny.age.stone.client.models.StoneChestModel;
import com.yanny.age.stone.subscribers.BlockSubscriber;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Vector3f;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.tileentity.IChestLid;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;

@OnlyIn(Dist.CLIENT)
public class StoneChestRenderer extends TileEntityRenderer<StoneChestTileEntity> {
    private static final ResourceLocation TEXTURE_NORMAL = new ResourceLocation(Reference.MODID, "textures/entity/stone_chest.png");
    private final StoneChestModel model = new StoneChestModel();

    public StoneChestRenderer(TileEntityRendererDispatcher p_i226006_1_) {
        super(p_i226006_1_);
    }

    @Override
    public void render(@Nonnull StoneChestTileEntity tileEntity, float partialTicks, @Nonnull MatrixStack matrixStack,
                       @Nonnull IRenderTypeBuffer renderTypeBuffer, int overlayUV, int lightmapUV) {
        //noinspection ConstantConditions
        BlockState blockstate = tileEntity.hasWorld() ? tileEntity.getBlockState() :
                BlockSubscriber.stone_chest.getDefaultState().with(StoneChestBlock.HORIZONTAL_FACING, Direction.SOUTH);

        matrixStack.push();
        matrixStack.translate(0, 1, 1);
        matrixStack.scale(1, -1, -1);

        float f = blockstate.get(StoneChestBlock.HORIZONTAL_FACING).getHorizontalAngle();

        if ((double)Math.abs(f) > 1.0E-5D) {
            matrixStack.translate(0.5, 0.5, 0.5);
            matrixStack.rotate(Vector3f.YP.rotationDegrees(f));
            matrixStack.translate(-0.5, -0.5, -0.5);
        }

        matrixStack.translate(0.5, -0.5, 0.5);

        this.applyLidRotation(tileEntity, partialTicks, model);
        model.render(matrixStack, renderTypeBuffer.getBuffer(RenderType.getEntityCutoutNoCull(TEXTURE_NORMAL)), overlayUV, lightmapUV, 1f, 1f, 1f, 1f);

        matrixStack.pop();
    }

    private void applyLidRotation(StoneChestTileEntity tileEntity, float angle, StoneChestModel model) {
        float f = ((IChestLid)tileEntity).getLidAngle(angle);
        f = 1.0F - f;
        f = 1.0F - f * f * f;
        model.getLid().rotateAngleY = -(f * ((float)Math.PI / 2F));
    }
}
