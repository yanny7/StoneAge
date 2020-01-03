package com.yanny.age.stone.manual;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class Utils {
    private static final Logger LOGGER = LogManager.getLogger();
    static final int MARGIN = 5;

    public static String getString(JsonElement element, String defaultValue) {
        if (element.isJsonPrimitive() && element.getAsJsonPrimitive().isString()) {
            return element.getAsString();
        } else {
            LOGGER.warn("Invalid element type: '{}'", element);
            return defaultValue;
        }
    }

    public static String getString(IManual manual, JsonObject object, String field, String defaultValue, boolean optional) {
        if (object.has(field)) {
            if ( object.get(field).isJsonPrimitive()) {
                if (object.getAsJsonPrimitive(field).isString()) {
                    return object.getAsJsonPrimitive(field).getAsString();
                } else if (object.getAsJsonPrimitive(field).isString()) {
                    return getString(manual.getConstant(object.getAsJsonPrimitive(field).getAsString()), defaultValue);
                }
            }
        } else {
            if (!optional) {
                LOGGER.warn("Element '{}' not found", field);
            }
            return defaultValue;
        }

        LOGGER.warn("Invalid element type: '{}'", field);
        return defaultValue;
    }

    public static int getInt(JsonElement element, int defaultValue) {
        if (element.isJsonPrimitive() && element.getAsJsonPrimitive().isNumber()) {
            return element.getAsInt();
        } else {
            LOGGER.warn("Invalid element type: '{}'", element);
            return defaultValue;
        }
    }

    public static Integer getInt(IManual manual, JsonObject object, String field, Integer defaultValue, boolean optional) {
        if (object.has(field)) {
            if ( object.get(field).isJsonPrimitive()) {
                if (object.getAsJsonPrimitive(field).isNumber()) {
                    return object.getAsJsonPrimitive(field).getAsInt();
                } else if (object.getAsJsonPrimitive(field).isString()) {
                    return getInt(manual.getConstant(object.getAsJsonPrimitive(field).getAsString()), defaultValue);
                }
            }
        } else {
            if (!optional) {
                LOGGER.warn("Element '{}' not found", field);
            }
            return defaultValue;
        }

        LOGGER.warn("Invalid element type: '{}'", field);
        return defaultValue;
    }

    public static Boolean getBool(JsonObject object, String field, Boolean defaultValue, boolean optional) {
        if (!object.has(field) || !object.get(field).isJsonPrimitive() || !object.getAsJsonPrimitive(field).isNumber()) {
            if (!optional) {
                LOGGER.warn("Element '{}' not found or not an integer", field);
            }
            return defaultValue;
        }

        return object.getAsJsonPrimitive(field).getAsBoolean();
    }

    public static Double getReal(JsonElement element, Double defaultValue) {
        if (element.isJsonPrimitive() && element.getAsJsonPrimitive().isNumber()) {
            return element.getAsDouble();
        } else {
            LOGGER.warn("Invalid element type: '{}'", element);
            return defaultValue;
        }
    }

    public static Double getReal(IManual manual, JsonObject object, String field, Double defaultValue, boolean optional) {
        if (object.has(field)) {
            if ( object.get(field).isJsonPrimitive()) {
                if (object.getAsJsonPrimitive(field).isNumber()) {
                    return object.getAsJsonPrimitive(field).getAsDouble();
                } else if (object.getAsJsonPrimitive(field).isString()) {
                    return getReal(manual.getConstant(object.getAsJsonPrimitive(field).getAsString()), defaultValue);
                }
            }
        } else {
            if (!optional) {
                LOGGER.warn("Element '{}' not found", field);
            }
            return defaultValue;
        }

        LOGGER.warn("Invalid element type: '{}'", field);
        return defaultValue;
    }

    public static JsonObject getObject(JsonObject object, String field) {
        if (!object.has(field) || !object.get(field).isJsonObject()) {
            LOGGER.warn("Element '{}' not found or not an object", field);
            return null;
        }

        return object.getAsJsonObject(field);
    }

    public static JsonArray getArray(JsonObject object, String field) {
        if (!object.has(field) || !object.get(field).isJsonArray()) {
            LOGGER.warn("Element '{}' not found or not an array", field);
            return null;
        }

        return object.getAsJsonArray(field);
    }

    public static void resizeHLayout(Widget parent, List<Widget> widgets) {
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

    public static void resizeVLayout(Widget parent, List<Widget> widgets) {
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
