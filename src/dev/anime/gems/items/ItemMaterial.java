package dev.anime.gems.items;

import dev.anime.gems.Main;
import dev.anime.gems.blocks.BlockOres.OreType;
import dev.anime.gems.init.ModItems;
import dev.anime.gems.utils.ICustomModel;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.client.model.ModelLoader;

public class ItemMaterial extends Item implements ICustomModel {

	private int extraMeta = 1;
	private static final String[] EXTRA_MATERIALS = new String[] { "shadowy_gem" };
	
	public ItemMaterial() {
		setRegistryName("materials");
		setUnlocalizedName(getRegistryName().toString());
		setMaxDamage(0);
		setHasSubtypes(true);
		setCreativeTab(CreativeTabs.MISC);
		ModItems.ITEMS.add(this);
	}
	
	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
		for (int i = 0; i < getMaxMeta() + extraMeta; i++) items.add(new ItemStack(this, 1, i));
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack) {
		int meta = stack.getMetadata();
		if (meta >= getMaxMeta() + extraMeta) return getUnlocalizedName();
		return getUnlocalizedName() + "_" + (meta < getMaxMeta() ? OreType.values()[stack.getMetadata()].getName() : EXTRA_MATERIALS[meta - getMaxMeta()]);
	}
	
	@Override
	public int getMaxMeta() {
		return OreType.values().length;
	}

	@Override
	public void handleModelLocations() {
		for (int i = 0; i < getMaxMeta(); i++) ModelLoader.setCustomModelResourceLocation(this, i, new ModelResourceLocation(Main.MODID + ":" + OreType.values()[i].getName(), "inventory"));
		for (int i = 0; i < extraMeta; i++) ModelLoader.setCustomModelResourceLocation(this, getMaxMeta() + i, new ModelResourceLocation(Main.MODID + ":" + EXTRA_MATERIALS[i]));
	}
	
}
