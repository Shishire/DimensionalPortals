package com.shishire.minecraft.worldmanager;

import net.minecraftforge.event.entity.EntityTravelToDimensionEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class DimensionHandler {

		@SubscribeEvent()
		public void onEntityTravelToDimension(EntityTravelToDimensionEvent event)
		{
			//WorldManager.LOG.info("Preventing " + event.getEntity() + " from traveling to Dimension " + event.getDimension());
			//event.setCanceled(true);
		}
}
