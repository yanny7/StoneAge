package com.yanny.age.stone.datagen;

import com.yanny.age.stone.subscribers.ItemSubscriber;
import com.yanny.age.stone.subscribers.ToolSubscriber;
import com.yanny.ages.api.utils.Tags;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.ItemTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.lwjgl.system.NonnullDefault;

import javax.annotation.Nullable;

@NonnullDefault
public class AgesApiItemTagGenerator extends ItemTagsProvider {

    public AgesApiItemTagGenerator(DataGenerator dataGenerator, BlockTagsProvider blockTagProvider, String modId, @Nullable ExistingFileHelper existingFileHelper) {
        super(dataGenerator, blockTagProvider, modId, existingFileHelper);
    }

    @Override
    protected void registerTags() {
        getOrCreateBuilder(Tags.Items.AXES).add(ToolSubscriber.bone_axe).add(ToolSubscriber.stone_axe);
        getOrCreateBuilder(Tags.Items.BONES).add(ItemSubscriber.antler).add(ItemSubscriber.fish_bone).add(ItemSubscriber.mammoth_tusk).add(ItemSubscriber.rhino_tusk).add(ItemSubscriber.saber_teeth);
        getOrCreateBuilder(Tags.Items.FISHING_NET_MESHES).add(ItemSubscriber.grass_mesh).add(ItemSubscriber.cobweb_mesh);
        getOrCreateBuilder(Tags.Items.HAMMERS).add(ToolSubscriber.stone_hammer);
        getOrCreateBuilder(Tags.Items.KNIVES).add(ToolSubscriber.flint_knife);
    }
}
