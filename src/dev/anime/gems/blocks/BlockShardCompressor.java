package dev.anime.gems.blocks;

import dev.anime.gems.Main;
import dev.anime.gems.init.ModBlocks;
import dev.anime.gems.tile.TileEntityShardCompressor;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.property.ExtendedBlockState;
import net.minecraftforge.common.property.IUnlistedProperty;
import net.minecraftforge.common.property.Properties;

public class BlockShardCompressor extends Block {

	public BlockShardCompressor() {
		super(Material.IRON);
		setRegistryName("shard_compressor");
		setUnlocalizedName(getRegistryName().toString());
		ModBlocks.BLOCKS.add(this);
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (!world.isRemote) player.openGui(Main.INSTANCE, 0, world, pos.getX(), pos.getY(), pos.getZ());
		return true;
	}
	
    @Override
    public ExtendedBlockState createBlockState() {
        return new ExtendedBlockState(this, new IProperty[]{ Properties.StaticProperty }, new IUnlistedProperty[]{ Properties.AnimationProperty });
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) { return false; }

    @Override
    public boolean isFullCube(IBlockState state) { return false; }

    @Override
    public boolean hasTileEntity(IBlockState state) {
        return true;
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos pos) {
        return state.withProperty(Properties.StaticProperty, true);
    }
    
    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
    	return EnumBlockRenderType.MODEL;
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return 0;
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState();
    }

    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new TileEntityShardCompressor();
    }
	
}
