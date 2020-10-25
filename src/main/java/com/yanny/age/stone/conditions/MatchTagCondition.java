package com.yanny.age.stone.conditions;

import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.yanny.age.stone.Reference;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.Tag;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.LootParameter;
import net.minecraft.world.storage.loot.LootParameters;
import net.minecraft.world.storage.loot.conditions.ILootCondition;

import javax.annotation.Nonnull;
import java.util.Set;

public class MatchTagCondition implements ILootCondition {
    private final Tag<Block> tag;

    private MatchTagCondition(Tag<Block> tag) {
        this.tag = tag;
    }

    @Nonnull
    @Override
    public Set<LootParameter<?>> getRequiredParameters() {
        return ImmutableSet.of(LootParameters.BLOCK_STATE);
    }

    @Override
    public boolean test(LootContext lootContext) {
        BlockState blockstate = lootContext.get(LootParameters.BLOCK_STATE);
        return blockstate != null && tag.contains(blockstate.getBlock());
    }

    public static class Serializer extends AbstractSerializer<MatchTagCondition> {
        public Serializer() {
            super(new ResourceLocation(Reference.MODID, "match_tag"), MatchTagCondition.class);
        }

        @Override
        public void serialize(@Nonnull JsonObject jsonObject, @Nonnull MatchTagCondition matchTagCondition, @Nonnull JsonSerializationContext serializationContext) {
            jsonObject.addProperty("tag", matchTagCondition.tag.toString());
        }

        @Nonnull
        @Override
        public MatchTagCondition deserialize(@Nonnull JsonObject jsonObject, @Nonnull JsonDeserializationContext context) {
            Tag<Block> optional = new BlockTags.Wrapper(new ResourceLocation(JSONUtils.getString(jsonObject, "tag")));
            return new MatchTagCondition(optional);
        }
    }
}
