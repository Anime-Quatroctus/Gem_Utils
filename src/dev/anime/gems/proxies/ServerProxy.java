package dev.anime.gems.proxies;

import com.google.common.collect.ImmutableMap;

import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.animation.ITimeValue;
import net.minecraftforge.common.model.animation.IAnimationStateMachine;

public class ServerProxy {
	
	public void registerTESRs() { }
	
	public void registerModelVariants(Item item, ResourceLocation[] locations) { }
	
	public ITimeValue createTimeValue(TimeType type, float value) {
		return (float f) -> {return 0F;};
	}
	
	public IAnimationStateMachine load(ResourceLocation location, ImmutableMap<String, ITimeValue> parameters) {
		return null;
	}
	
	public enum TimeType {
		VARIABLE, CONST;
	}
	
}