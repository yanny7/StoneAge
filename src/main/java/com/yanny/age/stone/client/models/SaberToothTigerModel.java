package com.yanny.age.stone.client.models;

import com.mojang.blaze3d.platform.GlStateManager;
import com.yanny.age.stone.entities.SaberToothTigerEntity;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.client.renderer.model.ModelBox;
import net.minecraft.util.math.MathHelper;

import javax.annotation.Nonnull;

public class SaberToothTigerModel extends EntityModel<SaberToothTigerEntity> {
	private final RendererModel body;
	private final RendererModel foot1;
	private final RendererModel foot2;
	private final RendererModel foot3;
	private final RendererModel foot4;
	private final RendererModel head;

	public SaberToothTigerModel() {
		textureWidth = 64;
		textureHeight = 64;

		body = new RendererModel(this);
		body.setRotationPoint(0.0F, 24.0F, -2.0F);
		body.cubeList.add(new ModelBox(body, 0, 42, -4.5F, -13.0F, -7.0F, 9, 8, 8, 0.0F, false));
		body.cubeList.add(new ModelBox(body, 0, 11, -2.5F, -13.0F, -9.0F, 5, 5, 2, 0.0F, false));
		body.cubeList.add(new ModelBox(body, 17, 3, -3.5F, -12.0F, 1.0F, 7, 6, 9, 0.0F, false));

		foot1 = new RendererModel(this);
		foot1.setRotationPoint(3.0F, 17.0F, 7.0F);
		foot1.cubeList.add(new ModelBox(foot1, 26, 41, -1.5F, 6.0F, -2.5F, 2, 1, 2, 0.0F, false));

		RendererModel bone = new RendererModel(this);
		bone.setRotationPoint(-3.0F, 3.0F, -2.0F);
		setRotationAngle(bone, -0.4363F, 0.0F, 0.0F);
		foot1.addChild(bone);
		bone.cubeList.add(new ModelBox(bone, 35, 50, 1.0F, -4.0F, -1.25F, 3, 4, 3, 0.0F, false));

		RendererModel bone2 = new RendererModel(this);
		bone2.setRotationPoint(0.0F, 3.0F, -2.0F);
		setRotationAngle(bone2, 0.3491F, 0.0F, 0.0F);
		foot1.addChild(bone2);
		bone2.cubeList.add(new ModelBox(bone2, 48, 51, -1.5F, -1.0F, -0.5F, 2, 5, 2, 0.0F, false));

		foot2 = new RendererModel(this);
		foot2.setRotationPoint(-2.0F, 17.0F, 7.0F);
		foot2.cubeList.add(new ModelBox(foot2, 26, 38, -1.5F, 6.0F, -2.5F, 2, 1, 2, 0.0F, false));

		RendererModel bone3 = new RendererModel(this);
		bone3.setRotationPoint(2.0F, 3.0F, -1.0F);
		setRotationAngle(bone3, -0.4363F, 0.0F, 0.0F);
		foot2.addChild(bone3);
		bone3.cubeList.add(new ModelBox(bone3, 1, 2, -4.0F, -3.5774F, -2.1563F, 3, 4, 3, 0.0F, false));

		RendererModel bone4 = new RendererModel(this);
		bone4.setRotationPoint(5.0F, 3.0F, -1.0F);
		setRotationAngle(bone4, 0.3491F, 0.0F, 0.0F);
		foot2.addChild(bone4);
		bone4.cubeList.add(new ModelBox(bone4, 47, 23, -6.5F, -1.342F, -1.4397F, 2, 5, 2, 0.0F, false));

		foot3 = new RendererModel(this);
		foot3.setRotationPoint(2.25F, 18.0F, -6.5F);

		RendererModel bone5 = new RendererModel(this);
		bone5.setRotationPoint(-2.0F, 6.0F, 6.5F);
		foot3.addChild(bone5);
		bone5.cubeList.add(new ModelBox(bone5, 49, 38, 2.0F, -4.0F, -6.75F, 2, 4, 2, 0.0F, false));
		bone5.cubeList.add(new ModelBox(bone5, 36, 42, 2.0F, -1.0F, -7.75F, 2, 1, 1, 0.0F, false));

		RendererModel bone6 = new RendererModel(this);
		bone6.setRotationPoint(1.0F, 1.0F, 0.0F);
		setRotationAngle(bone6, 0.3491F, 0.0F, 0.0F);
		foot3.addChild(bone6);
		bone6.cubeList.add(new ModelBox(bone6, 0, 36, -1.5F, -1.0F, -1.5F, 3, 3, 3, 0.0F, false));

		foot4 = new RendererModel(this);
		foot4.setRotationPoint(-1.75F, 18.0F, -6.5F);

		RendererModel bone7 = new RendererModel(this);
		bone7.setRotationPoint(2.0F, 6.0F, 6.5F);
		foot4.addChild(bone7);
		bone7.cubeList.add(new ModelBox(bone7, 49, 32, -4.5F, -4.0F, -6.75F, 2, 4, 2, 0.0F, false));
		bone7.cubeList.add(new ModelBox(bone7, 36, 40, -4.5F, -1.0F, -7.75F, 2, 1, 1, 0.0F, false));

		RendererModel bone8 = new RendererModel(this);
		bone8.setRotationPoint(5.0F, 1.0F, 0.0F);
		setRotationAngle(bone8, 0.3491F, 0.0F, 0.0F);
		foot4.addChild(bone8);
		bone8.cubeList.add(new ModelBox(bone8, 12, 36, -8.0F, -1.0F, -1.5F, 3, 3, 3, 0.0F, false));

		head = new RendererModel(this);
		head.setRotationPoint(0.0F, 13.0F, -10.0F);
		head.cubeList.add(new ModelBox(head, 0, 26, -3.0F, -2.5F, -3.0F, 6, 6, 3, 0.0F, false));
		head.cubeList.add(new ModelBox(head, 22, 28, -2.5F, -2.0F, -5.0F, 5, 5, 2, 0.0F, false));
		head.cubeList.add(new ModelBox(head, 0, 19, -2.0F, -0.5F, -8.0F, 4, 2, 3, 0.0F, false));

		RendererModel bone9 = new RendererModel(this);
		bone9.setRotationPoint(0.0F, 2.0F, -5.0F);
		setRotationAngle(bone9, 0.0873F, 0.0F, 0.0F);
		head.addChild(bone9);
		bone9.cubeList.add(new ModelBox(bone9, 19, 20, -1.5F, -0.5F, -3.0F, 3, 1, 3, 0.0F, false));

		RendererModel bone10 = new RendererModel(this);
		bone10.setRotationPoint(0.5F, 1.0F, -7.25F);
		setRotationAngle(bone10, 0.0873F, 0.0F, 0.0F);
		head.addChild(bone10);
		bone10.cubeList.add(new ModelBox(bone10, 41, 27, 0.4F, 0.25F, -0.75F, 1, 3, 1, 0.0F, false));
		bone10.cubeList.add(new ModelBox(bone10, 41, 27, -2.4F, 0.25F, -0.75F, 1, 3, 1, 0.0F, false));
	}

	@Override
	public void render(@Nonnull SaberToothTigerEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
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

	public void setRotationAngle(@Nonnull RendererModel RendererModel, float x, float y, float z) {
		RendererModel.rotateAngleX = x;
		RendererModel.rotateAngleY = y;
		RendererModel.rotateAngleZ = z;
	}

	@Override
	public void setRotationAngles(@Nonnull SaberToothTigerEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor) {
		this.head.rotateAngleX = headPitch * ((float)Math.PI / 180F);
		this.head.rotateAngleY = netHeadYaw * ((float)Math.PI / 180F);
		this.foot1.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
		this.foot2.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount;
		this.foot3.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
		this.foot4.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount;
	}
}