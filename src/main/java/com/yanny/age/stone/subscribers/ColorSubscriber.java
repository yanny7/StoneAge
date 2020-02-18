package com.yanny.age.stone.subscribers;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.yanny.age.stone.Reference.MODID;
import static com.yanny.age.stone.subscribers.ToolSubscriber.*;

@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ColorSubscriber {

    private static final int OVERLAY_BONE_COLOR = 0xe8e5d2;
    private static final int OVERLAY_STONE_COLOR = 0x6c6c6c;

    @SubscribeEvent
    public static void onColorRegister(ColorHandlerEvent.Item event) {
        event.getItemColors().register((itemStack, index) -> OVERLAY_BONE_COLOR, bone_axe_head, bone_pickaxe_head, bone_hoe_head, bone_shovel_head, bone_sword_head);
        event.getItemColors().register((itemStack, index) -> index == 0 ? OVERLAY_BONE_COLOR : -1, bone_axe, bone_pickaxe, bone_hoe, bone_shovel, bone_sword);
        event.getItemColors().register((itemStack, index) -> OVERLAY_STONE_COLOR, stone_axe_head, stone_pickaxe_head, stone_hoe_head, stone_shovel_head, stone_sword_head);
        event.getItemColors().register((itemStack, index) -> index == 0 ? OVERLAY_STONE_COLOR : -1, stone_axe, stone_pickaxe, stone_hoe, stone_shovel, stone_sword);
    }
}
