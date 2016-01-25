package com.hrb1658.coalcompressor.items;

import com.hrb1658.coalcompressor.ModBlocks;
import com.hrb1658.coalcompressor.Strings;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class ItemCoalCompressor extends Item {
	
	public ItemCoalCompressor() {
		setCreativeTab(CreativeTabs.tabMisc);
		setUnlocalizedName("itemCoalCompressor");
		setTextureName(Strings.MOD_ID + ":" + "coal_compressor");
		setMaxStackSize(1);
	}
	
	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float lx, float ly, float lz) {
		if (side == 0) {
			y--;
		}else if (side == 1) {
			y++;
		}else if (side == 2) {
			z--;
		}else if (side == 3) {
			z++;
		}else if (side == 4) {
			x--;
		}else if (side == 5) {
			x++;
		}
		
		if (player.canPlayerEdit(x, y, z, side, stack)) {
			int dir = MathHelper.floor_double((double)((player.rotationYaw + 180) * 4.0F / 360.0F) + 0.5D) & 3;
			if (dir == 0) dir = 1;
			else if (dir == 1) dir = 2;
			else if (dir == 2) dir = 0;
			else dir = 3;
			
			stack.stackSize--;
			world.setBlock(x, y, z, ModBlocks.blockCoalCompressor);
			world.setBlockMetadataWithNotify(x, y, z, dir, 2);
			if (!world.isRemote) {
				world.playSoundEffect(x + 0.5, y + 0.5, z + 0.5, Block.soundTypeStone.func_150496_b(), 1, 1);
			}
			return true;
		}
		return false;
	}
}
