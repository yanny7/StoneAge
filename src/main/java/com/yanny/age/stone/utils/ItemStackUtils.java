package com.yanny.age.stone.utils;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.model.BakedQuad;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.NonNullList;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.common.util.Constants;

import javax.annotation.Nonnull;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

import static net.minecraft.client.renderer.model.ItemCameraTransforms.*;

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

    public static void renderItem(ItemStack itemStackIn, TransformType transformTypeIn, int combinedLightIn, int combinedOverlayIn, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, float alpha) {
        renderItem(itemStackIn, transformTypeIn, matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn, alpha);
    }

    private static void renderItem(ItemStack itemStackIn, TransformType transformTypeIn, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn, float alpha) {
        if (!itemStackIn.isEmpty()) {
            IBakedModel ibakedmodel = getItemModelWithOverrides(itemStackIn);
            renderItem(itemStackIn, transformTypeIn, matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn, ibakedmodel, alpha);
        }
    }

    private static IBakedModel getItemModelWithOverrides(ItemStack stack) {
        Item item = stack.getItem();
        IBakedModel ibakedmodel = Minecraft.getInstance().getItemRenderer().getItemModelMesher().getItemModel(stack);
        return !item.hasCustomProperties() ? ibakedmodel : getModelWithOverrides(ibakedmodel, stack);
    }

    private static IBakedModel getModelWithOverrides(IBakedModel model, ItemStack stack) {
        IBakedModel ibakedmodel = model.getOverrides().getModelWithOverrides(model, stack, null, null);
        return ibakedmodel == null ? Minecraft.getInstance().getItemRenderer().getItemModelMesher().getModelManager().getMissingModel() : ibakedmodel;
    }

    private static void renderItem(ItemStack itemStackIn, TransformType transformTypeIn, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn, IBakedModel modelIn, float alpha) {
        if (!itemStackIn.isEmpty()) {
            matrixStackIn.push();
            boolean flag = transformTypeIn == TransformType.GUI;
            boolean flag1 = flag || transformTypeIn == TransformType.GROUND || transformTypeIn == TransformType.FIXED;
            if (itemStackIn.getItem() == Items.TRIDENT && flag1) {
                modelIn = Minecraft.getInstance().getItemRenderer().getItemModelMesher().getModelManager().getModel(new ModelResourceLocation("minecraft:trident#inventory"));
            }

            modelIn = ForgeHooksClient.handleCameraTransforms(matrixStackIn, modelIn, transformTypeIn, false);
            matrixStackIn.translate(-0.5D, -0.5D, -0.5D);
            if (modelIn.isBuiltInRenderer() || itemStackIn.getItem() == Items.TRIDENT && !flag1) {
                itemStackIn.getItem().getItemStackTileEntityRenderer().render(itemStackIn, matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn);
            } else {
                RenderType rendertype = RenderTypeLookup.getRenderType(itemStackIn);
                RenderType rendertype1;
                if (flag && Objects.equals(rendertype, Atlases.getTranslucentBlockType())) {
                    rendertype1 = Atlases.getTranslucentCullBlockType();
                } else {
                    rendertype1 = rendertype;
                }

                IVertexBuilder ivertexbuilder = ItemRenderer.getBuffer(bufferIn, rendertype1, true, itemStackIn.hasEffect());
                renderModel(modelIn, combinedLightIn, combinedOverlayIn, matrixStackIn, ivertexbuilder, alpha);
            }

            matrixStackIn.pop();
        }
    }

    @SuppressWarnings("deprecation")
    private static void renderModel(IBakedModel modelIn, int combinedLightIn, int combinedOverlayIn, MatrixStack matrixStackIn, IVertexBuilder bufferIn, float alpha) {
        Random random = new Random();
        Direction[] var10 = Direction.values();

        for (Direction direction : var10) {
            random.setSeed(42L);
            renderQuads(matrixStackIn, bufferIn, modelIn.getQuads(null, direction, random), combinedLightIn, combinedOverlayIn, alpha);
        }

        random.setSeed(42L);
        renderQuads(matrixStackIn, bufferIn, modelIn.getQuads(null, null, random), combinedLightIn, combinedOverlayIn, alpha);
    }

    private static void renderQuads(MatrixStack matrixStackIn, IVertexBuilder bufferIn, List<BakedQuad> quadsIn, int combinedLightIn, int combinedOverlayIn, float alpha) {
        MatrixStack.Entry matrixstack$entry = matrixStackIn.getLast();

        for (BakedQuad bakedquad : quadsIn) {
            bufferIn.addVertexData(matrixstack$entry, bakedquad, 1, 1, 1, alpha, combinedLightIn, combinedOverlayIn, true);
        }
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
