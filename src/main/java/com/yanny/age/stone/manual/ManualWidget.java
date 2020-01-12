package com.yanny.age.stone.manual;

import com.google.gson.*;
import com.yanny.age.stone.manual.handlers.*;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.resources.IResourceManager;
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

public class ManualWidget extends Widget implements IManual {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final Gson GSON = new Gson();

    private int currentPage = 0;

    private final Map<Integer, PageWidget> pages = new HashMap<>();
    private final Map<String, Integer> links = new HashMap<>();
    private final Map<String, JsonElement> constants = new HashMap<>();
    private final Map<IRecipeSerializer<?>, IRecipeHandler> recipeHandlerMap = new HashMap<>();
    private final ButtonWidget prevPage = new ButtonWidget("<", () -> setCurrentPage(Math.max(currentPage - 1, 0)));
    private final ButtonWidget nextPage = new ButtonWidget(">", () -> setCurrentPage(Math.min(currentPage + 1, pages.size() - 1)));
    private final ButtonWidget menuPage = new ButtonWidget("MENU", () -> setCurrentPage(0));

    public ManualWidget(int width, int height) {
        recipeHandlerMap.put(IRecipeSerializer.CRAFTING_SHAPED, new ShapedRecipeHandler());
        recipeHandlerMap.put(IRecipeSerializer.CRAFTING_SHAPELESS, new ShapelessRecipeHandler());
        recipeHandlerMap.put(IRecipeSerializer.SMELTING, new SmeltingRecipeHandler());
        recipeHandlerMap.put(IRecipeSerializer.BLASTING, new SmeltingRecipeHandler());
        recipeHandlerMap.put(IRecipeSerializer.SMOKING, new SmeltingRecipeHandler());
        recipeHandlerMap.put(IRecipeSerializer.CAMPFIRE_COOKING, new CampfireRecipeHandler());
        recipeHandlerMap.put(IRecipeSerializer.STONECUTTING, new StonecuttinRecipeHandler());

        setSize(width - Utils.MARGIN * 2, height - Utils.MARGIN * 2);
        prevPage.setSize(20, 20);
        nextPage.setSize(20, 20);
        menuPage.setSize(60, 20);
    }

    public void buildFromResources(ResourceLocation resource) {
        JsonObject object;
        IResourceManager manager = mc.getResourceManager();

        try (InputStreamReader inputstream = new InputStreamReader(manager.getResource(resource).getInputStream()); Reader reader = new BufferedReader(inputstream)) {
            object = JSONUtils.fromJson(GSON, reader, JsonObject.class);
        } catch (IllegalArgumentException | IOException | JsonParseException jsonparseexception) {
            LOGGER.error("Couldn't parse data file {} - {}", resource, jsonparseexception);
            return;
        }

        if (object != null) {
            int page = 0;

            JsonArray array = Utils.getArray(object, "content");

            if (array == null) {
                return;
            }

            loadConstants(constants, object);

            for (JsonElement element : array) {
                if (!element.isJsonObject()) {
                    LOGGER.warn("Element {} is not an object", element.toString());
                }

                pages.put(page, new PageWidget(this, element.getAsJsonObject(), page));
                page++;
            }
        } else {
            LOGGER.error("Couldn't parse data file {}", resource);
        }

        setCurrentPage(0);
    }

    public void addRecipeHandler(IRecipeSerializer<?> serializer, IRecipeHandler handler) {
        recipeHandlerMap.put(serializer, handler);
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
        prevPage.setActive(currentPage > 0);
        nextPage.setActive(currentPage < pages.size() - 1);
    }

    @Override
    public void drawBackgroundLayer(Screen screen, int mx, int my) {
        pages.get(currentPage).drawBackgroundLayer(screen, mx, my);
        prevPage.drawBackgroundLayer(screen, mx, my);
        nextPage.drawBackgroundLayer(screen, mx, my);
        menuPage.drawBackgroundLayer(screen, mx, my);
    }

    @Override
    public void render(Screen screen, int mx, int my) {
        pages.get(currentPage).render(screen, mx, my);
        prevPage.render(screen, mx, my);
        nextPage.render(screen, mx, my);
        menuPage.render(screen, mx, my);
    }

    @Override
    public void setPos(int x, int y) {
        super.setPos(x, y);
        pages.forEach((index, page) -> page.setPos(x + Utils.MARGIN, y + Utils.MARGIN));
        prevPage.setPos(x, y + getHeight() + 2 * Utils.MARGIN);
        nextPage.setPos(x + getWidth() + 2 * Utils.MARGIN - 20, y + getHeight() + 2 * Utils.MARGIN);
        menuPage.setPos(x + getWidth() / 2 + Utils.MARGIN - 30, y + getHeight() + 2 * Utils.MARGIN);
    }

    @Override
    public boolean mouseClicked(int mx, int my, int key) {
        return pages.get(currentPage).mouseClicked(mx, my, key) || prevPage.mouseClicked(mx, my, key) || nextPage.mouseClicked(mx, my, key) || menuPage.mouseClicked(mx, my, key);
    }

    @Override
    public void mouseMoved(int mx, int my) {
        pages.get(currentPage).mouseMoved(mx, my);
        prevPage.mouseMoved(mx, my);
        nextPage.mouseMoved(mx, my);
        menuPage.mouseMoved(mx, my);
    }

    public void addLink(String key, int page) {
        links.put(key, page);
    }

    public void changePage(String key) {
        Integer page = links.get(key);

        if (page == null) {
            LOGGER.warn("Page key '{}' does not exists", key);
            return;
        }

        setCurrentPage(links.get(key));
    }

    @Override
    public JsonElement getConstant(String key) {
        return constants.get(key);
    }

    @Override
    public IRecipeHandler getRecipeHandler(IRecipe<?> recipe) {
        return recipeHandlerMap.get(recipe.getSerializer());
    }

    private void loadConstants(Map<String, JsonElement> constants, JsonObject object) {
        JsonObject items = Utils.getObject(object, "constants");

        if (items != null) {
            for (Map.Entry<String, JsonElement> item : items.entrySet()) {
                JsonElement element = item.getValue();

                if (element.isJsonPrimitive()) {
                    constants.put(item.getKey(), element);
                } else {
                    LOGGER.warn("Invalid element type in constants: {}", item.getKey());
                }
            }
        }
    }
}
