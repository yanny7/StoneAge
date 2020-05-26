package com.yanny.age.stone.client.models;

import com.google.common.collect.ImmutableList;
import com.yanny.age.stone.entities.WoollyRhinoEntity;
import net.minecraft.client.renderer.entity.model.AgeableModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;

import javax.annotation.Nonnull;

public class WoollyRhinoModel extends AgeableModel<WoollyRhinoEntity> {
	private final ModelRenderer body;
	private final ModelRenderer head;
	private final ModelRenderer foot1;
	private final ModelRenderer foot2;
	private final ModelRenderer foot3;
	private final ModelRenderer foot4;

	public WoollyRhinoModel() {
		textureWidth = 64;
		textureHeight = 64;

		body = new ModelRenderer(this, 27, 21);
		body.setRotationPoint(0.0F, 24.0F, 0.0F);
		body.addBox(-5.0F, -17.0F, -9.0F, 10, 11, 6, 0.0F, false);

		ModelRenderer body1 = new ModelRenderer(this, 38, 50);
		body1.addBox(-4.0F, -14.75F, -10.0F, 8, 8, 1, 0.0F, false);
		body.addChild(body1);

		ModelRenderer body2 = new ModelRenderer(this, 28, 38);
		body2.addBox(-4.5F, -16.0F, -3.0F, 9, 10, 2, 0.0F, false);
		body.addChild(body2);

		ModelRenderer body3 = new ModelRenderer(this, 0, 40);
		body3.addBox(-4.5F, -15.0F, -1.0F, 9, 9, 10, 0.0F, false);
		body.addChild(body3);


		head = new ModelRenderer(this, 9, 31);
		head.setRotationPoint(0.0F, 14.0F, -8.0F);
		head.addBox(-3.0F, -3.0F, -7.0F, 6, 6, 3, 0.0F, false);

		ModelRenderer head1 = new ModelRenderer(this, 37, 10);
		head1.addBox(-3.5F, -4.0F, -4.0F, 7, 7, 4, 0.0F, false);
		head.addChild(head1);

		ModelRenderer head2 = new ModelRenderer(this, 43, 2);
		head2.addBox(-2.5F, -2.0F, -10.0F, 5, 5, 3, 0.0F, false);
		head.addChild(head2);

		ModelRenderer head3 = new ModelRenderer(this, 50, 44);
		head3.addBox(-0.5F, -9.0F, -9.5F, 1, 5, 1, 0.0F, false);
		head.addChild(head3);

		ModelRenderer head4 = new ModelRenderer(this, 3, 42);
		head4.addBox(-0.5F, -6.0F, -6.75F, 1, 3, 1, 0.0F, false);
		head.addChild(head4);

		ModelRenderer head5 = new ModelRenderer(this, 16, 12);
		head5.addBox(2.75F, -5.0F, -3.75F, 1, 2, 1, 0.0F, false);
		head5.addBox(-3.75F, -5.0F, -3.75F, 1, 2, 1, 0.0F, false);
		head.addChild(head5);

		ModelRenderer head6 = new ModelRenderer(this, 50, 38);
		head6.addBox(-1.0F, -4.0F, -9.75F, 2, 2, 2, 0.0F, false);
		head.addChild(head6);

		foot1 = new ModelRenderer(this, 0, 25);
		foot1.setRotationPoint(1.0F, 18.0F, -6.5F);
		foot1.addBox(0.0F, 0.0F, -1.5F, 3, 6, 3, 0.0F, false);

		foot2 = new ModelRenderer(this, 0, 16);
		foot2.setRotationPoint(-1.0F, 18.0F, -6.5F);
		foot2.addBox(-3.0F, 0.0F, -1.5F, 3, 6, 3, 0.0F, false);

		foot3 = new ModelRenderer(this, 12, 22);
		foot3.setRotationPoint(1.0F, 18.0F, 6.5F);
		foot3.addBox(0.0F, 0.0F, -1.5F, 3, 6, 3, 0.0F, false);

		foot4 = new ModelRenderer(this, 0, 0);
		foot4.setRotationPoint(-1.0F, 18.0F, 6.5F);
		foot4.addBox(-3.0F, 0.0F, -1.5F, 3, 6, 3, 0.0F, false);
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
	public void setRotationAngles(@Nonnull WoollyRhinoEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.setRotationAngles(limbSwing, limbSwingAmount, netHeadYaw, headPitch);
	}

	public void setRotationAngles(float limbSwing, float limbSwingAmount, float netHeadYaw, float headPitch) {
		this.head.rotateAngleX = headPitch * ((float)Math.PI / 180F);
		this.head.rotateAngleY = netHeadYaw * ((float)Math.PI / 180F);
		this.foot1.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
		this.foot2.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount;
		this.foot3.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
		this.foot4.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount;
	}
}