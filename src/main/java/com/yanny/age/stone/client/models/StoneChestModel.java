package com.yanny.age.stone.client.models;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;

@OnlyIn(Dist.CLIENT)
public class StoneChestModel extends Model {
	private final ModelRenderer body;
	private final ModelRenderer lid;

	public StoneChestModel() {
		super(RenderType::getEntityCutout);
		textureWidth = 64;
		textureHeight = 64;

		body = new ModelRenderer(this, 0, 48);
		body.setRotationPoint(0.0F, 24.0F, 0.0F);
		body.addBox(-7.0F, -2.0F, -7.0F, 14, 2, 14, 0.0F, false);

		ModelRenderer body1 = new ModelRenderer(this, 0, 24);
		body1.addBox(-7.0F, -12.0F, -7.0F, 2, 10, 14, 0.0F, false);
		body1.addBox(5.0F, -12.0F, -7.0F, 2, 10, 14, 0.0F, false);
		body.addChild(body1);

		ModelRenderer body2 = new ModelRenderer(this, 32, 36);
		body2.addBox(-5.0F, -12.0F, -7.0F, 10, 10, 2, 0.0F, false);
		body2.addBox(-5.0F, -12.0F, 5.0F, 10, 10, 2, 0.0F, false);
		body.addChild(body2);

		lid = new ModelRenderer(this, 0, 0);
		lid.setRotationPoint(3.0F, 10.0F, 5.0F);
		lid.addBox(-10.0F, 0.0F, -12.0F, 14, 2, 14, 0.0F, false);
	}

	public ModelRenderer getLid() {
		return lid;
	}

	@Override
	public void render(@Nonnull MatrixStack matrixStack, @Nonnull IVertexBuilder iVertexBuilder, int i, int i1, float v, float v1, float v2, float v3) {
		body.render(matrixStack, iVertexBuilder, i, i1, v, v1, v2, v3);
		lid.render(matrixStack, iVertexBuilder, i, i1, v, v1, v2, v3);
	}
}