package com.yanny.age.stone.compatibility.patchouli;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.yanny.age.stone.recipes.TreeStumpRecipe;
import com.yanny.age.stone.subscribers.BlockSubscriber;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.util.IReorderingProcessor;
import net.minecraft.util.ResourceLocation;
import vazkii.patchouli.api.IComponentRenderContext;
import vazkii.patchouli.api.IVariable;

import javax.annotation.Nonnull;
import java.util.function.UnaryOperator;

public class TreeStumpRecipeComponent extends BaseComponent {
    private transient TreeStumpRecipe recipe = null;
    private transient IReorderingProcessor title;

    @Override
    public void build(int componentX, int componentY, int pageNum) {
        x = componentX;
        y = componentY;
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public void render(@Nonnull MatrixStack matrixStack, @Nonnull IComponentRenderContext context, float partialTicks, int mouseX, int mouseY) {
        mc.textureManager.bindTexture(TEXTURE);
        RenderSystem.enableBlend();
        AbstractGui.blit(matrixStack, x, y, 11, 71, 96, 24, 128, 128);
        drawCenteredStringNoShadow(matrixStack, title, PAGE_WIDTH / 2, y - 12, context.getHeaderColor(), mc.fontRenderer);

        context.renderIngredient(matrixStack, x + 4, y + 4, mouseX, mouseY, recipe.getIngredients().get(0));
        context.renderItemStack(matrixStack, x + 40, y, mouseX, mouseY, BlockSubscriber.tree_stump.asItem().getDefaultInstance());
        context.renderIngredient(matrixStack, x + 40, y + 16, mouseX, mouseY, recipe.getTools().get(context.getTicksInBook() % recipe.getTools().size()));
        context.renderItemStack(matrixStack, x + 76, y + 4, mouseX, mouseY, recipe.getRecipeOutput());
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public void onVariablesAvailable(@Nonnull UnaryOperator<IVariable> unaryOperator) {
        String recipeName = unaryOperator.apply(IVariable.wrap("#recipe#")).asString();

        mc.world.getRecipeManager().getRecipe(new ResourceLocation(recipeName)).ifPresent((recipe) -> {
            this.recipe = (TreeStumpRecipe) recipe;
            this.title = recipe.getRecipeOutput().getDisplayName().func_241878_f();
        });
    }
}
