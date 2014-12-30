package com.hrb1658.coalcompressor.blocks;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import com.hrb1658.coalcompressor.CoalCompressorMod;
import com.hrb1658.coalcompressor.ModItems;
import com.hrb1658.coalcompressor.Strings;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockCoalCompressor extends BlockContainer {

	private Random random = new Random();
	
	public BlockCoalCompressor() {
		super(Material.rock);
		setHarvestLevel("pickaxe", 3);
		setHardness(10.0f);
		setResistance(6000.0f);
		setBlockName("blockCoalCompressor");
	}

	@Override
	public boolean renderAsNormalBlock() {
		return true;
	}
	
	@Override
	public boolean isOpaqueCube() {
		return true;
	}
	
	@Override
	public int getLightValue(IBlockAccess world, int x, int y, int z) {
		int state = world.getBlockMetadata(x, y, z) >> 3;
		return state == 0 ? 0 : 15;
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float lx, float ly, float lz) {
		if (world.isRemote) {
			return true;
		}
		
		TileEntity te = world.getTileEntity(x, y, z);
		if (te != null && te instanceof TileEntityCoalCompressor) {
			player.openGui(CoalCompressorMod.instance, 0, world, x, y, z);
			return true;
		}
		return false;
	}
	
	@Override
	public Item getItemDropped(int meta, Random rand, int fortune) {
		return ModItems.itemCoalCompressor;
	}
	
	@Override
	public void breakBlock(World world, int x, int y, int z, Block block, int par6) {
		if (world.isRemote) return;
		
		ArrayList<ItemStack> drops = new ArrayList<ItemStack>();
		
		TileEntity tileEntity = world.getTileEntity(x, y, z);
		
		if (tileEntity != null && tileEntity instanceof TileEntityCoalCompressor) {
			TileEntityCoalCompressor tileEntityCoalCompressor = (TileEntityCoalCompressor)tileEntity;
			if (tileEntityCoalCompressor.getCoalCompacted() > 0) {
				world.createExplosion(null, x + 0.5, y + 0.5, z + 0.5, tileEntityCoalCompressor.getCoalCompacted() / 4, true);
			}
			
			for (int i = 0;i < tileEntityCoalCompressor.getSizeInventory();i++) {
				ItemStack stack = tileEntityCoalCompressor.getStackInSlot(i);
				
				if (stack != null) {
					drops.add(stack.copy());
				}
			}
		}
		
		for (int i = 0;i < drops.size();i++) {
			EntityItem ei = new EntityItem(world, x + 0.5, y + 0.5, z + 0.5, drops.get(i));
			ei.setVelocity((random.nextDouble() - 0.5) * 0.25, random.nextDouble() * 0.5 * 0.25, (random.nextDouble() - 0.5) * 0.25);
			world.spawnEntityInWorld(ei);
		}
	}
	
	private IIcon top;
	private IIcon side;
	private IIcon frontOn;
	private IIcon frontOff;
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister reg) {
		side = reg.registerIcon(Strings.MOD_ID + ":" + "coal_compressor_side");
		frontOff = reg.registerIcon(Strings.MOD_ID + ":" + "coal_compressor_front_off");
		frontOn = reg.registerIcon(Strings.MOD_ID + ":" + "coal_compressor_front_on");
		top = reg.registerIcon(Strings.MOD_ID + ":" + "coal_compressor_top");
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int parSide, int parMeta) {
		int facing = parMeta & 7;
		int state = parMeta >> 3;
		
		IIcon front = state == 0 ? frontOff : frontOn;
		
		if (parSide <= 1) {
			return top;
		} else if (facing + 2 == parSide) {
			return front;
		} else {
			return side;
		}
	}
	
	@Override
	public TileEntity createNewTileEntity(World world, int par2) {
		return new TileEntityCoalCompressor();
	}
}
