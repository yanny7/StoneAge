package com.yanny.age.stone.compatibility.patchouli;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.yanny.age.stone.recipes.FlintWorkbenchRecipe;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.IReorderingProcessor;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import vazkii.patchouli.api.IComponentRenderContext;
import vazkii.patchouli.api.IVariable;

import javax.annotation.Nonnull;
import java.util.function.UnaryOperator;

public class FlintWorkbenchRecipeComponent extends BaseComponent {

    private transient FlintWorkbenchRecipe recipe = null;
    private transient IReorderingProcessor title;

    @Override
    public void render(@Nonnull MatrixStack matrixStack, @Nonnull IComponentRenderContext context, float partialTicks, int mouseX, int mouseY) {
        mc.textureManager.bindTexture(TEXTURE);
        RenderSystem.enableBlend();
        AbstractGui.blit(matrixStack, x - 2, y - 2, 0, 0, 100, 62, 128, 128);

        drawCenteredStringNoShadow(matrixStack, title, PAGE_WIDTH / 2, y - 12, context.getHeaderColor(), mc.fontRenderer);
        context.renderItemStack(matrixStack, x + 79, y + 22, mouseX, mouseY, recipe.getRecipeOutput());
        context.renderIngredient(matrixStack, x + 62, y + 9, mouseX, mouseY, recipe.getTool());

        NonNullList<Ingredient> ingredients = recipe.getIngredients();

        for (int i = 0; i < ingredients.size(); i++) {
            context.renderIngredient(matrixStack, x + (i % 3) * 19 + 3, y + (i / 3) * 19 + 3, mouseX, mouseY, ingredients.get(i));
        }
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public void onVariablesAvailable(@Nonnull UnaryOperator<IVariable> unaryOperator) {
        String recipeName = unaryOperator.apply(IVariable.wrap("#recipe#")).asString();

        mc.world.getRecipeManager().getRecipe(new ResourceLocation(recipeName)).ifPresent((recipe) -> {
            this.recipe = (FlintWorkbenchRecipe) recipe;
            this.title = recipe.getRecipeOutput().getDisplayName().func_241878_f();
        });

    }
}
