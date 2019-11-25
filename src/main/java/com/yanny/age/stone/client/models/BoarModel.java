package com.yanny.age.stone.client.models;

import com.mojang.blaze3d.platform.GlStateManager;
import com.yanny.age.stone.entities.BoarEntity;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.client.renderer.model.ModelBox;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BoarModel extends EntityModel<BoarEntity> {
	private final RendererModel body;
	private final RendererModel head;
	private final RendererModel foot1;
	private final RendererModel foot2;
	private final RendererModel foot3;
	private final RendererModel foot4;

	public BoarModel() {
		textureWidth = 64;
		textureHeight = 64;

		body = new RendererModel(this);
		body.setRotationPoint(0.0F, 14.0F, 0.0F);
		body.cubeList.add(new ModelBox(body, 0, 16, -5.0F, -4.0F, -8.0F, 10, 8, 16, 0.0F, false));

		foot1 = new RendererModel(this);
		foot1.setRotationPoint(3.0F, 18.0F, 6.0F);
		foot1.cubeList.add(new ModelBox(foot1, 36, 14, -2.0F, 0.0F, -2.0F, 4, 6, 4, 0.0F, false));

		foot2 = new RendererModel(this);
		foot2.setRotationPoint(-3.0F, 18.0F, 6.0F);
		foot2.cubeList.add(new ModelBox(foot2, 36, 14, -2.0F, 0.0F, -2.0F, 4, 6, 4, 0.0F, false));

		foot3 = new RendererModel(this);
		foot3.setRotationPoint(-3.0F, 18.0F, -6.0F);
		foot3.cubeList.add(new ModelBox(foot3, 36, 14, -2.0F, 0.0F, -2.0F, 4, 6, 4, 0.0F, false));

		foot4 = new RendererModel(this);
		foot4.setRotationPoint(3.0F, 18.0F, -6.0F);
		foot4.cubeList.add(new ModelBox(foot4, 36, 14, -2.0F, 0.0F, -2.0F, 4, 6, 4, 0.0F, false));

		head = new RendererModel(this);
		head.setRotationPoint(0.0F, 12.0F, -8.0F);
		head.cubeList.add(new ModelBox(head, 2, 0, -4.0F, -4.0F, -6.0F, 8, 8, 8, 0.0F, false));
		head.cubeList.add(new ModelBox(head, 54, 28, -2.0F, 0.0F, -7.0F, 4, 3, 1, 0.0F, false));

		RendererModel bone = new RendererModel(this);
		bone.setRotationPoint(0.0F, 2.0F, -7.0F);
		bone.rotateAngleX = -0.6109f;
		head.addChild(bone);
		bone.cubeList.add(new ModelBox(bone, 0, 0, 2.0F, 0.0F, -2.0F, 1, 1, 4, 0.0F, false));
		bone.cubeList.add(new ModelBox(bone, 0, 0, -3.0F, 0.0F, -2.0F, 1, 1, 4, 0.0F, false));
	}

	@Override
	public void render(BoarEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
		this.setRotationAngles(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
		if (isChild) {
			GlStateManager.pushMatrix();
			GlStateManager.scalef(0.75F, 0.75F, 0.75F);
			GlStateManager.translatef(0.0F, 0.5F, 0.0F);
			body.render(scale);
			head.render(scale);
			foot1.render(scale);
			foot2.render(scale);
			foot3.render(scale);
			foot4.render(scale);
			GlStateManager.popMatrix();
		} else {
			body.render(scale);
			head.render(scale);
			foot1.render(scale);
			foot2.render(scale);
			foot3.render(scale);
			foot4.render(scale);
		}
	}

	@Override
	public void setRotationAngles(BoarEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor) {
		this.head.rotateAngleX = headPitch * ((float)Math.PI / 180F);
		this.head.rotateAngleY = netHeadYaw * ((float)Math.PI / 180F);
		this.foot1.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
		this.foot2.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
		this.foot3.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount;
		this.foot4.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount;
	}
}