package dev.anime.gems.utils;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface IMetaModel {
	
	public int getMaxMeta();
	
	public String getUnlocalizedName(ItemStack stack);
	
	@SideOnly(Side.CLIENT)
	public void handleModelLocations();
	
}