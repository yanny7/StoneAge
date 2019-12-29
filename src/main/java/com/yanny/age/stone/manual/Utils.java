package com.yanny.age.stone.manual;

import com.google.gson.JsonObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
}
