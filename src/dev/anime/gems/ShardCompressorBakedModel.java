package dev.anime.gems;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.vecmath.Quat4f;
import javax.vecmath.Vector3f;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;

import dev.anime.gems.init.ModBlocks;
import dev.anime.gems.utils.ItemStackProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemOverrideList;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.common.model.IModelPart;
import net.minecraftforge.common.model.IModelState;
import net.minecraftforge.common.model.TRSRTransformation;
import net.minecraftforge.common.property.IExtendedBlockState;

public class ShardCompressorBakedModel implements IBakedModel {

	private static final IBlockState DEFAULT_STATE = ModBlocks.SHARD_COMPRESSOR.getDefaultState();
	
	private final IBakedModel MAIN_MODEL, ITEM_MODEL;
	private final ImmutableList<BakedQuad> MAIN, ITEM;
	private final List<BakedQuad> cache = new ArrayList<BakedQuad>();
	private int count;
	
	private static final Function<ResourceLocation, TextureAtlasSprite> BAKED_TEXTURE_GETTER = (ResourceLocation location) -> { return Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(location.toString()); };
	
	public ShardCompressorBakedModel() throws Exception {
		IModel temp = ModelLoaderRegistry.getModel(new ModelResourceLocation(ModBlocks.SHARD_COMPRESSOR.getRegistryName(), "inventory"));
		MAIN_MODEL = temp.bake(new ModelState(), DefaultVertexFormats.BLOCK, BAKED_TEXTURE_GETTER);
		temp = ModelLoaderRegistry.getModel(new ModelResourceLocation(ModBlocks.SHARD_COMPRESSOR.getRegistryName(), "inventory"));
		ITEM_MODEL = temp.bake(new ModelState(), DefaultVertexFormats.BLOCK, BAKED_TEXTURE_GETTER);
		MAIN = ImmutableList.copyOf(MAIN_MODEL.getQuads(DEFAULT_STATE, null, 0L));
		ITEM = ImmutableList.copyOf(ITEM_MODEL.getQuads(null, null, 0L));
	}
	
	public static class ModelState implements IModelState {

		@Override
		public Optional<TRSRTransformation> apply(Optional<? extends IModelPart> part) {
			return Optional.of(new TRSRTransformation(new Vector3f(1, 1, 1), new Quat4f(), new Vector3f(1, 1, 1), new Quat4f()));
		}
		
	}
	
	@Override
	public List<BakedQuad> getQuads(IBlockState state, EnumFacing side, long rand) {
		if (state instanceof IExtendedBlockState) {
			IExtendedBlockState exstate = (IExtendedBlockState) state;
			int count = exstate.getValue(ItemStackProperty.ITEMSTACK).getCount();
			if (count == 0 && this.count != 0) {
				cache.clear();
				cache.addAll(MAIN);
				 count = 0;
			} else if (count != 0 && this.count == 0) {
				cache.clear();
				cache.addAll(MAIN);
				cache.addAll(ITEM);
				count = 1;
			}
		} else if (cache.isEmpty()) cache.addAll(MAIN);
		return cache;
	}

	@Override
	public boolean isAmbientOcclusion() {
		return true;
	}

	@Override
	public boolean isGui3d() {
		return true;
	}

	@Override
	public boolean isBuiltInRenderer() {
		return false;
	}

	@Override
	public TextureAtlasSprite getParticleTexture() {
		return Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite("minecraft:iron_block");
	}

	@Override
	public ItemOverrideList getOverrides() {
		return MAIN_MODEL.getOverrides();
	}

}
