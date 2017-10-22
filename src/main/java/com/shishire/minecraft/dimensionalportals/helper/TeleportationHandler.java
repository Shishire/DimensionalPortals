//Stolen from https://github.com/maruohon/justenoughdimensions/blob/91b5b4418873a75b7d0c8ed275062c2f113cc0f6/src/main/java/fi/dy/masa/justenoughdimensions/command/CommandTeleportJED.java
package com.shishire.minecraft.dimensionalportals.helper;

import java.lang.invoke.MethodHandle;
import java.lang.reflect.Field;

import com.shishire.minecraft.dimensionalportals.DimensionalPortals;
import com.shishire.minecraft.dimensionalportals.util.MethodHandleUtils;
import com.shishire.minecraft.dimensionalportals.util.MethodHandleUtils.UnableToFindMethodHandleException;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.BossInfoServer;
import net.minecraft.world.World;
import net.minecraft.world.WorldProviderEnd;
import net.minecraft.world.WorldServer;
import net.minecraft.world.border.WorldBorder;
import net.minecraft.world.end.DragonFightManager;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import net.minecraftforge.fml.relauncher.ReflectionHelper.UnableToAccessFieldException;

public class TeleportationHandler {
    private MethodHandle methodHandle_Entity_copyDataFromOld;
    
    public TeleportationHandler()
    {
    	try
        {
            this.methodHandle_Entity_copyDataFromOld = MethodHandleUtils.getMethodHandleVirtual(
                    Entity.class, new String[] { "func_180432_n", "copyDataFromOld" }, Entity.class);
        }
        catch (UnableToFindMethodHandleException e)
        {
            DimensionalPortals.logger.error(this.getClass().getName() + ": Failed to get MethodHandle for Entity#copyDataFromOld()", e);
        }
    }


    public Entity teleportEntityToLocation(Entity entity, TeleportData data, MinecraftServer server)
    {
        // TODO hook up the mounted entity TP code from Ender Utilities?
        entity.dismountRidingEntity();
        entity.removePassengers();
        
        // Don't trigger EntityPlayerMP movement distance check.  We're teleporting, this isn't a cheater.
        if (entity instanceof EntityPlayerMP)
        {
        	Field f;
			try {
				f = EntityPlayerMP.class.getDeclaredField("invulnerableDimensionChange");
	        	f.setAccessible(true);
	        	f.setBoolean(entity, true);
			} catch (NoSuchFieldException e) {
				DimensionalPortals.logger.warn("Can't modify EntityPlayerMP to allow teleportation.");
				e.printStackTrace();
			} catch (SecurityException e) {
				DimensionalPortals.logger.warn("Can't modify EntityPlayerMP to allow teleportation.");
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				DimensionalPortals.logger.warn("Can't modify EntityPlayerMP to allow teleportation.");
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				DimensionalPortals.logger.warn("Can't modify EntityPlayerMP to allow teleportation.");
				e.printStackTrace();
			}
        }

        if (entity.getEntityWorld().provider.getDimension() != data.getDimension())
        {
            return this.teleportEntityToDimension(entity, data, server);
        }
        else
        {
            return this.teleportEntityInsideSameDimension(entity, data);
        }
    }

    private Entity teleportEntityInsideSameDimension(Entity entity, TeleportData data)
    {
        Vec3d pos = data.getPosition(entity.getEntityWorld());

        // Load the chunk first
        entity.getEntityWorld().getChunkFromChunkCoords((int) Math.floor(pos.x / 16D), (int) Math.floor(pos.z / 16D));

        entity.setLocationAndAngles(pos.x, pos.y, pos.z, data.getYaw(), data.getPitch());
        entity.setPositionAndUpdate(pos.x, pos.y, pos.z);
        return entity;
    }

