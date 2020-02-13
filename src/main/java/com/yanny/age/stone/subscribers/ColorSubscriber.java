package com.yanny.age.stone.subscribers;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.yanny.age.stone.Reference.MODID;
import static com.yanny.age.stone.subscribers.ToolSubscriber.*;
import static com.yanny.age.stone.subscribers.ToolSubscriber.bone_sword;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ColorSubscriber {

    private static final int OVERLAY_BONE_COLOR = 0xe8e5d2;

    @SubscribeEvent
    public static void onColorRegister(ColorHandlerEvent.Item event) {
        event.getItemColors().register((itemStack, index) -> OVERLAY_BONE_COLOR, bone_axe_head, bone_pickaxe_head, bone_hoe_head, bone_shovel_head, bone_sword_head);
        event.getItemColors().register((itemStack, index) -> index == 0 ? OVERLAY_BONE_COLOR : -1, bone_axe, bone_pickaxe, bone_hoe, bone_shovel, bone_sword);
    }
}
