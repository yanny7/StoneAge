package com.yanny.age.stone.manual;

import com.google.gson.JsonObject;
import com.mojang.datafixers.util.Pair;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;

import static com.yanny.age.stone.manual.Widget.DYNAMIC;

class ConfigHolder {
    public static final Pair<String, Obj<?>> TEXT = new Pair<>("text", new Obj<>(String.class, "<UNSET>", false));
    public static final Pair<String, Obj<?>> SCALE = new Pair<>("scale", new Obj<>(Float.class, 1.0f, true));
    public static final Pair<String, Obj<?>> COLOR = new Pair<>("color", new Obj<>(Integer.class, -1, true));
    public static final Pair<String, Obj<?>> WIDTH = new Pair<>("width", new Obj<>(Integer.class, DYNAMIC, true));
    public static final Pair<String, Obj<?>> HEIGHT = new Pair<>("height", new Obj<>(Integer.class, DYNAMIC, true));
    public static final Pair<String, Obj<?>> ITEM = new Pair<>("item", new Obj<>(String.class, "minecraft:stone", false));
    public static final Pair<String, Obj<?>> IMG_WIDTH = new Pair<>("w", new Obj<>(Integer.class, 16, false));
    public static final Pair<String, Obj<?>> IMG_HEIGHT = new Pair<>("h", new Obj<>(Integer.class, 16, false));
    public static final Pair<String, Obj<?>> IMG_U = new Pair<>("u", new Obj<>(Integer.class, 0, true));
    public static final Pair<String, Obj<?>> IMG_V = new Pair<>("v", new Obj<>(Integer.class, 0, true));
    public static final Pair<String, Obj<?>> IMAGE = new Pair<>("image", new Obj<>(String.class, "minecraft:textures/block/stone.png", false));

    private Map<String, Obj<?>> objMap = new HashMap<>();
    private Map<String, ?> values = new HashMap<>();

    @SafeVarargs
    ConfigHolder(@Nonnull Pair<String, Obj<?>> ...types) {
        for (Pair<String, Obj<?>> type : types) {
            objMap.put(type.getFirst(), type.getSecond());
        }
    }

    void Load(JsonObject object, IManual manual) {
        for (Map.Entry<String, Obj<?>> entry : objMap.entrySet()) {
            Obj<?> type = entry.getValue();
            String key = entry.getKey();
            values.put(key, Utils.get(type.type, manual, object, key, type.defaultValue, type.optional));
        }
    }

    <T> T getValue(@Nonnull Pair<String, Obj<?>> key) {
        Obj<?> type = objMap.get(key.getFirst());
        if (type.type.isAssignableFrom(key.getSecond().type)) {
            //noinspection unchecked
            return (T) type.type.cast(values.get(key.getFirst()));
        } else {
            return null;
        }
    }

    private static class Obj<T> {
        private T defaultValue;
        private Class<T> type;
        private boolean optional;

        public Obj(Class<T> type, T defaultValue, boolean optional) {
            this.type = type;
            this.defaultValue = defaultValue;
            this.optional = optional;
        }
    }
}
