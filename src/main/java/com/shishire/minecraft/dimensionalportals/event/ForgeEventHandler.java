package com.shishire.minecraft.dimensionalportals.event;

import com.shishire.minecraft.dimensionalportals.DimensionalPortals;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@EventBusSubscriber(modid = DimensionalPortals.MOD_ID)
public class ForgeEventHandler
{	
	@SubscribeEvent
	public static void registerBlocks(Register<Block> event)
	{
		DimensionalPortals.proxy.registerBlocks(event.getRegistry());
	}
	
	@SubscribeEvent
	public static void registerItems(Register<Item> event)
	{
		DimensionalPortals.proxy.registerItems(event.getRegistry());
	}
	
	@SubscribeEvent
	public static void registerModels(ModelRegistryEvent event)
	{
		DimensionalPortals.proxy.registerModels();
	}
}
