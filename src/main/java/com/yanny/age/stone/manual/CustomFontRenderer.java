package com.yanny.age.stone.manual;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.fonts.Font;
import net.minecraft.client.gui.fonts.IGlyph;
import net.minecraft.client.gui.fonts.TexturedGlyph;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

import javax.annotation.Nonnull;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class CustomFontRenderer {
    private FontRenderer fontRenderer;

    CustomFontRenderer(FontRenderer fontRenderer) {
        this.fontRenderer = fontRenderer;
    }

    void drawSplitString(String str, int x, int y, int wrapWidth, int textColor, Align align, boolean justify) {
        str = trimStringNewline(str);
        renderSplitString(str, x, y, wrapWidth, textColor, align, justify);
    }

    String trimStringNewline(String str) {
        try {
            Class<?>[] argTypes = new Class[] { String.class };
            Method trim = ObfuscationReflectionHelper.findMethod(FontRenderer.class, "func_78273_d", argTypes);
            return (String) trim.invoke(fontRenderer, str);
        } catch (IllegalAccessException | InvocationTargetException | ObfuscationReflectionHelper.UnableToFindMethodException e) {
            e.printStackTrace();
        }

        return "";
    }

    public int getWordWrappedHeight(String str, int maxLength) {
        return 9 * this.wrapFormattedStringToWidth(str, maxLength).size();
    }

    Font getFont() {
        try {
            Field f = ObfuscationReflectionHelper.findField(FontRenderer.class, "field_211127_e");
            return (Font) f.get(fontRenderer);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        throw new IllegalArgumentException();
    }

    List<Link> analyseSplitStringLinks(String str, int x, int y, int width, Align align, boolean justify) {
        str = trimStringNewline(str);
        return analyseSplitString(str, x, y, width, align, justify);
    }

    @Nonnull
    private List<StringWidth> wrapFormattedStringToWidth(@Nonnull String str, int wrapWidth) {
        String s;
        String s1;
        List<StringWidth> list = new ArrayList<>();
        MutableBool bool = new MutableBool();

        for(s = ""; !str.isEmpty(); s = s + s1 + "\n") {
            Size i = this.sizeStringToWidth(str, wrapWidth, bool);

            if (str.length() <= i.count) {
                list.add(new StringWidth(str, i.size, false));
                return list;
            }

            s1 = str.substring(0, i.count);
            char c0 = str.charAt(i.count);
            boolean flag = c0 == ' ' || c0 == '\n';
            str = TextFormatting.getFormatString(s1) + str.substring(i.count + (flag ? 1 : 0));
            list.add(new StringWidth(s1, i.size, true));
        }

        return list;
    }

    @Nonnull
    private Size sizeStringToWidth(@Nonnull String str, int wrapWidth, MutableBool link) {
        int i = Math.max(1, wrapWidth);
        int j = str.length();
        float f = 0.0F;
        float r = 0.0F;
        int k = 0;
        int l = -1;
        boolean flag = false;
        boolean wasSpace;

        for(boolean flag1 = true; k < j; ++k) {
            char c0 = str.charAt(k);
            wasSpace = false;

            switch(c0) {
                case '\n':
                    --k;
                    break;
                case ' ':
                    l = k;
                    r = f;
                    wasSpace = true;
                default:
                    if (f != 0.0F) {
                        flag1 = false;
                    }

                    f += this.getCharWidth(c0);

                    if (flag) {
                        ++f;
                    }
                    break;
                case '\u00a7': {
                    if (k < j - 1) {
                        ++k;
                        TextFormatting textformatting = TextFormatting.fromFormattingCode(str.charAt(k));

                        if (textformatting == TextFormatting.BOLD) {
                            flag = true;
                        } else if (textformatting != null && textformatting.isNormalStyle()) {
                            flag = false;
                        }

                        if ((str.charAt(k) == 'p')) {
                            if (!link.valid) {
                                int s = 2;
                                link.valid = true;

                                while ((k + s < str.length()) && (str.charAt(k + s) != ' ')) {
                                    s++;
                                }

                                k += s;
                            } else {
                                link.valid = false;
                            }
                        }
                    }
                }
            }

            if (c0 == '\n') {
                ++k;
                l = k;
                r = f;
                break;
            }

            if (f > (float)i) {
                if (flag1) {
                    ++k;
                }
                if (wasSpace) {
                    f -= this.getCharWidth(' ');
                }
                break;
            }
        }

        return k != j && l != -1 && l < k ? new Size(l, r) : new Size(k, f);
    }

    public float getCharWidth(char character) {
        return character == 167 ? 0.0F : getFont().findGlyph(character).getAdvance(false);
    }

    private void renderSplitString(String str, int x, int y, int wrapWidth, int textColor, Align align, boolean justify) {
        MutableBool link = new MutableBool();

        for(StringWidth s : this.wrapFormattedStringToWidth(str, wrapWidth)) {
            float f = (float)x;

            if (fontRenderer.getBidiFlag()) {
                int i = fontRenderer.getStringWidth(fontRenderer.bidiReorder(s.str));
                f += (float)(wrapWidth - i);
            }

            if (justify && s.fullLine) {
                this.renderString(s.str, f, y, textColor, false, link, wrapWidth - s.width);
            } else {
                switch (align) {
                    case LEFT:
                        this.renderString(s.str, f, y, textColor, false, link, 0);
                        break;
                    case RIGHT:
                        this.renderString(s.str, f + (wrapWidth - s.width), y, textColor, false, link, 0);
                        break;
                    case CENTER:
                        this.renderString(s.str, f + (wrapWidth - s.width) / 2f, y, textColor, false, link, 0);
                        break;
                }
            }

            y += fontRenderer.FONT_HEIGHT;
        }
    }

    private int renderString(String text, float x, float y, int color, boolean dropShadow, MutableBool bool, float width) {
        if (text == null) {
            return 0;
        } else {
            if (fontRenderer.getBidiFlag()) {
                text = fontRenderer.bidiReorder(text);
            }

            if ((color & -67108864) == 0) {
                color |= -16777216;
            }

            if (dropShadow) {
                this.renderStringAtPos(text, x, y, color, true, bool, width);
            }

            x = this.renderStringAtPos(text, x, y, color, false, bool, width);
            return (int)x + (dropShadow ? 1 : 0);
        }
    }

    private float renderStringAtPos(@Nonnull String text, float x, float y, int color, boolean isShadow, MutableBool link, float width) {
        float f = isShadow ? 0.25F : 1.0F;
        float f1 = (float)(color >> 16 & 255) / 255.0F * f;
        float f2 = (float)(color >> 8 & 255) / 255.0F * f;
        float f3 = (float)(color & 255) / 255.0F * f;
        float f4 = f1;
        float f5 = f2;
        float f6 = f3;
        float f7 = (float)(color >> 24 & 255) / 255.0F;
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        ResourceLocation resourcelocation = null;
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
        boolean flag = false;
        boolean flag1 = false;
        boolean flag2 = false;
        boolean flag3 = false;
        boolean flag4 = false;
        List<Entry> list = Lists.newArrayList();
        int spaces = getSpaces(text);
        float spaceWidth = 0;

        if (width > 0 && spaces > 0) {
            spaceWidth = width / spaces;
        }

        for(int i = 0; i < text.length(); ++i) {
            char c0 = text.charAt(i);

            if (c0 == 167 && i + 1 < text.length()) {
                TextFormatting textformatting = TextFormatting.fromFormattingCode(text.charAt(i + 1));

                if (textformatting != null) {
                    if (textformatting.isNormalStyle()) {
                        flag = false;
                        flag1 = false;
                        flag4 = false;
                        flag3 = false;
                        flag2 = false;
                        f4 = f1;
                        f5 = f2;
                        f6 = f3;
                    }

                    if (textformatting.getColor() != null) {
                        int j = textformatting.getColor();
                        f4 = (float)(j >> 16 & 255) / 255.0F * f;
                        f5 = (float)(j >> 8 & 255) / 255.0F * f;
                        f6 = (float)(j & 255) / 255.0F * f;
                    } else if (textformatting == TextFormatting.OBFUSCATED) {
                        flag = true;
                    } else if (textformatting == TextFormatting.BOLD) {
                        flag1 = true;
                    } else if (textformatting == TextFormatting.STRIKETHROUGH) {
                        flag4 = true;
                    } else if (textformatting == TextFormatting.UNDERLINE) {
                        flag3 = true;
                    } else if (textformatting == TextFormatting.ITALIC) {
                        flag2 = true;
                    }
                } else if (text.charAt(i + 1) == 'p') {
                    if (!link.valid) {
                        int j = 2;
                        link.valid = true;

                        while ((i + j < text.length()) && (text.charAt(i + j) != ' ')) {
                            j++;
                        }

                        i += j - 1;
                    } else {
                        link.valid = false;
                    }
                }

                ++i;
            } else {
                IGlyph iglyph = getFont().findGlyph(c0);
                TexturedGlyph texturedglyph = flag && c0 != ' ' ? getFont().obfuscate(iglyph) : getFont().getGlyph(c0);
                ResourceLocation resourcelocation1 = texturedglyph.getTextureLocation();

                if (resourcelocation1 != null) {
                    if (resourcelocation != resourcelocation1) {
                        tessellator.draw();
                        Minecraft.getInstance().textureManager.bindTexture(resourcelocation1);
                        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
                        resourcelocation = resourcelocation1;
                    }

                    float f8 = flag1 ? iglyph.getBoldOffset() : 0.0F;
                    float f9 = isShadow ? iglyph.getShadowOffset() : 0.0F;
                    this.renderGlyph(texturedglyph, flag1, flag2, f8, x + f9, y + f9, bufferbuilder, f4, f5, f6, f7);
                }

                float f10 = iglyph.getAdvance(flag1);
                float f11 = isShadow ? 1.0F : 0.0F;

                if (flag4) {
                    list.add(new Entry(x + f11 - 1.0F, y + f11 + 4.5F, x + f11 + f10, y + f11 + 4.5F - 1.0F, f4, f5, f6, f7));
                }

                if (flag3) {
                    list.add(new Entry(x + f11 - 1.0F, y + f11 + 9.0F, x + f11 + f10, y + f11 + 9.0F - 1.0F, f4, f5, f6, f7));
                }

                x += f10;

                if (spaceWidth > 0 && c0 == ' ') {
                    x += spaceWidth;
                }
            }
        }

        tessellator.draw();
        if (!list.isEmpty()) {
            GlStateManager.disableTexture();
            bufferbuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);

            for(Entry fontrenderer$entry : list) {
                fontrenderer$entry.pipe(bufferbuilder);
            }

            tessellator.draw();
            GlStateManager.enableTexture();
        }

        return x;
    }

    private int getSpaces(String text) {
        int count = 0;

        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);

            if (c == ' ') {
                count++;
            } else if ((c == '\u00a7') && (i < text.length() - 1) && (text.charAt(i + 1) == 'p')) {
                count--;
            }
        }

        return count;
    }

    private void renderGlyph(@Nonnull TexturedGlyph glyph, boolean bold, boolean italic, float boldOffset, float x, float y, BufferBuilder builder, float r, float g, float b, float a) {
        glyph.render(Minecraft.getInstance().textureManager, italic, x, y, builder, r, g, b, a);

        if (bold) {
            glyph.render(Minecraft.getInstance().textureManager, italic, x + boldOffset, y, builder, r, g, b, a);
        }

    }

    @Nonnull
    private List<Link> analyseSplitString(String str, int x, int y, int width, Align align, boolean justify) {
        List<Link> list = new ArrayList<>();
        int line = 0;

        for(StringWidth s : wrapFormattedStringToWidth(str, width)) {
            if (justify && s.fullLine) {
                analyseString(list, s.str, (float) x, (float) y, line, width - s.width);
            } else {
                switch (align) {
                    case LEFT:
                        analyseString(list, s.str, (float) x, (float) y, line, 0);
                        break;
                    case RIGHT:
                        analyseString(list, s.str, (float) x + (width - s.width), (float) y, line, 0);
                        break;
                    case CENTER:
                        analyseString(list, s.str, (float) x + (width - s.width) / 2f, (float) y, line, 0);
                        break;
                }
            }

            y += Math.ceil(fontRenderer.FONT_HEIGHT);
            line++;
        }

        return list;
    }

    private void analyseString(List<Link> list, String text, float x, float y, int line, float width) {
        if (text != null) {
            analyseStringAtPos(list, text, x, y, line, width);
        }
    }

    private void analyseStringAtPos(List<Link> list, @Nonnull String text, float xIn, float yIn, int line, float width) {
        boolean bold = false;
        float x = xIn;
        int spaces = getSpaces(text);
        float spaceWidth = 0;

        if (width > 0 && spaces > 0) {
            spaceWidth = width / spaces;
        }

        for(int i = 0; i < text.length(); ++i) {
            char c0 = text.charAt(i);

            if (c0 == 167 && i + 1 < text.length()) {
                TextFormatting textformatting = TextFormatting.fromFormattingCode(text.charAt(i + 1));

                if (textformatting != null) {
                    if (textformatting.isNormalStyle()) {
                        bold = false;
                    }

                    if (textformatting == TextFormatting.BOLD) {
                        bold = true;
                    }
                } else if (text.charAt(i + 1) == 'p') {
                    Link link;
                    Rect rect;

                    if (list.isEmpty()) {
                        link = new Link();
                    } else {
                        link = list.get(list.size() - 1);
                    }

                    if (link.rects.isEmpty() || link.rects.get(link.rects.size() - 1).valid) {
                        rect = new Rect();
                        rect.x1 = x;
                        rect.y1 = yIn;
                        rect.line = line;
                        link = new Link();
                        link.rects.add(rect);
                        list.add(link);

                        StringBuilder sb = new StringBuilder();
                        int j = 2;

                        while((i + j < text.length()) && (text.charAt(i + j) != ' ')) {
                            sb.append(text.charAt(i + j));
                            j++;
                        }

                        link.key = sb.toString();
                        i += j - 1;
                    } else {
                        rect = link.rects.get(link.rects.size() - 1);
                        rect.x2 = x;
                        rect.y2 = yIn + fontRenderer.FONT_HEIGHT;
                        rect.valid = true;
                        rect.line = line;
                    }
                }

                ++i;
            } else {
                IGlyph iglyph = getFont().findGlyph(c0);
                float f10 = iglyph.getAdvance(bold);
                x += f10;

                if (spaceWidth > 0 && c0 == ' ') {
                    x += spaceWidth;
                }
            }
        }

        if (!list.isEmpty() && list.get(list.size() - 1).hasUnclosedLink()) {
            Link link = list.get(list.size() - 1);
            Rect rect = link.rects.get(link.rects.size() - 1);

            if (rect.line != line) {
                rect.x1 = xIn;
                rect.y1 = yIn;
            }

            rect.x2 = x;
            rect.y2 = yIn + fontRenderer.FONT_HEIGHT;
            rect.valid = true;

            Rect newLine = new Rect();
            newLine.x1 = xIn;
            newLine.y1 = rect.y2;
            link.rects.add(newLine);
        }
    }

    static class Link {
        List<Rect> rects = new ArrayList<>();
        String key;

        boolean hasUnclosedLink() {
            return !rects.isEmpty() && !rects.get(rects.size() - 1).valid;
        }

        boolean inArea(int mx, int my, float scale) {
            return rects.stream().anyMatch(rect -> rect.inArea(mx, my, scale));
        }
    }

    static class Rect {
        boolean valid;
        int line;
        float x1, x2, y1, y2;

        boolean inArea(int mx, int my, float scale) {
            return mx >= x1 * scale && mx <= x2 * scale && my >= y1 * scale && my <= y2 * scale;
        }
    }

    static class Entry {
        protected final float x1;
        protected final float y1;
        protected final float x2;
        protected final float y2;
        protected final float red;
        protected final float green;
        protected final float blue;
        protected final float alpha;

        private Entry(float x1, float y1, float x2, float y2, float red, float green, float blue, float alpha) {
            this.x1 = x1;
            this.y1 = y1;
            this.x2 = x2;
            this.y2 = y2;
            this.red = red;
            this.green = green;
            this.blue = blue;
            this.alpha = alpha;
        }

        public void pipe(@Nonnull BufferBuilder buffer) {
            buffer.pos(this.x1, this.y1, 0.0D).color(this.red, this.green, this.blue, this.alpha).endVertex();
            buffer.pos(this.x2, this.y1, 0.0D).color(this.red, this.green, this.blue, this.alpha).endVertex();
            buffer.pos(this.x2, this.y2, 0.0D).color(this.red, this.green, this.blue, this.alpha).endVertex();
            buffer.pos(this.x1, this.y2, 0.0D).color(this.red, this.green, this.blue, this.alpha).endVertex();
        }
    }

    static class MutableBool {
        boolean valid;
    }

    static class StringWidth {
        String str;
        float width;
        boolean fullLine;

        StringWidth(String str, float width, boolean fullLine) {
            this.str = str;
            this.width = width;
            this.fullLine = fullLine;
        }
    }

    static class Size {
        int count;
        float size;

        Size(int count, float size) {
            this.count = count;
            this.size = size;
        }
    }
}
