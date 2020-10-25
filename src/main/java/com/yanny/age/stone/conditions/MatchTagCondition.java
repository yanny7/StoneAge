package com.yanny.age.stone.conditions;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.yanny.age.stone.subscribers.ModifierSubscriber;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.loot.ILootSerializer;
import net.minecraft.loot.LootConditionType;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootParameters;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.Tags;

import javax.annotation.Nonnull;

public class MatchTagCondition implements ILootCondition {
    private final Tags.IOptionalNamedTag<Block> tag;

    private MatchTagCondition(Tags.IOptionalNamedTag<Block> tag) {
        this.tag = tag;
    }

    @Nonnull
    @Override
    public LootConditionType func_230419_b_() {
        return ModifierSubscriber.matchTag;
    }

    @Override
    public boolean test(LootContext lootContext) {
        BlockState blockstate = lootContext.get(LootParameters.BLOCK_STATE);
        return blockstate != null && tag.contains(blockstate.getBlock());
    }

    public static class Serializer implements ILootSerializer<MatchTagCondition> {

        @Override
        public void serialize(@Nonnull JsonObject jsonObject, @Nonnull MatchTagCondition matchTagCondition, @Nonnull JsonSerializationContext serializationContext) {
            jsonObject.addProperty("tag", matchTagCondition.tag.getName().toString());
        }

        @Nonnull
        @Override
        public MatchTagCondition deserialize(@Nonnull JsonObject jsonObject, @Nonnull JsonDeserializationContext context) {
            Tags.IOptionalNamedTag<Block> optional = BlockTags.createOptional(new ResourceLocation(JSONUtils.getString(jsonObject, "tag")));
            return new MatchTagCondition(optional);
        }
    }
}
