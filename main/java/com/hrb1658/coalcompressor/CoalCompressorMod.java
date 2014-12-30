package com.hrb1658.coalcompressor;

import com.hrb1658.coalcompressor.proxy.Proxy;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Strings.MOD_ID, name = Strings.MOD_NAME, version = Strings.MOD_VERSION)
public class CoalCompressorMod {
	
	@Instance
	public static CoalCompressorMod instance;
	
	@SidedProxy(clientSide = "com.hrb1658.coalcompressor.proxy.ClientProxy", serverSide = "com.hrb1658.coalcompressor.proxy.ServerProxy")
	public static Proxy proxy;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		proxy.preInit(event);
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event) {
		proxy.init(event);
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		proxy.postInit(event);
	}
}
