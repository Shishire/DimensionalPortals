package com.shishire.minecraft.dimensionalportals.proxy;

import com.shishire.minecraft.dimensionalportals.DimensionalPortals;
import com.shishire.minecraft.dimensionalportals.blocks.BlockEndPortal;
import com.shishire.minecraft.dimensionalportals.blocks.BlockPortal;
import com.shishire.minecraft.dimensionalportals.init.RegistryHolder;
import com.shishire.minecraft.dimensionalportals.tileentity.TileEntityEndPortal;
import com.shishire.minecraft.dimensionalportals.tileentity.TileEntityPortal;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;

public class CommonProxy
{
	public void registerBlocks(IForgeRegistry<Block> registry)
	{
		BlockPortal netherPortal = (BlockPortal) new BlockPortal();
		registry.register(netherPortal);
		GameRegistry.registerTileEntity(TileEntityPortal.class,DimensionalPortals.MOD_ID + ":portal");
		
		BlockEndPortal endPortal = (BlockEndPortal) new BlockEndPortal();
		registry.register(endPortal);
		GameRegistry.registerTileEntity(TileEntityEndPortal.class, DimensionalPortals.MOD_ID + ":end_portal");
	}
	public void registerItems(IForgeRegistry<Item> registry)
	{
		//registry.register(new ItemBlock(RegistryHolder.blockEndPortal).setRegistryName(RegistryHolder.blockEndPortal.getRegistryName()));
		registry.register(new ItemBlock(RegistryHolder.blockPortal).setRegistryName(RegistryHolder.blockPortal.getRegistryName()));
	}

	public void registerModels() {

	}

	public void preInit(FMLPreInitializationEvent event) {
		
	}
	public void init(FMLInitializationEvent event) {
		
	}
}
