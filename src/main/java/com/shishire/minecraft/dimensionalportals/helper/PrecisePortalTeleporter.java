//Stolen from https://github.com/maruohon/justenoughdimensions/blob/91b5b4418873a75b7d0c8ed275062c2f113cc0f6/src/main/java/fi/dy/masa/justenoughdimensions/command/CommandTeleportJED.java
package com.shishire.minecraft.dimensionalportals.helper;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Teleporter;
import net.minecraft.world.WorldProviderEnd;
import net.minecraft.world.WorldServer;

public class PrecisePortalTeleporter extends Teleporter {

    
    private final WorldServer world;
    private final TeleportData data;


    public PrecisePortalTeleporter(WorldServer worldIn, TeleportData data)
    {
        super(worldIn);

        this.world = worldIn;
        this.data = data;
    }

    @Override
    public boolean makePortal(Entity entityIn)
    {
        return true;
    }

    @Override
    public boolean placeInExistingPortal(Entity entityIn, float rotationYaw)
    {
        Vec3d pos = this.data.getPosition(entityIn.getEntityWorld());
        entityIn.setLocationAndAngles(pos.x, pos.y, pos.z, this.data.getYaw(), this.data.getPitch());
        return true;
    }

    @Override
    public void removeStalePortalLocations(long worldTime)
    {
        // NO-OP
    }

    @Override
    public void placeInPortal(Entity entityIn, float rotationYaw)
    {
        BlockPos spawnCoord = this.world.provider.getSpawnCoordinate();

        // For End type dimensions, generate the platform and place the entity there,
        // UNLESS the world has a different spawn point set.
        if ((this.world.provider.getDimensionType().getId() == 1 || this.world.provider instanceof WorldProviderEnd)
             && this.world.getSpawnPoint().equals(spawnCoord))
        {
            IBlockState obsidian = Blocks.OBSIDIAN.getDefaultState();
            IBlockState air = Blocks.AIR.getDefaultState();
            int spawnX = spawnCoord.getX();
            int spawnY = spawnCoord.getY();
            int spawnZ = spawnCoord.getZ();

            for (int zOff = -2; zOff <= 2; ++zOff)
            {
                for (int xOff = -2; xOff <= 2; ++xOff)
                {
                    for (int yOff = -1; yOff < 3; yOff++)
                    {
                        this.world.setBlockState(new BlockPos(spawnX + xOff, spawnY + yOff, spawnZ + zOff), yOff < 0 ? obsidian : air);
                    }
                }
            }

            entityIn.setLocationAndAngles((double)spawnX, (double)spawnY, (double)spawnZ, entityIn.rotationYaw, 0.0F);
            entityIn.motionX = 0.0D;
            entityIn.motionY = 0.0D;
            entityIn.motionZ = 0.0D;
        }
    }
}
