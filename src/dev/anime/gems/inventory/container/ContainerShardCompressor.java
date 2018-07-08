package dev.anime.gems.inventory.container;

import dev.anime.gems.blocks.BlockOres.OreType;
import dev.anime.gems.init.ModItems;
import dev.anime.gems.tile.TileEntityShardCompressor;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;

public class ContainerShardCompressor extends ContainerBase {

	public ContainerShardCompressor(TileEntityShardCompressor te, InventoryPlayer inventory) {
		super(te, inventory, 8, 84);
		this.addSlotToContainer(new FuelSlot(items, 0, 56, 53));
		this.addSlotToContainer(new CustomSlot(items, 1, 56, 17, (ItemStack stack) -> stack.getItem() == ModItems.MATERIALS && stack.getMetadata() == OreType.SHADOW.ordinal()));
		this.addSlotToContainer(new CustomSlot(items, 2, 116, 35, (ItemStack stack) -> false));
	}
	
}
