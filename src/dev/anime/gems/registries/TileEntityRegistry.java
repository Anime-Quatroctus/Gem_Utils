package dev.anime.gems.registries;

import dev.anime.gems.Main;
import dev.anime.gems.tile.TileEntityOre;
import dev.anime.gems.tile.TileEntityShardCompressor;
import net.minecraft.tileentity.TileEntity;

public class TileEntityRegistry {
	
	public static void registerAllTileEntities() {
		registerTileEntity("teores", TileEntityOre.class);
		registerTileEntity("teshardcompressor", TileEntityShardCompressor.class);
	}
	
	private static void registerTileEntity(String key, Class<? extends TileEntity> teClass) {
		TileEntity.register(Main.MODID + ":" + key, teClass);
	}
	
}
