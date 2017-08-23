package com.shishire.minecraft.worldmanager.proxy;

import com.shishire.minecraft.worldmanager.DimensionHandler;
import com.shishire.minecraft.worldmanager.WorldManager;
import com.shishire.minecraft.worldmanager.blocks.BlockEndPortal;
import com.shishire.minecraft.worldmanager.blocks.BlockPortal;
import com.shishire.minecraft.worldmanager.blocks.TileEntityPortal;
import com.shishire.minecraft.worldmanager.tileentity.TileEntityEndPortal;

import net.minecraft.block.material.Material;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.registry.ExistingSubstitutionException;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry.Type;

public class CommonProxy
{
	public void init(FMLInitializationEvent event)
	{
		MinecraftForge.EVENT_BUS.register(new DimensionHandler());
		try
		{
			final BlockEndPortal endPortal = (BlockEndPortal) new BlockEndPortal(Material.PORTAL);
			GameRegistry.register(endPortal);
			GameRegistry.register(new ItemBlock(endPortal).setRegistryName(endPortal.getRegistryName()));
			GameRegistry.registerTileEntity(TileEntityEndPortal.class, WorldManager.MOD_ID + ":end_portal");
			GameRegistry.addSubstitutionAlias("minecraft:end_portal", Type.BLOCK, new BlockEndPortal(Material.PORTAL));
		}
		catch (final ExistingSubstitutionException e)
		{
			WorldManager.LOG.warn("Unable to replace End Portal.  End Portals may behave incorrectly");
		}
		try
		{
			final BlockPortal netherPortal = (BlockPortal) new BlockPortal();
			GameRegistry.register(netherPortal);
			GameRegistry.register(new ItemBlock(netherPortal).setRegistryName(netherPortal.getRegistryName()));
			GameRegistry.registerTileEntity(TileEntityPortal.class, WorldManager.MOD_ID + ":portal");
			GameRegistry.addSubstitutionAlias("minecraft:portal", Type.BLOCK, new BlockPortal());
		}
		catch (final ExistingSubstitutionException e)
		{
			WorldManager.LOG.warn("Unable to replace Nether Portal.  Nether Portals may behave incorrectly");
		}
	}
}
