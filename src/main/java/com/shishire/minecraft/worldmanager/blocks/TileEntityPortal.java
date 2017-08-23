package com.shishire.minecraft.worldmanager.blocks;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public class TileEntityPortal extends TileEntity
{
	private Integer dimension;

	public void setDimension(Integer dimension)
	{
		this.dimension = dimension;
	}

	public Integer getDimension()
	{
		return this.dimension;
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);
		nbt.setInteger("Dimension", this.dimension);
		return nbt;

	}

	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
		this.dimension = nbt.getInteger("Dimension");
	}
}
