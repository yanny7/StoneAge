package com.yanny.age.zero.subscribers;

import com.yanny.age.zero.recipes.*;
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
    public static final DryingRackRecipeSerializer drying_rack = null;
    public static final TanningRackRecipeSerializer tanning_rack = null;
    public static final TreeStumpRecipeSerializer tree_stump = null;

    @SubscribeEvent
    public static void registerTileEntity(RegistryEvent.Register<IRecipeSerializer<?>> event) {
        IForgeRegistry<IRecipeSerializer<?>> registry = event.getRegistry();
        registry.register(new FlintWorkbenchRecipeSerializer().setRegistryName(MODID, "flint_workbench"));
        registry.register(new DryingRackRecipeSerializer<>(DryingRackRecipe::new).setRegistryName(MODID, "drying_rack"));
        registry.register(new TanningRackRecipeSerializer<>(TanningRackRecipe::new).setRegistryName(MODID, "tanning_rack"));
        registry.register(new TreeStumpRecipeSerializer<>(TreeStumpRecipe::new).setRegistryName(MODID, "tree_stump"));
    }
}
