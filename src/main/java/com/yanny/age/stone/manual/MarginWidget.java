package com.yanny.age.stone.manual;

import com.google.gson.JsonObject;
import com.mojang.datafixers.util.Pair;
import org.apache.commons.lang3.ArrayUtils;

import javax.annotation.Nonnull;

import static com.yanny.age.stone.manual.ConfigHolder.*;

public abstract class MarginWidget extends ConfigurableWidget {

    private final int margin_top;
    private final int margin_bottom;
    private final int tmpMarginLeft;
    private final int tmpMarginRight;
    private final Align align;

    private int margin_left;
    private int margin_right;

    @SafeVarargs
    MarginWidget(JsonObject object, IManual manual, @Nonnull Pair<String, ConfigHolder.Obj<?, ?>>...types) {
        //noinspection unchecked
        super(object, manual, ArrayUtils.addAll(types, MARGIN_LEFT, MARGIN_RIGHT, MARGIN_TOP, MARGIN_BOTTOM, ALIGN_CENTER));

        margin_top = configHolder.getValue(MARGIN_TOP);
        tmpMarginLeft = configHolder.getValue(MARGIN_LEFT);
        margin_bottom = configHolder.getValue(MARGIN_BOTTOM);
        tmpMarginRight = configHolder.getValue(MARGIN_RIGHT);
        align = configHolder.getValue(ALIGN_CENTER);
    }

    abstract int getRawWidth();

    @Override
    public void setWidth(int width) {
        int minWidth = getRawWidth();

        if (minWidth < width) {
            switch (align) {
                case CENTER:
                    if (tmpMarginLeft < 0) {
                        if (tmpMarginRight < 0) {
                            margin_left = margin_right = (width - minWidth) / 2;
                        } else {
                            margin_left = (width - minWidth - tmpMarginRight);
                            margin_right = tmpMarginRight;
                        }
                    } else {
                        margin_left = tmpMarginLeft;
                        margin_right = (width - minWidth - tmpMarginLeft);
                    }
                    break;
                case RIGHT:
                    if (tmpMarginLeft < 0) {
                        margin_left = width - minWidth;
                        margin_right = 0;
                    } else {
                        margin_left = tmpMarginLeft;
                        margin_right = (width - minWidth - tmpMarginLeft);
                    }
                    break;
                case LEFT:
                    margin_left = Math.max(tmpMarginLeft, 0);
                    margin_right = (width - minWidth - tmpMarginLeft);
                    break;
            }
        }

        super.setWidth(width);
    }

    public int getMarginLeft() {
        return margin_left;
    }

    public int getRawMarginLeft() {
        return tmpMarginLeft;
    }

    public int getMarginTop() {
        return margin_top;
    }

    public int getMarginRight() {
        return margin_right;
    }

    public int getRawMarginRight() {
        return tmpMarginRight;
    }

    public int getMarginBottom() {
        return margin_bottom;
    }
}
