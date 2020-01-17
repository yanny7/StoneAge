package com.yanny.age.stone.manual;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.client.gui.screen.Screen;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class VerticalLayoutWidget extends Widget {
    public static final String TYPE = "vlayout";

    private static final Logger LOGGER = LogManager.getLogger();
    private final List<Widget> widgets = new ArrayList<>();

    VerticalLayoutWidget(JsonObject object, IManual manual) {
        JsonArray array = Utils.getArray(object, "content");
        if (array == null) {
            return;
        }

        for (JsonElement element : array) {
            if (!element.isJsonObject()) {
                LOGGER.warn("Element {} is not an object", element.toString());
                continue;
            }

            JsonObject obj = element.getAsJsonObject();
            String type = Utils.get(String.class, manual, obj, "type", null, false);

            if (type == null) {
                continue;
            }

            Widget widget = WidgetFactory.getWidget(type, obj, manual);
            widgets.add(widget);
        }
    }

    @Override
    public void drawBackgroundLayer(Screen screen, int mx, int my) {
        widgets.forEach(widget -> {
            if (widget.visible) {
                widget.drawBackgroundLayer(screen, mx, my);
            }
        });
    }

    @Override
    public void render(Screen screen, int mx, int my) {
        widgets.forEach(widget -> {
            if (widget.visible) {
                widget.render(screen, mx, my);
            }
        });
    }

    @Override
    public void setPos(int x, int y) {
        super.setPos(x, y);
        int pos = y;

        Utils.resizeVLayout(this, widgets);

        for (Widget widget : widgets) {
            widget.setPos(x, pos);
            pos += widget.getHeight();
        }
    }

    @Override
    public int getMinWidth(int height) {
        int width = DYNAMIC;

        for (Widget widget : widgets) {
            width = Math.max(width, widget.getMinWidth(height));
        }

        return width;
    }

    @Override
    public int getMinHeight(int width) {
        int height = 0;

        for (Widget widget : widgets) {
            height += widget.getMinHeight(width);
        }

        return height;
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
}
