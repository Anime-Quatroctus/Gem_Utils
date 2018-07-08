package dev.anime.gems.proxies;

import dev.anime.gems.tile.TileEntityShardCompressor;
import net.minecraftforge.client.model.animation.AnimationTESR;
import net.minecraftforge.fml.client.registry.ClientRegistry;

public class ClientProxy extends ServerProxy {

	@Override
	public void preInit() {
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityShardCompressor.class, new AnimationTESR<TileEntityShardCompressor>());
	}
	
}