package com.shishire.minecraft.worldmanager.proxy;

import com.shishire.minecraft.worldmanager.DimensionHandler;
import com.shishire.minecraft.worldmanager.WorldManager;
import com.shishire.minecraft.worldmanager.blocks.BlockPortal;
import com.shishire.minecraft.worldmanager.tileentity.TileEntityPortal;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.ExistingSubstitutionException;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry.Type;

public class CommonProxy
{
	public void preInit(FMLPreInitializationEvent event)
	{
		MinecraftForge.EVENT_BUS.register(new DimensionHandler());
		
		this.registerBlocks();
	}

	protected void registerBlocks() {
		try
		{
			BlockPortal netherPortal = (BlockPortal) new BlockPortal();
			GameRegistry.register(netherPortal);
			GameRegistry.registerTileEntity(TileEntityPortal.class, WorldManager.MOD_ID + ":portal");
			GameRegistry.addSubstitutionAlias("minecraft:portal", Type.BLOCK, new BlockPortal());
		}
		catch (ExistingSubstitutionException e)
		{
			WorldManager.LOG.warn("Unable to replace Nether Portal.  Nether Portals may behave incorrectly");
		}
	}
}
