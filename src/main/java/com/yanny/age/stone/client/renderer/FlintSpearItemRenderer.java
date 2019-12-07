package com.yanny.age.stone.client.renderer;

import com.mojang.blaze3d.platform.GlStateManager;
import com.yanny.age.stone.client.models.FlintSpearItemModel;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class FlintSpearItemRenderer extends ItemStackTileEntityRenderer {
    private final FlintSpearItemModel model = new FlintSpearItemModel();

    @Override
    public void renderByItem(ItemStack itemStackIn) {
        Minecraft.getInstance().getTextureManager().bindTexture(FlintSpearRenderer.TEXTURE);

        GlStateManager.pushMatrix();
        GlStateManager.translatef(0.5f, 0.5f, 0.5f);

/* render lines
        GlStateManager.lineWidth(2F);
        GlStateManager.disableTexture();
        GlStateManager.disableLighting();
        BufferBuilder bb = Tessellator.getInstance().getBuffer();
        bb.begin(GL11.GL_LINES, DefaultVertexFormats.POSITION_COLOR);
        bb.pos(0, 0, 0).color(0, 1, 0, 1F).endVertex();
        bb.pos(1, 0, 0).color(0, 1, 0, 1F).endVertex();

        bb.pos(0, 0, 0).color(1, 0, 0, 1F).endVertex();
        bb.pos(0, 1, 0).color(1, 0, 0, 1F).endVertex();

        bb.pos(0, 0, 0).color(0, 0, 1, 1F).endVertex();
        bb.pos(0, 0, 1).color(0, 0, 1, 1F).endVertex();
        Tessellator.getInstance().draw();
        GlStateManager.enableLighting();
        GlStateManager.enableTexture();
*/

        GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.model.render();
        GlStateManager.popMatrix();

    }
}
