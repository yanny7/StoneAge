package com.yanny.age.zero.subscribers;

import com.yanny.age.zero.Reference;
import com.yanny.ages.api.group.ModItemGroup;
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

    public static final Item flint_axe = null;
    public static final Item flint_pickaxe = null;
    public static final Item flint_shovel = null;
    public static final Item flint_sword = null;
    public static final Item flint_hoe = null;

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        IForgeRegistry<Item> registry = event.getRegistry();
        Item.Properties combatProperties = new Item.Properties().maxStackSize(1).group(ItemGroup.COMBAT);
        Item.Properties toolProperties = new Item.Properties().maxStackSize(1).group(ItemGroup.TOOLS);

        registry.register(new AxeItem(FlintTier.FLINT_TIER, 5, -3.6f, toolProperties).setRegistryName(Reference.MODID, "flint_axe"));
        registry.register(new PickaxeItem(FlintTier.FLINT_TIER, 1, -3.2f, toolProperties).setRegistryName(Reference.MODID, "flint_pickaxe"));
        registry.register(new ShovelItem(FlintTier.FLINT_TIER, 1.5f, -3.6f, toolProperties).setRegistryName(Reference.MODID, "flint_shovel"));
        registry.register(new SwordItem(FlintTier.FLINT_TIER, 3, -3.2f, combatProperties).setRegistryName(Reference.MODID, "flint_sword"));
        registry.register(new HoeItem(FlintTier.FLINT_TIER, -3.4f, toolProperties).setRegistryName(Reference.MODID, "flint_hoe"));
    }

    private enum FlintTier implements IItemTier {
        FLINT_TIER(0, 59, 2.0F, 0.0F, 15, () -> {
            return Ingredient.fromItems(Items.FLINT);
        })
        ;

        private final int harvestLevel;
        private final int maxUses;
        private final float efficiency;
        private final float attackDamage;
        private final int enchantability;
        private final LazyLoadBase<Ingredient> repairMaterial;

        FlintTier(int harvestLevel, int maxUses, float efficiency, float attackDamage, int enchantability, Supplier<Ingredient> repairMaterial) {
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

