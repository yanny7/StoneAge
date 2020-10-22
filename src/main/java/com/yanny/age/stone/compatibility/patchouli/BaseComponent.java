package com.yanny.age.stone.compatibility.patchouli;

import com.yanny.age.stone.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.ResourceLocation;
import vazkii.patchouli.api.ICustomComponent;

public abstract class BaseComponent implements ICustomComponent  {
    protected static final transient Minecraft mc = Minecraft.getInstance();
    protected static final transient int PAGE_WIDTH = 116;
    protected static final transient ResourceLocation TEXTURE = new ResourceLocation(Reference.MODID, "textures/gui/patchouli/crafting.png");

    protected transient int x;
    protected transient int y;

    @Override
    public void build(int componentX, int componentY, int pageNum) {
        x = componentX;
        y = componentY;
    }

    protected void drawCenteredStringNoShadow(String s, int x, int y, int color, FontRenderer fontRenderer) {
        fontRenderer.drawString(s, x - fontRenderer.getStringWidth(s) / 2.0F, y, color);
    }
}
