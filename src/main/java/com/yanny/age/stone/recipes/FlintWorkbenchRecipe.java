package com.yanny.age.stone.recipes;

import com.yanny.age.stone.Reference;
import com.yanny.age.stone.subscribers.RecipeSubscriber;
import com.yanny.ages.api.items.AgesPartItem;
import com.yanny.ages.api.items.AgesToolItem;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nonnull;

import java.util.Arrays;

import static com.yanny.ages.api.items.AgesToolItem.*;

public class FlintWorkbenchRecipe implements IRecipe<IInventory> {
    private static final Logger LOGGER = LogManager.getLogger();
    public static final IRecipeType<FlintWorkbenchRecipe> flint_workbench = IRecipeType.register(Reference.MODID + ":flint_workbench");
    public static final int MAX_WIDTH = 3;
    public static final int MAX_HEIGHT = 3;

    private final int recipeWidth;
    private final int recipeHeight;
    private final NonNullList<Ingredient> recipeItems;
    private final ItemStack recipeOutput;
    private final ResourceLocation id;
    private final String group;
    private final Ingredient tool;

    public FlintWorkbenchRecipe(@Nonnull ResourceLocation id, @Nonnull String group, int recipeWidth, int recipeHeight, @Nonnull Ingredient tool,
                                @Nonnull NonNullList<Ingredient> ingredients, @Nonnull ItemStack output) {
        this.id = id;
        this.group = group;
        this.recipeWidth = recipeWidth;
        this.recipeHeight = recipeHeight;
        this.recipeItems = ingredients;
        this.recipeOutput = output;
        this.tool = tool;
    }

    @Nonnull
    @Override
    public ResourceLocation getId() {
        return this.id;
    }

    @Nonnull
    @Override
    public IRecipeSerializer<?> getSerializer() {
        //noinspection ConstantConditions
        return RecipeSubscriber.flint_workbench;
    }

    @Nonnull
    @Override
    public IRecipeType<?> getType() {
        return flint_workbench;
    }

    @Nonnull
    @Override
    public String getGroup() {
        return this.group;
    }

    @Nonnull
    @Override
    public ItemStack getRecipeOutput() {
        return this.recipeOutput;
    }

    @Nonnull
    @Override
    public NonNullList<Ingredient> getIngredients() {
        return this.recipeItems;
    }

    @Override
    public boolean canFit(int width, int height) {
        return width >= this.recipeWidth && height >= this.recipeHeight;
    }

    @Override
    public boolean matches(@Nonnull IInventory inv, @Nonnull World worldIn) {
        for(int x = 0; x <= MAX_WIDTH - recipeWidth; ++x) {
            for(int y = 0; y <= MAX_HEIGHT - recipeHeight; ++y) {
                if (this.checkMatch(inv, x, y, true)) {
                    return true;
                }

                if (this.checkMatch(inv, x, y, false)) {
                    return true;
                }
            }
        }

        return false;
    }

    @Nonnull
    @Override
    public ItemStack getCraftingResult(@Nonnull IInventory inv) {
        ItemStack result = getRecipeOutput().copy();

        if (result.getItem() instanceof AgesPartItem) {
            AgesPartItem item = (AgesPartItem) result.getItem();
            item.applyStats(result);
        } else if (result.getItem() instanceof AgesToolItem) {
            ItemStack part = null;

            for (int i = 0; i < inv.getSizeInventory(); i++) {
                ItemStack itemStack = inv.getStackInSlot(i);

                if (itemStack.getItem() instanceof AgesPartItem) {
                    part = itemStack;
                }
            }

            if (part != null) {
                setAdditionalModifiers(result, getAdditionalAttackDamage(part), getAdditionalAttackSpeed(part), getAdditionalEfficiency(part));
            } else {
                LOGGER.warn("Expected item with AgesToolItem parent '{}' recipe", recipeOutput.getDisplayName());
            }
        }

        return result;
    }

    public int getWidth() {
        return this.recipeWidth;
    }

    public int getHeight() {
        return this.recipeHeight;
    }

    /**
     * Checks if the region of a crafting inventory is match for the recipe.
     */
    private boolean checkMatch(@Nonnull IInventory inventory, int dx, int dy, boolean reversed) {
        for(int x = 0; x < MAX_WIDTH; ++x) {
            for(int y = 0; y < MAX_HEIGHT; ++y) {
                int x1 = x - dx;
                int y1 = y - dy;
                Ingredient ingredient = Ingredient.EMPTY;

                if (x1 >= 0 && y1 >= 0 && x1 < this.recipeWidth && y1 < this.recipeHeight) {
                    if (reversed) {
                        ingredient = this.recipeItems.get(this.recipeWidth - x1 - 1 + y1 * this.recipeWidth);
                    } else {
                        ingredient = this.recipeItems.get(x1 + y1 * this.recipeWidth);
                    }
                }

                if (!ingredient.test(inventory.getStackInSlot(x + y * MAX_WIDTH))) {
                    return false;
                }
            }
        }

        return true;
    }

    @Nonnull
    public Ingredient getTool() {
        return tool;
    }

    public boolean testTool(ItemStack itemStack) {
        return Arrays.stream(tool.getMatchingStacks()).anyMatch(item -> item.getItem() == itemStack.getItem());
    }
}
