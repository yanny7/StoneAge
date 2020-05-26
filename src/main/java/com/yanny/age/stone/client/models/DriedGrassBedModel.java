package com.yanny.age.stone.client.models;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.model.ModelRenderer;

import javax.annotation.Nonnull;

public class DriedGrassBedModel extends Model {
	private final ModelRenderer head;
	private final ModelRenderer foot;

	public DriedGrassBedModel() {
		super(RenderType::getEntityCutoutNoCull);
		textureWidth = 64;
		textureHeight = 64;

		head = new ModelRenderer(this, 0, 17);
		head.addBox(2.0F, -1.5F, 2.0F, 12, 3, 14, 0.0F, false);

		foot = new ModelRenderer(this, 0, 0);
		foot.addBox(2.0F, -1.5F, 0, 12, 3, 14, 0.0F, false);
	}

	public void setVisibleHead() {
		head.showModel = true;
		foot.showModel = false;
	}

	public void setVisibleFoot() {
		head.showModel = false;
		foot.showModel = true;
	}

	@Override
	public void render(@Nonnull MatrixStack matrixStack, @Nonnull IVertexBuilder iVertexBuilder, int i, int i1, float v, float v1, float v2, float v3) {
		head.render(matrixStack, iVertexBuilder, i, i1, v, v1, v2, v3);
		foot.render(matrixStack, iVertexBuilder, i, i1, v, v1, v2, v3);
	}
}