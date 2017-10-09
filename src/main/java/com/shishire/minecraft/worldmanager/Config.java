package com.shishire.minecraft.worldmanager;

import net.minecraftforge.common.config.Configuration;

public class Config
{
	private Configuration configFile;
	
	public Config(Configuration configFile)
	{
		this.configFile = configFile;
		
		this.configFile.load();
	}
}
