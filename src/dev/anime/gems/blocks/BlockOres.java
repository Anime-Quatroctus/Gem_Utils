package dev.anime.gems.blocks;

import java.util.Random;

import dev.anime.gems.init.ModBlocks;
import dev.anime.gems.init.ModItems;
import dev.anime.gems.tile.TileEntityOre;
import dev.anime.gems.utils.IMetaModel;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockOres extends Block implements IMetaModel {
	
	public static final PropertyEnum<OreType> ORE_TYPE = PropertyEnum.create("type", OreType.class);
	
	public BlockOres() {
		super(Material.ROCK);
		this.setDefaultState(getDefaultState().withProperty(ORE_TYPE, OreType.RUBY));
		this.setRegistryName("ores");
		this.setUnlocalizedName(this.getRegistryName().toString());
		this.setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
		ModBlocks.BLOCKS.add(this);
	}
	
	@Override
	public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
		return new ItemStack(this, 1, damageDropped(state));
	}
	
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return state.getValue(ORE_TYPE) == OreType.ANGEL ? Item.getItemFromBlock(this) : ModItems.MATERIALS;
	}
	
	@Override
	public int damageDropped(IBlockState state) {
		return state.getValue(ORE_TYPE).ordinal();
	}
	
	@Override
	public int quantityDropped(IBlockState state, int fortune, Random random) {
		int baseDrop = state.getValue(ORE_TYPE) == OreType.SHADOW ? random.nextInt(2) + 1 : 1;
		if (state.getValue(ORE_TYPE) != OreType.ANGEL && fortune > 0) baseDrop *= random.nextInt(fortune + 2);
		return baseDrop;
	}
	
	@Override
	public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items) {
		for (int i = 0; i < getMaxMeta(); i++) items.add(new ItemStack(this, 1, i));
	}
	
	@Override
	public BlockRenderLayer getBlockLayer() {
		return BlockRenderLayer.CUTOUT;
	}
	
	@Override
	public boolean hasTileEntity(IBlockState state) {
		return true;
	}
	
	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return new TileEntityOre(state.getValue(ORE_TYPE));
	}
	
	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
		return getExtendedState(state, worldIn, pos);
	}
	
	@Override
	public IBlockState getExtendedState(IBlockState state, IBlockAccess world, BlockPos pos) {
		TileEntityOre te = (TileEntityOre) world.getTileEntity(pos);
		if (state != null) return state.withProperty(ORE_TYPE, te.getOreType());
		else return getDefaultState().withProperty(ORE_TYPE, te.getOreType());
	}
	
	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, ORE_TYPE);
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta) {
		return meta < getMaxMeta() ? getDefaultState().withProperty(ORE_TYPE, OreType.values()[meta]) : getDefaultState();
	}
	
	@Override
	public int getMetaFromState(IBlockState state) {
		return 0;
	}
	
	@Override
	public int getMaxMeta() {
		return OreType.values().length;
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return getUnlocalizedName() + "_" + OreType.values()[stack.getMetadata()].getName();
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void handleModelLocations() {
		Item item = Item.getItemFromBlock(this);
		for (int i = 0; i < getMaxMeta(); i++) {
			ModelLoader.setCustomModelResourceLocation(item, i, new ModelResourceLocation(this.getRegistryName().toString() + "_" + OreType.values()[i].getName(), "inventory"));
		}
	}
	
	public static enum OreType implements IStringSerializable {
		RUBY, SAPPHIRE, AMETHYST, AMBER, SHADOW, ANGEL, /*DEVIL, GODLY, LINARITE, CRYOLITE, KYANITE, BOTRYOGEN, ETTRINGITE, ELBAITE, BRUCITE, BIOTITE, AXINITE*/;

		@Override
		public String getName() {
			return this.toString().toLowerCase();
		}
	}
	
}
