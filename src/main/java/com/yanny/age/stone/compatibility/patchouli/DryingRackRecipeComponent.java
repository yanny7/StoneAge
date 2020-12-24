package com.yanny.age.stone.compatibility.patchouli;

import com.mojang.blaze3d.systems.RenderSystem;
import com.yanny.age.stone.recipes.DryingRackRecipe;
import com.yanny.age.stone.subscribers.BlockSubscriber;
import jdk.nashorn.internal.ir.Block;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.util.ResourceLocation;
import vazkii.patchouli.api.IComponentRenderContext;

import javax.annotation.Nonnull;
import java.util.function.Function;

public class DryingRackRecipeComponent extends BaseComponent {
    private transient DryingRackRecipe recipe = null;
    private transient String title;

    @Override
    public void build(int componentX, int componentY, int pageNum) {
        x = componentX;
        y = componentY;
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public void render(@Nonnull IComponentRenderContext context, float partialTicks, int mouseX, int mouseY) {
        if (recipe == null) {
            return;
        }

        mc.textureManager.bindTexture(TEXTURE);
        RenderSystem.enableBlend();
        AbstractGui.blit(x, y, 11, 71, 96, 24, 128, 128);
        drawCenteredStringNoShadow(title, PAGE_WIDTH / 2, y - 10, context.getHeaderColor(), mc.fontRenderer);

        context.renderIngredient(x + 4, y + 4, mouseX, mouseY, recipe.getIngredients().get(0));
        context.renderItemStack(x + 40, y + 4, mouseX, mouseY, BlockSubscriber.drying_rack.asItem().getDefaultInstance());
        context.renderItemStack(x + 76, y + 4, mouseX, mouseY, recipe.getRecipeOutput());
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public void onVariablesAvailable(@Nonnull Function<String, String> lookup) {
        String recipeName = lookup.apply("#recipe#");

        mc.world.getRecipeManager().getRecipe(new ResourceLocation(recipeName)).ifPresent((recipe) -> {
            this.recipe = (DryingRackRecipe) recipe;
            this.title = recipe.getRecipeOutput().getDisplayName().getFormattedText();
        });
    }
}
