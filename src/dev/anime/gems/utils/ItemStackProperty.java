package dev.anime.gems.utils;

import net.minecraft.item.ItemStack;
import net.minecraftforge.common.property.IUnlistedProperty;

public class ItemStackProperty implements IUnlistedProperty<ItemStack> {
	
	public static final ItemStackProperty ITEMSTACK = new ItemStackProperty();
	
	private ItemStack value;
	
	@Override
	public String getName() {
		return "stack";
	}

	@Override
	public boolean isValid(ItemStack value) {
		return value instanceof ItemStack;
	}

	@Override
	@SuppressWarnings("unchecked")
	public Class<ItemStack> getType() {
		return (Class<ItemStack>)value.getClass();
	}

	@Override
	public String valueToString(ItemStack value) {
		return value.getItem().getRegistryName().toString();
	}

}
