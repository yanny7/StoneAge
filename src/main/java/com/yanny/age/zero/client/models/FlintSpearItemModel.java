package com.yanny.age.zero.client.models;

import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.model.ModelBox;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class FlintSpearItemModel extends Model {
	private final RendererModel bone;

	public FlintSpearItemModel() {
		textureWidth = 32;
		textureHeight = 32;

		bone = new RendererModel(this);
		bone.cubeList.add(new ModelBox(bone, 0, 0, -0.5F, -16F, -0.5F, 1, 29, 1, 0.0F, false));
		bone.cubeList.add(new ModelBox(bone, 4, 0, -1.5F, 12F, -0.5F, 1, 2, 1, 0.0F, false));
		bone.cubeList.add(new ModelBox(bone, 8, 0, 0.5F, 12F, -0.5F, 1, 2, 1, 0.0F, false));
		bone.cubeList.add(new ModelBox(bone, 4, 3, -1.0F, 14F, -0.5F, 2, 1, 1, 0.0F, false));
		bone.cubeList.add(new ModelBox(bone, 4, 5, -0.5F, 13f, -0.5F, 1, 3, 1, 0.0F, false));
		bone.cubeList.add(new ModelBox(bone, 4, 9, -0.5F, 13F, -1.0F, 1, 2, 2, 0.0F, false));
	}

	public void render() {
		bone.render(1 / 16f);
	}
}