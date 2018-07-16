package dev.anime.gems.proxies;

import com.google.common.collect.ImmutableMap;

import dev.anime.gems.tile.TileEntityShardCompressor;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.client.model.animation.AnimationTESR;
import net.minecraftforge.common.animation.ITimeValue;
import net.minecraftforge.common.animation.TimeValues;
import net.minecraftforge.common.model.animation.IAnimationStateMachine;
import net.minecraftforge.fml.client.registry.ClientRegistry;

public class ClientProxy extends ServerProxy {

	@Override
	public void preInit() {
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityShardCompressor.class, new AnimationTESR<TileEntityShardCompressor>());
	}
	
	@Override
	public ITimeValue createTimeValue(TimeType type, float value) {
		switch (type) {
			case VARIABLE: return new TimeValues.VariableValue(value);
			case CONST: return new TimeValues.ConstValue(value);
			default: return new TimeValues.ConstValue(0);
		}
	}
	
	@Override
	public IAnimationStateMachine load(ResourceLocation location, ImmutableMap<String, ITimeValue> parameters) {
		return ModelLoaderRegistry.loadASM(location, parameters);
	}
	
}