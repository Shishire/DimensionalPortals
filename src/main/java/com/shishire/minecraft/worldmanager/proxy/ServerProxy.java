package com.shishire.minecraft.worldmanager.proxy;

import com.shishire.minecraft.worldmanager.WorldManager;
import com.shishire.minecraft.worldmanager.blocks.BlockEndPortal;
import com.shishire.minecraft.worldmanager.tileentity.TileEntityEndPortal;

import net.minecraft.block.material.Material;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.ExistingSubstitutionException;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry.Type;

public class ServerProxy extends CommonProxy
{

	@Override
	public void preInit(FMLPreInitializationEvent event)
	{
		super.preInit(event);
	}
	
	@Override
	protected void registerBlocks()
	{
		super.registerBlocks();
		try
		{
			BlockEndPortal endPortal = (BlockEndPortal) new BlockEndPortal(Material.PORTAL);
			GameRegistry.register(endPortal);
			GameRegistry.registerTileEntity(TileEntityEndPortal.class, WorldManager.MOD_ID + ":end_portal");
			GameRegistry.addSubstitutionAlias("minecraft:end_portal", Type.BLOCK, new BlockEndPortal(Material.PORTAL));
		}
		catch (ExistingSubstitutionException e)
		{
			WorldManager.LOG.warn("Unable to replace End Portal.  End Portals may behave incorrectly");
		}
	}
}
