package com.yanny.age.stone.client.models;

import com.google.common.collect.ImmutableList;
import com.yanny.age.stone.entities.FowlEntity;
import net.minecraft.client.renderer.entity.model.AgeableModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;

@OnlyIn(Dist.CLIENT)
public class FowlModel extends AgeableModel<FowlEntity> {
	private final ModelRenderer body;
	private final ModelRenderer head;
	private final ModelRenderer foot1;
	private final ModelRenderer foot2;
	private final ModelRenderer wing1;
	private final ModelRenderer wing2;

	public FowlModel() {
		textureWidth = 64;
		textureHeight = 64;

		body = new ModelRenderer(this, 36, 0);
		body.setRotationPoint(0.0F, 17.0F, 0.0F);
		body.addBox(-3.0F, -3.0F, -4.0F, 6, 6, 8, 0.0F, false);

		ModelRenderer body1 = new ModelRenderer(this, 52, 14);
		body1.addBox(-1.0F, -5.0F, 3.0F, 2, 5, 4, 0.0F, false);
		body.addChild(body1);

		head = new ModelRenderer(this, 22, 0);
		head.setRotationPoint(0.0F, 14.0F, -4.0F);
		head.addBox(-2.0F, -5.0F, -2.0F, 4, 7, 3, 0.0F, false);

		ModelRenderer head1 = new ModelRenderer(this, 36, 5);
		head1.addBox(-1.0F, -3.0F, -4.0F, 2, 1, 2, 0.0F, false);
		head.addChild(head1);

		foot1 = new ModelRenderer(this, 60, 0);
		foot1.setRotationPoint(-1.0F, 20.0F, 0.0F);
		foot1.addBox(-1.0F, -1.0F, 0.0F, 1, 5, 1, 0.0F, false);

		ModelRenderer foot11 = new ModelRenderer(this, 24, 11);
		foot11.addBox(-2.0F, 4.0F, -2.0F, 3, 0, 3, 0.0F, false);
		foot1.addChild(foot11);

		foot2 = new ModelRenderer(this, 60, 0);
		foot2.setRotationPoint(1.0F, 20.0F, 0.0F);
		foot2.addBox(0.0F, -1.0F, 0.0F, 1, 5, 1, 0.0F, false);

		ModelRenderer foot21 = new ModelRenderer(this, 24, 11);
		foot21.addBox(-1.0F, 4.0F, -2.0F, 3, 0, 3, 0.0F, false);
		foot2.addChild(foot21);

		wing1 = new ModelRenderer(this, 0, 0);
		wing1.setRotationPoint(3.0F, 15.0F, 0.0F);
		wing1.addBox(0.0F, 0.0F, -3.0F, 1, 4, 5, 0.0F, false);

		wing2 = new ModelRenderer(this);
		wing2.setRotationPoint(-3.0F, 15.0F, 0.0F);
		wing2.addBox(-1.0F, 0.0F, -3.0F, 1, 4, 5, 0.0F, false);
	}

	@Override
	public void setRotationAngles(@Nonnull FowlEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
	}

	@Nonnull
	@Override
	protected Iterable<ModelRenderer> getHeadParts() {
		return ImmutableList.of();
	}

	@Nonnull
	@Override
	protected Iterable<ModelRenderer> getBodyParts() {
		return ImmutableList.of(head, body, foot1, foot2, wing1, wing2);
	}

	public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.head.rotateAngleX = headPitch * ((float)Math.PI / 180F);
		this.head.rotateAngleY = netHeadYaw * ((float)Math.PI / 180F);
		this.foot1.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
		this.foot2.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount;
		this.wing1.rotateAngleZ = -ageInTicks;
		this.wing2.rotateAngleZ = ageInTicks;
	}
}