package com.yanny.age.zero.blocks;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.util.Constants;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

class ItemStackUtils {

    static CompoundNBT serializeStacks(@Nonnull NonNullList<ItemStack> stacks) {
        ListNBT listNBT = new ListNBT();
        CompoundNBT compoundNBT = new CompoundNBT();

        stacks.forEach(itemStack -> {
            CompoundNBT nbt = new CompoundNBT();
            itemStack.write(nbt);
            listNBT.add(nbt);
        });

        compoundNBT.put("Items", listNBT);
        return compoundNBT;
    }

    static CompoundNBT serializeIngredients(@Nonnull List<Ingredient> ingredients) {
        ListNBT listNBT = new ListNBT();
        CompoundNBT compoundNBT = new CompoundNBT();

        ingredients.forEach(ingredient -> {
            ListNBT itemsNBT = new ListNBT();
            CompoundNBT itemsCompound = new CompoundNBT();

            for (ItemStack itemStack : ingredient.getMatchingStacks()) {
                CompoundNBT nbt = new CompoundNBT();
                itemStack.write(nbt);
                itemsNBT.add(nbt);
            }

            itemsCompound.put("Items", itemsNBT);
            listNBT.add(itemsCompound);
        });

        compoundNBT.put("Items", listNBT);
        return compoundNBT;
    }

    static void deserializeStacks(@Nonnull CompoundNBT compoundNBT, @Nonnull NonNullList<ItemStack> stacks) {
        assert compoundNBT.contains("Items", Constants.NBT.TAG_LIST);
        ListNBT listNBT = compoundNBT.getList("Items", Constants.NBT.TAG_COMPOUND);
        assert listNBT.size() == stacks.size();
        AtomicInteger cnt = new AtomicInteger(0);

        listNBT.forEach(nbt -> {
            ItemStack itemStack = ItemStack.read((CompoundNBT) nbt);
            stacks.set(cnt.getAndIncrement(), itemStack);
        });
    }

    static void deserializeIngredients(@Nonnull CompoundNBT compoundNBT, @Nonnull List<Ingredient> ingredients) {
        assert compoundNBT.contains("Items", Constants.NBT.TAG_LIST);
        ListNBT listNBT = compoundNBT.getList("Items", Constants.NBT.TAG_COMPOUND);

        ingredients.clear();

        listNBT.forEach(nbt -> {
            assert ((CompoundNBT) nbt).contains("Items", Constants.NBT.TAG_LIST);
            ListNBT itemsNBT = ((CompoundNBT) nbt).getList("Items", Constants.NBT.TAG_COMPOUND);
            ArrayList<ItemStack> itemStacks = new ArrayList<>();

            itemsNBT.forEach(itemNbt -> {
                ItemStack itemStack = ItemStack.read((CompoundNBT) itemNbt);
                itemStacks.add(itemStack);
            });

            ingredients.add(Ingredient.fromStacks(itemStacks.toArray(new ItemStack[0])));
        });
    }
}
