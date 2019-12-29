package com.yanny.age.stone.manual;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.client.gui.AbstractGui;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;

public class PageWidget extends Widget {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final Map<String, BiFunction<PageWidget, JsonObject, Widget>> FACTORY = new HashMap<>();
    private Set<Widget> widgets = new HashSet<>();
    private final ManualWidget manual;
    private final int page;

    static {
        FACTORY.put(TextWidget.TYPE, TextWidget::new);
        FACTORY.put(TitleWidget.TYPE, TitleWidget::new);
    }

    public PageWidget(ManualWidget manual, JsonArray array, int width, int height, int page) {
        super(width, height);
        this.manual = manual;
        this.page = page;

        for (JsonElement element : array) {
            if (!element.isJsonObject()) {
                LOGGER.warn("Element {} is not an object", element.toString());
                continue;
            }

            JsonObject object = element.getAsJsonObject();
            String type = Utils.getString(object, "type", null, false);

            if (type == null) {
                continue;
            }

            addWidget(FACTORY.get(type).apply(this, object));
        }
    }

    @Override
    public void draw(AbstractGui screen, int mx, int my) {
        widgets.forEach(widget -> {
            if (widget.visible) {
                widget.draw(screen, mx, my);
            }
        });
    }

    @Override
    public boolean mouseClicked(int mx, int my, int key) {
        for (Widget widget : widgets) {
            if (widget.inBounds(mx, my) && widget.mouseClicked(mx, my, key)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public void setPos(int x, int y) {
        super.setPos(x, y);
        int pos = y;

        for (Widget widget : widgets) {
            widget.setPos(x, pos);
            pos += widget.height;
        }
    }

    public void changePage(String key) {
        manual.changePage(key);
    }

    public void addLink(String key) {
        manual.addLink(key, page);
    }

    void addWidget(Widget widget) {
        widgets.add(widget);
    }
}
