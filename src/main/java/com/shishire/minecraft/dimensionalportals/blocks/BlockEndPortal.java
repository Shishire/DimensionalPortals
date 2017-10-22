package com.shishire.minecraft.dimensionalportals.blocks;

import com.shishire.minecraft.dimensionalportals.DimensionalPortals;
import com.shishire.minecraft.dimensionalportals.helper.TeleportationHandler;
import com.shishire.minecraft.dimensionalportals.tileentity.TileEntityEndPortal;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockEndPortal extends net.minecraft.block.BlockEndPortal
{

	public BlockEndPortal()
	{
		super(Material.PORTAL);
		this.setLightLevel(1.0F);
		this.setRegistryName("end_portal");
		this.setCreativeTab(CreativeTabs.MISC);
		this.setUnlocalizedName(DimensionalPortals.MOD_ID + ".end_portal");
		this.setHardness(-1.0F);
		this.setResistance(6000000.0F);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		TileEntityEndPortal tileEntityEndPortal = null;
		if (!worldIn.isRemote)
		{
			tileEntityEndPortal = new TileEntityEndPortal(0,worldIn.getMinecraftServer());
		}

		return tileEntityEndPortal;
	}

	@Override
	public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn)
	{
        if (!worldIn.isRemote && !entityIn.isRiding() && !entityIn.isBeingRidden() && entityIn.isNonBoss() && entityIn.getEntityBoundingBox().intersects(state.getBoundingBox(worldIn, pos).offset(pos)))
		{
			TileEntity tileEntityIn = worldIn.getTileEntity(pos);
			
			if (tileEntityIn instanceof TileEntityEndPortal)
			{
				TileEntityEndPortal tileEntityEndPortal = (TileEntityEndPortal) tileEntityIn;
				
				TeleportationHandler teleportHandler = new TeleportationHandler();
				teleportHandler.teleportEntityToLocation(entityIn, tileEntityEndPortal.getDestination(), worldIn.getMinecraftServer());
			}
			else
			{
				DimensionalPortals.logger.error(entityIn.getName() + " interacted with " + state.getBlock().getUnlocalizedName()
					+ " but " + tileEntityIn.getClass().toString() + " is not " + TileEntityEndPortal.class.toString());
			}

			//DimensionalPortals.LOG.info("No dimension hopping with DimensionalPortals. Muahahaha!");
		}
	}
}
