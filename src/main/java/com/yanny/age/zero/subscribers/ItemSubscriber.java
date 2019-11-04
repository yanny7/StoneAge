package com.yanny.age.zero.subscribers;

import com.yanny.age.zero.Reference;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;

import static com.yanny.age.zero.Reference.MODID;

@SuppressWarnings("unused")
@ObjectHolder(Reference.MODID)
@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ItemSubscriber {
    public static final Item venison = null;
    public static final Item cooked_venison = null;
    public static final Item fat = null;
    public static final Item cooked_fat = null;
    public static final Item raw_hide = null;

    private static final Food VENISON = (new Food.Builder()).hunger(3).saturation(0.3f).meat().build();
    private static final Food COOKED_VENISON = (new Food.Builder()).hunger(8).saturation(0.8f).meat().build();
    private static final Food FAT = (new Food.Builder()).hunger(1).saturation(0.1f).meat().build();
    private static final Food COOKED_FAT = (new Food.Builder()).hunger(6).saturation(0.6f).meat().build();

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        IForgeRegistry<Item> registry = event.getRegistry();
        registry.register(new Item(new Item.Properties().group(ItemGroup.FOOD).maxStackSize(64).food(VENISON)).setRegistryName(MODID, "venison"));
        registry.register(new Item(new Item.Properties().group(ItemGroup.FOOD).maxStackSize(64).food(COOKED_VENISON)).setRegistryName(MODID, "cooked_venison"));
        registry.register(new Item(new Item.Properties().group(ItemGroup.FOOD).maxStackSize(64).food(FAT)).setRegistryName(MODID, "fat"));
        registry.register(new Item(new Item.Properties().group(ItemGroup.FOOD).maxStackSize(64).food(COOKED_FAT)).setRegistryName(MODID, "cooked_fat"));
        registry.register(new Item(new Item.Properties().group(ItemGroup.MATERIALS).maxStackSize(64)).setRegistryName(MODID, "raw_hide"));
    }
}
