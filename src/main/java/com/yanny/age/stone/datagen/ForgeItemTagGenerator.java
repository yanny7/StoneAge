package com.yanny.age.stone.datagen;

import com.yanny.age.stone.subscribers.ToolSubscriber;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.ItemTagsProvider;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.lwjgl.system.NonnullDefault;

import javax.annotation.Nullable;

@NonnullDefault
public class ForgeItemTagGenerator extends ItemTagsProvider {

    public ForgeItemTagGenerator(DataGenerator dataGenerator, BlockTagsProvider blockTagProvider, String modId, @Nullable ExistingFileHelper existingFileHelper) {
        super(dataGenerator, blockTagProvider, modId, existingFileHelper);
    }

    @Override
    protected void registerTags() {
        getOrCreateBuilder(Tags.Items.SHEARS).add(ToolSubscriber.bone_shears);
    }
}
