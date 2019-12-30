package com.yanny.age.stone.manual;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

public class ManualWidget extends Widget {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final Gson GSON = new Gson();

    private Map<Integer, PageWidget> pages = new HashMap<>();
    private Map<String, Integer> links = new HashMap<>();
    private int currentPage = 0;

    public ManualWidget(ResourceLocation resource, int width, int height) {
        this.width = width - Utils.MARGIN * 2;
        this.height = height - Utils.MARGIN * 2;
        JsonArray array;

        try (
                InputStreamReader inputstream = new InputStreamReader(Minecraft.getInstance().getResourceManager().getResource(resource).getInputStream());
                Reader reader = new BufferedReader(inputstream)
        ) {
            array = JSONUtils.fromJson(GSON, reader, JsonArray.class);
        } catch (IllegalArgumentException | IOException | JsonParseException jsonparseexception) {
            LOGGER.error("Couldn't parse data file {} - {}", resource, jsonparseexception);
            return;
        }

        if (array != null) {
            int page = 0;

            for (JsonElement element : array) {
                if (!element.isJsonArray()) {
                    LOGGER.warn("Element {} is not an array", element.toString());
                }

                pages.put(page, new PageWidget(this, element.getAsJsonArray(), page));
                page++;
            }
        }
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    @Override
    public void drawBackgroundLayer(Screen screen, int mx, int my) {
        pages.get(currentPage).drawBackgroundLayer(screen, mx, my);
    }

    @Override
    public void render(Screen screen, int mx, int my) {
        pages.get(currentPage).render(screen, mx, my);
    }

    @Override
    public void setPos(int x, int y) {
        super.setPos(x, y);
        pages.forEach((index, page) -> page.setPos(x + Utils.MARGIN, y + Utils.MARGIN));
    }

    @Override
    public boolean mouseClicked(int mx, int my, int key) {
        return pages.get(currentPage).mouseClicked(mx, my, key);
    }

    public void addLink(String key, int page) {
        links.put(key, page);
    }

    public void changePage(String key) {
        setCurrentPage(links.get(key));
    }
}
