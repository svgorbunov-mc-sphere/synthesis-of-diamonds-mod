package com.hrb1658.coalcompressor.blocks;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.Constants.NBT;

public class TileEntityCoalCompressor extends TileEntity implements IInventory {

	public static final int MAX_FUEL = 6400;
	
	private ItemStack[] items;
	private int counter;
	private int coalCompacted;
	private boolean burning;
	private int fuel;
	
	public TileEntityCoalCompressor() {
		items = new ItemStack[getSizeInventory()];
		counter = 0;
		coalCompacted = 0;
		fuel = 0;
		burning = false;
	}
	
	public int getFuel() {
		return fuel;
	}
	
	public int getCounter() {
		return counter;
	}
	
	public int getCoalCompacted() {
		return coalCompacted;
	}
	
	public boolean isBurning() {
		return burning;
	}
	
	@Override
	public void updateEntity() {
		if (!burning) {
			counter = 0;
		}
		
		if (items[2] != null && items[2].getItem() == Items.lava_bucket) {
			items[2] = new ItemStack(Items.bucket);
			fuel += 800;
			if (fuel > MAX_FUEL) {
				fuel = MAX_FUEL;
			}
			worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
			markDirty();
		}
		
		ItemStack stack = items[0];
		if (stack != null && stack.stackSize > 0 && hasFuel()) {
			if (!burning) {
				worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, getBlockMetadata() | 8, 2);
			}
			burning = true;
			counter++;
			if (counter == 37) {
				fuel -= counter;
				if (fuel < 0) {
					fuel = 0;
				}
				counter = 0;
				worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
				markDirty();
				stack.stackSize--;
				if (stack.stackSize == 0) {
					items[0] = null;
				}
				coalCompacted++;
				if (coalCompacted == 64) {
					coalCompacted = 0;
					worldObj.playSoundEffect(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5, "random.fizz", 1, 1);
					worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
					markDirty();
					if (items[1] == null) {
						items[1] = new ItemStack(Items.diamond);
					} else {
						items[1].stackSize++;
					}
				}
			}
		} else {
			if (burning) {
				worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, getBlockMetadata() & 7, 2);
			}
			burning = false;
		}
		
		super.updateEntity();
	}
	
	public boolean hasFuel() {
		return fuel > 0;
	}
	
	@Override
	public void writeToNBT(NBTTagCompound tag) {
		super.writeToNBT(tag);
		NBTTagList itemsList = new NBTTagList();
		for (int i = 0;i < items.length;i++) {
			if (items[i] != null) {
				byte slot = (byte)i;
				NBTTagCompound comp = new NBTTagCompound();
				comp.setByte("Slot", slot);
				items[i].writeToNBT(comp);
				itemsList.appendTag(comp);
			}
		}
		tag.setTag("Inventory", itemsList);
		tag.setInteger("Counter", counter);
		tag.setInteger("CoalCompacted", coalCompacted);
		tag.setBoolean("Burning", burning);
		tag.setInteger("Fuel", fuel);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound tag) {
		super.readFromNBT(tag);
		items = new ItemStack[getSizeInventory()];
		
		NBTTagList itemsList = tag.getTagList("Inventory", NBT.TAG_COMPOUND);
		for (int i = 0;i < itemsList.tagCount();i++) {
			NBTTagCompound comp = itemsList.getCompoundTagAt(i);
			byte slot = comp.getByte("Slot");
			ItemStack stack = ItemStack.loadItemStackFromNBT(comp);
			items[slot] = stack;
		}
		counter = tag.getInteger("Counter");
		coalCompacted = tag.getInteger("CoalCompacted");
		burning = tag.getBoolean("Burning");
		fuel = tag.getInteger("Fuel");
	}
	
	@Override
	public Packet getDescriptionPacket() {
		NBTTagCompound tag = new NBTTagCompound();
		writeToNBT(tag);
		return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, getBlockMetadata(), tag);
	}
	
	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
		readFromNBT(pkt.func_148857_g());
	}
	
	@Override
	public int getSizeInventory() {
		return 3;
	}

	@Override
	public ItemStack getStackInSlot(int slot) {
		return items[slot];
	}

	@Override
	public ItemStack decrStackSize(int slot, int number) {
		
		ItemStack current = items[slot];
		ItemStack ret = null;
		
		if (current == null) return null;
		
		if (current.stackSize <= number) {
			ret = current.copy();
			items[slot] = null;
		} else {
			ret = items[slot].splitStack(number);
		}
		
		return ret;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int slot) {
		return items[slot];
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack stack) {
		items[slot] = stack;		
	}

	@Override
	public String getInventoryName() {
		return "CoalCompressor";
	}

	@Override
	public boolean hasCustomInventoryName() {
		return false;
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		return worldObj.getTileEntity(xCoord, yCoord, zCoord) == this && player.getDistanceSq(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5) < 64;
	}

	@Override
	public void openInventory() {
		
	}

	@Override
	public void closeInventory() {
		
	}
	
	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack) {
		if (slot == 0 && stack.getItem() == Items.coal) {
			return true;
		}
		return false;
	}

}
