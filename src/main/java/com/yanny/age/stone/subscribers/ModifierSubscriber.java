package com.yanny.age.stone.subscribers;

import com.yanny.age.stone.conditions.MatchTagCondition;
import com.yanny.age.stone.modifiers.LeavesDropModifier;
import net.minecraft.loot.LootConditionType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistry;

import javax.annotation.Nonnull;

import static com.yanny.age.stone.Reference.MODID;

@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModifierSubscriber {
    public static LootConditionType matchTag = null;

    @SubscribeEvent
    public static void registerModifierSerializers(@Nonnull final RegistryEvent.Register<GlobalLootModifierSerializer<?>> event) {
        IForgeRegistry<GlobalLootModifierSerializer<?>> registry = event.getRegistry();

        registry.register(new LeavesDropModifier.Serializer().setRegistryName(new ResourceLocation(MODID,"leaves_drop")));

        matchTag = Registry.register(Registry.LOOT_CONDITION_TYPE, new ResourceLocation(MODID, "match_tag"), new LootConditionType(new MatchTagCondition.Serializer()));
    }
}
