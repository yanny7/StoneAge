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
public class FlintSpearItemModel extends Model {
	private final ModelRenderer bone;

	public FlintSpearItemModel() {
		super(RenderType::entityCutoutNoCull);
		textureWidth = 32;
		textureHeight = 32;

		bone = new ModelRenderer(this, 0, 0);
		bone.setRotationPoint(0.0F, 24.0F, 0.0F);
		bone.addBox(-0.5F, -16F, -0.5F, 1, 29, 1, 0.0F, false);

		ModelRenderer bone1 = new ModelRenderer(this, 4, 0);
		bone1.addBox(-1.5F, 12F, -0.5F, 1, 2, 1, 0.0F, false);
		bone.addChild(bone1);

		ModelRenderer bone2 = new ModelRenderer(this, 8, 0);
		bone2.addBox(0.5F, 12F, -0.5F, 1, 2, 1, 0.0F, false);
		bone.addChild(bone2);

		ModelRenderer bone3 = new ModelRenderer(this, 4, 3);
		bone3.addBox(-1.0F, 14F, -0.5F, 2, 1, 1, 0.0F, false);
		bone.addChild(bone3);

		ModelRenderer bone4 = new ModelRenderer(this, 4, 5);
		bone4.addBox(-0.5F, 13f, -0.5F, 1, 3, 1, 0.0F, false);
		bone.addChild(bone4);

		ModelRenderer bone5 = new ModelRenderer(this, 4, 9);
		bone5.addBox(-0.5F, 13F, -1.0F, 1, 2, 2, 0.0F, false);
		bone.addChild(bone5);
	}

	@Override
	public void render(@Nonnull MatrixStack matrixStack, @Nonnull IVertexBuilder iVertexBuilder, int i, int i1, float v, float v1, float v2, float v3) {
		bone.render(matrixStack, iVertexBuilder, i, i1, v, v1, v2, v3);
	}
}