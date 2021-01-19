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
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;
import org.lwjgl.system.NonnullDefault;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.function.Consumer;

@NonnullDefault
public class DryingRackRecipeBuilder {
    private final Item result;
    private final int count;
    private final Ingredient input;
    private final Advancement.Builder advancementBuilder = Advancement.Builder.builder();
    @Nullable private String group = null;
    private int dryingTime = 300;

    public DryingRackRecipeBuilder(IItemProvider resultIn, IItemProvider input, int countIn) {
        this.result = resultIn.asItem();
        this.input = Ingredient.fromItems(input);
        this.count = countIn;
    }

    public static DryingRackRecipeBuilder recipe(IItemProvider resultIn, IItemProvider input) {
        return recipe(resultIn, input, 1);
    }

    public static DryingRackRecipeBuilder recipe(IItemProvider resultIn, IItemProvider input, int countIn) {
        return new DryingRackRecipeBuilder(resultIn, input, countIn);
    }

    public DryingRackRecipeBuilder dryingTime(int dryingTime) {
        this.dryingTime = dryingTime;
        return this;
    }

    public DryingRackRecipeBuilder addCriterion(String name, ICriterionInstance criterionIn) {
        this.advancementBuilder.withCriterion(name, criterionIn);
        return this;
    }

    public DryingRackRecipeBuilder setGroup(String groupIn) {
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
        }

        this.advancementBuilder.withParentId(new ResourceLocation("recipes/root")).withCriterion("has_the_recipe", new RecipeUnlockedTrigger.Instance(id)).withRewards(AdvancementRewards.Builder.recipe(id)).withRequirementsStrategy(IRequirementsStrategy.OR);
        consumerIn.accept(new Result(id, this.result, this.input, this.count, this.dryingTime, this.group == null ? "" : this.group, this.advancementBuilder, new ResourceLocation(id.getNamespace(), "recipes/" + this.result.getGroup().getPath() + "/" + id.getPath())));
    }

    public static class Result implements IFinishedRecipe {
        private final ResourceLocation id;
        private final Item result;
        private final int count;
        private final int dryingTime;
        private final String group;
        private final Ingredient input;
        private final Advancement.Builder advancementBuilder;
        private final ResourceLocation advancementId;

        public Result(ResourceLocation idIn, Item resultIn, Ingredient input, int countIn, int dryingTime, String groupIn, Advancement.Builder advancementBuilderIn, ResourceLocation advancementIdIn) {
            this.id = idIn;
            this.result = resultIn;
            this.count = countIn;
            this.dryingTime = dryingTime;
            this.group = groupIn;
            this.input = input;
            this.advancementBuilder = advancementBuilderIn;
            this.advancementId = advancementIdIn;
        }

        public void serialize(JsonObject json) {
            if (!this.group.isEmpty()) {
                json.addProperty("group", this.group);
            }

            json.add("ingredient", input.serialize());
            json.addProperty("dryingTime", dryingTime);

            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("item", Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(this.result)).toString());

            if (this.count > 1) {
                jsonObject.addProperty("count", this.count);
            }

            json.add("result", jsonObject);
        }

        @SuppressWarnings("ConstantConditions")
        public IRecipeSerializer<?> getSerializer() {
            return RecipeSubscriber.drying_rack;
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
