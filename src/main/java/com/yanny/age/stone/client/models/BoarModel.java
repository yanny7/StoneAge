package com.yanny.age.stone.client.models;

import com.google.common.collect.ImmutableList;
import com.yanny.age.stone.entities.BoarEntity;
import net.minecraft.client.renderer.entity.model.AgeableModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;

@OnlyIn(Dist.CLIENT)
public class BoarModel extends AgeableModel<BoarEntity> {
	private final ModelRenderer body;
	private final ModelRenderer head;
	private final ModelRenderer foot1;
	private final ModelRenderer foot2;
	private final ModelRenderer foot3;
	private final ModelRenderer foot4;

	public BoarModel() {
		textureWidth = 64;
		textureHeight = 64;

		body = new ModelRenderer(this, 0, 16);
		body.setRotationPoint(0.0F, 14.0F, 0.0F);
		body.addBox(-5.0F, -4.0F, -8.0F, 10, 8, 16, 0.0F, false);

		foot1 = new ModelRenderer(this, 36, 14);
		foot1.setRotationPoint(3.0F, 18.0F, 6.0F);
		foot1.addBox(-2.0F, 0.0F, -2.0F, 4, 6, 4, 0.0F, false);

		foot2 = new ModelRenderer(this, 36, 14);
		foot2.setRotationPoint(-3.0F, 18.0F, 6.0F);
		foot2.addBox(-2.0F, 0.0F, -2.0F, 4, 6, 4, 0.0F, false);

		foot3 = new ModelRenderer(this, 36, 14);
		foot3.setRotationPoint(-3.0F, 18.0F, -6.0F);
		foot3.addBox(-2.0F, 0.0F, -2.0F, 4, 6, 4, 0.0F, false);

		foot4 = new ModelRenderer(this, 36, 14);
		foot4.setRotationPoint(3.0F, 18.0F, -6.0F);
		foot4.addBox(-2.0F, 0.0F, -2.0F, 4, 6, 4, 0.0F, false);

		head = new ModelRenderer(this, 2, 0);
		head.setRotationPoint(0.0F, 12.0F, -8.0F);
		head.addBox(-4.0F, -4.0F, -6.0F, 8, 8, 8, 0.0F, false);

		ModelRenderer head2 = new ModelRenderer(this, 54, 28);
		head2.addBox(-2.0F, 0.0F, -7.0F, 4, 3, 1, 0.0F, false);
		head.addChild(head2);

		ModelRenderer bone = new ModelRenderer(this, 0, 0);
		bone.setRotationPoint(0.0F, 2.0F, -7.0F);
		bone.rotateAngleX = -0.6109f;
		bone.addBox( 2.0F, 0.0F, -2.0F, 1, 1, 4, 0.0F, false);
		bone.addBox(-3.0F, 0.0F, -2.0F, 1, 1, 4, 0.0F, false);
		head.addChild(bone);
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
	public void setRotationAngles(@Nonnull BoarEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.setRotationAngles(limbSwing, limbSwingAmount, netHeadYaw, headPitch);
	}

	public void setRotationAngles(float limbSwing, float limbSwingAmount, float netHeadYaw, float headPitch) {
		this.head.rotateAngleX = headPitch * ((float)Math.PI / 180F);
		this.head.rotateAngleY = netHeadYaw * ((float)Math.PI / 180F);
		this.foot1.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
		this.foot2.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
		this.foot3.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount;
		this.foot4.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount;
	}
}