package com.yanny.age.stone.manual;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.client.gui.screen.Screen;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

import static com.yanny.age.stone.manual.ConfigHolder.*;

public class HorizontalLayoutWidget extends ConfigurableWidget {
    public static final String TYPE = "hlayout";

    private static final Logger LOGGER = LogManager.getLogger();
    private final List<Widget> widgets = new ArrayList<>();

    private final int margin_right;
    private final int margin_left;
    private final int margin_top;
    private final int margin_bottom;

    HorizontalLayoutWidget(JsonObject object, IManual manual) {
        super(object, manual, MARGIN_LEFT, MARGIN_RIGHT, MARGIN_TOP, MARGIN_BOTTOM);

        margin_right = configHolder.getValue(MARGIN_RIGHT);
        margin_left = configHolder.getValue(MARGIN_LEFT);
        margin_top = configHolder.getValue(MARGIN_TOP);
        margin_bottom = configHolder.getValue(MARGIN_BOTTOM);

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
        int pos = x + margin_left;
        int oldWidth = getWidth();

        setWidth(oldWidth - margin_left - margin_right);
        Utils.resizeHLayout(this, widgets);
        setWidth(oldWidth);

        for (Widget widget : widgets) {
            widget.setPos(pos, y + margin_top);
            pos += widget.getWidth();
        }
    }

    @Override
    public int getMinHeight(int width) {
        int height = DYNAMIC;
        int totalWidth = 0;
        int oldWidth = getWidth();

        setWidth(width);
        Utils.resizeHLayout(this, widgets);
        setWidth(oldWidth);

        for (Widget widget : widgets) {
            height = Math.max(height, widget.getMinHeight(width - totalWidth));
            totalWidth += widget.getWidth();
        }

        return height + margin_top + margin_bottom;
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
