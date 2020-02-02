package com.yanny.age.stone.utils;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.model.BakedQuad;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.NonNullList;
import net.minecraft.util.Util;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class ItemStackUtils {

    public static CompoundNBT serializeStacks(@Nonnull NonNullList<ItemStack> stacks) {
        ListNBT listNBT = new ListNBT();
        CompoundNBT compoundNBT = new CompoundNBT();

        stacks.forEach(itemStack -> {
            CompoundNBT nbt = new CompoundNBT();
            itemStack.write(nbt);
            listNBT.add(nbt);
        });

        compoundNBT.put("Items", listNBT);
        return compoundNBT;
    }

    public static CompoundNBT serializeIngredients(@Nonnull List<Ingredient> ingredients) {
        ListNBT listNBT = new ListNBT();
        CompoundNBT compoundNBT = new CompoundNBT();

        ingredients.forEach(ingredient -> {
            ListNBT itemsNBT = new ListNBT();
            CompoundNBT itemsCompound = new CompoundNBT();

            for (ItemStack itemStack : ingredient.getMatchingStacks()) {
                CompoundNBT nbt = new CompoundNBT();
                itemStack.write(nbt);
                itemsNBT.add(nbt);
            }

            itemsCompound.put("Items", itemsNBT);
            listNBT.add(itemsCompound);
        });

        compoundNBT.put("Items", listNBT);
        return compoundNBT;
    }

    public static void deserializeStacks(@Nonnull CompoundNBT compoundNBT, @Nonnull NonNullList<ItemStack> stacks) {
        assert compoundNBT.contains("Items", Constants.NBT.TAG_LIST);
        ListNBT listNBT = compoundNBT.getList("Items", Constants.NBT.TAG_COMPOUND);
        assert listNBT.size() == stacks.size();
        AtomicInteger cnt = new AtomicInteger(0);

        listNBT.forEach(nbt -> {
            ItemStack itemStack = ItemStack.read((CompoundNBT) nbt);
            stacks.set(cnt.getAndIncrement(), itemStack);
        });
    }

    public static void deserializeIngredients(@Nonnull CompoundNBT compoundNBT, @Nonnull List<Ingredient> ingredients) {
        assert compoundNBT.contains("Items", Constants.NBT.TAG_LIST);
        ListNBT listNBT = compoundNBT.getList("Items", Constants.NBT.TAG_COMPOUND);

        ingredients.clear();

        listNBT.forEach(nbt -> {
            assert ((CompoundNBT) nbt).contains("Items", Constants.NBT.TAG_LIST);
            ListNBT itemsNBT = ((CompoundNBT) nbt).getList("Items", Constants.NBT.TAG_COMPOUND);
            ArrayList<ItemStack> itemStacks = new ArrayList<>();

            itemsNBT.forEach(itemNbt -> {
                ItemStack itemStack = ItemStack.read((CompoundNBT) itemNbt);
                itemStacks.add(itemStack);
            });

            ingredients.add(Ingredient.fromStacks(itemStacks.toArray(new ItemStack[0])));
        });
    }

    public static void insertItems(@Nonnull List<ItemStack> input, List<ItemStack> output, int startIndex, int endIndex) {
        assert output.size() > startIndex && output.size() >= endIndex && startIndex < endIndex;

        for (ItemStack itemStack : input) {
            if (itemStack.isEmpty()) {
                return;
            }

            int index = getFirstFreeOrValid(itemStack, output, startIndex, endIndex);

            if (index < 0) {
                return;
            }

            int items = itemStack.getCount();

            if (!output.get(index).isEmpty()) {
                while (items > 0) {
                    ItemStack item = output.get(index);

                    int amount = item.getMaxStackSize() - item.getCount();

                    if (amount < items) {
                        item.grow(amount);
                        items -= amount;
                        index = getFirstFreeOrValid(itemStack, output, index, endIndex);
                    } else {
                        item.grow(items);
                        items = 0;
                    }
                }
            } else {
                output.set(index, itemStack.copy());
            }
        }
    }

    @SuppressWarnings("deprecation")
    public static void renderItem(ItemStack stack, float alpha, ItemCameraTransforms.TransformType cameraTransformType) {
        if (!stack.isEmpty()) {
            IBakedModel ibakedmodel = getModelWithOverrides(stack);
            renderItemModel(stack, ibakedmodel, cameraTransformType, alpha);
        }
    }

    public static IBakedModel getModelWithOverrides(ItemStack stack) {
        return getItemModelWithOverrides(stack, null, null);
    }

    public static IBakedModel getItemModelWithOverrides(ItemStack stack, @Nullable World worldIn, @Nullable LivingEntity entitylivingbaseIn) {
        IBakedModel ibakedmodel = Minecraft.getInstance().getItemRenderer().getItemModelMesher().getItemModel(stack);
        Item item = stack.getItem();
        return !item.hasCustomProperties() ? ibakedmodel : getModelWithOverrides(ibakedmodel, stack, worldIn, entitylivingbaseIn);
    }

    public static void renderItem(ItemStack stack, IBakedModel model, float alpha) {
        if (!stack.isEmpty()) {
            GlStateManager.pushMatrix();
            GlStateManager.translatef(-0.5F, -0.5F, -0.5F);
            if (model.isBuiltInRenderer()) {
                GlStateManager.color4f(1.0F, 1.0F, 1.0F, alpha);
                GlStateManager.enableRescaleNormal();
                stack.getItem().getTileEntityItemStackRenderer().renderByItem(stack);
            } else {
                renderModel(model, stack, alpha);
                if (stack.hasEffect()) {
                    renderEffect(Minecraft.getInstance().textureManager, () -> renderModel(model), 8);
                }
            }

            GlStateManager.popMatrix();
        }
    }

    public static void renderQuads(BufferBuilder renderer, List<BakedQuad> quads, int color) {
        int i = 0;

        for(int j = quads.size(); i < j; ++i) {
            BakedQuad bakedquad = quads.get(i);
            net.minecraftforge.client.model.pipeline.LightUtil.renderQuadColor(renderer, bakedquad, color);
        }

    }

    public static void renderEffect(TextureManager textureManagerIn, Runnable renderModelFunction, int scale) {
        GlStateManager.depthMask(false);
        GlStateManager.depthFunc(514);
        GlStateManager.disableLighting();
        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_COLOR, GlStateManager.DestFactor.ONE);
        textureManagerIn.bindTexture(ItemRenderer.RES_ITEM_GLINT);
        GlStateManager.matrixMode(5890);
        GlStateManager.pushMatrix();
        GlStateManager.scalef((float)scale, (float)scale, (float)scale);
        float f = (float)(Util.milliTime() % 3000L) / 3000.0F / (float)scale;
        GlStateManager.translatef(f, 0.0F, 0.0F);
        GlStateManager.rotatef(-50.0F, 0.0F, 0.0F, 1.0F);
        renderModelFunction.run();
        GlStateManager.popMatrix();
        GlStateManager.pushMatrix();
        GlStateManager.scalef((float)scale, (float)scale, (float)scale);
        float f1 = (float)(Util.milliTime() % 4873L) / 4873.0F / (float)scale;
        GlStateManager.translatef(-f1, 0.0F, 0.0F);
        GlStateManager.rotatef(10.0F, 0.0F, 0.0F, 1.0F);
        renderModelFunction.run();
        GlStateManager.popMatrix();
        GlStateManager.matrixMode(5888);
        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        GlStateManager.enableLighting();
        GlStateManager.depthFunc(515);
        GlStateManager.depthMask(true);
        textureManagerIn.bindTexture(AtlasTexture.LOCATION_BLOCKS_TEXTURE);
    }

    @SuppressWarnings("deprecation")
    protected static void renderItemModel(ItemStack stack, IBakedModel bakedmodel, ItemCameraTransforms.TransformType transform, float alpha) {
        if (!stack.isEmpty()) {
            Minecraft.getInstance().getTextureManager().bindTexture(AtlasTexture.LOCATION_BLOCKS_TEXTURE);
            Minecraft.getInstance().getTextureManager().getTexture(AtlasTexture.LOCATION_BLOCKS_TEXTURE).setBlurMipmap(false, false);
            GlStateManager.color4f(1.0F, 1.0F, 1.0F, alpha);
            GlStateManager.enableRescaleNormal();
            GlStateManager.alphaFunc(516, 0.1F);
            GlStateManager.enableBlend();
            GlStateManager.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
            GlStateManager.pushMatrix();

            bakedmodel = net.minecraftforge.client.ForgeHooksClient.handleCameraTransforms(bakedmodel, transform, false);

            renderItem(stack, bakedmodel, alpha);
            GlStateManager.cullFace(GlStateManager.CullFace.BACK);
            GlStateManager.popMatrix();
            GlStateManager.disableRescaleNormal();
            GlStateManager.disableBlend();
            Minecraft.getInstance().getTextureManager().bindTexture(AtlasTexture.LOCATION_BLOCKS_TEXTURE);
            Minecraft.getInstance().getTextureManager().getTexture(AtlasTexture.LOCATION_BLOCKS_TEXTURE).restoreLastBlurMipmap();
        }
    }

    private static IBakedModel getModelWithOverrides(IBakedModel model, ItemStack stack, @Nullable World worldIn, @Nullable LivingEntity entityIn) {
        IBakedModel ibakedmodel = model.getOverrides().getModelWithOverrides(model, stack, worldIn, entityIn);
        return ibakedmodel == null ? Minecraft.getInstance().getItemRenderer().getItemModelMesher().getModelManager().getMissingModel() : ibakedmodel;
    }

    private static void renderModel(IBakedModel model, ItemStack stack, float alpha) {
        renderModel(model, 0xffffff | (Math.round(alpha * 0xff) << 24), stack);
    }

    private static void renderModel(IBakedModel model) {
        renderModel(model, -8372020, ItemStack.EMPTY);
    }

    @SuppressWarnings("deprecation")
    private static void renderModel(IBakedModel model, int color, ItemStack stack) {
        if (net.minecraftforge.common.ForgeConfig.CLIENT.allowEmissiveItems.get()) {
            net.minecraftforge.client.ForgeHooksClient.renderLitItem(Minecraft.getInstance().getItemRenderer(), model, color, stack);
            return;
        }
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(7, DefaultVertexFormats.ITEM);
        Random random = new Random();

        for(Direction direction : Direction.values()) {
            random.setSeed(42L);
            renderQuads(bufferbuilder, model.getQuads(null, direction, random), color);
        }

        random.setSeed(42L);
        renderQuads(bufferbuilder, model.getQuads(null, null, random), color);
        tessellator.draw();
    }

    private static int getFirstFreeOrValid(ItemStack item, List<ItemStack> output, int startIndex, int endIndex) {
        for (int i = startIndex; i < endIndex; i++) {
            ItemStack itemStack = output.get(i);

            if (itemStack.isItemEqual(item)) {
                if (itemStack.getCount() < itemStack.getMaxStackSize()) {
                    return i;
                }
            } else if (itemStack.isEmpty()) {
                return i;
            }
        }

        return -1;
    }
}
