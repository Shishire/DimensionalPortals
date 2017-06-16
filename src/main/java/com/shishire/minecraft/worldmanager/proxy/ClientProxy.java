package com.shishire.minecraft.worldmanager.proxy;

import com.shishire.minecraft.worldmanager.client.renderer.tileentity.TileEntityEndPortalRenderer;
import com.shishire.minecraft.worldmanager.tileentity.TileEntityEndPortal;

import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

public class ClientProxy extends CommonProxy {
	
	@Override
	public void init(FMLInitializationEvent event)
	{
		super.init(event);
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityEndPortal.class, new TileEntityEndPortalRenderer());
	}
}
