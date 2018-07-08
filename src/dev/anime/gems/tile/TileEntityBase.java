package dev.anime.gems.tile;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.items.ItemStackHandler;

public abstract class TileEntityBase extends TileEntity {
	
	protected ItemStackHandler items;
	
	public ItemStackHandler getItems() {
		return items;
	}
	
	public abstract int getDataMax();
	
	public abstract int getData(int id);
	
	public abstract void setData(int id, int value);
	
}
