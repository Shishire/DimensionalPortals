package com.shishire.minecraft.dimensionalportals.init;

import com.shishire.minecraft.dimensionalportals.DimensionalPortals;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;

@ObjectHolder(DimensionalPortals.MOD_ID)
public class RegistryHolder {
	@ObjectHolder("end_portal")
	public static final Block blockEndPortal = null;
	@ObjectHolder("portal")
	public static final Block blockPortal = null;

	@ObjectHolder("end_portal")
	public static final Item itemEndPortal = null;
	@ObjectHolder("portal")
	public static final Item itemPortal = null;
}
