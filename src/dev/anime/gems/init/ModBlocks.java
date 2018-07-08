package dev.anime.gems.init;

import java.util.ArrayList;
import java.util.List;

import dev.anime.gems.blocks.BlockOres;
import dev.anime.gems.blocks.BlockShardCompressor;
import net.minecraft.block.Block;

public class ModBlocks {
	
	public static List<Block> BLOCKS = new ArrayList<Block>();
	
	public static Block ORES;
	
	public static Block SHARD_COMPRESSOR;
	public static Block ANGELIC_SMELTER;
	
	public static final void init() {
		ORES = new BlockOres();
		SHARD_COMPRESSOR = new BlockShardCompressor();
	}
	
}