    private Entity teleportEntityToDimension(Entity entity, TeleportData data, MinecraftServer server)
    {
        WorldServer worldDst = server.getWorld(data.getDimension());

        Vec3d pos = data.getPosition(worldDst);
        double x = pos.x;
        double y = pos.y;
        double z = pos.z;

        // Load the chunk first
        worldDst.getChunkFromChunkCoords((int) Math.floor(x / 16D), (int) Math.floor(z / 16D));

        if (entity instanceof EntityPlayerMP)
        {
            EntityPlayerMP player = (EntityPlayerMP) entity;
            World worldOld = player.getEntityWorld();
            PrecisePortalTeleporter teleporter = new PrecisePortalTeleporter(worldDst, data);

            player.setLocationAndAngles(x, y, z, player.rotationYaw, player.rotationPitch);
            server.getPlayerList().transferPlayerToDimension(player, data.getDimension(), teleporter);

            // See PlayerList#transferEntityToWorld()
            if (worldOld.provider.getDimension() == 1)
            {
                worldDst.spawnEntity(player);
            }

            // Teleporting FROM The End
            if (worldOld.provider instanceof WorldProviderEnd)
            {
                this.removeDragonBossBarHack(player, (WorldProviderEnd) worldOld.provider);
            }

            player.setPositionAndUpdate(x, y, z);
            worldDst.updateEntityWithOptionalForce(player, false);
            player.addExperience(0);
            player.setPlayerHealthUpdated();
        }
        else
        {
            WorldServer worldSrc = (WorldServer) entity.getEntityWorld();

            worldSrc.removeEntity(entity);
            entity.isDead = false;
            worldSrc.updateEntityWithOptionalForce(entity, false);

            Entity entityNew = EntityList.newEntity(entity.getClass(), worldDst);

            if (entityNew != null)
            {
                this.copyDataFromOld(entityNew, entity);
                entityNew.setLocationAndAngles(x, y, z, data.getYaw(), data.getPitch());

                boolean flag = entityNew.forceSpawn;
                entityNew.forceSpawn = true;
                worldDst.spawnEntity(entityNew);
                entityNew.forceSpawn = flag;

                worldDst.updateEntityWithOptionalForce(entityNew, false);
                entity.isDead = true;

                worldSrc.resetUpdateEntityTick();
                worldDst.resetUpdateEntityTick();
            }

            entity = entityNew;
        }

        return entity;
    }

    public static Vec3d getClampedDestinationPosition(Vec3d posIn, World worldDst)
    {
        return getClampedDestinationPosition(posIn.x, posIn.y, posIn.z, worldDst);
    }

    public static Vec3d getClampedDestinationPosition(double x, double y, double z, World worldDst)
    {
        WorldBorder border = worldDst.getWorldBorder();

        x = MathHelper.clamp(x, border.minX() + 2, border.maxX() - 2);
        z = MathHelper.clamp(z, border.minZ() + 2, border.maxZ() - 2);

        return new Vec3d(x, y, z);
    }

    private void removeDragonBossBarHack(EntityPlayerMP player, WorldProviderEnd provider)
    {
        // FIXME 1.9 - Somewhat ugly way to clear the Boss Info stuff when teleporting FROM The End
        DragonFightManager manager = provider.getDragonFightManager();

        if (manager != null)
        {
            try
            {
                BossInfoServer bossInfo = ReflectionHelper.getPrivateValue(DragonFightManager.class, manager, "field_186109_c", "bossInfo");
                if (bossInfo != null)
                {
                    bossInfo.removePlayer(player);
                }
            }
            catch (UnableToAccessFieldException e)
            {
                DimensionalPortals.logger.warn(this.getClass().getName() + ": Failed to get DragonFightManager#bossInfo");
            }
        }
    }

    private void copyDataFromOld(Entity target, Entity old)
    {
        try
        {
            this.methodHandle_Entity_copyDataFromOld.invokeExact(target, old);
        }
        catch (Throwable e)
        {
        	DimensionalPortals.logger.error("Error while trying invoke Entity#copyDataFromOld()", e);
        }
    }
}
