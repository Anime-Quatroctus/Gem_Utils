package dev.anime.gems.utils;

import dev.anime.gems.Main;
import dev.anime.gems.items.GemAxe;
import dev.anime.gems.items.GemHoe;
import dev.anime.gems.items.GemPickaxe;
import dev.anime.gems.items.GemShovel;
import dev.anime.gems.items.GemSword;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface ICustomModel {
	
	public int getMaxMeta();
	
	public String getUnlocalizedName(ItemStack stack);
	
	@SideOnly(Side.CLIENT)
	public void handleModelLocations();
	
	@SideOnly(Side.CLIENT)
	public static class GemToolsMeshDefinition implements ItemMeshDefinition {
		
		private static final ModelResourceLocation MODEL_MISSING = new ModelResourceLocation("builtin/missing", "missing");
		
		@Override
		public ModelResourceLocation getModelLocation(ItemStack stack) {
			if (stack.getTagCompound() != null && stack.getTagCompound().hasKey("gem_type")) {
				Item item = stack.getItem();
				if (item instanceof GemPickaxe) {
					return new ModelResourceLocation(Main.MODID + ":" + stack.getTagCompound().getString("gem_type") + "_pickaxe", "inventory");
				} else if (item instanceof GemShovel) {
					return new ModelResourceLocation(Main.MODID + ":" + stack.getTagCompound().getString("gem_type") + "_shovel", "inventory");
				} else if (item instanceof GemAxe) {
					return new ModelResourceLocation(Main.MODID + ":" + stack.getTagCompound().getString("gem_type") + "_axe", "inventory");
				} else if (item instanceof GemHoe) {
					return new ModelResourceLocation(Main.MODID + ":" + stack.getTagCompound().getString("gem_type") + "_hoe", "inventory");
				} else if (item instanceof GemSword) {
					return new ModelResourceLocation(Main.MODID + ":" + stack.getTagCompound().getString("gem_type") + "_sword", "inventory");
//				} else if (item instanceof GemArmor) {
					
				}
			}
			return MODEL_MISSING;
		}
	}
}