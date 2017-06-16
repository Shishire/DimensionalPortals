package com.shishire.minecraft.worldmanager.blocks;

import com.shishire.minecraft.worldmanager.WorldManager;
import com.shishire.minecraft.worldmanager.tileentity.TileEntityEndPortal;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockEndPortal extends net.minecraft.block.BlockEndPortal {

	public BlockEndPortal(Material materialIn) {
		super(materialIn);
		this.setRegistryName("end_portal");
		this.setCreativeTab(CreativeTabs.MISC);
	}
    public TileEntity createNewTileEntity(World worldIn, int meta)
    {
    	TileEntityEndPortal tileEntityEndPortal = new TileEntityEndPortal();
    	tileEntityEndPortal.setDimension(1);
    	
        return tileEntityEndPortal;
    }
    public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn)
    {
        if (!entityIn.isRiding() && !entityIn.isBeingRidden() && entityIn.isNonBoss() && !worldIn.isRemote && entityIn.getEntityBoundingBox().intersectsWith(state.getBoundingBox(worldIn, pos).offset(pos)))
        {
            //entityIn.changeDimension(1);
        	TileEntity tileEntityIn = worldIn.getTileEntity(pos);
        	if(tileEntityIn instanceof TileEntityEndPortal)
        	{
        		TileEntityEndPortal tileEntityEndPortal = (TileEntityEndPortal)tileEntityIn;
        		entityIn.changeDimension(tileEntityEndPortal.getDimension());
        		WorldManager.LOG.info("Moving " + entityIn.getName() + " to dimension " + tileEntityEndPortal.getDimension());
        	}
        	//WorldManager.LOG.info("No dimension hopping with WorldManager. Muahahaha!");
        	
        }
    }
}
