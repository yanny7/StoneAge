package com.yanny.age.stone.datagen;

import com.google.gson.JsonObject;
import com.yanny.age.stone.subscribers.RecipeSubscriber;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.ICriterionInstance;
import net.minecraft.advancements.IRequirementsStrategy;
import net.minecraft.advancements.criterion.RecipeUnlockedTrigger;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.tags.Tag;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;
import org.lwjgl.system.NonnullDefault;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.function.Consumer;

@NonnullDefault
public class TreeStumpRecipeBuilder {
    private final Item result;
    private final int count;
    private final Ingredient input;
    private final Advancement.Builder advancementBuilder = Advancement.Builder.builder();
    @Nullable private String group = null;
    @Nullable private Ingredient tool = null;
    private int chopTimes = 1;

    public TreeStumpRecipeBuilder(IItemProvider resultIn, IItemProvider input, int countIn) {
        this.result = resultIn.asItem();
        this.input = Ingredient.fromItems(input);
        this.count = countIn;
    }

    public static TreeStumpRecipeBuilder recipe(IItemProvider resultIn, IItemProvider input) {
        return recipe(resultIn, input, 1);
    }

    public static TreeStumpRecipeBuilder recipe(IItemProvider resultIn, IItemProvider input, int countIn) {
        return new TreeStumpRecipeBuilder(resultIn, input, countIn);
    }

    public TreeStumpRecipeBuilder tool(Tag<Item> tool) {
        return this.tool(Ingredient.fromTag(tool));
    }

    public TreeStumpRecipeBuilder tool(IItemProvider tool) {
        return this.tool(Ingredient.fromItems(tool));
    }

    public TreeStumpRecipeBuilder tool(Ingredient tool) {
        if (this.tool != null) {
            throw new IllegalArgumentException("Tool is already defined!");
        } else {
            this.tool = tool;
            return this;
        }
    }

    public TreeStumpRecipeBuilder chopTimes(int chopTimes) {
        this.chopTimes = chopTimes;
        return this;
    }

    public TreeStumpRecipeBuilder addCriterion(String name, ICriterionInstance criterionIn) {
        this.advancementBuilder.withCriterion(name, criterionIn);
        return this;
    }

    public TreeStumpRecipeBuilder setGroup(String groupIn) {
        this.group = groupIn;
        return this;
    }

    public void build(Consumer<IFinishedRecipe> consumerIn) {
        this.build(consumerIn, Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(this.result)));
    }

    public void build(Consumer<IFinishedRecipe> consumerIn, String save) {
        ResourceLocation resourcelocation = ForgeRegistries.ITEMS.getKey(this.result);

        if ((new ResourceLocation(save)).equals(resourcelocation)) {
            throw new IllegalStateException("Shaped Recipe " + save + " should remove its 'save' argument");
        } else {
            this.build(consumerIn, new ResourceLocation(save));
        }
    }

    public void build(Consumer<IFinishedRecipe> consumerIn, ResourceLocation id) {
        if (this.result.getGroup() == null) {
            throw new IllegalStateException("Recipe " + id + " has null group!");
        } else if (this.tool == null) {
            throw new IllegalStateException("Tool is not set!");
        }

        this.advancementBuilder.withParentId(new ResourceLocation("recipes/root")).withCriterion("has_the_recipe", new RecipeUnlockedTrigger.Instance(id)).withRewards(AdvancementRewards.Builder.recipe(id)).withRequirementsStrategy(IRequirementsStrategy.OR);
        consumerIn.accept(new Result(id, this.result, this.input, this.count, this.chopTimes, this.group == null ? "" : this.group, this.tool, this.advancementBuilder, new ResourceLocation(id.getNamespace(), "recipes/" + this.result.getGroup().getPath() + "/" + id.getPath())));
    }

    public static class Result implements IFinishedRecipe {
        private final ResourceLocation id;
        private final Item result;
        private final int count;
        private final int chopTimes;
        private final String group;
        private final Ingredient input;
        private final Ingredient tool;
        private final Advancement.Builder advancementBuilder;
        private final ResourceLocation advancementId;

        public Result(ResourceLocation idIn, Item resultIn, Ingredient input, int countIn, int chopTimes, String groupIn, Ingredient tool, Advancement.Builder advancementBuilderIn, ResourceLocation advancementIdIn) {
            this.id = idIn;
            this.result = resultIn;
            this.count = countIn;
            this.chopTimes = chopTimes;
            this.group = groupIn;
            this.tool = tool;
            this.input = input;
            this.advancementBuilder = advancementBuilderIn;
            this.advancementId = advancementIdIn;
        }

        public void serialize(JsonObject json) {
            if (!this.group.isEmpty()) {
                json.addProperty("group", this.group);
            }

            json.add("tool", tool.serialize());
            json.add("ingredient", input.serialize());
            json.addProperty("chopTimes", chopTimes);

            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("item", Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(this.result)).toString());

            if (this.count > 1) {
                jsonObject.addProperty("count", this.count);
            }

            json.add("result", jsonObject);
        }

        @SuppressWarnings("ConstantConditions")
        public IRecipeSerializer<?> getSerializer() {
            return RecipeSubscriber.tree_stump;
        }

        /**
         * Gets the ID for the recipe.
         */
        public ResourceLocation getID() {
            return this.id;
        }

        /**
         * Gets the JSON for the advancement that unlocks this recipe. Null if there is no advancement.
         */
        @Nullable
        public JsonObject getAdvancementJson() {
            return this.advancementBuilder.serialize();
        }

        /**
         * Gets the ID for the advancement associated with this recipe. Should not be null if {@link #getAdvancementJson}
         * is non-null.
         */
        @Nullable
        public ResourceLocation getAdvancementID() {
            return this.advancementId;
        }
    }
}
