package com.shishire.minecraft.worldmanager.tileentity;

import net.minecraft.nbt.NBTTagCompound;

public class TileEntityEndPortal extends net.minecraft.tileentity.TileEntityEndPortal {
	private Integer dimension;

	public void setDimension(Integer dimension)
	{
		this.dimension = dimension;
	}
	public Integer getDimension()
	{
		return this.dimension;
	}
	public NBTTagCompound writeToNBT(NBTTagCompound nbt)
    {
		nbt.setInteger("Dimension", this.dimension);
		return nbt;
		
    }
	public void readDataFromNBT(NBTTagCompound nbt)
    {
		this.dimension = nbt.getInteger("Dimension");
    }
}
