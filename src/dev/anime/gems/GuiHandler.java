package dev.anime.gems;

import dev.anime.gems.inventory.container.ContainerShardCompressor;
import dev.anime.gems.inventory.gui.GuiShardCompressor;
import dev.anime.gems.tile.TileEntityShardCompressor;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {
	
	public static final int COMPRESSOR_ID = 0, ANGELIC_ID = 1;
	
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		BlockPos pos = new BlockPos(x, y, z);
		TileEntity te = world.getTileEntity(pos);
		InventoryPlayer inventory = player.inventory;
		switch (ID) {
			case COMPRESSOR_ID: return new ContainerShardCompressor((TileEntityShardCompressor) te, inventory);
			
			default: return null;
		}
	}
	
	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		BlockPos pos = new BlockPos(x, y, z);
		TileEntity te = world.getTileEntity(pos);
		InventoryPlayer inventory = player.inventory;
		switch (ID) {
			case COMPRESSOR_ID: return new GuiShardCompressor((TileEntityShardCompressor) te, inventory);
			
			default: return null;
		}
	}
	
}