package dev.anime.gems.registries;

import org.apache.logging.log4j.Level;

import dev.anime.gems.Main;
import dev.anime.gems.init.ModBlocks;
import dev.anime.gems.init.ModItems;
import dev.anime.gems.items.ItemMetaBlock;
import dev.anime.gems.utils.DictionaryHelper;
import dev.anime.gems.utils.ICustomModel;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@EventBusSubscriber
public class ItemRegistry {
	
	@SubscribeEvent
	public static void registerItems(Register<Item> event) {
		if (Main.DEBUG) Main.LOGGER.log(Level.INFO, "Gem Utilities Registering Items.");
		ModBlocks.BLOCKS.forEach((Block block) -> event.getRegistry().register(createProperItemBlock(block)));
		ModItems.init();
		ModItems.ITEMS.forEach((Item item) -> event.getRegistry().register(item));
		DictionaryHelper.registerOresToDictionary();
	}
	
	private static ItemBlock createProperItemBlock(Block block) {
		if (block instanceof ICustomModel && ((ICustomModel) block).getMaxMeta() > 0) return (ItemBlock)new ItemMetaBlock(block).setRegistryName(block.getRegistryName());
		else return (ItemBlock) new ItemBlock(block).setRegistryName(block.getRegistryName());
	}
	
}