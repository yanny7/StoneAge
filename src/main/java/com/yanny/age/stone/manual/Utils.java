package com.yanny.age.stone.manual;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class Utils {
    private static final Logger LOGGER = LogManager.getLogger();
    static final int MARGIN = 5;

    public static String getString(JsonObject object, String field, String defaultValue, boolean optional) {
        if (!object.has(field) || !object.get(field).isJsonPrimitive() || !object.getAsJsonPrimitive(field).isString()) {
            if (!optional) {
                LOGGER.warn("Element '{}' not found or not a string", field);
            }
            return defaultValue;
        }

        return object.getAsJsonPrimitive(field).getAsString();
    }

    public static Integer getInt(JsonObject object, String field, Integer defaultValue, boolean optional) {
        if (!object.has(field) || !object.get(field).isJsonPrimitive() || !object.getAsJsonPrimitive(field).isNumber()) {
            if (!optional) {
                LOGGER.warn("Element '{}' not found or not an integer", field);
            }
            return defaultValue;
        }

        return object.getAsJsonPrimitive(field).getAsInt();
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

    public static Double getReal(JsonObject object, String field, Double defaultValue, boolean optional) {
        if (!object.has(field) || !object.get(field).isJsonPrimitive() || !object.getAsJsonPrimitive(field).isNumber()) {
            if (!optional) {
                LOGGER.warn("Element '{}' not found or not an integer", field);
            }
            return defaultValue;
        }

        return object.getAsJsonPrimitive(field).getAsDouble();
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

    public static void resizeHLayout(Widget widget, List<Widget> widgets) {
        int width = 0;
        int dCount = 0;

        for (Widget w : widgets) {
            w.width = w.getMinWidth(widget.height);

            if (w.width < 0) {
                dCount++;
            } else {
                width += w.width;
            }

            if (w.height < 0) {
                w.height = w.getMinHeight(w.width);
            }

            if (w.height < 0) {
                w.height = widget.height;
            }
        }

        int leftWidth = widget.width - width;

        if (leftWidth < 0) {
            for (Widget w : widgets) {
                w.width = widget.width / widgets.size();
            }
            LOGGER.warn("Total width is greater than parent width! Setting all to dynamic");
        } else {
            if (dCount > 0) {
                for (Widget w : widgets) {
                    if (w.width < 0) {
                        w.width = leftWidth / dCount;
                    }
                }
            }
        }
    }

    public static void resizeVLayout(Widget widget, List<Widget> widgets) {
        int height = 0;
        int dCount = 0;

        for (Widget w : widgets) {
            w.height = w.getMinHeight(widget.width);

            if (w.height < 0) {
                dCount++;
            } else {
                height += w.height;
            }

            if (w.width < 0) {
                w.width = w.getMinWidth(w.height);
            }

            if (w.width < 0) {
                w.width = widget.width;
            }
        }

        int leftHeight = widget.height - height;

        if (leftHeight < 0) {
            for (Widget w : widgets) {
                w.height = widget.height / widgets.size();
            }
            LOGGER.warn("Total height is greater than parent height! Setting all to dynamic");
        } else {
            if (dCount > 0) {
                for (Widget w : widgets) {
                    if (w.height < 0) {
                        w.height = leftHeight / dCount;
                    }
                }
            }
        }
    }
}
