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
public class MillstoneModel extends Model {
	private final ModelRenderer body;

	public MillstoneModel() {
		super(RenderType::entityCutoutNoCull);
		textureWidth = 64;
		textureHeight = 64;

		body = new ModelRenderer(this);
		body.setRotationPoint(0.0F, 0.0F, 0.0F);
		body.addBox(-5.0F, 7.0F, -5.0F, 10, 4, 10);
	}

	public void rotate(float angle) {
		this.body.rotateAngleY = angle;
	}

	@Override
	public void render(@Nonnull MatrixStack matrixStack, @Nonnull IVertexBuilder iVertexBuilder, int i, int i1, float v, float v1, float v2, float v3) {
		body.render(matrixStack, iVertexBuilder, i, i1, v, v1, v2, v3);
	}
}