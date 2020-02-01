package com.yanny.age.stone.client.models;

import com.google.common.collect.ImmutableList;
import com.yanny.age.stone.entities.MammothEntity;
import net.minecraft.client.renderer.entity.model.AgeableModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;

import javax.annotation.Nonnull;

public class MammothModel extends AgeableModel<MammothEntity> {
	private final ModelRenderer body;
	private final ModelRenderer head;
	private final ModelRenderer foot1;
	private final ModelRenderer foot2;
	private final ModelRenderer foot3;
	private final ModelRenderer foot4;

	public MammothModel() {
		textureWidth = 64;
		textureHeight = 64;

		body = new ModelRenderer(this, 0, 39);
		body.setRotationPoint(0.0F, 5.0F, 0.0F);
		body.addBox(-6.0F, -8.0F, -9.0F, 12, 17, 8, 0.0F, false);

		ModelRenderer body1 = new ModelRenderer(this, 2, 41);
		body1.addBox(-6.0F, -7.0F, -1.0F, 12, 16, 6, 0.0F, false);
		body1.addBox(-6.0F, -6.0F, 5.0F, 12, 15, 6, 0.0F, false);
		body.addChild(body1);

		head = new ModelRenderer(this, 0 , 17);
		head.setRotationPoint(0.0F, -1.0F, -7.0F);
		setRotationAngle(head, -0.3491F, 0.0F, 0.0F);
		head.addBox(-4.0F, -3.0F, -8.0F, 8, 9, 8, 0.0F, false);

		ModelRenderer head1 = new ModelRenderer(this, 4, 1);
		head1.addBox(-3.0F, 6.0F, -7.0F, 6, 8, 7, 0.0F, false);
		head.addChild(head1);

		ModelRenderer head2 = new ModelRenderer(this, 32, 0);
		head2.addBox(3.0F, 6.0F, -5.0F, 1, 11, 1, 0.0F, false);
		head2.addBox(-4.0F, 6.0F, -5.0F, 1, 11, 1, 0.0F, false);
		head.addChild(head2);

		ModelRenderer ears2 = new ModelRenderer(this, 10, 7);
		ears2.setRotationPoint(0.0F, 2.0F, -6.0F);
		setRotationAngle(ears2, 0.0F, 0.2618F, 0.0F);
		head.addChild(ears2);
		ears2.addBox(-9.0F, -5.0F, 1.0F, 5, 6, 1, 0.0F, false);

		ModelRenderer ears = new ModelRenderer(this, 12,  7);
		ears.setRotationPoint(0.0F, 2.0F, -6.0F);
		setRotationAngle(ears, 0.0F, -0.2618F, 0.0F);
		head.addChild(ears);
		ears.addBox(4.0F, -5.0F, 1.0F, 5, 6, 1, 0.0F, false);

		ModelRenderer horns = new ModelRenderer(this, 32, 0);
		horns.setRotationPoint(0.0F, 17.0F, -4.0F);
		setRotationAngle(horns, -1.2217F, 0.0F, 0.0F);
		head.addChild(horns);
		horns.addBox(-4.0F, 0.0F, -1.0F, 1, 7, 1, 0.0F, false);
		horns.addBox(3.0F, 0.0F, -1.0F, 1, 7, 1, 0.0F, false);

		ModelRenderer horns2 = new ModelRenderer(this, 32, 0);
		horns2.setRotationPoint(0.0F, 7.0F, 0.0F);
		setRotationAngle(horns2, -0.8727F, 0.0F, 0.0F);
		horns.addChild(horns2);
		horns2.addBox(-4.0F, 0.0F, -1.0F, 1, 4, 1, 0.0F, false);
		horns2.addBox(3.0F, 0.0F, -1.0F, 1, 4, 1, 0.0F, false);

		ModelRenderer bone = new ModelRenderer(this, 10, 4);
		bone.setRotationPoint(0.0F, 14.0F, -6.0F);
		setRotationAngle(bone, 0.3491F, 0.0F, 0.0F);
		head.addChild(bone);
		bone.addBox(-2.0F, -0.342F, 0.0603F, 4, 5, 4, 0.0F, false);

		ModelRenderer bone2 = new ModelRenderer(this, 14, 5);
		bone2.setRotationPoint(0.0F, 5.0F, 0.0F);
		setRotationAngle(bone2, 0.3491F, 0.0F, 0.0F);
		bone.addChild(bone2);
		bone2.addBox(-1.0F, -0.0405F, 0.7704F, 2, 5, 3, 0.0F, false);

		ModelRenderer bone3 = new ModelRenderer(this, 15, 6);
		bone3.setRotationPoint(0.0F, 5.0F, 1.0F);
		setRotationAngle(bone3, 0.8727F, 0.0F, 0.0F);
		bone2.addChild(bone3);
		bone3.addBox(-1.0F, -0.081F, 0.0408F, 2, 7, 2, 0.0F, false);

		foot1 = new ModelRenderer(this, 48, 49);
		foot1.setRotationPoint(-3.0F, 13.0F, 8.0F);
		foot1.addBox(-2.0F, 0.0F, -2.0F, 4, 11, 4, 0.0F, false);

		foot2 = new ModelRenderer(this, 48, 49);
		foot2.setRotationPoint(3.0F, 13.0F, 8.0F);
		foot2.addBox(-2.0F, 0.0F, -2.0F, 4, 11, 4, 0.0F, false);

		foot3 = new ModelRenderer(this, 48, 49);
		foot3.setRotationPoint(3.0F, 13.0F, -6.0F);
		foot3.addBox(-2.0F, 0.0F, -2.0F, 4, 11, 4, 0.0F, false);

		foot4 = new ModelRenderer(this, 48, 49);
		foot4.setRotationPoint(-3.0F, 13.0F, -6.0F);
		foot4.addBox(-2.0F, 0.0F, -2.0F, 4, 11, 4, 0.0F, false);
	}

	@Nonnull
	@Override
	protected Iterable<ModelRenderer> getHeadParts() {
		return ImmutableList.of();
	}

	@Nonnull
	@Override
	protected Iterable<ModelRenderer> getBodyParts() {
		return ImmutableList.of(head, body, foot1, foot2, foot3, foot4);
	}

	@Override
	public void render(@Nonnull MammothEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.setRotationAngles(limbSwing, limbSwingAmount, netHeadYaw, headPitch);
	}

	public void setRotationAngle(ModelRenderer ModelRenderer, float x, float y, float z) {
		ModelRenderer.rotateAngleX = x;
		ModelRenderer.rotateAngleY = y;
		ModelRenderer.rotateAngleZ = z;
	}

	public void setRotationAngles(float limbSwing, float limbSwingAmount, float netHeadYaw, float headPitch) {
		this.head.rotateAngleX = headPitch * ((float)Math.PI / 180F) - 0.7854F;
		this.head.rotateAngleY = netHeadYaw * ((float)Math.PI / 180F);
		this.foot1.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
		this.foot2.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount;
		this.foot3.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
		this.foot4.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount;
	}
}