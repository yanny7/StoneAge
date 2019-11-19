package com.yanny.age.zero.client.models;

import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.model.ModelBox;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class StoneChestModel extends Model {
	private final RendererModel body;
	private final RendererModel lid;

	public StoneChestModel() {
		textureWidth = 64;
		textureHeight = 64;

		body = new RendererModel(this);
		body.setRotationPoint(0.0F, 24.0F, 0.0F);
		body.cubeList.add(new ModelBox(body, 0, 48, -7.0F, -2.0F, -7.0F, 14, 2, 14, 0.0F, false));
		body.cubeList.add(new ModelBox(body, 0, 24, -7.0F, -12.0F, -7.0F, 2, 10, 14, 0.0F, false));
		body.cubeList.add(new ModelBox(body, 0, 24, 5.0F, -12.0F, -7.0F, 2, 10, 14, 0.0F, false));
		body.cubeList.add(new ModelBox(body, 32, 36, -5.0F, -12.0F, -7.0F, 10, 10, 2, 0.0F, false));
		body.cubeList.add(new ModelBox(body, 32, 36, -5.0F, -12.0F, 5.0F, 10, 10, 2, 0.0F, false));

		lid = new RendererModel(this);
		lid.setRotationPoint(3.0F, 10.0F, 5.0F);
		lid.cubeList.add(new ModelBox(lid, 0, 0, -10.0F, 0.0F, -12.0F, 14, 2, 14, 0.0F, false));
	}

	public void renderAll() {
		this.lid.render(0.0625F);
		this.body.render(0.0625F);
	}

	public RendererModel getLid() {
		return lid;
	}
}