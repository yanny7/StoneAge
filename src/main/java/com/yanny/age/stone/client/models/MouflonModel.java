package com.yanny.age.stone.client.models;

import com.mojang.blaze3d.platform.GlStateManager;
import com.yanny.age.stone.entities.MouflonEntity;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.client.renderer.model.ModelBox;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;

@OnlyIn(Dist.CLIENT)
public class MouflonModel extends EntityModel<MouflonEntity> {
	private final RendererModel body;
	private final RendererModel head;
	private final RendererModel feet1;
	private final RendererModel feet2;
	private final RendererModel feet3;
	private final RendererModel feet4;

	public MouflonModel() {
		textureWidth = 64;
		textureHeight = 64;

		body = new RendererModel(this);
		body.setRotationPoint(0.0F, 13.0F, 0.0F);
		body.cubeList.add(new ModelBox(body, 0, 23, -4.0F, -6.0F, -7.0F, 8, 7, 15, 0.0F, false));
		body.cubeList.add(new ModelBox(body, 0, 20, -1.0F, -5.0F, 8.0F, 2, 3, 1, 0.0F, false));

		head = new RendererModel(this);
		head.setRotationPoint(0.0F, 7.0F, -7.0F);
		head.cubeList.add(new ModelBox(head, 40, 0, -3.0F, -4.0F, -4.0F, 6, 5, 6, 0.0F, false));

		feet1 = new RendererModel(this);
		feet1.setRotationPoint(2.0F, 14.0F, -5.0F);
		feet1.cubeList.add(new ModelBox(feet1, 0, 0, -1.0F, 0.0F, -1.0F, 2, 10, 2, 0.0F, false));

		feet2 = new RendererModel(this);
		feet2.setRotationPoint(2.0F, 14.0F, 6.0F);
		feet2.cubeList.add(new ModelBox(feet2, 0, 0, -1.0F, 0.0F, -1.0F, 2, 10, 2, 0.0F, false));

		feet3 = new RendererModel(this);
		feet3.setRotationPoint(-2.0F, 14.0F, 6.0F);
		feet3.cubeList.add(new ModelBox(feet3, 0, 0, -1.0F, 0.0F, -1.0F, 2, 10, 2, 0.0F, false));

		feet4 = new RendererModel(this);
		feet4.setRotationPoint(-2.0F, 14.0F, -5.0F);
		feet4.cubeList.add(new ModelBox(feet4, 0, 0, -1.0F, 0.0F, -1.0F, 2, 10, 2, 0.0F, false));
	}

	@Override
	public void render(@Nonnull MouflonEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
		this.setRotationAngles(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
		if (isChild) {
			GlStateManager.pushMatrix();
			GlStateManager.scalef(0.75F, 0.75F, 0.75F);
			GlStateManager.translatef(0.0F, 0.5F, 0.0F);
			body.render(scale);
			head.render(scale);
			feet1.render(scale);
			feet2.render(scale);
			feet3.render(scale);
			feet4.render(scale);
			GlStateManager.popMatrix();
		} else {
			body.render(scale);
			head.render(scale);
			feet1.render(scale);
			feet2.render(scale);
			feet3.render(scale);
			feet4.render(scale);
		}
	}

	@Override
	public void setRotationAngles(@Nonnull MouflonEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor) {
		this.head.rotateAngleX = headPitch * ((float)Math.PI / 180F);
		this.head.rotateAngleY = netHeadYaw * ((float)Math.PI / 180F);
		this.feet1.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
		this.feet2.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount;
		this.feet3.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
		this.feet4.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount;
	}
}