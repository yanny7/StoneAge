package com.yanny.age.stone.utils;

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

public class ItemStackUtils {

    public static CompoundNBT serializeStacks(@Nonnull NonNullList<ItemStack> stacks) {
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

    public static CompoundNBT serializeIngredients(@Nonnull List<Ingredient> ingredients) {
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

    public static void deserializeStacks(@Nonnull CompoundNBT compoundNBT, @Nonnull NonNullList<ItemStack> stacks) {
        assert compoundNBT.contains("Items", Constants.NBT.TAG_LIST);
        ListNBT listNBT = compoundNBT.getList("Items", Constants.NBT.TAG_COMPOUND);
        assert listNBT.size() == stacks.size();
        AtomicInteger cnt = new AtomicInteger(0);

        listNBT.forEach(nbt -> {
            ItemStack itemStack = ItemStack.read((CompoundNBT) nbt);
            stacks.set(cnt.getAndIncrement(), itemStack);
        });
    }

    public static void deserializeIngredients(@Nonnull CompoundNBT compoundNBT, @Nonnull List<Ingredient> ingredients) {
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

    public static void insertItems(@Nonnull List<ItemStack> input, List<ItemStack> output, int startIndex, int endIndex) {
        assert output.size() > startIndex && output.size() >= endIndex && startIndex < endIndex;

        for (ItemStack itemStack : input) {
            if (itemStack.isEmpty()) {
                return;
            }

            int index = getFirstFreeOrValid(itemStack, output, startIndex, endIndex);

            if (index < 0) {
                return;
            }

            int items = itemStack.getCount();

            if (!output.get(index).isEmpty()) {
                while (items > 0) {
                    ItemStack item = output.get(index);

                    int amount = item.getMaxStackSize() - item.getCount();

                    if (amount < items) {
                        item.grow(amount);
                        items -= amount;
                        index = getFirstFreeOrValid(itemStack, output, index, endIndex);
                    } else {
                        item.grow(items);
                        items = 0;
                    }
                }
            } else {
                output.set(index, itemStack.copy());
            }
        }
    }

    private static int getFirstFreeOrValid(ItemStack item, List<ItemStack> output, int startIndex, int endIndex) {
        for (int i = startIndex; i < endIndex; i++) {
            ItemStack itemStack = output.get(i);

            if (itemStack.isItemEqual(item)) {
                if (itemStack.getCount() < itemStack.getMaxStackSize()) {
                    return i;
                }
            } else if (itemStack.isEmpty()) {
                return i;
            }
        }

        return -1;
    }
}
