package com.yanny.age.stone.manual;

import com.google.gson.JsonObject;
import com.mojang.datafixers.util.Pair;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipe;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;

import static com.yanny.age.stone.manual.Widget.DYNAMIC;

class ConfigHolder {
    private static final Logger LOGGER = LogManager.getLogger();

    public static final Pair<String, Obj<?, ?>> TEXT = new Pair<>("text", new Obj<>(String.class, String.class, "<UNSET>", false, s -> s, s -> true));
    public static final Pair<String, Obj<?, ?>> SCALE = new Pair<>("scale", new Obj<>(Float.class, Float.class, 1.0f, true, s -> s, s -> s != 0));
    public static final Pair<String, Obj<?, ?>> COLOR = new Pair<>("color", new Obj<>(Integer.class, Integer.class, -1, true, s -> s, s -> true));
    public static final Pair<String, Obj<?, ?>> WIDTH = new Pair<>("width", new Obj<>(Integer.class, Integer.class, DYNAMIC, true, s -> s, s -> true));
    public static final Pair<String, Obj<?, ?>> HEIGHT = new Pair<>("height", new Obj<>(Integer.class, Integer.class, DYNAMIC, true, s -> s, s -> true));
    public static final Pair<String, Obj<?, ?>> ITEM = new Pair<>("item", new Obj<>(String.class, ItemStack.class, "minecraft:stone", false,
            s -> new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation(s))), s -> ForgeRegistries.ITEMS.getValue(new ResourceLocation(s)) != null));
    public static final Pair<String, Obj<?, ?>> IMG_WIDTH = new Pair<>("w", new Obj<>(Integer.class, Integer.class, 16, false, s -> s, s -> s > 0));
    public static final Pair<String, Obj<?, ?>> IMG_HEIGHT = new Pair<>("h", new Obj<>(Integer.class, Integer.class, 16, false, s -> s, s -> s > 0));
    public static final Pair<String, Obj<?, ?>> IMG_U = new Pair<>("u", new Obj<>(Integer.class, Integer.class, 0, true, s -> s, s -> s >= 0));
    public static final Pair<String, Obj<?, ?>> IMG_V = new Pair<>("v", new Obj<>(Integer.class, Integer.class, 0, true, s -> s, s -> s >= 0));
    public static final Pair<String, Obj<?, ?>> IMAGE = new Pair<>("image", new Obj<>(String.class, ResourceLocation.class, "minecraft:textures/block/stone.png", false,
            ResourceLocation::new, s -> Minecraft.getInstance().getResourceManager().hasResource(new ResourceLocation(s))));
    public static final Pair<String, Obj<?, ?>> MARGIN_TOP = new Pair<>("margin_top", new Obj<>(Integer.class, Integer.class, 0, true, s -> s, s -> s >= 0));
    public static final Pair<String, Obj<?, ?>> MARGIN_LEFT = new Pair<>("margin_left", new Obj<>(Integer.class, Integer.class, DYNAMIC, true, s -> s, s -> true));
    public static final Pair<String, Obj<?, ?>> MARGIN_BOTTOM = new Pair<>("margin_bottom", new Obj<>(Integer.class, Integer.class, 0, true, s -> s, s -> s >= 0));
    public static final Pair<String, Obj<?, ?>> MARGIN_RIGHT = new Pair<>("margin_right", new Obj<>(Integer.class, Integer.class, DYNAMIC, true, s -> s, s -> true));
    public static final Pair<String, Obj<?, ?>> ALIGN_CENTER = new Pair<>("align", new Obj<>(String.class, Align.class, "CENTER", true,
            Align::fromString, s -> Align.fromString(s) != null));
    public static final Pair<String, Obj<?, ?>> ALIGN_LEFT = new Pair<>("align", new Obj<>(String.class, Align.class, "LEFT", true,
            Align::fromString, s -> Align.fromString(s) != null));
    public static final Pair<String, Obj<?, ?>> JUSTIFY = new Pair<>("justify", new Obj<>(Boolean.class, Boolean.class, false, true, s -> s, s -> true));
    public static final Pair<String, Obj<?, ?>> RECIPE = new Pair<>("recipe", new Obj<>(String.class, IRecipeWidget.class, "", false,
            s -> {
                IRecipe<?> recipe = Minecraft.getInstance().world.getRecipeManager().getRecipe(new ResourceLocation(s)).orElse(null);
                return recipe instanceof IRecipeWidget ? (IRecipeWidget) recipe : new DefaultRecipe();
            },
            s -> {
                IRecipe<?> recipe = Minecraft.getInstance().world.getRecipeManager().getRecipe(new ResourceLocation(s)).orElse(null);
                return recipe instanceof IRecipeWidget;
            }));

    private Map<String, Obj<?, ?>> objMap = new HashMap<>();
    private Map<String, ?> values = new HashMap<>();

    @SafeVarargs
    ConfigHolder(@Nonnull Pair<String, Obj<?, ?>> ...types) {
        for (Pair<String, Obj<?, ?>> type : types) {
            objMap.put(type.getFirst(), type.getSecond());
        }
    }

    void loadConfig(JsonObject object, IManual manual) {
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
            if (!type.checkValue(s)) {
                LOGGER.warn("Param type check failed! {}", s);
                new Exception().printStackTrace();
                return null;
            }

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

    static class Obj<T, R> {
        private Class<T> param;
        private Class<R> result;
        private T defaultValue;
        private boolean optional;
        private Function<T, R> transform;
        private Predicate<T> check;

        public Obj(Class<T> param, Class<R> result, T defaultValue, boolean optional, Function<T, R> transform, Predicate<T> check) {
            this.param = param;
            this.result = result;
            this.defaultValue = defaultValue;
            this.optional = optional;
            this.transform = transform;
            this.check = check;
        }

        R getValue(Object t) {
            //noinspection unchecked
            return transform.apply((T) t);
        }

        boolean checkValue(Object t) {
            //noinspection unchecked
            return check.test((T) t);
        }
    }

    private static class DefaultRecipe extends ShapedRecipe implements IRecipeWidget {
        public DefaultRecipe() {
            super(new ResourceLocation(""), "", 1, 1, NonNullList.from(Ingredient.EMPTY), ItemStack.EMPTY);
        }

        @Nonnull
        @Override
        public List<RecipeIngredient> getRecipeIngredients() {
            return new ArrayList<>();
        }

        @Nonnull
        @Override
        public RecipeBackground getRecipeBackground() {
            return new RecipeBackground(new ResourceLocation("minecraft", "textures/block/stone.png"), 0, 0, 16, 16);
        }
    }
}
