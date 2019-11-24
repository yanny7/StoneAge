package com.yanny.age.zero.proxy;

import com.yanny.age.zero.blocks.*;
import com.yanny.age.zero.client.renderer.*;
import com.yanny.age.zero.entities.*;
import com.yanny.age.zero.subscribers.ContainerSubscriber;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;


public class ClientProxy implements IProxy {

    @Override
    public void init() {
        ScreenManager.registerFactory(ContainerSubscriber.stone_chest, StoneChestGui::new);

        RenderingRegistry.registerEntityRenderingHandler(DeerEntity.class, new DeerRenderer.RenderFactory());
        RenderingRegistry.registerEntityRenderingHandler(BoarEntity.class, new BoarRenderer.RenderFactory());
        RenderingRegistry.registerEntityRenderingHandler(AurochEntity.class, new AurochRenderer.RenderFactory());
        RenderingRegistry.registerEntityRenderingHandler(FowlEntity.class, new FowlRenderer.RenderFactory());
        RenderingRegistry.registerEntityRenderingHandler(MouflonEntity.class, new MouflonRenderer.RenderFactory());
        RenderingRegistry.registerEntityRenderingHandler(FlintSpearEntity.class, new FlintSpearRenderer.RenderFactory());

        ClientRegistry.bindTileEntitySpecialRenderer(FlintWorkbenchTileEntity.class, new FlintWorkbenchRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(DryingRackTileEntity.class, new DryingRackRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TanningRackTileEntity.class, new TanningRackRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(StoneChestTileEntity.class, new StoneChestRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TreeStumpTileEntity.class, new TreeStumpRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(AquaductTileEntity.class, new AquaductRenderer());
    }

    @Override
    public World getClientWorld() {
        return Minecraft.getInstance().world;
    }

    @Override
    public PlayerEntity getClientPlayer() {
        return Minecraft.getInstance().player;
    }
}
