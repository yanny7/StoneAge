package com.yanny.age.zero.client.models;

import com.yanny.age.zero.entities.FowlEntity;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.client.renderer.model.ModelBox;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class FowlModel extends EntityModel<FowlEntity> {
	private final RendererModel body;
	private final RendererModel head;
	private final RendererModel foot1;
	private final RendererModel foot2;
	private final RendererModel wing1;
	private final RendererModel wing2;

	public FowlModel() {
		textureWidth = 64;
		textureHeight = 64;

		body = new RendererModel(this);
		body.setRotationPoint(0.0F, 17.0F, 0.0F);
		body.cubeList.add(new ModelBox(body, 36, 0, -3.0F, -3.0F, -4.0F, 6, 6, 8, 0.0F, false));
		body.cubeList.add(new ModelBox(body, 52, 14, -1.0F, -5.0F, 3.0F, 2, 5, 4, 0.0F, false));

		head = new RendererModel(this);
		head.setRotationPoint(0.0F, 14.0F, -4.0F);
		head.cubeList.add(new ModelBox(head, 22, 0, -2.0F, -5.0F, -2.0F, 4, 7, 3, 0.0F, false));
		head.cubeList.add(new ModelBox(head, 36, 5, -1.0F, -3.0F, -4.0F, 2, 1, 2, 0.0F, false));

		foot1 = new RendererModel(this);
		foot1.setRotationPoint(-1.0F, 20.0F, 0.0F);
		foot1.cubeList.add(new ModelBox(foot1, 60, 0, -1.0F, -1.0F, 0.0F, 1, 5, 1, 0.0F, false));
		foot1.cubeList.add(new ModelBox(foot1, 24, 11, -2.0F, 4.0F, -2.0F, 3, 0, 3, 0.0F, false));

		foot2 = new RendererModel(this);
		foot2.setRotationPoint(1.0F, 20.0F, 0.0F);
		foot2.cubeList.add(new ModelBox(foot2, 60, 0, 0.0F, -1.0F, 0.0F, 1, 5, 1, 0.0F, false));
		foot2.cubeList.add(new ModelBox(foot2, 24, 11, -1.0F, 4.0F, -2.0F, 3, 0, 3, 0.0F, false));

		wing1 = new RendererModel(this);
		wing1.setRotationPoint(3.0F, 15.0F, 0.0F);
		wing1.cubeList.add(new ModelBox(wing1, 0, 0, 0.0F, 0.0F, -3.0F, 1, 4, 5, 0.0F, false));

		wing2 = new RendererModel(this);
		wing2.setRotationPoint(-3.0F, 15.0F, 0.0F);
		wing2.cubeList.add(new ModelBox(wing2, 0, 0, -1.0F, 0.0F, -3.0F, 1, 4, 5, 0.0F, false));
	}

	@Override
	public void render(FowlEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
		body.render(scale);
		head.render(scale);
		foot1.render(scale);
		foot2.render(scale);
		wing1.render(scale);
		wing2.render(scale);
	}

	@Override
	public void setRotationAngles(FowlEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor) {
		this.head.rotateAngleX = headPitch * ((float)Math.PI / 180F);
		this.head.rotateAngleY = netHeadYaw * ((float)Math.PI / 180F);
		this.foot1.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
		this.foot2.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount;
		this.wing1.rotateAngleZ = -ageInTicks;
		this.wing2.rotateAngleZ = ageInTicks;
	}
}