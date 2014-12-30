package com.hrb1658.coalcompressor.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import com.hrb1658.coalcompressor.Strings;
import com.hrb1658.coalcompressor.blocks.TileEntityCoalCompressor;

public class GuiCoalCompressor extends GuiContainer {

	private ResourceLocation texture = new ResourceLocation(Strings.MOD_ID, "textures/gui/container/coal_compressor.png");
	private TileEntityCoalCompressor te;
	
	public GuiCoalCompressor(TileEntityCoalCompressor te, EntityPlayer player) {
		super(new ContainerCoalCompressor(te, player));
		this.te = te;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
		Minecraft.getMinecraft().renderEngine.bindTexture(texture);
		
		GL11.glColor4f(1, 1, 1, 1);
		
		int x = (width - xSize) / 2;
		int y = (height - ySize) / 2;
		
		drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int par1, int par2) {
		Minecraft.getMinecraft().renderEngine.bindTexture(texture);
		
		GL11.glColor4f(1, 1, 1, 1);
		
		int x = (width - xSize) / 2;
		int y = (height - ySize) / 2;
		
		float counter = te.getCounter() / 37.0f;
		int counterWidth = (int) (80 * counter);
		drawTexturedModalRect(48, 34, 176, 10, counterWidth, 2);
		
		float coalCompacted = te.getCoalCompacted() / 64.0f;
		int coalCompactedWidth = (int) (80 * coalCompacted);
		drawTexturedModalRect(48, 43, 176, 0, coalCompactedWidth, 7);
		
		float fuelPercentage = (float)te.getFuel() / (float)TileEntityCoalCompressor.MAX_FUEL;
		int fuelHeight = (int) (fuelPercentage * 18);
		drawTexturedModalRect(104, 59 + 18 - fuelHeight, 176, 14 + 18 - fuelHeight, 50, fuelHeight);
		
		drawCenteredString(fontRendererObj, "Coal Compressor", xSize / 2, 5, 0xFFFFFF);
		
		int totalSeconds = -((te.getCounter() + te.getCoalCompacted() * 37) / 20) + 120;
		int minutes = totalSeconds / 60;
		int seconds = totalSeconds % 60;
		
		if (te.isBurning()) {
			drawString(fontRendererObj, "Time: " + minutes + ":" + String.format("%02d", seconds), 10, 64, 0xFFFFFF);
		}
	}
}
