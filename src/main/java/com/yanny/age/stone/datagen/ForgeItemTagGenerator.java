package com.yanny.age.stone.datagen;

import com.yanny.age.stone.subscribers.ToolSubscriber;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.ItemTagsProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.Tags;
import org.lwjgl.system.NonnullDefault;

import java.nio.file.Path;

@NonnullDefault
public class ForgeItemTagGenerator extends ItemTagsProvider {

    public ForgeItemTagGenerator(DataGenerator dataGenerator) {
        super(dataGenerator);
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    protected void registerTags() {
        getBuilder(Tags.Items.SHEARS).add(ToolSubscriber.bone_shears);
    }

    @Override
    protected Path makePath(ResourceLocation id) {
        return this.generator.getOutputFolder().resolve("data/forge/tags/items/" + id.getPath() + ".json");
    }
}
