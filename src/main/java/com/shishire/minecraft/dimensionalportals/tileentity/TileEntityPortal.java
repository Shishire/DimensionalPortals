package com.shishire.minecraft.dimensionalportals.tileentity;

import com.shishire.minecraft.dimensionalportals.helper.TeleportData;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;

public class TileEntityPortal extends TileEntity
{
	private TeleportData destination;
	
	public TileEntityPortal(){
		super();
	}
	
	public TileEntityPortal(int dimension, MinecraftServer server)
	{
		super();
		this.destination = new TeleportData(dimension, server);
	}

	public void setDestinationDimension(Integer dimension)	{ this.destination.setDimension(dimension);	}
	public Integer getDestinationDimension()				{ return this.destination.getDimension();	}
	public void setDestinationX(double x)					{ this.destination.setX(x);					}
	public double getDestinationX()							{ return this.destination.getX();			}
	public void setDestinationY(double y)					{ this.destination.setY(y);					}
	public double getDestinationY()							{ return this.destination.getY();			}
	public void setDestinationZ(double z)					{ this.destination.setZ(z);					}
	public double getDestinationZ()							{ return this.destination.getZ();			}
	public void setDestinationPitch(float pitch)			{ this.destination.setPitch(pitch);			}
	public float getDestinaitonPitch()						{ return this.destination.getPitch();		}
	public void setDestinationYaw(float yaw)				{ this.destination.setYaw(yaw);				}
	public float getDestinationYaw()						{ return this.destination.getYaw();			}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);
		NBTTagCompound destinationData = new NBTTagCompound();
		destinationData.setInteger("Dimension", this.destination.getDimension());
		destinationData.setDouble("X", this.destination.getX());
		destinationData.setDouble("Y", this.destination.getY());
		destinationData.setDouble("Z", this.destination.getZ());
		destinationData.setDouble("Pitch", this.destination.getPitch());
		destinationData.setDouble("Yaw", this.destination.getYaw());
		nbt.setTag("Destination", destinationData);
		return nbt;

	}

	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
		NBTTagCompound destinationData = nbt.getCompoundTag("Destination");
		this.destination = new TeleportData(
				destinationData.getInteger("Dimension"),
				destinationData.getDouble("X"),
				destinationData.getDouble("Y"),
				destinationData.getDouble("Z"),
				destinationData.getFloat("Yaw"),
				destinationData.getFloat("Pitch")
		);
	}
	public TeleportData getDestination() {
		return this.destination;
	}
}
