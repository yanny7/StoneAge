package com.yanny.age.stone.client.renderer;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.yanny.age.stone.blocks.DroughtGrassBedTileEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;

@OnlyIn(Dist.CLIENT)
public class DroughtGrassBedItemRenderer extends ItemStackTileEntityRenderer {
    private static final TileEntity tileEntity = new DroughtGrassBedTileEntity();

    @Override
    public void render(ItemStack itemStack, @Nonnull MatrixStack matrixStack, @Nonnull IRenderTypeBuffer renderTypeBuffer, int overlayUV, int lightmapUV) {
        TileEntityRendererDispatcher.instance.renderNullable(tileEntity, matrixStack, renderTypeBuffer, overlayUV, lightmapUV);
    }
}
