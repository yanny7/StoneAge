package com.yanny.age.zero.subscribers;

import com.yanny.age.zero.Reference;
import com.yanny.age.zero.items.HammerItem;
import com.yanny.age.zero.items.SickleItem;
import net.minecraft.item.*;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.LazyLoadBase;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;

import java.util.function.Supplier;

@SuppressWarnings("unused")
@ObjectHolder(Reference.MODID)
@Mod.EventBusSubscriber(modid = Reference.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ToolSubscriber {

    public static final Item antler_pickaxe = null;
    public static final Item antler_axe = null;
    public static final Item antler_shovel = null;
    public static final Item antler_hoe = null;
    public static final Item bone_sword = null;
    public static final Item antler_sickle = null;
    public static final Item flint_knife = null;
    public static final Item stone_hammer = null;

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        IForgeRegistry<Item> registry = event.getRegistry();
        Item.Properties combatProperties = new Item.Properties().maxStackSize(1).group(ItemGroup.COMBAT);
        Item.Properties toolProperties = new Item.Properties().maxStackSize(1).group(ItemGroup.TOOLS);

        registry.register(new PickaxeItem(Tiers.BONE_TIER, 1, -3.2f, toolProperties).setRegistryName(Reference.MODID, "antler_pickaxe"));
        registry.register(new AxeItem(Tiers.BONE_TIER, 1, -3.2f, toolProperties).setRegistryName(Reference.MODID, "antler_axe"));
        registry.register(new ShovelItem(Tiers.BONE_TIER, 1.5f, -3.2f, toolProperties).setRegistryName(Reference.MODID, "antler_shovel"));
        registry.register(new HoeItem(Tiers.BONE_TIER, -3.2f, toolProperties).setRegistryName(Reference.MODID, "antler_hoe"));
        registry.register(new SwordItem(Tiers.BONE_TIER, 2,-2.5f, combatProperties).setRegistryName(Reference.MODID, "bone_sword"));
        registry.register(new SickleItem(toolProperties.maxDamage(Tiers.BONE_TIER.maxUses)).setRegistryName(Reference.MODID, "antler_sickle"));
        registry.register(new SwordItem(Tiers.BONE_TIER, 0,-1.0f, combatProperties).setRegistryName(Reference.MODID, "flint_knife"));
        registry.register(new HammerItem(ItemTier.STONE, 2, -3.5f, toolProperties).setRegistryName(Reference.MODID, "stone_hammer"));
    }

    public enum Tiers implements IItemTier {
        BONE_TIER(-1, 35, 2.0F, 1.0F, 15, () -> {
            return Ingredient.fromItems(Items.BONE);
        })
        ;

        private final int harvestLevel;
        private final int maxUses;
        private final float efficiency;
        private final float attackDamage;
        private final int enchantability;
        private final LazyLoadBase<Ingredient> repairMaterial;

        Tiers(int harvestLevel, int maxUses, float efficiency, float attackDamage, int enchantability, Supplier<Ingredient> repairMaterial) {
            this.harvestLevel = harvestLevel;
            this.maxUses = maxUses;
            this.efficiency = efficiency;
            this.attackDamage = attackDamage;
            this.enchantability = enchantability;
            this.repairMaterial = new LazyLoadBase<>(repairMaterial);
        }

        public int getMaxUses() {
            return this.maxUses;
        }

        public float getEfficiency() {
            return this.efficiency;
        }

        public float getAttackDamage() {
            return this.attackDamage;
        }

        public int getHarvestLevel() {
            return this.harvestLevel;
        }

        public int getEnchantability() {
            return this.enchantability;
        }

        public Ingredient getRepairMaterial() {
            return this.repairMaterial.getValue();
        }
    }
}

