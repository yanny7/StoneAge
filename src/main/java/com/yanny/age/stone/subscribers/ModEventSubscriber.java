package com.yanny.age.stone.subscribers;

import com.yanny.age.stone.ExampleMod;
import com.yanny.age.stone.compatibility.top.TopCompatibility;
import com.yanny.age.stone.config.ConfigHelper;
import com.yanny.age.stone.config.ConfigHolder;
import com.yanny.age.stone.datagen.AgesApiBlockTagGenerator;
import com.yanny.age.stone.datagen.AgesApiItemTagGenerator;
import com.yanny.age.stone.datagen.ForgeItemTagGenerator;
import com.yanny.age.stone.datagen.RecipeGenerator;
import com.yanny.ages.api.Reference;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;

import javax.annotation.Nonnull;

import static com.yanny.age.stone.Reference.MODID;

@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEventSubscriber {

    @SubscribeEvent
    public static void init(@Nonnull FMLCommonSetupEvent event) {
        ExampleMod.proxy.init();
    }

    @SubscribeEvent
    public static void registerTOP(@Nonnull InterModEnqueueEvent event) {
        TopCompatibility.register();
    }

    @SubscribeEvent
    public static void onModConfigEvent(@Nonnull ModConfig.ModConfigEvent event) {
        final ModConfig config = event.getConfig();

        if (!config.getModId().equals(MODID)) {
            return;
        }

        // Rebake the configs when they change
        if (config.getSpec() == ConfigHolder.CLIENT_SPEC) {
            ConfigHelper.bakeClient();
        } else if (config.getSpec() == ConfigHolder.SERVER_SPEC) {
            ConfigHelper.bakeServer();
        }
    }

    @SuppressWarnings("ConstantConditions")
    @SubscribeEvent
    public static void onClientSetupEvent(@Nonnull FMLClientSetupEvent event) {
        RenderTypeLookup.setRenderLayer(BlockSubscriber.fishing_net, RenderType.getCutoutMipped());
        RenderTypeLookup.setRenderLayer(BlockSubscriber.aqueduct, RenderType.getCutoutMipped());
        RenderTypeLookup.setRenderLayer(BlockSubscriber.dried_grass_bed, RenderType.getCutoutMipped());
    }

    @SubscribeEvent
    public static void dataGen(@Nonnull GatherDataEvent event) {
        if (event.includeServer()) {
            // ages_api MODID
            AgesApiBlockTagGenerator agesApiBlockTagGenerator = new AgesApiBlockTagGenerator(event.getGenerator(), Reference.MODID, event.getExistingFileHelper());
            AgesApiItemTagGenerator agesApiItemTagGenerator = new AgesApiItemTagGenerator(event.getGenerator(), agesApiBlockTagGenerator, Reference.MODID, event.getExistingFileHelper());
            ForgeItemTagGenerator forgeItemTagGenerator = new ForgeItemTagGenerator(event.getGenerator(), agesApiBlockTagGenerator, "forge", event.getExistingFileHelper());
            RecipeGenerator recipeGenerator = new RecipeGenerator(event.getGenerator());

            event.getGenerator().addProvider(agesApiBlockTagGenerator);
            event.getGenerator().addProvider(agesApiItemTagGenerator);
            event.getGenerator().addProvider(forgeItemTagGenerator);
            event.getGenerator().addProvider(recipeGenerator);
        }
    }
}
