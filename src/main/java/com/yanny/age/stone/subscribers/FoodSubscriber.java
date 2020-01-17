package com.yanny.age.stone.subscribers;

import com.yanny.age.stone.Reference;
import com.yanny.ages.api.group.ModItemGroup;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;

import static com.yanny.age.stone.Reference.MODID;

@SuppressWarnings("unused")
@ObjectHolder(Reference.MODID)
@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class FoodSubscriber {
    public static final Item fat = null;
    public static final Item cooked_fat = null;
    public static final Item venison = null;
    public static final Item cooked_venison = null;
    public static final Item fowl_meat = null;
    public static final Item cooked_fowl_meat = null;
    public static final Item auroch_meat = null;
    public static final Item cooked_auroch_meat = null;
    public static final Item mouflon_meat = null;
    public static final Item cooked_mouflon_meat = null;
    public static final Item boar_meat = null;
    public static final Item cooked_boar_meat = null;
    public static final Item mammoth_meat = null;
    public static final Item cooked_mammoth_meat = null;
    public static final Item rhino_meat = null;
    public static final Item cooked_rhino_meat = null;
    public static final Item tiger_meat = null;
    public static final Item cooked_tiger_meat = null;

    private static final Food FAT = (new Food.Builder()).hunger(1).saturation(0.1f).meat().build();
    private static final Food COOKED_FAT = (new Food.Builder()).hunger(2).saturation(0.2f).meat().build();
    private static final Food VENISON = (new Food.Builder()).hunger(1).saturation(0.15f).meat().build();
    private static final Food COOKED_VENISON = (new Food.Builder()).hunger(3).saturation(0.3f).meat().build();
    private static final Food FOWL_MEAT = (new Food.Builder()).hunger(2).saturation(0.2f).meat().build();
    private static final Food COOKED_FOWL_MEAT = (new Food.Builder()).hunger(4).saturation(0.4f).meat().build();
    private static final Food AUROCH_MEAT = (new Food.Builder()).hunger(2).saturation(0.25f).meat().build();
    private static final Food COOKED_AUROCH_MEAT = (new Food.Builder()).hunger(5).saturation(0.5f).meat().build();
    private static final Food MOUFLON_MEAT = (new Food.Builder()).hunger(3).saturation(0.3f).meat().build();
    private static final Food COOKED_MOUFLON_MEAT = (new Food.Builder()).hunger(6).saturation(0.6f).meat().build();
    private static final Food BOAR_MEAT = (new Food.Builder()).hunger(3).saturation(0.35f).meat().build();
    private static final Food COOKED_BOAR_MEAT = (new Food.Builder()).hunger(7).saturation(0.7f).meat().build();
    private static final Food MAMMOTH_MEAT = (new Food.Builder()).hunger(4).saturation(0.4f).meat().build();
    private static final Food COOKED_MAMMOTH_MEAT = (new Food.Builder()).hunger(8).saturation(0.8f).meat().build();
    private static final Food RHINO_MEAT = (new Food.Builder()).hunger(4).saturation(0.45f).meat().build();
    private static final Food COOKED_RHINO_MEAT = (new Food.Builder()).hunger(9).saturation(0.9f).meat().build();
    private static final Food TIGER_MEAT = (new Food.Builder()).hunger(5).saturation(0.5f).meat().build();
    private static final Food COOKED_TIGER_MEAT = (new Food.Builder()).hunger(10).saturation(1.0f).meat().build();

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        IForgeRegistry<Item> registry = event.getRegistry();
        registry.register(new Item(new Item.Properties().group(ModItemGroup.AGES).maxStackSize(64).food(FAT)).setRegistryName(MODID, "fat"));
        registry.register(new Item(new Item.Properties().group(ModItemGroup.AGES).maxStackSize(64).food(COOKED_FAT)).setRegistryName(MODID, "cooked_fat"));
        registry.register(new Item(new Item.Properties().group(ModItemGroup.AGES).maxStackSize(64).food(VENISON)).setRegistryName(MODID, "venison"));
        registry.register(new Item(new Item.Properties().group(ModItemGroup.AGES).maxStackSize(64).food(COOKED_VENISON)).setRegistryName(MODID, "cooked_venison"));
        registry.register(new Item(new Item.Properties().group(ModItemGroup.AGES).maxStackSize(64).food(FOWL_MEAT)).setRegistryName(MODID, "fowl_meat"));
        registry.register(new Item(new Item.Properties().group(ModItemGroup.AGES).maxStackSize(64).food(COOKED_FOWL_MEAT)).setRegistryName(MODID, "cooked_fowl_meat"));
        registry.register(new Item(new Item.Properties().group(ModItemGroup.AGES).maxStackSize(64).food(AUROCH_MEAT)).setRegistryName(MODID, "auroch_meat"));
        registry.register(new Item(new Item.Properties().group(ModItemGroup.AGES).maxStackSize(64).food(COOKED_AUROCH_MEAT)).setRegistryName(MODID, "cooked_auroch_meat"));
        registry.register(new Item(new Item.Properties().group(ModItemGroup.AGES).maxStackSize(64).food(MOUFLON_MEAT)).setRegistryName(MODID, "mouflon_meat"));
        registry.register(new Item(new Item.Properties().group(ModItemGroup.AGES).maxStackSize(64).food(COOKED_MOUFLON_MEAT)).setRegistryName(MODID, "cooked_mouflon_meat"));
        registry.register(new Item(new Item.Properties().group(ModItemGroup.AGES).maxStackSize(64).food(BOAR_MEAT)).setRegistryName(MODID, "boar_meat"));
        registry.register(new Item(new Item.Properties().group(ModItemGroup.AGES).maxStackSize(64).food(COOKED_BOAR_MEAT)).setRegistryName(MODID, "cooked_boar_meat"));
        registry.register(new Item(new Item.Properties().group(ModItemGroup.AGES).maxStackSize(64).food(MAMMOTH_MEAT)).setRegistryName(MODID, "mammoth_meat"));
        registry.register(new Item(new Item.Properties().group(ModItemGroup.AGES).maxStackSize(64).food(COOKED_MAMMOTH_MEAT)).setRegistryName(MODID, "cooked_mammoth_meat"));
        registry.register(new Item(new Item.Properties().group(ModItemGroup.AGES).maxStackSize(64).food(RHINO_MEAT)).setRegistryName(MODID, "rhino_meat"));
        registry.register(new Item(new Item.Properties().group(ModItemGroup.AGES).maxStackSize(64).food(COOKED_RHINO_MEAT)).setRegistryName(MODID, "cooked_rhino_meat"));
        registry.register(new Item(new Item.Properties().group(ModItemGroup.AGES).maxStackSize(64).food(TIGER_MEAT)).setRegistryName(MODID, "tiger_meat"));
        registry.register(new Item(new Item.Properties().group(ModItemGroup.AGES).maxStackSize(64).food(COOKED_TIGER_MEAT)).setRegistryName(MODID, "cooked_tiger_meat"));
    }
}
