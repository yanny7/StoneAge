package com.yanny.age.stone.client.models;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.yanny.age.stone.entities.DeerEntity;
import net.minecraft.client.renderer.entity.model.AgeableModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;

@OnlyIn(Dist.CLIENT)
public class DeerModel extends AgeableModel<DeerEntity> {
	private final ModelRenderer body;
	private final ModelRenderer head;
	private final ModelRenderer feet1;
	private final ModelRenderer feet2;
	private final ModelRenderer feet3;
	private final ModelRenderer feet4;

	public DeerModel() {
		textureWidth = 64;
		textureHeight = 64;

		body = new ModelRenderer(this, 24, 0);
		body.setRotationPoint(0.0F, 13.0F, 0.0F);
		body.addBox(-3.0F, -3.0F, -6.0F, 6, 6, 14, 0.0F, false);

		ModelRenderer tail = new ModelRenderer(this, 50, 0);
		tail.setRotationPoint(0.0F, 0.0F, 0.0F);
		setRotationAngle(tail, -0.7854F, 0.0F);
		body.addChild(tail);
		tail.addBox(-1.0F, -10.0F, 3.0F, 2, 3, 1, 0.0F, false);

		head = new ModelRenderer(this, 50, 8);
		head.setRotationPoint(0.0F, 11.0F, -5.0F);
		setRotationAngle(head, -0.7854F, 0.0F);
		head.addBox(-1.0F, -1.1213F, -4.0503F, 2, 2, 4, 0.0F, false);

		ModelRenderer bone3 = new ModelRenderer(this, 20, 5);
		bone3.setRotationPoint(0.0F, -2.1213F, 4.9497F);
		setRotationAngle(bone3, 0.7854F, 0.0F);
		head.addChild(bone3);
		bone3.addBox(-1.5F, -6.0F, -13.0F, 3, 3, 6, 0.0F, false);

		ModelRenderer bone = new ModelRenderer(this, 4, 0);
		bone.setRotationPoint(0.0F, -5.0F, -8.0F);
		setRotationAngle(bone, 0.0F, -0.5236F);
		bone3.addChild(bone);
		bone.addBox(-1.0F, -3.0F, -1.0F, 1, 2, 1, 0.0F, false);

		ModelRenderer bone2 = new ModelRenderer(this, 4, 0);
		bone2.setRotationPoint(0.0F, -5.0F, -8.0F);
		setRotationAngle(bone2, 0.0F, 0.5236F);
		bone3.addChild(bone2);
		bone2.addBox(0.0F, -3.0F, -1.0F, 1, 2, 1, 0.0F, false);

		feet1 = new ModelRenderer(this, 0, 0);
		feet1.setRotationPoint(1.0F, 16.0F, -5.0F);
		feet1.addBox(0.0F, -1.0F, 0.0F, 1, 9, 1, 0.0F, false);

		feet2 = new ModelRenderer(this, 0, 0);
		feet2.setRotationPoint(1.0F, 16.0F, 6.0F);
		feet2.addBox(0.0F, -1.0F, 0.0F, 1, 9, 1, 0.0F, false);

		feet3 = new ModelRenderer(this, 0, 0);
		feet3.setRotationPoint(-1.0F, 16.0F, 6.0F);
		feet3.addBox(-1.0F, -1.0F, 0.0F, 1, 9, 1, 0.0F, false);

		feet4 = new ModelRenderer(this, 0, 0);
		feet4.setRotationPoint(-1.0F, 16.0F, -5.0F);
		feet4.addBox( -1.0F, -1.0F, 0.0F, 1, 9, 1, 0.0F, false);
	}

	@Nonnull
	@Override
	protected Iterable<ModelRenderer> getHeadParts() {
		return ImmutableList.of();
	}

	@Nonnull
	@Override
	protected Iterable<ModelRenderer> getBodyParts() {
		return ImmutableList.of(head, body, feet1, feet2, feet3, feet4);
	}

	@Override
	public void render(@Nonnull MatrixStack matrixStackIn, @Nonnull IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
		matrixStackIn.push();
		matrixStackIn.scale(1.5f, 1.5f, 1.5f);
		matrixStackIn.translate(0, -0.5, 0);
		super.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
		matrixStackIn.pop();
	}

	@Override
	public void setRotationAngles(@Nonnull DeerEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.setRotationAngles(limbSwing, limbSwingAmount, netHeadYaw, headPitch);
	}

	private void setRotationAngle(@Nonnull ModelRenderer ModelRenderer, float x, float z) {
		ModelRenderer.rotateAngleX = x;
		ModelRenderer.rotateAngleZ = z;
	}

	public void setRotationAngles(float limbSwing, float limbSwingAmount, float netHeadYaw, float headPitch) {
		this.head.rotateAngleX = headPitch * ((float)Math.PI / 180F) - 0.7854F;
		this.head.rotateAngleY = netHeadYaw * ((float)Math.PI / 180F);
		this.feet1.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
		this.feet2.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
		this.feet3.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount;
		this.feet4.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount;
	}
}