package dev.anime.gems.utils;

import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.items.ItemStackHandler;

public class RecipeItemStackHandler extends ItemStackHandler {
	
	private int inStart, inLast;
	
	public RecipeItemStackHandler(int size, int inStart, int inLast) {
		super(size);
		this.inStart = inStart;
		this.inLast = inLast;
	}
	
	public RecipeItemStackHandler(NonNullList<ItemStack> items, int inStart, int inLast) {
		super(items);
		this.inStart = inStart;
		this.inLast = inLast;
	}
	
	public ItemStack[] getItems() {
		return stacks.subList(inStart, inLast + 1).toArray(new ItemStack[] {});
	}
	
}
