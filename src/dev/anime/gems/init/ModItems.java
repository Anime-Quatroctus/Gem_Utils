package dev.anime.gems.init;

import java.util.ArrayList;
import java.util.List;

import dev.anime.gems.items.GemAxe;
import dev.anime.gems.items.GemHoe;
import dev.anime.gems.items.GemPickaxe;
import dev.anime.gems.items.GemShovel;
import dev.anime.gems.items.GemSword;
import dev.anime.gems.items.ItemMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraftforge.common.util.EnumHelper;

public class ModItems {
	
	public static List<Item> ITEMS = new ArrayList<Item>();
	
	public static Item MATERIALS;
	
	// Power Core, Power Receiver, Power Transceiver
	public static Item POWER_COMPONENTS;
	
	// Hammer, etc.
	public static Item HAND_TOOLS;
	
	public static Item SWORDS;
	public static Item PICKAXES;
	public static Item SHOVELS;
	public static Item AXES;
	public static Item HOES;
	
	private static final ToolMaterial BASIC = EnumHelper.addToolMaterial("basic", 2, 512, 7, 2.5F, 12);
	
	public static final void init() {
		MATERIALS = new ItemMaterial();
		
		SWORDS = new GemSword(BASIC);
		PICKAXES = new GemPickaxe(BASIC);
		SHOVELS = new GemShovel(BASIC);
		AXES = new GemAxe(BASIC);
		HOES= new GemHoe(BASIC);
	}
	
}