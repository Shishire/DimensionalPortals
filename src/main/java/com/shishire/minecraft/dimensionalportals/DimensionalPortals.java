/**
 *
 */
package com.shishire.minecraft.dimensionalportals;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.shishire.minecraft.dimensionalportals.proxy.CommonProxy;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

/**
 * @author user
 *
 */
@Mod(
	modid = DimensionalPortals.MOD_ID,
	version = DimensionalPortals.VERSION,
	name = DimensionalPortals.MOD_NAME)
public class DimensionalPortals
{
	public static final String MOD_ID = "dimensionalportals";
	public static final String VERSION = "0.1";
	public static final String MOD_NAME = "Dimensional Portals";

	@Instance(DimensionalPortals.MOD_ID)
	public static DimensionalPortals	instance;

	public static final Logger logger = LogManager.getLogger(DimensionalPortals.MOD_ID);
	
	public static Config config;

	@SidedProxy(
		clientSide = "com.shishire.minecraft.dimensionalportals.proxy.ClientProxy",
		serverSide = "com.shishire.minecraft.dimensionalportals.proxy.ServerProxy")
	public static CommonProxy	proxy;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		DimensionalPortals.config = new Config(new Configuration(event.getSuggestedConfigurationFile()));

		DimensionalPortals.logger.info("Initializing Dimensional Portals.  Hang on to your hats.");
		DimensionalPortals.proxy.preInit(event);
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		DimensionalPortals.proxy.init(event);
	}
}
