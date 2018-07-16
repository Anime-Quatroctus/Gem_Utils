package dev.anime.gems.tile;

import com.google.common.collect.ImmutableMap;

import dev.anime.gems.Main;
import dev.anime.gems.network.SyncTEMessage;
import dev.anime.gems.proxies.ServerProxy.TimeType;
import dev.anime.gems.recipes.ProcessingRecipe;
import dev.anime.gems.recipes.ProcessingRecipes;
import dev.anime.gems.utils.RecipeItemStackHandler;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.animation.ITimeValue;
import net.minecraftforge.common.animation.TimeValues.VariableValue;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.model.animation.CapabilityAnimation;
import net.minecraftforge.common.model.animation.IAnimationStateMachine;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraftforge.items.CapabilityItemHandler;

public class TileEntityShardCompressor extends TileEntityBase implements ITickable {

	private int fuelTimeRemaining, fuelTimeMax, currentItemProcessTime;
	
	public static final int FINISH_PROCESS_TIME = 300;
	
	private ProcessingRecipe recipe;
	
	private final IAnimationStateMachine asm;
	private ITimeValue move;
	
	public TileEntityShardCompressor() {
		this.items = new RecipeItemStackHandler(3, 1, 1);
		move = Main.PROXY.createTimeValue(TimeType.VARIABLE, 0);
		asm = Main.PROXY.load(new ResourceLocation(Main.MODID, "asms/block/shard_compressor.json"), ImmutableMap.of("move", move));
	}
	
	@Override
	public void update() {
		if (!world.isRemote) {
			if ((recipe != null && recipe.matches(ProcessingRecipes.transformArray(((RecipeItemStackHandler) items).getItems()), true, false)) || (recipe = ProcessingRecipes.COMPRESSOR_RECIPES.findRecipe(((RecipeItemStackHandler) items).getItems(), false, false)) != null) {
				if (fuelTimeRemaining > 0 || canBurnFuel()) {
					currentItemProcessTime++;
					if (currentItemProcessTime >= FINISH_PROCESS_TIME) {
						items.extractItem(1, recipe.getInput(0).getAsStack().getCount(), false);
						items.insertItem(2, recipe.getOutput(0).getAsStack().copy(), false);
						currentItemProcessTime = 0;
					}
					if (currentItemProcessTime == 200 || currentItemProcessTime == 225 || currentItemProcessTime == 250 || currentItemProcessTime == 275 || currentItemProcessTime == 300) Main.WRAPPER.sendToAllTracking(new SyncTEMessage(getPos(), getUpdateTag()), new TargetPoint(world.provider.getDimension(), pos.getX(), pos.getY(), pos.getZ(), 64));
					markDirty();
				}
			}
			fuelTimeRemaining--;
		} else { // Animation update client side only.
			if (asm.currentState().equals("move")) {
				if (fuelTimeRemaining > 0) {
					currentItemProcessTime++;
					fuelTimeRemaining--;
				}
				((VariableValue)move).setValue((currentItemProcessTime - 200) / 99F);
			}
			if (currentItemProcessTime >= 200) {
				if (asm.currentState().equals("default")) {
					asm.transition("move");
				}
			}
			if (currentItemProcessTime == 300 && asm.currentState().equals("move")) {
				asm.transition("default");
				currentItemProcessTime = 0;
				((VariableValue)move).setValue(0);
			}
		}
	}
	
	private boolean canBurnFuel() {
		if (items.getStackInSlot(0).isEmpty()) return false;
		int burnTime = TileEntityFurnace.getItemBurnTime(items.getStackInSlot(0));
		if (burnTime <= 0) return false;
		fuelTimeRemaining += burnTime;
		fuelTimeMax = burnTime;
		items.extractItem(0, 1, false);
		return true;
	}
	
	@Override
	public boolean hasFastRenderer() {
		return true;
	}
	
	@Override
	public int getDataMax() {
		return 3;
	}
	
	@Override
	public int getData(int id) {
		switch (id) {
			case 0: return fuelTimeRemaining;
			case 1: return currentItemProcessTime;
			case 2: return fuelTimeMax;
			default: return 0;
		}
	}
	
	@Override
	public void setData(int id, int value) {
		switch (id) {
			case 0: fuelTimeRemaining = value;
			case 1: currentItemProcessTime = value;
			case 2: fuelTimeMax = value;
		}
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		compound.setInteger("fuelTime", fuelTimeRemaining);
		compound.setInteger("fuelTimeMax", fuelTimeMax);
		compound.setInteger("processTime", currentItemProcessTime);
		compound.setTag("items", items.serializeNBT());
		return compound;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		fuelTimeRemaining = compound.getInteger("fuelTime");
		fuelTimeMax = compound.getInteger("fuelTimeMax");
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
