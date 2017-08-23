package com.shishire.minecraft.worldmanager.proxy;

import com.shishire.minecraft.worldmanager.DimensionHandler;
import com.shishire.minecraft.worldmanager.WorldManager;
import com.shishire.minecraft.worldmanager.blocks.BlockEndPortal;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.registry.ExistingSubstitutionException;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry.Type;

public class CommonProxy {
	public void init(FMLInitializationEvent event)
	{
		MinecraftForge.EVENT_BUS.register(new DimensionHandler());
		try {
			BlockEndPortal endPortal = (BlockEndPortal) new BlockEndPortal(Material.PORTAL).setHardness(-1.0F).setResistance(6000000.0F);
			GameRegistry.register(endPortal);
			GameRegistry.register(new ItemBlock(endPortal).setRegistryName(endPortal.getRegistryName()));
			GameRegistry.addSubstitutionAlias("minecraft:end_portal", Type.BLOCK, new BlockEndPortal(Material.PORTAL));
		} catch (ExistingSubstitutionException e) {
			WorldManager.LOG.warn("Unable to replace End Portal.  End Portals may behave incorrectly");
		}
	}
}
