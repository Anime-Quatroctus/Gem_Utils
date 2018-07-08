package dev.anime.gems.tile;

import com.google.common.collect.ImmutableMap;

import dev.anime.gems.Main;
import dev.anime.gems.blocks.BlockOres.OreType;
import dev.anime.gems.init.ModItems;
import dev.anime.gems.utils.IMetaModel;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.common.animation.TimeValues;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.model.animation.CapabilityAnimation;
import net.minecraftforge.common.model.animation.IAnimationStateMachine;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class TileEntityShardCompressor extends TileEntityBase implements ITickable {

	private int fuelTimeRemaining, currentItemProcessTime;
	
	public static final int FINISH_PROCESS_TIME = 300;
	private static final ResourceLocation LOCATION = new ResourceLocation(Main.MODID, "asms/block/shard_compressor.json");
	
	@SideOnly(Side.CLIENT)
	private IAnimationStateMachine asm;
	
	@SideOnly(Side.CLIENT)
	private TimeValues.VariableValue move;
	
	private boolean temp = false;
	
	public TileEntityShardCompressor() {
		this.items = new ItemStackHandler(3);
		if (FMLCommonHandler.instance().getSide() == Side.CLIENT) {
			move = new TimeValues.VariableValue(0);
			asm = ModelLoaderRegistry.loadASM(LOCATION, ImmutableMap.of("move", move));
		}
	}
	
	@Override
	public void update() {
			if (fuelTimeRemaining > 0 || canBurnFuel()) {
				ItemStack input = items.getStackInSlot(1);
				if (input.getItem() == ModItems.MATERIALS && input.getMetadata() == OreType.SHADOW.ordinal() && input.getCount() >= 4) {
					currentItemProcessTime++;
					fuelTimeRemaining--;
					if (world.isRemote && !temp) {
						if (asm.currentState().equals("default")) asm.transition("moving");
						else move.apply(1);
					}
//					else if (world.isRemote) asm.transition("default");
					if (currentItemProcessTime >= FINISH_PROCESS_TIME) {
						items.extractItem(1, 4, false);
						items.insertItem(2, new ItemStack(ModItems.MATERIALS, 1, ((IMetaModel) ModItems.MATERIALS).getMaxMeta()), false);
						currentItemProcessTime = 0;
					}
					markDirty();
				}
			}
	}
	
	private boolean canBurnFuel() {
		if (items.getStackInSlot(0) == ItemStack.EMPTY) return false;
		int burnTime = TileEntityFurnace.getItemBurnTime(items.getStackInSlot(0));
		if (burnTime <= 0) return false;
		fuelTimeRemaining += burnTime;
		items.extractItem(0, 1, false);
		return true;
	}
	
	@Override
	public boolean hasFastRenderer() {
		return true;
	}
	
	@Override
	public int getDataMax() {
		return 2;
	}
	
	@Override
	public int getData(int id) {
		switch (id) {
			case 0: return fuelTimeRemaining;
			case 1: return currentItemProcessTime;
			default: return 0;
		}
	}
	
	@Override
	public void setData(int id, int value) {
		switch (id) {
			case 0: fuelTimeRemaining = value;
			case 1: currentItemProcessTime = value;
		}
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		compound.setInteger("fuelTime", fuelTimeRemaining);
		compound.setInteger("processTime", currentItemProcessTime);
		compound.setTag("items", items.serializeNBT());
		return compound;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		fuelTimeRemaining = compound.getInteger("fuelTime");
		currentItemProcessTime = compound.getInteger("processTime");
		items.deserializeNBT(compound.getCompoundTag("items"));
	}
	
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
			return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(items);
		} else if (capability == CapabilityAnimation.ANIMATION_CAPABILITY) {
			return CapabilityAnimation.ANIMATION_CAPABILITY.cast(asm);
		} else return super.getCapability(capability, facing);
	}
	
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		return getCapability(capability, facing) != null || super.hasCapability(capability, facing);
	}
	
	@Override
	public SPacketUpdateTileEntity getUpdatePacket() {
		return new SPacketUpdateTileEntity(pos, 3, getUpdateTag());
	}
	
	@Override
	public NBTTagCompound getUpdateTag() {
		return writeToNBT(new NBTTagCompound());
	}
	
	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
		handleUpdateTag(pkt.getNbtCompound());
	}
	
}
