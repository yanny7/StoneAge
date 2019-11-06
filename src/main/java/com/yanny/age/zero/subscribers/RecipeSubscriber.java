package com.yanny.age.zero.subscribers;

import com.yanny.age.zero.recipes.FlintWorkbenchRecipeSerializer;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;

import static com.yanny.age.zero.Reference.MODID;

@SuppressWarnings("unused")
@ObjectHolder(MODID)
@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class RecipeSubscriber {
    public static final FlintWorkbenchRecipeSerializer flint_workbench = null;

    @SubscribeEvent
    public static void registerTileEntity(RegistryEvent.Register<IRecipeSerializer<?>> event) {
        IForgeRegistry<IRecipeSerializer<?>> registry = event.getRegistry();
        registry.register(new FlintWorkbenchRecipeSerializer().setRegistryName(MODID, "flint_workbench"));
    }
}
