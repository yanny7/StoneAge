package com.yanny.age.stone.subscribers;

import com.yanny.age.stone.Reference;
import com.yanny.age.stone.effects.LeprosyEffect;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Potion;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;

import static com.yanny.age.stone.Reference.MODID;

@ObjectHolder(Reference.MODID)
@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class PotionEffectSubscriber {
    public static final Effect leprosy = null;

    public static final Potion leprosy_potion = null;
    public static final Potion strong_leprosy_potion = null;
    public static final Potion very_strong_leprosy_potion = null;

    @SubscribeEvent
    public static void registerEffects(RegistryEvent.Register<Effect> event) {
        IForgeRegistry<Effect> registry = event.getRegistry();
        registry.register(new LeprosyEffect().addAttributesModifier(SharedMonsterAttributes.MOVEMENT_SPEED, "7107DE5E-7CE8-4030-940E-514C1F160890", -0.2F, AttributeModifier.Operation.MULTIPLY_TOTAL).setRegistryName(MODID, "leprosy"));
    }

    @SubscribeEvent
    public static void registerPotion(RegistryEvent.Register<Potion> event) {
        IForgeRegistry<Potion> registry = event.getRegistry();
        registry.register(new Potion("leprosy", new EffectInstance(leprosy, 3600)).setRegistryName(MODID, "leprosy_potion"));
        registry.register(new Potion("leprosy", new EffectInstance(leprosy, 1800, 1)).setRegistryName(MODID, "strong_leprosy_potion"));
        registry.register(new Potion("leprosy", new EffectInstance(leprosy, 900, 2)).setRegistryName(MODID, "very_strong_leprosy_potion"));
    }
}
