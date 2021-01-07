package com.yanny.age.stone.datagen;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Sets;
import com.mojang.datafixers.util.Pair;
import com.yanny.age.stone.subscribers.BlockSubscriber;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.LootTableProvider;
import net.minecraft.data.loot.BlockLootTables;
import net.minecraft.loot.*;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.system.NonnullDefault;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

@NonnullDefault
public class LootTableGenerator extends LootTableProvider {

    private static final Set<Block> BLOCKS = Sets.newHashSet(BlockSubscriber.aqueduct, BlockSubscriber.drying_rack, BlockSubscriber.feeder, BlockSubscriber.fishing_net,
            BlockSubscriber.flint_workbench, BlockSubscriber.millstone, BlockSubscriber.stone_chest, BlockSubscriber.tanning_rack, BlockSubscriber.thatch_block,
            BlockSubscriber.thatch_stairs, BlockSubscriber.tree_stump);

    public LootTableGenerator(DataGenerator dataGeneratorIn) {
        super(dataGeneratorIn);
    }

    @Override
    protected List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootParameterSet>> getTables() {
        return ImmutableList.of(Pair.of(Blocks::new, LootParameterSets.BLOCK));
    }

    @Override
    protected void validate(Map<ResourceLocation, LootTable> map, ValidationTracker validationtracker) {
        map.forEach((name, table) -> LootTableManager.validateLootTable(validationtracker, name, table));
    }

    private static class Blocks extends BlockLootTables {
        @Override
        protected void addTables() {
            BLOCKS.forEach(this::registerDropSelfLootTable);
        }

        @Override
        protected Iterable<Block> getKnownBlocks() {
            return new ArrayList<>(BLOCKS);
        }
    }
}
