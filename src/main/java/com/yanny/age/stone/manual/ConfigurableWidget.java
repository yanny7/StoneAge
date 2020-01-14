package com.yanny.age.stone.manual;

import com.google.gson.JsonObject;
import com.mojang.datafixers.util.Pair;

import javax.annotation.Nonnull;

public abstract class ConfigurableWidget extends Widget {

    protected final JsonObject object;
    protected final IManual manual;
    protected final ConfigHolder configHolder;

    @SafeVarargs
    ConfigurableWidget(JsonObject object, IManual manual, @Nonnull Pair<String, ConfigHolder.Obj<?, ?>>...types) {
        this.object = object;
        this.manual = manual;
        configHolder = new ConfigHolder(types);
        configHolder.loadConfig(object, manual);
    }
}
