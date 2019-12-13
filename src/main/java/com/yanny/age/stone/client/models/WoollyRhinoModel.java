package com.yanny.age.stone.client.models;

import com.mojang.blaze3d.platform.GlStateManager;
import com.yanny.age.stone.entities.WoollyRhinoEntity;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.client.renderer.model.ModelBox;
import net.minecraft.util.math.MathHelper;

public class WoollyRhinoModel extends EntityModel<WoollyRhinoEntity> {
	private final RendererModel body;
	private final RendererModel head;
	private final RendererModel foot1;
	private final RendererModel foot2;
	private final RendererModel foot3;
	private final RendererModel foot4;

	public WoollyRhinoModel() {
		textureWidth = 64;
		textureHeight = 64;

		body = new RendererModel(this);
		body.setRotationPoint(0.0F, 24.0F, 0.0F);
		body.cubeList.add(new ModelBox(body, 27, 21, -5.0F, -17.0F, -9.0F, 10, 11, 6, 0.0F, false));
		body.cubeList.add(new ModelBox(body, 38, 50, -4.0F, -14.75F, -10.0F, 8, 8, 1, 0.0F, false));
		body.cubeList.add(new ModelBox(body, 28, 38, -4.5F, -16.0F, -3.0F, 9, 10, 2, 0.0F, false));
		body.cubeList.add(new ModelBox(body, 0, 40, -4.5F, -15.0F, -1.0F, 9, 9, 10, 0.0F, false));

		head = new RendererModel(this);
		head.setRotationPoint(0.0F, 14.0F, -8.0F);
		head.cubeList.add(new ModelBox(head, 9, 31, -3.0F, -3.0F, -7.0F, 6, 6, 3, 0.0F, false));
		head.cubeList.add(new ModelBox(head, 37, 10, -3.5F, -4.0F, -4.0F, 7, 7, 4, 0.0F, false));
		head.cubeList.add(new ModelBox(head, 43, 2, -2.5F, -2.0F, -10.0F, 5, 5, 3, 0.0F, false));
		head.cubeList.add(new ModelBox(head, 50, 44, -0.5F, -9.0F, -9.5F, 1, 5, 1, 0.0F, false));
		head.cubeList.add(new ModelBox(head, 3, 42, -0.5F, -6.0F, -6.75F, 1, 3, 1, 0.0F, false));
		head.cubeList.add(new ModelBox(head, 16, 12, 2.75F, -5.0F, -3.75F, 1, 2, 1, 0.0F, false));
		head.cubeList.add(new ModelBox(head, 16, 12, -3.75F, -5.0F, -3.75F, 1, 2, 1, 0.0F, false));
		head.cubeList.add(new ModelBox(head, 50, 38, -1.0F, -4.0F, -9.75F, 2, 2, 2, 0.0F, false));

		foot1 = new RendererModel(this);
		foot1.setRotationPoint(1.0F, 18.0F, -6.5F);
		foot1.cubeList.add(new ModelBox(foot1, 0, 25, 0.0F, 0.0F, -1.5F, 3, 6, 3, 0.0F, false));

		foot2 = new RendererModel(this);
		foot2.setRotationPoint(-1.0F, 18.0F, -6.5F);
		foot2.cubeList.add(new ModelBox(foot2, 0, 16, -3.0F, 0.0F, -1.5F, 3, 6, 3, 0.0F, false));

		foot3 = new RendererModel(this);
		foot3.setRotationPoint(1.0F, 18.0F, 6.5F);
		foot3.cubeList.add(new ModelBox(foot3, 12, 22, 0.0F, 0.0F, -1.5F, 3, 6, 3, 0.0F, false));

		foot4 = new RendererModel(this);
		foot4.setRotationPoint(-1.0F, 18.0F, 6.5F);
		foot4.cubeList.add(new ModelBox(foot4, 0, 0, -3.0F, 0.0F, -1.5F, 3, 6, 3, 0.0F, false));
	}

	@Override
	public void render(WoollyRhinoEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
		this.setRotationAngles(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
		GlStateManager.pushMatrix();

		if (isChild) {
			GlStateManager.scalef(0.75F, 0.75F, 0.75F);
			GlStateManager.translatef(0.0F, 0.5F, 0.0F);
		} else {
			GlStateManager.scalef(1.5F, 1.5F, 1.5F);
			GlStateManager.translatef(0.0F, -0.5F, 0.0F);
		}

		body.render(scale);
		head.render(scale);
		foot1.render(scale);
		foot2.render(scale);
		foot3.render(scale);
		foot4.render(scale);
		GlStateManager.popMatrix();
	}

	public void setRotationAngle(RendererModel RendererModel, float x, float y, float z) {
		RendererModel.rotateAngleX = x;
		RendererModel.rotateAngleY = y;
		RendererModel.rotateAngleZ = z;
	}

	@Override
	public void setRotationAngles(WoollyRhinoEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor) {
		this.head.rotateAngleX = headPitch * ((float)Math.PI / 180F);
		this.head.rotateAngleY = netHeadYaw * ((float)Math.PI / 180F);
		this.foot1.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
		this.foot2.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount;
		this.foot3.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
		this.foot4.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount;
	}
}