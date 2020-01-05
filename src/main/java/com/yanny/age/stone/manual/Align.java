package com.yanny.age.stone.manual;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

public enum Align {
    RIGHT,
    LEFT,
    CENTER
    ;

    private static final Map<String, Align> ALIGN_MAP = new HashMap<>();

    static {
        for (Align align : values()) {
            ALIGN_MAP.put(align.name(), align);
        }
    }

    @Nullable
    static Align fromString(String align) {
        return ALIGN_MAP.get(align);
    }
}
