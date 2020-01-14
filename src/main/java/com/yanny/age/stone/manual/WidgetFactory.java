package com.yanny.age.stone.manual;

import com.google.gson.JsonObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

class WidgetFactory {
    private static final Map<String, FactoryFunction<JsonObject, IManual, Widget>> FACTORY = new HashMap<>();
    private static final Logger LOGGER = LogManager.getLogger();

    static {
        FACTORY.put(TextWidget.TYPE, TextWidget::new);
        FACTORY.put(TitleWidget.TYPE, TitleWidget::new);
        FACTORY.put(ItemWidget.TYPE, ItemWidget::new);
        FACTORY.put(HorizontalLayoutWidget.TYPE, HorizontalLayoutWidget::new);
        FACTORY.put(VerticalLayoutWidget.TYPE, VerticalLayoutWidget::new);
        FACTORY.put(ImageWidget.TYPE, ImageWidget::new);
        FACTORY.put(RecipeWidget.TYPE, RecipeWidget::new);
        FACTORY.put(ListWidget.TYPE, ListWidget::new);
    }

    static Widget getWidget(String name, JsonObject object, IManual manual) {
        FactoryFunction<JsonObject, IManual, Widget> w = FACTORY.get(name);

        if (w != null) {
            return FACTORY.get(name).apply(object, manual);
        } else {
            LOGGER.warn("Widget '{}' does not exists!", name);
            return new EmptyWidget();
        }
    }

    interface FactoryFunction<A, B, R> {
        R apply(A a, B b);
    }

    private static class EmptyWidget extends Widget {

    }
}
