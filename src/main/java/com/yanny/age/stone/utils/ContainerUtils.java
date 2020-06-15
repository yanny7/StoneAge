package com.yanny.age.stone.utils;

import javax.annotation.Nonnull;

public class ContainerUtils {

    public static int addSlotRange(@Nonnull Consumer3P<Integer, Integer, Integer> consumer, int index, int x, int y, int amount, int dx) {
        for (int i = 0 ; i < amount ; i++) {
            consumer.accept(index, x, y);
            x += dx;
            index++;
        }

        return index;
    }

    @SuppressWarnings("SameParameterValue")
    public static void addSlotBox(@Nonnull Consumer3P<Integer, Integer, Integer> consumer, int index, int x, int y, int horAmount, int dx, int verAmount, int dy) {
        for (int j = 0 ; j < verAmount ; j++) {
            index = addSlotRange(consumer, index, x, y, horAmount, dx);
            y += dy;
        }
    }

    @SuppressWarnings("SameParameterValue")
    public static void layoutPlayerInventorySlots(@Nonnull Consumer3P<Integer, Integer, Integer> consumer, int leftCol, int topRow) {
        addSlotBox(consumer, 9, leftCol, topRow, 9, 18, 3, 18);

        // Hotbar offset
        topRow += 58;
        addSlotRange(consumer, 0, leftCol, topRow, 9, 18);
    }
}
