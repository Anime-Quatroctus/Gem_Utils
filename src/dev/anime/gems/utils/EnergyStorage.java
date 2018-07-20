package dev.anime.gems.utils;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.energy.IEnergyStorage;

public class EnergyStorage implements IEnergyStorage {
	
	public int energy, maxEnergy;
	public int maxReceive, maxExtract;
	
	private final boolean saveAll;
	
	/**
	 * @param maxEnergy The maximum amount of energy this storage can store.
	 * @param maxReceive The maximum amount of energy this storage can receive externally.
	 * @param maxExtract The maximum amount of energy that can be extracted from this storage.
	 * @param saveAll If this storage should write anything other than the stored energy.
	 */
	public EnergyStorage(int maxEnergy, int maxReceive, int maxExtract, boolean saveAll) {
		this.maxEnergy = maxEnergy;
		this.maxReceive = maxReceive;
		this.maxExtract = maxExtract;
		this.saveAll = saveAll;
	}
	
	public void writeToNBT(NBTTagCompound compound) {
		NBTTagCompound tag = new NBTTagCompound();
		tag.setInteger("energy", energy);
		if (saveAll) {
			tag.setInteger("maxEnergy", maxEnergy);
			tag.setInteger("maxExtract", maxExtract);
			tag.setInteger("maxReceive", maxReceive);
		}
		compound.setTag("energy", tag);
	}
	
	public void readFromNBT(NBTTagCompound compound) {
		NBTTagCompound tag = compound.getCompoundTag("energy");
		this.energy = tag.getInteger("energy");
		if (saveAll) {
			this.maxEnergy = tag.getInteger("maxEnergy");
			this.maxExtract = tag.getInteger("maxExtract");
			this.maxReceive = tag.getInteger("maxReceive");
		}
	}
	
	@Override
	public int receiveEnergy(int maxReceive, boolean simulate) {
		return receiveInternal(Math.min(maxReceive, this.maxReceive), simulate);
	}

	@Override
	public int extractEnergy(int maxExtract, boolean simulate) {
		return extractInternal(Math.min(maxExtract, this.maxExtract), simulate);
	}
	
	public int extractInternal(int maxExtract, boolean simulate) {
		int energy = Math.min(maxExtract, this.energy);
		if (simulate) this.energy -= energy;
		return energy;
	}
	
	public int receiveInternal(int maxReceive, boolean simulate) {
		int energy = Math.min(maxReceive, this.energy);
		if (simulate) this.energy -= energy;
		return energy;
	}
	
	@Override
	public int getEnergyStored() {
		return energy;
	}

	@Override
	public int getMaxEnergyStored() {
		return maxEnergy;
	}

	@Override
	public boolean canExtract() {
		return maxExtract > 0;
	}

	@Override
	public boolean canReceive() {
		return maxReceive > 0;
	}
	
}
