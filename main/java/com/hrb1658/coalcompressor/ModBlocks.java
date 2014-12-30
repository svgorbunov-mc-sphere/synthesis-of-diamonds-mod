package com.hrb1658.coalcompressor;

import net.minecraft.block.Block;

import com.hrb1658.coalcompressor.blocks.BlockCoalCompressor;
import com.hrb1658.coalcompressor.blocks.TileEntityCoalCompressor;

import cpw.mods.fml.common.registry.GameRegistry;

public class ModBlocks {
	
	public static Block blockCoalCompressor;
	
	public static void init() {
		GameRegistry.registerBlock(blockCoalCompressor = new BlockCoalCompressor(), "blockCoalCompressor");
		
		GameRegistry.registerTileEntity(TileEntityCoalCompressor.class, "tileEntityCoalCompressor");
	}
}
