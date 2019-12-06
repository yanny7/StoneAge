package com.yanny.age.stone.client.models;

import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.client.renderer.model.Model;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MillstoneModel extends Model {
	private final RendererModel body;

	public MillstoneModel() {
		textureWidth = 64;
		textureHeight = 64;

		body = new RendererModel(this);
		body.setRotationPoint(0.0F, 0.0F, 0.0F);
		body.addBox(-5.0F, 7.0F, -5.0F, 10, 4, 10);
	}

	public void renderAll() {
		this.body.render(0.0625F);
	}

	public void rotate(float angle) {
		this.body.rotateAngleY = angle;
	}
}