package com.hrb1658.coalcompressor.proxy;

import com.hrb1658.coalcompressor.CoalCompressorMod;
import com.hrb1658.coalcompressor.ModBlocks;
import com.hrb1658.coalcompressor.ModItems;
import com.hrb1658.coalcompressor.Recipes;
import com.hrb1658.coalcompressor.gui.GuiHandler;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;

public class Proxy {
	
	public void preInit(FMLPreInitializationEvent event) {
		
	}
	
	public void init(FMLInitializationEvent event) {
		ModBlocks.init();
		ModItems.init();
		
		Recipes.initCrafting();
		Recipes.initSmelting();
		
		NetworkRegistry.INSTANCE.registerGuiHandler(CoalCompressorMod.instance, new GuiHandler());
	}
	
	public void postInit(FMLPostInitializationEvent event) {
		
	}
}
