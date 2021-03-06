package dev.anime.gems.registries;

import org.apache.logging.log4j.Level;

import dev.anime.gems.Main;
import dev.anime.gems.ShardCompressorBakedModel;
import dev.anime.gems.init.ModBlocks;
import dev.anime.gems.init.ModItems;
import dev.anime.gems.utils.ICustomModel;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@EventBusSubscriber
public class ModelRegistry {
	
	@SubscribeEvent
	public static void registerModels(ModelRegistryEvent event) {
		if (Main.DEBUG) Main.LOGGER.log(Level.INFO, "Gem Utilities Registering Item Models.");
		for (Item item : ModItems.ITEMS) {
			if (item instanceof ICustomModel) ((ICustomModel) item).handleModelLocations();
			else ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
			
		}
		if (Main.DEBUG) Main.LOGGER.log(Level.INFO, "Gem Utilities Registering Block Models.");
		for (Block block : ModBlocks.BLOCKS) {
			if (block instanceof ICustomModel) ((ICustomModel) block).handleModelLocations();
			else ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 0, new ModelResourceLocation(block.getRegistryName(), "inventory"));
		}
	}
	
//	@SubscribeEvent
	public static void bakeModel(ModelBakeEvent event) {
		try {
			event.getModelRegistry().putObject(new ModelResourceLocation(ModBlocks.SHARD_COMPRESSOR.getRegistryName(), "inventory"), new ShardCompressorBakedModel());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}