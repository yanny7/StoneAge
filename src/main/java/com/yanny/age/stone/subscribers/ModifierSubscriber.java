package com.yanny.age.stone.subscribers;

import com.yanny.age.stone.conditions.MatchTagCondition;
import com.yanny.age.stone.modifiers.LeavesDropModifier;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.conditions.LootConditionManager;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistry;

import javax.annotation.Nonnull;

import static com.yanny.age.stone.Reference.MODID;

@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModifierSubscriber {

    @SubscribeEvent
    public static void registerModifierSerializers(@Nonnull final RegistryEvent.Register<GlobalLootModifierSerializer<?>> event) {
        IForgeRegistry<GlobalLootModifierSerializer<?>> registry = event.getRegistry();

        registry.register(new LeavesDropModifier.Serializer().setRegistryName(new ResourceLocation(MODID,"leaves_drop")));

        LootConditionManager.registerCondition(new MatchTagCondition.Serializer());
    }
}
