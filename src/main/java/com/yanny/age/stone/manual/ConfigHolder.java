package com.yanny.age.stone.manual;

import com.google.gson.JsonObject;
import com.mojang.datafixers.util.Pair;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static com.yanny.age.stone.manual.Widget.DYNAMIC;

class ConfigHolder {
    private static final Logger LOGGER = LogManager.getLogger();

    public static final Pair<String, Obj<?, ?>> TEXT = new Pair<>("text", new Obj<>(String.class, String.class, "<UNSET>", false, s -> s));
    public static final Pair<String, Obj<?, ?>> SCALE = new Pair<>("scale", new Obj<>(Float.class, Float.class, 1.0f, true, s -> s));
    public static final Pair<String, Obj<?, ?>> COLOR = new Pair<>("color", new Obj<>(Integer.class, Integer.class, -1, true, s -> s));
    public static final Pair<String, Obj<?, ?>> WIDTH = new Pair<>("width", new Obj<>(Integer.class, Integer.class, DYNAMIC, true, s -> s));
    public static final Pair<String, Obj<?, ?>> HEIGHT = new Pair<>("height", new Obj<>(Integer.class, Integer.class, DYNAMIC, true, s -> s));
    public static final Pair<String, Obj<?, ?>> ITEM = new Pair<>("item", new Obj<>(String.class, ItemStack.class, "minecraft:stone", false, s -> new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation(s)))));
    public static final Pair<String, Obj<?, ?>> IMG_WIDTH = new Pair<>("w", new Obj<>(Integer.class, Integer.class, 16, false, s -> s));
    public static final Pair<String, Obj<?, ?>> IMG_HEIGHT = new Pair<>("h", new Obj<>(Integer.class, Integer.class, 16, false, s -> s));
    public static final Pair<String, Obj<?, ?>> IMG_U = new Pair<>("u", new Obj<>(Integer.class, Integer.class, 0, true, s -> s));
    public static final Pair<String, Obj<?, ?>> IMG_V = new Pair<>("v", new Obj<>(Integer.class, Integer.class, 0, true, s -> s));
    public static final Pair<String, Obj<?, ?>> IMAGE = new Pair<>("image", new Obj<>(String.class, ResourceLocation.class, "minecraft:textures/block/stone.png", false, ResourceLocation::new));

    private Map<String, Obj<?, ?>> objMap = new HashMap<>();
    private Map<String, ?> values = new HashMap<>();

    @SafeVarargs
    ConfigHolder(@Nonnull Pair<String, Obj<?, ?>> ...types) {
        for (Pair<String, Obj<?, ?>> type : types) {
            objMap.put(type.getFirst(), type.getSecond());
        }
    }

    void Load(JsonObject object, IManual manual) {
        for (Map.Entry<String, Obj<?, ?>> entry : objMap.entrySet()) {
            Obj<?, ?> type = entry.getValue();
            String key = entry.getKey();
            values.put(key, Utils.get(type.param, manual, object, key, type.defaultValue, type.optional));
        }
    }

    <T> T getValue(@Nonnull Pair<String, Obj<?, ?>> key) {
        Obj<?, ?> type = objMap.get(key.getFirst());
        Object s = values.get(key.getFirst());

        if (type.param.isAssignableFrom(s.getClass())) {
            Object r = type.getValue(s);

            if (type.result.isAssignableFrom(r.getClass())) {
                //noinspection unchecked
                return (T) type.getValue(s);
            } else {
                LOGGER.warn("Invalid result type, cannot convert {} to return type", type.result.getCanonicalName());
                new Exception().printStackTrace();
            }
        } else {
            LOGGER.warn("Invalid parameter type, cannot convert {} to {}", s.getClass().getCanonicalName(), type.param.getCanonicalName());
            new Exception().printStackTrace();
        }

        return null;
    }

    private static class Obj<T, R> {
        private Class<T> param;
        private Class<R> result;
        private T defaultValue;
        private boolean optional;
        private Function<T, R> function;

        public Obj(Class<T> param, Class<R> result, T defaultValue, boolean optional, Function<T, R> function) {
            this.param = param;
            this.result = result;
            this.defaultValue = defaultValue;
            this.optional = optional;
            this.function = function;
        }

        R getValue(Object t) {
            //noinspection unchecked
            return function.apply((T) t);
        }
    }
}
