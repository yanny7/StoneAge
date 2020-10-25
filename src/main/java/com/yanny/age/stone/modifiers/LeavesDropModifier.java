package com.yanny.age.stone.modifiers;

import com.google.gson.JsonObject;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootParameters;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.common.loot.LootModifier;

import javax.annotation.Nonnull;
import java.util.List;

public class LeavesDropModifier extends LootModifier {
    private final float chance;

    private LeavesDropModifier(ILootCondition[] conditionsIn, float chance) {
        super(conditionsIn);
        this.chance = chance;
    }

    @Nonnull
    @Override
    protected List<ItemStack> doApply(List<ItemStack> generatedLoot, LootContext context) {
        ItemStack ctxTool = context.get(LootParameters.TOOL);
        float chanceAfterLooting = chance;

        if (ctxTool != null) {
            if (EnchantmentHelper.getEnchantments(ctxTool).containsKey(Enchantments.SILK_TOUCH)) {
                return generatedLoot;
            }

            if (EnchantmentHelper.getEnchantments(ctxTool).containsKey(Enchantments.LOOTING)) {
                int enchantmentLevel = EnchantmentHelper.getEnchantmentLevel(Enchantments.LOOTING, ctxTool);
                chanceAfterLooting = chanceAfterLooting * enchantmentLevel;
            }
        }

        if (Math.random() < chanceAfterLooting) {
            generatedLoot.add(new ItemStack(Items.STICK, Math.max(1, Math.round(chanceAfterLooting))));
        }

        return generatedLoot;
    }

    public static class Serializer extends GlobalLootModifierSerializer<LeavesDropModifier> {

        @Override
        public LeavesDropModifier read(ResourceLocation location, JsonObject object, ILootCondition[] conditions) {
            float chance = JSONUtils.getFloat(object, "chance");
            return new LeavesDropModifier(conditions, chance);
        }

        @Override
        public JsonObject write(LeavesDropModifier instance) {
            JsonObject object = new JsonObject();
            object.addProperty("chance", instance.chance);
            return object;
        }
    }
}
