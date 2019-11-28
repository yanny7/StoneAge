package com.yanny.age.stone.client.models;

import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.model.ModelBox;

public class DroughtGrassBedModel extends Model {
	private final RendererModel head;
	private final RendererModel foot;

	public DroughtGrassBedModel() {
		textureWidth = 64;
		textureHeight = 64;

		head = new RendererModel(this);
		head.cubeList.add(new ModelBox(head, 0, 17, 2.0F, -1.5F, 2.0F, 12, 3, 14, 0.0F, false));

		foot = new RendererModel(this);
		foot.cubeList.add(new ModelBox(foot, 0, 0, 2.0F, -1.5F, 0, 12, 3, 14, 0.0F, false));
	}

	public void render() {
		head.render(0.0625F);
		foot.render(0.0625F);
	}

	public void setVisibleHead() {
		head.showModel = true;
		foot.showModel = false;
	}

	public void setVisibleFoot() {
		head.showModel = false;
		foot.showModel = true;
	}
}