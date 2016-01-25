package com.hrb1658.coalcompressor.gui;

import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotCoalCompressor extends Slot {

	public SlotCoalCompressor(IInventory inventory, int slot, int x, int y) {
		super(inventory, slot, x, y);
	}

	@Override
	public boolean isItemValid(ItemStack stack) {
		if (slotNumber == 0 && stack.getItem() == Items.coal) {
			return true;
		}
		if (slotNumber == 2 && stack.getItem() == Items.lava_bucket) {
			return true;
		}
		return false;
	}
}
