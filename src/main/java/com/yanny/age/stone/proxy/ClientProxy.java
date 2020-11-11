package com.yanny.age.stone.proxy;

import com.yanny.age.stone.blocks.FeederGui;
import com.yanny.age.stone.blocks.FishingNetGui;
import com.yanny.age.stone.blocks.MillstoneGui;
import com.yanny.age.stone.blocks.StoneChestGui;
import com.yanny.age.stone.client.renderer.*;
import com.yanny.age.stone.items.BackpackGui;
import com.yanny.age.stone.subscribers.ContainerSubscriber;
import com.yanny.age.stone.subscribers.EntitySubscriber;
import com.yanny.age.stone.subscribers.TileEntitySubscriber;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

import javax.annotation.Nullable;

public class ClientProxy implements IProxy {

    @Override
    public void init() {
        ScreenManager.registerFactory(ContainerSubscriber.stone_chest, StoneChestGui::new);
        ScreenManager.registerFactory(ContainerSubscriber.feeder, FeederGui::new);
        ScreenManager.registerFactory(ContainerSubscriber.millstone, MillstoneGui::new);
        ScreenManager.registerFactory(ContainerSubscriber.backpack, BackpackGui::new);
        ScreenManager.registerFactory(ContainerSubscriber.fishing_net, FishingNetGui::new);

        RenderingRegistry.registerEntityRenderingHandler(EntitySubscriber.deer, new DeerRenderer.RenderFactory());
        RenderingRegistry.registerEntityRenderingHandler(EntitySubscriber.boar, new BoarRenderer.RenderFactory());
        RenderingRegistry.registerEntityRenderingHandler(EntitySubscriber.auroch, new AurochRenderer.RenderFactory());
        RenderingRegistry.registerEntityRenderingHandler(EntitySubscriber.fowl, new FowlRenderer.RenderFactory());
        RenderingRegistry.registerEntityRenderingHandler(EntitySubscriber.mouflon, new MouflonRenderer.RenderFactory());
        RenderingRegistry.registerEntityRenderingHandler(EntitySubscriber.flint_spear, new FlintSpearRenderer.RenderFactory());
        RenderingRegistry.registerEntityRenderingHandler(EntitySubscriber.mammoth, new MammothRenderer.RenderFactory());
        RenderingRegistry.registerEntityRenderingHandler(EntitySubscriber.saber_tooth_tiger, new SaberToothTigerRenderer.RenderFactory());
        RenderingRegistry.registerEntityRenderingHandler(EntitySubscriber.woolly_rhino, new WoollyRhinoRenderer.RenderFactory());
        RenderingRegistry.registerEntityRenderingHandler(EntitySubscriber.terror_bird, new TerrorBirdRenderer.RenderFactory());

        ClientRegistry.bindTileEntityRenderer(TileEntitySubscriber.flint_workbench, FlintWorkbenchRenderer::new);
        ClientRegistry.bindTileEntityRenderer(TileEntitySubscriber.drying_rack, DryingRackRenderer::new);
        ClientRegistry.bindTileEntityRenderer(TileEntitySubscriber.tanning_rack, TanningRackRenderer::new);
        ClientRegistry.bindTileEntityRenderer(TileEntitySubscriber.stone_chest, StoneChestRenderer::new);
        ClientRegistry.bindTileEntityRenderer(TileEntitySubscriber.tree_stump, TreeStumpRenderer::new);
        ClientRegistry.bindTileEntityRenderer(TileEntitySubscriber.aqueduct, AqueductRenderer::new);
        ClientRegistry.bindTileEntityRenderer(TileEntitySubscriber.feeder, FeederRenderer::new);
        ClientRegistry.bindTileEntityRenderer(TileEntitySubscriber.millstone, MillstoneRenderer::new);
    }

    @Override
    @Nullable
    public World getClientWorld() {
        return Minecraft.getInstance().world;
    }

    @Override
    @Nullable
    public PlayerEntity getClientPlayer() {
        return Minecraft.getInstance().player;
    }
}
