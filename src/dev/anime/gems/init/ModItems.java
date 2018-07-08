package dev.anime.gems.init;

import java.util.ArrayList;
import java.util.List;

import dev.anime.gems.items.ItemMaterial;
import net.minecraft.item.Item;

public class ModItems {
	
	public static List<Item> ITEMS = new ArrayList<Item>();
	
	public static Item MATERIALS;
	
	public static final void init() {
		MATERIALS = new ItemMaterial();
	}
	
}