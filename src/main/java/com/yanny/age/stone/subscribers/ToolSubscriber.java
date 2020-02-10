package com.yanny.age.stone.subscribers;

import com.yanny.age.stone.Reference;
import com.yanny.age.stone.client.renderer.FlintSpearItemRenderer;
import com.yanny.age.stone.items.BoneTierProperties;
import com.yanny.age.stone.items.HammerItem;
import com.yanny.age.stone.items.SpearItem;
import com.yanny.ages.api.group.ModItemGroup;
import com.yanny.ages.api.items.*;
import net.minecraft.item.*;
import net.minecraft.item.crafting.Ingredient;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;

import javax.annotation.Nonnull;
import java.util.function.Supplier;

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

    public static final Item bone_shears = null;
    public static final Item flint_knife = null;
    public static final Item stone_hammer = null;
    public static final Item flint_spear = null;

    public static final Item bone_axe_head = null;
    public static final Item bone_pickaxe_head = null;
    public static final Item bone_hoe_head = null;
    public static final Item bone_shovel_head = null;
    public static final Item bone_sword_head = null;

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        IForgeRegistry<Item> registry = event.getRegistry();
        BoneTierProperties boneTierProperties = new BoneTierProperties();
        Item.Properties combatProperties = new Item.Properties().maxStackSize(1).group(ModItemGroup.AGES);
        Item.Properties toolProperties = new Item.Properties().maxStackSize(1).group(ModItemGroup.AGES);
        Item.Properties spearProperties = new Item.Properties().maxStackSize(16).maxDamage(250).group(ModItemGroup.AGES).setISTER(() -> FlintSpearItemRenderer::new);

        registry.register(new AgesPickaxeItem(Tiers.BONE_TIER, 1, -3.2f, toolProperties).setRegistryName(Reference.MODID, "bone_pickaxe"));
        registry.register(new AgesAxeItem(Tiers.BONE_TIER, 1, -3.2f, toolProperties).setRegistryName(Reference.MODID, "bone_axe"));
        registry.register(new AgesShovelItem(Tiers.BONE_TIER, 1.5f, -3.2f, toolProperties).setRegistryName(Reference.MODID, "bone_shovel"));
        registry.register(new AgesHoeItem(Tiers.BONE_TIER, -3.2f, toolProperties).setRegistryName(Reference.MODID, "bone_hoe"));
        registry.register(new AgesSwordItem(Tiers.BONE_TIER, 2,-2.5f, combatProperties).setRegistryName(Reference.MODID, "bone_sword"));

        registry.register(new ShearsItem(toolProperties.maxDamage(Tiers.BONE_TIER.maxUses)).setRegistryName(Reference.MODID, "bone_shears"));
        registry.register(new SwordItem(Tiers.BONE_TIER, 0,-1.0f, combatProperties).setRegistryName(Reference.MODID, "flint_knife"));
        registry.register(new HammerItem(ItemTier.STONE, 2, -3.5f, toolProperties).setRegistryName(Reference.MODID, "stone_hammer"));
        registry.register(new SpearItem(Tiers.BONE_TIER, 5.5f, -3.2f, spearProperties).setRegistryName(Reference.MODID, "flint_spear"));

        registry.register(new AgesPartItem(new Item.Properties().group(ModItemGroup.AGES).maxStackSize(64), boneTierProperties).setRegistryName(MODID, "bone_axe_head"));
        registry.register(new AgesPartItem(new Item.Properties().group(ModItemGroup.AGES).maxStackSize(64), boneTierProperties).setRegistryName(MODID, "bone_pickaxe_head"));
        registry.register(new AgesPartItem(new Item.Properties().group(ModItemGroup.AGES).maxStackSize(64), boneTierProperties).setRegistryName(MODID, "bone_hoe_head"));
        registry.register(new AgesPartItem(new Item.Properties().group(ModItemGroup.AGES).maxStackSize(64), boneTierProperties).setRegistryName(MODID, "bone_shovel_head"));
        registry.register(new AgesPartItem(new Item.Properties().group(ModItemGroup.AGES).maxStackSize(64), boneTierProperties).setRegistryName(MODID, "bone_sword_head"));
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
        private final Supplier<Ingredient> repairMaterial;

        Tiers(int harvestLevel, int maxUses, float efficiency, float attackDamage, int enchantability, Supplier<Ingredient> repairMaterial) {
            this.harvestLevel = harvestLevel;
            this.maxUses = maxUses;
            this.efficiency = efficiency;
            this.attackDamage = attackDamage;
            this.enchantability = enchantability;
            this.repairMaterial = repairMaterial;
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

        @Nonnull
        public Ingredient getRepairMaterial() {
            return this.repairMaterial.get();
        }
    }
}

