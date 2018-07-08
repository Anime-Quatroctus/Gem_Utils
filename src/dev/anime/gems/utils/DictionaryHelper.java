package dev.anime.gems.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.Level;

import dev.anime.gems.Main;
import dev.anime.gems.blocks.BlockOres;
import dev.anime.gems.blocks.BlockOres.OreType;
import dev.anime.gems.init.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class DictionaryHelper {
	
	public static void registerOresToDictionary() {
		for (int meta = 0; meta < ((BlockOres)ModBlocks.ORES).getMaxMeta(); meta++) {
			String string = capatalize(OreType.values()[meta].getName());
			Main.LOGGER.log(Level.INFO, string);
			registerStack("ore" + string, new ItemStack(ModBlocks.ORES, 1, meta));
		}
	}
	
	public static void registerMaterialsToDictionary() {
		
	}
	
	private static final void registerBlock(String name, Block block) {
		OreDictionary.registerOre(name, block);
	}

	private static final void registerItem(String name, Item item) {
		OreDictionary.registerOre(name, item);
	}
	
	private static final void registerStack(String name, ItemStack stack) {
		OreDictionary.registerOre(name, stack);
	}
	
	private static final String capatalize(String string) {
		return StringUtils.capitalize(string);
	}
	
}
