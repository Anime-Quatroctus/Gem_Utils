package dev.anime.gems.utils;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public final class ItemStackHelper {
	
	public static final boolean matches(ItemStack stack, Item item) {
		return stack.getItem() == item;
	}
	
	public static boolean matches(ItemStack stack, Item item, int meta) {
		return stack.getItem() == item && meta == stack.getMetadata();
	}
	
	public static boolean matches(ItemStack stack, Item item, int meta, NBTTagCompound tag) {
		boolean nbt = stack.getTagCompound() != null && tag != null;
		return stack.getItem() == item && stack.getMetadata() == meta && nbt ? stack.getTagCompound().equals(tag) : true;
	}
	
	public static boolean matches(ItemStack initial, ItemStack secondary) {
		return matches(initial, secondary.getItem(), secondary.getMetadata(), secondary.getTagCompound()) && initial.areCapsCompatible(secondary);
	}
	
	public static ItemStack createNBTItem(ItemStack stack, String type) {
		if (stack.getTagCompound() == null) stack.setTagCompound(new NBTTagCompound());
		stack.getTagCompound().setString("gem_type", type);
		return stack;
	}
	
}
