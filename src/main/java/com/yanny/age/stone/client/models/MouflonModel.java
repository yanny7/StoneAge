package com.yanny.age.stone.client.models;

import com.google.common.collect.ImmutableList;
import com.yanny.age.stone.entities.MouflonEntity;
import net.minecraft.client.renderer.entity.model.AgeableModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;

@OnlyIn(Dist.CLIENT)
public class MouflonModel extends AgeableModel<MouflonEntity> {
	private final ModelRenderer body;
	private final ModelRenderer head;
	private final ModelRenderer feet1;
	private final ModelRenderer feet2;
	private final ModelRenderer feet3;
	private final ModelRenderer feet4;

	public MouflonModel() {
		textureWidth = 64;
		textureHeight = 64;

		body = new ModelRenderer(this, 0, 23);
		body.setRotationPoint(0.0F, 13.0F, 0.0F);
		body.addBox(-4.0F, -6.0F, -7.0F, 8, 7, 15, 0.0F, false);

		ModelRenderer body1 = new ModelRenderer(this, 0, 20);
		body1.addBox(-1.0F, -5.0F, 8.0F, 2, 3, 1, 0.0F, false);
		body.addChild(body1);

		head = new ModelRenderer(this, 40, 0);
		head.setRotationPoint(0.0F, 7.0F, -7.0F);
		head.addBox(-3.0F, -4.0F, -4.0F, 6, 5, 6, 0.0F, false);

		feet1 = new ModelRenderer(this, 0, 0);
		feet1.setRotationPoint(2.0F, 14.0F, -5.0F);
		feet1.addBox(-1.0F, 0.0F, -1.0F, 2, 10, 2, 0.0F, false);

		feet2 = new ModelRenderer(this, 0, 0);
		feet2.setRotationPoint(2.0F, 14.0F, 6.0F);
		feet2.addBox(-1.0F, 0.0F, -1.0F, 2, 10, 2, 0.0F, false);

		feet3 = new ModelRenderer(this, 0, 0);
		feet3.setRotationPoint(-2.0F, 14.0F, 6.0F);
		feet3.addBox(-1.0F, 0.0F, -1.0F, 2, 10, 2, 0.0F, false);

		feet4 = new ModelRenderer(this, 0, 0);
		feet4.setRotationPoint(-2.0F, 14.0F, -5.0F);
		feet4.addBox(-1.0F, 0.0F, -1.0F, 2, 10, 2, 0.0F, false);
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
	public void render(@Nonnull MouflonEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.setRotationAngles(limbSwing, limbSwingAmount, netHeadYaw, headPitch);
	}

	public void setRotationAngles(float limbSwing, float limbSwingAmount, float netHeadYaw, float headPitch) {
		this.head.rotateAngleX = headPitch * ((float)Math.PI / 180F);
		this.head.rotateAngleY = netHeadYaw * ((float)Math.PI / 180F);
		this.feet1.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
		this.feet2.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount;
		this.feet3.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
		this.feet4.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount;
	}
}