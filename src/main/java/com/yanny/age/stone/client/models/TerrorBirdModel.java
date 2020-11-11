package com.yanny.age.stone.client.models;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.yanny.age.stone.entities.TerrorBirdEntity;
import net.minecraft.client.renderer.entity.model.AgeableModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;

import javax.annotation.Nonnull;

public class TerrorBirdModel extends AgeableModel<TerrorBirdEntity> {
	private final ModelRenderer rfeet;
	private final ModelRenderer body;
	private final ModelRenderer head;
	private final ModelRenderer lfeet;

	public TerrorBirdModel() {
		textureWidth = 64;
		textureHeight = 64;

		rfeet = new ModelRenderer(this);
		rfeet.setRotationPoint(1.0F, 17.0F, 1.0F);
		rfeet.setTextureOffset(23, 54).addBox(0.0F, 6.0F, -3.0F, 2.0F, 1.0F, 3.0F, 0.0F, false);
		rfeet.setTextureOffset(0, 53).addBox(0.0F, -1.0F, -1.0F, 2.0F, 4.0F, 2.0F, 0.0F, false);

		ModelRenderer knee = new ModelRenderer(this);
		knee.setRotationPoint(1.0F, 4.25F, -0.5F);
		rfeet.addChild(knee);
		setRotationAngle(knee, -0.1745F, 0.0F, 0.0F);
		knee.setTextureOffset(0, 39).addBox(-0.5F, -2.0F, -0.5F, 1.0F, 4.0F, 1.0F, 0.0F, false);

		body = new ModelRenderer(this);
		body.setRotationPoint(0.0F, 19.25F, -0.5F);
		body.setTextureOffset(0, 51).addBox(-3.5F, -7.25F, -4.5F, 7.0F, 5.0F, 8.0F, 0.0F, true);

		ModelRenderer bone = new ModelRenderer(this);
		bone.setRotationPoint(0.0F, -4.25F, 5.0F);
		body.addChild(bone);
		setRotationAngle(bone, -0.1745F, 0.0F, 0.0F);
		bone.setTextureOffset(30, 56).addBox(-2.5F, -2.0F, -2.5F, 5.0F, 4.0F, 4.0F, 0.0F, false);
		bone.setTextureOffset(48, 61).addBox(-1.5F, -1.5F, 1.5F, 3.0F, 2.0F, 1.0F, 0.0F, false);
		bone.setTextureOffset(50, 56).addBox(-1.5F, -1.5F, 2.5F, 3.0F, 1.0F, 4.0F, 0.0F, false);

		head = new ModelRenderer(this);
		head.setRotationPoint(0.0F, 13.0F, -4.0F);
		head.setTextureOffset(37, 43).addBox(-2.0F, -9.0F, -5.0F, 4.0F, 3.0F, 4.0F, 0.0F, false);
		head.setTextureOffset(10, 38).addBox(-1.5F, -9.0F, -10.0F, 3.0F, 2.0F, 5.0F, 0.0F, false);
		head.setTextureOffset(26, 32).addBox(-1.0F, -7.75F, -10.15F, 2.0F, 1.0F, 1.0F, 0.0F, false);

		ModelRenderer bone2 = new ModelRenderer(this);
		bone2.setRotationPoint(0.0F, -5.0F, -2.0F);
		head.addChild(bone2);
		setRotationAngle(bone2, 0.3491F, 0.0F, 0.0F);
		bone2.setTextureOffset(0, 8).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 8.0F, 2.0F, 0.0F, false);

		ModelRenderer bone3 = new ModelRenderer(this);
		bone3.setRotationPoint(0.0F, -7.0F, -5.0F);
		head.addChild(bone3);
		setRotationAngle(bone3, 0.0873F, 0.0F, 0.0F);
		bone3.setTextureOffset(4, 30).addBox(-1.5F, 0.0F, -4.0F, 3.0F, 1.0F, 4.0F, 0.0F, false);

		lfeet = new ModelRenderer(this);
		lfeet.setRotationPoint(-1.0F, 17.0F, 1.0F);
		lfeet.setTextureOffset(23, 54).addBox(-2.0F, 6.0F, -3.0F, 2.0F, 1.0F, 3.0F, 0.0F, false);
		lfeet.setTextureOffset(0, 53).addBox(-2.0F, -1.0F, -1.0F, 2.0F, 4.0F, 2.0F, 0.0F, false);

		ModelRenderer knee2 = new ModelRenderer(this);
		knee2.setRotationPoint(-1.0F, 4.25F, -0.5F);
		lfeet.addChild(knee2);
		setRotationAngle(knee2, -0.1745F, 0.0F, 0.0F);
		knee2.setTextureOffset(0, 39).addBox(-0.5F, -2.0F, -0.5F, 1.0F, 4.0F, 1.0F, 0.0F, false);
	}

	@Override
	public void setRotationAngles(@Nonnull TerrorBirdEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch){
		this.head.rotateAngleX = headPitch * ((float)Math.PI / 180F);
		this.head.rotateAngleY = netHeadYaw * ((float)Math.PI / 180F);
		this.lfeet.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
		this.rfeet.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount;
	}

	@Override
	public void render(@Nonnull MatrixStack matrixStackIn, @Nonnull IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
		matrixStackIn.scale(1.5f, 1.5f, 1.5f);
		matrixStackIn.translate(0, -0.5f, 0);
		super.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
	}

	@Nonnull
	@Override
	protected Iterable<ModelRenderer> getHeadParts() {
		return ImmutableList.of();
	}

	@Nonnull
	@Override
	protected Iterable<ModelRenderer> getBodyParts() {
		return ImmutableList.of(body, rfeet, lfeet, head);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}