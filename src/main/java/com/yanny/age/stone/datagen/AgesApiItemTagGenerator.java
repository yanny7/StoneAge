package com.yanny.age.stone.datagen;

import com.yanny.age.stone.subscribers.ItemSubscriber;
import com.yanny.age.stone.subscribers.ToolSubscriber;
import com.yanny.ages.api.Reference;
import com.yanny.ages.api.utils.Tags;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.ItemTagsProvider;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.system.NonnullDefault;

import java.nio.file.Path;

@NonnullDefault
public class AgesApiItemTagGenerator extends ItemTagsProvider {

    public AgesApiItemTagGenerator(DataGenerator dataGenerator) {
        super(dataGenerator);
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    protected void registerTags() {
        getBuilder(Tags.Items.AXES).add(ToolSubscriber.bone_axe).add(ToolSubscriber.stone_axe);
        getBuilder(Tags.Items.BONES).add(ItemSubscriber.antler).add(ItemSubscriber.fish_bone).add(ItemSubscriber.mammoth_tusk).add(ItemSubscriber.rhino_tusk).add(ItemSubscriber.saber_teeth);
        getBuilder(Tags.Items.FISHING_NET_MESHES).add(ItemSubscriber.grass_mesh).add(ItemSubscriber.cobweb_mesh);
        getBuilder(Tags.Items.HAMMERS).add(ToolSubscriber.stone_hammer);
        getBuilder(Tags.Items.KNIVES).add(ToolSubscriber.flint_knife);
    }

    @Override
    protected Path makePath(ResourceLocation id) {
        return this.generator.getOutputFolder().resolve("data/" + Reference.MODID + "/tags/items/" + id.getPath() + ".json");
    }
}
