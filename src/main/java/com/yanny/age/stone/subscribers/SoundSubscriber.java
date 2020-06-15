package com.yanny.age.stone.subscribers;

import com.yanny.age.stone.Reference;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;

import javax.annotation.Nonnull;

import static com.yanny.age.stone.Reference.MODID;

@ObjectHolder(Reference.MODID)
@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class SoundSubscriber {
    public static final SoundEvent mammoth_ambient = null;
    public static final SoundEvent mammoth_hit = null;
    public static final SoundEvent mammoth_death = null;
    public static final SoundEvent saber_tooth_tiger_ambient = null;
    public static final SoundEvent saber_tooth_tiger_hit = null;
    public static final SoundEvent saber_tooth_tiger_death = null;
    public static final SoundEvent woolly_rhino_ambient = null;
    public static final SoundEvent woolly_rhino_hit = null;
    public static final SoundEvent woolly_rhino_death = null;

    @SubscribeEvent
    public static void registerSounds(@Nonnull RegistryEvent.Register<SoundEvent> event) {
        IForgeRegistry<SoundEvent> registry = event.getRegistry();
        registry.register(new SoundEvent(new ResourceLocation(Reference.MODID, "mammoth_ambient")).setRegistryName(MODID, "mammoth_ambient"));
        registry.register(new SoundEvent(new ResourceLocation(Reference.MODID, "mammoth_hit")).setRegistryName(MODID, "mammoth_hit"));
        registry.register(new SoundEvent(new ResourceLocation(Reference.MODID, "mammoth_death")).setRegistryName(MODID, "mammoth_death"));
        registry.register(new SoundEvent(new ResourceLocation(Reference.MODID, "saber_tooth_tiger_ambient")).setRegistryName(MODID, "saber_tooth_tiger_ambient"));
        registry.register(new SoundEvent(new ResourceLocation(Reference.MODID, "saber_tooth_tiger_hit")).setRegistryName(MODID, "saber_tooth_tiger_hit"));
        registry.register(new SoundEvent(new ResourceLocation(Reference.MODID, "saber_tooth_tiger_death")).setRegistryName(MODID, "saber_tooth_tiger_death"));
        registry.register(new SoundEvent(new ResourceLocation(Reference.MODID, "woolly_rhino_ambient")).setRegistryName(MODID, "woolly_rhino_ambient"));
        registry.register(new SoundEvent(new ResourceLocation(Reference.MODID, "woolly_rhino_hit")).setRegistryName(MODID, "woolly_rhino_hit"));
        registry.register(new SoundEvent(new ResourceLocation(Reference.MODID, "woolly_rhino_death")).setRegistryName(MODID, "woolly_rhino_death"));
    }
}
