package com.hrb1658.coalcompressor;

import com.hrb1658.coalcompressor.items.ItemCoalCompressor;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;

public class ModItems {
	
	public static Item itemCoalCompressor;
	
	public static void init() {
		GameRegistry.registerItem(itemCoalCompressor = new ItemCoalCompressor(), "itemCoalCompressor");
	}
}
