package com.shishire.minecraft.dimensionalportals.proxy;

import com.shishire.minecraft.dimensionalportals.client.renderer.block.statemap.NullStateMapper;
import com.shishire.minecraft.dimensionalportals.init.RegistryHolder;
import net.minecraftforge.client.model.ModelLoader;

public class ClientProxy extends CommonProxy
{	
	@Override
	public void registerModels()
	{
		NullStateMapper nullMapper = new NullStateMapper();
		ModelLoader.setCustomStateMapper(RegistryHolder.blockEndPortal, nullMapper);
	}
}
