package com.yanny.age.stone.client.models;

import com.mojang.blaze3d.platform.GlStateManager;
import com.yanny.age.stone.entities.MammothEntity;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.client.renderer.model.ModelBox;
import net.minecraft.util.math.MathHelper;

import javax.annotation.Nonnull;

public class MammothModel extends EntityModel<MammothEntity> {
	private final RendererModel body;
	private final RendererModel head;
	private final RendererModel foot1;
	private final RendererModel foot2;
	private final RendererModel foot3;
	private final RendererModel foot4;

	public MammothModel() {
		textureWidth = 64;
		textureHeight = 64;

		body = new RendererModel(this);
		body.setRotationPoint(0.0F, 5.0F, 0.0F);
		body.cubeList.add(new ModelBox(body, 0, 39, -6.0F, -8.0F, -9.0F, 12, 17, 8, 0.0F, false));
		body.cubeList.add(new ModelBox(body, 2, 41, -6.0F, -7.0F, -1.0F, 12, 16, 6, 0.0F, false));
		body.cubeList.add(new ModelBox(body, 2, 41, -6.0F, -6.0F, 5.0F, 12, 15, 6, 0.0F, false));

		head = new RendererModel(this);
		head.setRotationPoint(0.0F, -1.0F, -7.0F);
		setRotationAngle(head, -0.3491F, 0.0F, 0.0F);
		head.cubeList.add(new ModelBox(head, 0, 17, -4.0F, -3.0F, -8.0F, 8, 9, 8, 0.0F, false));
		head.cubeList.add(new ModelBox(head, 4, 1, -3.0F, 6.0F, -7.0F, 6, 8, 7, 0.0F, false));
		head.cubeList.add(new ModelBox(head, 32, 0, 3.0F, 6.0F, -5.0F, 1, 11, 1, 0.0F, false));
		head.cubeList.add(new ModelBox(head, 32, 0, -4.0F, 6.0F, -5.0F, 1, 11, 1, 0.0F, false));

		RendererModel ears2 = new RendererModel(this);
		ears2.setRotationPoint(0.0F, 2.0F, -6.0F);
		setRotationAngle(ears2, 0.0F, 0.2618F, 0.0F);
		head.addChild(ears2);
		ears2.cubeList.add(new ModelBox(ears2, 10, 7, -9.0F, -5.0F, 1.0F, 5, 6, 1, 0.0F, false));

		RendererModel ears = new RendererModel(this);
		ears.setRotationPoint(0.0F, 2.0F, -6.0F);
		setRotationAngle(ears, 0.0F, -0.2618F, 0.0F);
		head.addChild(ears);
		ears.cubeList.add(new ModelBox(ears, 12, 7, 4.0F, -5.0F, 1.0F, 5, 6, 1, 0.0F, false));

		RendererModel horns = new RendererModel(this);
		horns.setRotationPoint(0.0F, 17.0F, -4.0F);
		setRotationAngle(horns, -1.2217F, 0.0F, 0.0F);
		head.addChild(horns);
		horns.cubeList.add(new ModelBox(horns, 32, 0, -4.0F, 0.0F, -1.0F, 1, 7, 1, 0.0F, false));
		horns.cubeList.add(new ModelBox(horns, 32, 0, 3.0F, 0.0F, -1.0F, 1, 7, 1, 0.0F, false));

		RendererModel horns2 = new RendererModel(this);
		horns2.setRotationPoint(0.0F, 7.0F, 0.0F);
		setRotationAngle(horns2, -0.8727F, 0.0F, 0.0F);
		horns.addChild(horns2);
		horns2.cubeList.add(new ModelBox(horns2, 32, 0, -4.0F, 0.0F, -1.0F, 1, 4, 1, 0.0F, false));
		horns2.cubeList.add(new ModelBox(horns2, 32, 0, 3.0F, 0.0F, -1.0F, 1, 4, 1, 0.0F, false));

		RendererModel bone = new RendererModel(this);
		bone.setRotationPoint(0.0F, 14.0F, -6.0F);
		setRotationAngle(bone, 0.3491F, 0.0F, 0.0F);
		head.addChild(bone);
		bone.cubeList.add(new ModelBox(bone, 10, 4, -2.0F, -0.342F, 0.0603F, 4, 5, 4, 0.0F, false));

		RendererModel bone2 = new RendererModel(this);
		bone2.setRotationPoint(0.0F, 5.0F, 0.0F);
		setRotationAngle(bone2, 0.3491F, 0.0F, 0.0F);
		bone.addChild(bone2);
		bone2.cubeList.add(new ModelBox(bone2, 14, 5, -1.0F, -0.0405F, 0.7704F, 2, 5, 3, 0.0F, false));

		RendererModel bone3 = new RendererModel(this);
		bone3.setRotationPoint(0.0F, 5.0F, 1.0F);
		setRotationAngle(bone3, 0.8727F, 0.0F, 0.0F);
		bone2.addChild(bone3);
		bone3.cubeList.add(new ModelBox(bone3, 15, 6, -1.0F, -0.081F, 0.0408F, 2, 7, 2, 0.0F, false));

		foot1 = new RendererModel(this);
		foot1.setRotationPoint(-3.0F, 13.0F, 8.0F);
		foot1.cubeList.add(new ModelBox(foot1, 48, 49, -2.0F, 0.0F, -2.0F, 4, 11, 4, 0.0F, false));

		foot2 = new RendererModel(this);
		foot2.setRotationPoint(3.0F, 13.0F, 8.0F);
		foot2.cubeList.add(new ModelBox(foot2, 48, 49, -2.0F, 0.0F, -2.0F, 4, 11, 4, 0.0F, false));

		foot3 = new RendererModel(this);
		foot3.setRotationPoint(3.0F, 13.0F, -6.0F);
		foot3.cubeList.add(new ModelBox(foot3, 48, 49, -2.0F, 0.0F, -2.0F, 4, 11, 4, 0.0F, false));

		foot4 = new RendererModel(this);
		foot4.setRotationPoint(-3.0F, 13.0F, -6.0F);
		foot4.cubeList.add(new ModelBox(foot4, 48, 49, -2.0F, 0.0F, -2.0F, 4, 11, 4, 0.0F, false));
	}

	@Override
	public void render(@Nonnull MammothEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
		this.setRotationAngles(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
		GlStateManager.pushMatrix();

		if (!isChild) {
			GlStateManager.scalef(2.0F, 2.0F, 2.0F);
			GlStateManager.translatef(0.0F, -0.75F, 0.0F);
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
	public void setRotationAngles(@Nonnull MammothEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor) {
		this.head.rotateAngleX = headPitch * ((float)Math.PI / 180F) - 0.7854F;
		this.head.rotateAngleY = netHeadYaw * ((float)Math.PI / 180F);
		this.foot1.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
		this.foot2.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount;
		this.foot3.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
		this.foot4.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount;
	}
}