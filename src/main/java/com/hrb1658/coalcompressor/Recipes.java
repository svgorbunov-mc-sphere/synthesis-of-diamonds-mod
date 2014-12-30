package com.hrb1658.coalcompressor;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.registry.GameRegistry;

public class Recipes {
	
	public static void initCrafting() {
		GameRegistry.addShapedRecipe(new ItemStack(ModItems.itemCoalCompressor), "IOI", "ORO", "IOI", 'I', Items.iron_ingot, 'O', Blocks.obsidian, 'R', Blocks.redstone_block);
	}
	
	public static void initSmelting() {
		
	}
	
}
