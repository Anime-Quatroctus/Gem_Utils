package dev.anime.gems.registries;

import org.apache.logging.log4j.Level;

import dev.anime.gems.Main;
import dev.anime.gems.init.ModBlocks;
import net.minecraft.block.Block;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@EventBusSubscriber
public class BlockRegistry {
	
	@SubscribeEvent
	public static void registerBlocks(Register<Block> event) {
		if (Main.DEBUG) Main.LOGGER.log(Level.INFO, "Gem Utilities Registering Blocks.");
		ModBlocks.init();
		ModBlocks.BLOCKS.forEach((Block block) -> event.getRegistry().register(block));
	}
	
}