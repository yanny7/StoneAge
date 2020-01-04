package com.yanny.age.stone.manual;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class Utils {
    private static final Logger LOGGER = LogManager.getLogger();
    static final int MARGIN = 5;

    private static final Map<Class<?>, Function<JsonPrimitive, Boolean>> IS;
    private static final Map<Class<?>, Function<JsonPrimitive, Object>> GET;
    static {
        IS = new HashMap<>();
        IS.put(String.class, JsonPrimitive::isString);
        IS.put(Integer.class, JsonPrimitive::isNumber);
        IS.put(Double.class, JsonPrimitive::isNumber);
        IS.put(Float.class, JsonPrimitive::isNumber);

        GET = new HashMap<>();
        GET.put(String.class, JsonPrimitive::getAsString);
        GET.put(Integer.class, JsonPrimitive::getAsInt);
        GET.put(Double.class, JsonPrimitive::getAsDouble);
        GET.put(Float.class, JsonPrimitive::getAsFloat);
    }

    public static <T> T get(Class<?> t, @Nonnull JsonElement element, T defaultValue) {
        if (element.isJsonPrimitive() && IS.get(t).apply(element.getAsJsonPrimitive())) {
            //noinspection unchecked
            return (T) GET.get(t).apply(element.getAsJsonPrimitive());
        } else {
            LOGGER.warn("Invalid element type: '{}'", element);
            return defaultValue;
        }
    }

    public static <T> T get(Class<?> t, IManual manual, @Nonnull JsonObject object, String field, Object defaultValue, boolean optional) {
        if (object.has(field)) {
            if ( object.get(field).isJsonPrimitive()) {
                if (String.class.isAssignableFrom(t)) {
                    if (IS.get(t).apply(object.getAsJsonPrimitive(field))) {
                        //noinspection unchecked
                        return (T) GET.get(t).apply(object.getAsJsonPrimitive(field));
                    }
                } else {
                    if (object.getAsJsonPrimitive(field).isString()) {
                        JsonElement constant = manual.getConstant(object.getAsJsonPrimitive(field).getAsString());

                        if (constant == null) {
                            LOGGER.warn("Constant '{}' not defined!", field);
                            //noinspection unchecked
                            return (T) defaultValue;
                        }
                        //noinspection unchecked
                        return (T) get(t, constant, defaultValue);
                    } else if (IS.get(t).apply(object.getAsJsonPrimitive(field))) {
                        //noinspection unchecked
                        return (T) GET.get(t).apply(object.getAsJsonPrimitive(field));
                    }
                }
            }
        } else {
            if (!optional) {
                LOGGER.warn("Element '{}' not found", field);
            }
            //noinspection unchecked
            return (T) defaultValue;
        }

        LOGGER.warn("Invalid element type: '{}'", field);
        //noinspection unchecked
        return (T) defaultValue;
    }

    @Nullable
    public static JsonObject getObject(@Nonnull JsonObject object, String field) {
        if (!object.has(field) || !object.get(field).isJsonObject()) {
            LOGGER.warn("Element '{}' not found or not an object", field);
            return null;
        }

        return object.getAsJsonObject(field);
    }

    @Nullable
    public static JsonArray getArray(@Nonnull JsonObject object, String field) {
        if (!object.has(field) || !object.get(field).isJsonArray()) {
            LOGGER.warn("Element '{}' not found or not an array", field);
            return null;
        }

        return object.getAsJsonArray(field);
    }

    public static void resizeHLayout(Widget parent, @Nonnull List<Widget> widgets) {
        int width = 0;
        int dCount = 0;

        for (Widget widget : widgets) {
            widget.width = widget.getMinWidth(parent.height);

            if (widget.width < 0) {
                dCount++;
            } else {
                width += widget.width;
            }
        }

        int leftWidth = parent.width - width;

        if (leftWidth < 0) {
            for (Widget widget : widgets) {
                if (widget.width < 0) {
                    widget.width = 50;
                    LOGGER.warn("Width not set for {}, parent width: {}!", widget.getClass().getCanonicalName(), parent.width);
                }
                widget.width = parent.width / widgets.size();
            }
            LOGGER.warn("Total width is greater than parent width!");
        } else {
            if (dCount > 0) {
                for (Widget widget : widgets) {
                    if (widget.width < 0) {
                        widget.width = leftWidth / dCount;
                    }
                }
            }
        }

        for (Widget widget : widgets) {
            if (widget.height < 0) {
                widget.height = widget.getMinHeight(widget.width);
            }

            if (widget.height < 0) {
                widget.height = parent.height;
            }
        }
    }

    public static void resizeVLayout(Widget parent, @Nonnull List<Widget> widgets) {
        int height = 0;
        int dCount = 0;

        for (Widget widget : widgets) {
            widget.height = widget.getMinHeight(parent.width);

            if (widget.height < 0) {
                dCount++;
            } else {
                height += widget.height;
            }
        }

        int leftHeight = parent.height - height;

        if (leftHeight < 0) {
            for (Widget widget : widgets) {
                if (widget.height < 0) {
                    widget.height = 50;
                    LOGGER.warn("Height not set for {}, parent height: {}!", widget.getClass().getCanonicalName(), parent.height);
                }
            }
            LOGGER.warn("Total height is greater than parent height!");
        } else {
            if (dCount > 0) {
                for (Widget widget : widgets) {
                    if (widget.height < 0) {
                        widget.height = leftHeight / dCount;
                    }
                }
            }
        }

        for (Widget widget : widgets) {
            if (widget.width < 0) {
                widget.width = widget.getMinWidth(widget.height);
            }

            if (widget.width < 0) {
                widget.width = parent.width;
            }
        }
    }
}
