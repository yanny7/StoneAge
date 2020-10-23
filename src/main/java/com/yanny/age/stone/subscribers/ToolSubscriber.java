package com.yanny.age.stone.subscribers;

import com.yanny.age.stone.Reference;
import com.yanny.age.stone.client.renderer.FlintSpearItemRenderer;
import com.yanny.age.stone.items.BoneTierProperties;
import com.yanny.age.stone.items.HammerItem;
import com.yanny.age.stone.items.SpearItem;
import com.yanny.age.stone.items.StoneTierProperties;
import com.yanny.age.stone.group.ModItemGroup;
import com.yanny.ages.api.items.*;
import net.minecraft.item.Item;
import net.minecraft.item.ItemTier;
import net.minecraft.item.ShearsItem;
import net.minecraft.item.SwordItem;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;

import javax.annotation.Nonnull;

import static com.yanny.age.stone.Reference.MODID;

@SuppressWarnings("unused")
@ObjectHolder(Reference.MODID)
@Mod.EventBusSubscriber(modid = Reference.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ToolSubscriber {

    public static final Item bone_pickaxe = null;
    public static final Item bone_axe = null;
    public static final Item bone_shovel = null;
    public static final Item bone_hoe = null;
    public static final Item bone_sword = null;
    public static final Item stone_pickaxe = null;
    public static final Item stone_axe = null;
    public static final Item stone_shovel = null;
    public static final Item stone_hoe = null;
    public static final Item stone_sword = null;

    public static final Item bone_shears = null;
    public static final Item flint_knife = null;
    public static final Item stone_hammer = null;
    public static final Item flint_spear = null;

    public static final Item bone_axe_head = null;
    public static final Item bone_pickaxe_head = null;
    public static final Item bone_hoe_head = null;
    public static final Item bone_shovel_head = null;
    public static final Item bone_sword_head = null;
    public static final Item stone_axe_head = null;
    public static final Item stone_pickaxe_head = null;
    public static final Item stone_hoe_head = null;
    public static final Item stone_shovel_head = null;
    public static final Item stone_sword_head = null;

    @SubscribeEvent
    public static void registerItems(@Nonnull RegistryEvent.Register<Item> event) {
        IForgeRegistry<Item> registry = event.getRegistry();
        BoneTierProperties boneTierProperties = new BoneTierProperties();
        StoneTierProperties stoneTierProperties = new StoneTierProperties();
        Item.Properties combatProperties = new Item.Properties().maxStackSize(1).group(ModItemGroup.AGES);
        Item.Properties toolProperties = new Item.Properties().maxStackSize(1).group(ModItemGroup.AGES);
        Item.Properties spearProperties = new Item.Properties().maxStackSize(16).maxDamage(250).group(ModItemGroup.AGES).setISTER(() -> FlintSpearItemRenderer::new);

        registry.register(new AgesPickaxeItem(ItemTier.WOOD, 1, -2.8f, toolProperties).setRegistryName(Reference.MODID, "bone_pickaxe"));
        registry.register(new AgesAxeItem(ItemTier.WOOD, 6, -3.2f, toolProperties).setRegistryName(Reference.MODID, "bone_axe"));
        registry.register(new AgesShovelItem(ItemTier.WOOD, 1.5f, -3.0f, toolProperties).setRegistryName(Reference.MODID, "bone_shovel"));
        registry.register(new AgesHoeItem(ItemTier.WOOD, -3.0f, toolProperties).setRegistryName(Reference.MODID, "bone_hoe"));
        registry.register(new AgesSwordItem(ItemTier.WOOD, 3,-2.4f, combatProperties).setRegistryName(Reference.MODID, "bone_sword"));

        registry.register(new AgesPickaxeItem(ItemTier.STONE, 1, -2.8f, toolProperties).setRegistryName(Reference.MODID, "stone_pickaxe"));
        registry.register(new AgesAxeItem(ItemTier.STONE, 7, -3.2f, toolProperties).setRegistryName(Reference.MODID, "stone_axe"));
        registry.register(new AgesShovelItem(ItemTier.STONE, 1.5f, -3.0f, toolProperties).setRegistryName(Reference.MODID, "stone_shovel"));
        registry.register(new AgesHoeItem(ItemTier.STONE, -2.0f, toolProperties).setRegistryName(Reference.MODID, "stone_hoe"));
        registry.register(new AgesSwordItem(ItemTier.STONE, 3,-2.4f, combatProperties).setRegistryName(Reference.MODID, "stone_sword"));

        registry.register(new ShearsItem(toolProperties.maxDamage(ItemTier.WOOD.getMaxUses())).setRegistryName(Reference.MODID, "bone_shears"));
        registry.register(new SwordItem(ItemTier.WOOD, 0,-1.0f, combatProperties).setRegistryName(Reference.MODID, "flint_knife"));
        registry.register(new HammerItem(ItemTier.STONE, 2, -3.5f, toolProperties).setRegistryName(Reference.MODID, "stone_hammer"));
        registry.register(new SpearItem(ItemTier.WOOD, 5.5f, -3.2f, spearProperties).setRegistryName(Reference.MODID, "flint_spear"));

        registry.register(new AgesPartItem(new Item.Properties().group(ModItemGroup.AGES).maxStackSize(64), boneTierProperties).setRegistryName(MODID, "bone_axe_head"));
        registry.register(new AgesPartItem(new Item.Properties().group(ModItemGroup.AGES).maxStackSize(64), boneTierProperties).setRegistryName(MODID, "bone_pickaxe_head"));
        registry.register(new AgesPartItem(new Item.Properties().group(ModItemGroup.AGES).maxStackSize(64), boneTierProperties).setRegistryName(MODID, "bone_hoe_head"));
        registry.register(new AgesPartItem(new Item.Properties().group(ModItemGroup.AGES).maxStackSize(64), boneTierProperties).setRegistryName(MODID, "bone_shovel_head"));
        registry.register(new AgesPartItem(new Item.Properties().group(ModItemGroup.AGES).maxStackSize(64), boneTierProperties).setRegistryName(MODID, "bone_sword_head"));

        registry.register(new AgesPartItem(new Item.Properties().group(ModItemGroup.AGES).maxStackSize(64), stoneTierProperties).setRegistryName(MODID, "stone_axe_head"));
        registry.register(new AgesPartItem(new Item.Properties().group(ModItemGroup.AGES).maxStackSize(64), stoneTierProperties).setRegistryName(MODID, "stone_pickaxe_head"));
        registry.register(new AgesPartItem(new Item.Properties().group(ModItemGroup.AGES).maxStackSize(64), stoneTierProperties).setRegistryName(MODID, "stone_hoe_head"));
        registry.register(new AgesPartItem(new Item.Properties().group(ModItemGroup.AGES).maxStackSize(64), stoneTierProperties).setRegistryName(MODID, "stone_shovel_head"));
        registry.register(new AgesPartItem(new Item.Properties().group(ModItemGroup.AGES).maxStackSize(64), stoneTierProperties).setRegistryName(MODID, "stone_sword_head"));
    }
}

