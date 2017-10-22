package com.shishire.minecraft.dimensionalportals.blocks;

import javax.annotation.Nullable;

import com.shishire.minecraft.dimensionalportals.DimensionalPortals;
import com.shishire.minecraft.dimensionalportals.helper.TeleportationHandler;
import com.shishire.minecraft.dimensionalportals.tileentity.TileEntityPortal;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockPortal extends Block implements ITileEntityProvider
{

	public BlockPortal()
	{
		super(Material.PORTAL);
		this.setLightLevel(0.75F);
		this.setRegistryName("portal");
		this.setCreativeTab(CreativeTabs.MISC);
		this.setUnlocalizedName(DimensionalPortals.MOD_ID + ".portal");
		this.setHardness(-1.0F);
		this.setSoundType(SoundType.GLASS);
	}
	
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		TileEntityPortal tileEntityPortal = null;
		if (!worldIn.isRemote)
		{
			tileEntityPortal = new TileEntityPortal(0,worldIn.getMinecraftServer());
		}

		return tileEntityPortal;
	}
	
    /**
     * Called When an Entity Collided with the Block
     */
    public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn)
    {
    	 if (!worldIn.isRemote && !entityIn.isRiding() && !entityIn.isBeingRidden() && entityIn.isNonBoss() && entityIn.getEntityBoundingBox().intersects(state.getBoundingBox(worldIn, pos).offset(pos)))
 		{
 			TileEntity tileEntityIn = worldIn.getTileEntity(pos);
 			
 			if (tileEntityIn instanceof TileEntityPortal)
 			{
 				TileEntityPortal tileEntityPortal = (TileEntityPortal) tileEntityIn;
 				
 				TeleportationHandler teleportHandler = new TeleportationHandler();
 				teleportHandler.teleportEntityToLocation(entityIn, tileEntityPortal.getDestination(), worldIn.getMinecraftServer());
 			}
 			else
 			{
 				DimensionalPortals.logger.error(entityIn.getName() + " interacted with " + state.getBlock().getUnlocalizedName()
 					+ " but " + tileEntityIn.getClass().toString() + " is not " + TileEntityPortal.class.toString());
 			}

 		}
    }
    /**
     * Used to determine ambient occlusion and culling when rebuilding chunks for render
     */
    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }

    @SuppressWarnings("deprecation")
	@SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side)
    {
        IBlockState iblockstate = blockAccess.getBlockState(pos.offset(side));
        Block block = iblockstate.getBlock();

        return block == this ? false : super.shouldSideBeRendered(blockState, blockAccess, pos, side);
    }

    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer()
    {
        return BlockRenderLayer.TRANSLUCENT;
    }

    @Nullable
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos)
    {
        return NULL_AABB;
    }
    
    public boolean isFullCube(IBlockState state)
    {
        return false;
    }
}
