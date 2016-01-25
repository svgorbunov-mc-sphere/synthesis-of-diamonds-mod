package com.hrb1658.coalcompressor.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import com.hrb1658.coalcompressor.blocks.TileEntityCoalCompressor;

public class ContainerCoalCompressor extends Container {

	private TileEntityCoalCompressor tileEntity;

	public ContainerCoalCompressor(TileEntityCoalCompressor te, EntityPlayer player) {
		tileEntity = te;
		
		addSlotToContainer(new SlotCoalCompressor(tileEntity, 0, 26, 27));
		addSlotToContainer(new SlotCoalCompressor(tileEntity, 1, 134, 27));
		addSlotToContainer(new SlotCoalCompressor(tileEntity, 2, 80, 60));

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 9; j++) {
				addSlotToContainer(new Slot(player.inventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
			}
		}
		
		for (int i = 0; i < 9; i++) {
			addSlotToContainer(new Slot(player.inventory, i, 8 + i * 18, 142));
		}
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slot) {
		return null;
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return tileEntity.isUseableByPlayer(player);
	}
}
