package com.yanny.age.stone.manual;

import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

class WidgetFactory {
    private static final Map<String, BiFunction<Widget, JsonObject, Widget>> FACTORY = new HashMap<>();

    static {
        FACTORY.put(TextWidget.TYPE, TextWidget::new);
        FACTORY.put(TitleWidget.TYPE, TitleWidget::new);
        FACTORY.put(ItemWidget.TYPE, ItemWidget::new);
        FACTORY.put(HorizontalLayoutWidget.TYPE, HorizontalLayoutWidget::new);
        FACTORY.put(VerticalLayoutWidget.TYPE, VerticalLayoutWidget::new);
    }

    static Widget getWidget(String name, Widget parent, JsonObject object) {
        return FACTORY.get(name).apply(parent, object);
    }
}
