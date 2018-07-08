package dev.anime.gems.inventory.gui;

import dev.anime.gems.inventory.container.ContainerShardCompressor;
import dev.anime.gems.tile.TileEntityShardCompressor;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GuiShardCompressor extends GuiContainer {

	private static final ResourceLocation FURNACE_GUI_TEXTURES = new ResourceLocation("textures/gui/container/furnace.png");
	
	private TileEntityShardCompressor te;
	private InventoryPlayer inventory;
	
	public GuiShardCompressor(TileEntityShardCompressor te, InventoryPlayer inventory) {
		super(new ContainerShardCompressor(te, inventory));
		this.te = te;
		this.inventory = inventory;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		String s = "Shard Compressor";
        this.fontRenderer.drawString(s, this.xSize / 2 - this.fontRenderer.getStringWidth(s) / 2, 6, 4210752);
        this.fontRenderer.drawString(this.inventory.getDisplayName().getUnformattedText(), 8, this.ySize - 96 + 2, 4210752);
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		mc.getTextureManager().bindTexture(FURNACE_GUI_TEXTURES);
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);
        
        if (te.getData(0) > 0) {
            int k = this.getBurnLeftScaled(13);
            this.drawTexturedModalRect(i + 56, j + 36 + 12 - k, 176, 12 - k, 14, k + 1);
        }
        int l = this.getCookProgressScaled(24);
        this.drawTexturedModalRect(i + 79, j + 34, 176, 14, l + 1, 16);
	}

    private int getCookProgressScaled(int pixels) {
        int j = this.te.getData(1);
        return j != 0 ? j * pixels / TileEntityShardCompressor.FINISH_PROCESS_TIME : 0;
    }
	
	private int getBurnLeftScaled(int pixels) {
        return this.te.getData(0) * pixels / TileEntityShardCompressor.FINISH_PROCESS_TIME;
    }
	
}
