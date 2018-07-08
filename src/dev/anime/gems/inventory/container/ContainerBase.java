package dev.anime.gems.inventory.container;

import com.google.common.base.Predicate;

import dev.anime.gems.tile.TileEntityBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerBase extends Container {
	
	protected IItemHandler items;
	protected TileEntityBase te;
	
	private int hotbarStart = 0, mainInventoryStart = 9, itemsStart = 36, itemsEnd;
	
	public ContainerBase(TileEntityBase te, InventoryPlayer inventory, int playerInvenStartX, int playerInvenStartY) {
		this.te = te;
		this.items = te.getItems();
		if (items != null) itemsEnd = itemsStart + items.getSlots();
		for (int i1 = 0; i1 < 9; ++i1) this.addSlotToContainer(new Slot(inventory, i1, playerInvenStartX + i1 * 18, playerInvenStartY + 58));
		for (int k = 0; k < 3; ++k) {
            for (int l = 0; l < 9; ++l) {
                this.addSlotToContainer(new Slot(inventory, l + k * 9 + 9, playerInvenStartX + l * 18, playerInvenStartY + k * 18));
            }
        }
	}
	
	@Override
	public void addListener(IContainerListener listener) {
		super.addListener(listener);
	}
	
	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();
		for (IContainerListener listener : listeners) {
			for (int i = 0; i < te.getDataMax(); i++) listener.sendWindowProperty(this, i, te.getData(i));
		}
	}
	
	@Override
	public void updateProgressBar(int id, int data) {
		te.setData(id, data);
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
		ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);
        if (slot != null && slot.getHasStack()) {
        	ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();
            if (index >= itemsStart) {
            	if (!mergeItemStack(itemstack1, hotbarStart, mainInventoryStart, false)) {
            		if (!mergeItemStack(itemstack1, mainInventoryStart, itemsStart, false)) {
            			return ItemStack.EMPTY;
            		}
            	}
            	slot.onSlotChange(itemstack1, itemstack);
            } else if (index < mainInventoryStart) {
            	if (!mergeItemStack(itemstack1, itemsStart, itemsEnd, false)) {
            		if (!mergeItemStack(itemstack1, mainInventoryStart, itemsStart, false)) {
            			return ItemStack.EMPTY;
            		}
            	}
            } else if (index >= mainInventoryStart && index < itemsStart) {
            	if (!mergeItemStack(itemstack1, itemsStart, itemsEnd, false)) {
            		if (!mergeItemStack(itemstack1, hotbarStart, mainInventoryStart, false)) {
            			return ItemStack.EMPTY;
            		}
            	}
            }
            if (itemstack1.isEmpty()) slot.putStack(ItemStack.EMPTY);
            else slot.onSlotChanged();
            if (itemstack1.getCount() == itemstack.getCount()) return ItemStack.EMPTY;
            slot.onTake(playerIn, itemstack1);
        }
        return itemstack;
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		return true;
	}
	
	@Override
	protected boolean mergeItemStack(ItemStack stack, int startIndex, int endIndex, boolean reverseDirection) {
        boolean flag = false;
        int i = startIndex;
        if (reverseDirection)i = endIndex - 1;
        if (stack.isStackable()) {
            while (!stack.isEmpty()) {
                if (reverseDirection) {
                    if (i < startIndex) break;
                } else if (i >= endIndex) break;
                Slot slot = this.inventorySlots.get(i);
                ItemStack itemstack = slot.getStack();
                if (slot.isItemValid(stack)) {
                	if (!itemstack.isEmpty() && itemstack.getItem() == stack.getItem() && (!stack.getHasSubtypes() || stack.getMetadata() == itemstack.getMetadata()) && ItemStack.areItemStackTagsEqual(stack, itemstack)) {            
                    	int j = itemstack.getCount() + stack.getCount();
                    	int maxSize = Math.min(slot.getSlotStackLimit(), stack.getMaxStackSize());
                    	if (j <= maxSize) {
                    		stack.setCount(0);
                        	itemstack.setCount(j);
                        	slot.onSlotChanged();
                        	flag = true;
                    	} else if (itemstack.getCount() < maxSize) {
                        	stack.shrink(maxSize - itemstack.getCount());
                        	itemstack.setCount(maxSize);
                        	slot.onSlotChanged();
                        	flag = true;
                    	}
                	}
                }
                if (reverseDirection) {
                    --i;
                } else {
                    ++i;
                }
            }
        }
        if (!stack.isEmpty()) {
            if (reverseDirection) {
                i = endIndex - 1;
            } else {
                i = startIndex;
            }
            while (true) {
                if (reverseDirection) {
                    if (i < startIndex) break;
                } else if (i >= endIndex) break;
                Slot slot1 = this.inventorySlots.get(i);
                ItemStack itemstack1 = slot1.getStack();
                if (slot1.isItemValid(stack)) {
                	if (itemstack1.isEmpty() && slot1.isItemValid(stack)) {
                    	if (stack.getCount() > slot1.getSlotStackLimit()) {
                        	slot1.putStack(stack.splitStack(slot1.getSlotStackLimit()));
                    	} else slot1.putStack(stack.splitStack(stack.getCount()));
                    	slot1.onSlotChanged();
                    	flag = true;
                    	break;
                	}
                }
               	if (reverseDirection) --i;
               	else ++i;
            }
        }
        return flag;
    }
	
	public IItemHandler getItems() {
		return items;
	}
	
	public static class FuelSlot extends SlotItemHandler {

		public FuelSlot(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
			super(itemHandler, index, xPosition, yPosition);
		}
		
		@Override
		public boolean isItemValid(ItemStack stack) {
			return TileEntityFurnace.isItemFuel(stack);
		}
		
	}
	
	public static class CustomSlot extends SlotItemHandler {

		private Predicate<ItemStack> isItemValid;
		
		public CustomSlot(IItemHandler itemHandler, int index, int xPosition, int yPosition, Predicate<ItemStack> pred) {
			super(itemHandler, index, xPosition, yPosition);
			this.isItemValid = pred;
		}
		
		@Override
		public boolean isItemValid(ItemStack stack) {
			return isItemValid.apply(stack);
		}
		
	}
	
}
