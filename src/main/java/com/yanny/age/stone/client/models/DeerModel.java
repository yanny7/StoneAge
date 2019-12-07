package com.yanny.age.stone.client.models;

import com.mojang.blaze3d.platform.GlStateManager;
import com.yanny.age.stone.entities.DeerEntity;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.client.renderer.model.ModelBox;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class DeerModel extends EntityModel<DeerEntity> {
	private final RendererModel body;
	private final RendererModel head;
	private final RendererModel feet1;
	private final RendererModel feet2;
	private final RendererModel feet3;
	private final RendererModel feet4;

	public DeerModel() {
		textureWidth = 64;
		textureHeight = 64;

		body = new RendererModel(this);
		body.setRotationPoint(0.0F, 13.0F, 0.0F);
		body.cubeList.add(new ModelBox(body, 24, 0, -3.0F, -3.0F, -6.0F, 6, 6, 14, 0.0F, false));

		RendererModel tail = new RendererModel(this);
		tail.setRotationPoint(0.0F, 0.0F, 0.0F);
		setRotationAngle(tail, -0.7854F, 0.0F);
		body.addChild(tail);
		tail.cubeList.add(new ModelBox(tail, 50, 0, -1.0F, -10.0F, 3.0F, 2, 3, 1, 0.0F, false));

		head = new RendererModel(this);
		head.setRotationPoint(0.0F, 11.0F, -5.0F);
		setRotationAngle(head, -0.7854F, 0.0F);
		head.cubeList.add(new ModelBox(head, 50, 8, -1.0F, -1.1213F, -4.0503F, 2, 2, 4, 0.0F, false));

		RendererModel bone3 = new RendererModel(this);
		bone3.setRotationPoint(0.0F, -2.1213F, 4.9497F);
		setRotationAngle(bone3, 0.7854F, 0.0F);
		head.addChild(bone3);
		bone3.cubeList.add(new ModelBox(bone3, 20, 5, -1.5F, -6.0F, -13.0F, 3, 3, 6, 0.0F, false));

		RendererModel bone = new RendererModel(this);
		bone.setRotationPoint(0.0F, -5.0F, -8.0F);
		setRotationAngle(bone, 0.0F, -0.5236F);
		bone3.addChild(bone);
		bone.cubeList.add(new ModelBox(bone, 4, 0, -1.0F, -3.0F, -1.0F, 1, 2, 1, 0.0F, false));

		RendererModel bone2 = new RendererModel(this);
		bone2.setRotationPoint(0.0F, -5.0F, -8.0F);
		setRotationAngle(bone2, 0.0F, 0.5236F);
		bone3.addChild(bone2);
		bone2.cubeList.add(new ModelBox(bone2, 4, 0, 0.0F, -3.0F, -1.0F, 1, 2, 1, 0.0F, false));

		feet1 = new RendererModel(this);
		feet1.setRotationPoint(1.0F, 16.0F, -5.0F);
		feet1.cubeList.add(new ModelBox(feet1, 0, 0, 0.0F, -1.0F, 0.0F, 1, 9, 1, 0.0F, false));

		feet2 = new RendererModel(this);
		feet2.setRotationPoint(1.0F, 16.0F, 6.0F);
		feet2.cubeList.add(new ModelBox(feet2, 0, 0, 0.0F, -1.0F, 0.0F, 1, 9, 1, 0.0F, false));

		feet3 = new RendererModel(this);
		feet3.setRotationPoint(-1.0F, 16.0F, 6.0F);
		feet3.cubeList.add(new ModelBox(feet3, 0, 0, -1.0F, -1.0F, 0.0F, 1, 9, 1, 0.0F, false));

		feet4 = new RendererModel(this);
		feet4.setRotationPoint(-1.0F, 16.0F, -5.0F);
		feet4.cubeList.add(new ModelBox(feet4, 0, 0, -1.0F, -1.0F, 0.0F, 1, 9, 1, 0.0F, false));
	}

	@Override
	public void render(DeerEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
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
		feet1.render(scale);
		feet2.render(scale);
		feet3.render(scale);
		feet4.render(scale);
		GlStateManager.popMatrix();
	}

	private void setRotationAngle(RendererModel RendererModel, float x, float z) {
		RendererModel.rotateAngleX = x;
		RendererModel.rotateAngleZ = z;
	}

	@Override
	public void setRotationAngles(DeerEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor) {
		this.head.rotateAngleX = headPitch * ((float)Math.PI / 180F) - 0.7854F;
		this.head.rotateAngleY = netHeadYaw * ((float)Math.PI / 180F);
		this.feet1.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
		this.feet2.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
		this.feet3.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount;
		this.feet4.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount;
	}
}