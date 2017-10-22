//Stolen from https://github.com/maruohon/justenoughdimensions/blob/91b5b4418873a75b7d0c8ed275062c2f113cc0f6/src/main/java/fi/dy/masa/justenoughdimensions/command/CommandTeleportJED.java
package com.shishire.minecraft.dimensionalportals.helper;

import net.minecraft.entity.Entity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.border.WorldBorder;

public class TeleportData {
	private Entity entity;
	private int dimension;
	private double posX;
	private double posY;
	private double posZ;
	private float yaw;
	private float pitch;

	public TeleportData(int dimension, MinecraftServer server)
	{
		this.dimension = dimension;
		this.useSpawn(server);
	}
	
	public TeleportData(Entity entity, int dimension, boolean useSpawn, MinecraftServer server)
	{
		this.entity = entity;
		this.dimension = dimension;
		this.posX = entity.posX;
		this.posY = entity.posY;
		this.posZ = entity.posZ;
		this.yaw = entity.rotationYaw;
		this.pitch = entity.rotationPitch;

		if (useSpawn)
		{
			this.useSpawn(server);
		}
	}

	public TeleportData(int dimension, double x, double y, double z)
	{
		this.dimension = dimension;
		this.posX = x;
		this.posY = y;
		this.posZ = z;
	}
	public TeleportData(Entity entity, int dimension, double x, double y, double z)
	{
		this.entity = entity;
		this.dimension = dimension;
		this.posX = x;
		this.posY = y;
		this.posZ = z;
		this.yaw = entity.rotationYaw;
		this.pitch = entity.rotationPitch;
	}

	public TeleportData(int dimension, double x, double y, double z, float yaw, float pitch)
	{
		this.dimension = dimension;
		this.posX = x;
		this.posY = y;
		this.posZ = z;
		this.yaw = yaw;
		this.pitch = pitch;
	}
	public TeleportData(Entity entity, int dimension, double x, double y, double z, float yaw, float pitch)
	{
		this.entity = entity;
		this.dimension = dimension;
		this.posX = x;
		this.posY = y;
		this.posZ = z;
		this.yaw = yaw;
		this.pitch = pitch;
	}

	public TeleportData(Entity entity, Entity otherEntity)
	{
		this.entity = entity;
		this.dimension = otherEntity.getEntityWorld().provider.getDimension();
		this.posX = otherEntity.posX;
		this.posY = otherEntity.posY;
		this.posZ = otherEntity.posZ;
		this.yaw = otherEntity.rotationYaw;
		this.pitch = otherEntity.rotationPitch;
	}

	private void useSpawn(MinecraftServer server)
	{
		WorldServer world = server.getWorld(this.dimension);

		if (world != null)
		{
			BlockPos spawn = world.getSpawnCoordinate();

			if (spawn != null)
			{
				this.posX = spawn.getX() + 0.5;
				this.posY = spawn.getY();
				this.posZ = spawn.getZ() + 0.5;
			}
		}
	}
	
	public Entity getEntity() { return this.entity;    }
	public int getDimension() { return this.dimension; }
	public double getX()      { return this.posX;      }
	public double getY()      { return this.posY;      }
	public double getZ()      { return this.posZ;      }
	public float getYaw()     { return this.yaw;       }
	public float getPitch()   { return this.pitch;     }
	
	public void setDimension(int dimension) { this.dimension = dimension; }
	public void setX(double x) { this.posX = x; }
	public void setY(double y) { this.posX = y; }
	public void setZ(double z) { this.posX = z; }
	public void setPitch(float pitch) { this.pitch = pitch; }
	public void setYaw(float yaw) { this.yaw = yaw; }

	public Vec3d getPosition(World world)
	{
		return getClampedDestinationPosition(this.posX, this.posY, this.posZ, world);
	}
	
    public static Vec3d getClampedDestinationPosition(double x, double y, double z, World worldDst)
    {
        WorldBorder border = worldDst.getWorldBorder();

        x = MathHelper.clamp(x, border.minX() + 2, border.maxX() - 2);
        z = MathHelper.clamp(z, border.minZ() + 2, border.maxZ() - 2);

        return new Vec3d(x, y, z);
    }
}