/**
 *
 */
package com.shishire.minecraft.worldmanager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.shishire.minecraft.worldmanager.proxy.CommonProxy;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

/**
 * @author user
 *
 */
@Mod(
	modid = WorldManager.MOD_ID,
	version = WorldManager.VERSION,
	name = WorldManager.MOD_NAME)
public class WorldManager
{
	public static final String	MOD_ID		= "worldmanager";
	public static final String	VERSION		= "0.1";
	public static final String	MOD_NAME	= "World Manager";

	@Instance(WorldManager.MOD_ID)
	public static WorldManager	instance;

	public static final Logger	LOG			= LogManager.getLogger(WorldManager.MOD_ID);

	@SidedProxy(
		clientSide = "com.shishire.minecraft.worldmanager.proxy.ClientProxy",
		serverSide = "com.shishire.minecraft.worldmanager.proxy.ServerProxy")
	public static CommonProxy	proxy;

	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		WorldManager.LOG.info("Initializing World Manager.  Hang on to your hats.");
		WorldManager.proxy.init(event);

	}
}
