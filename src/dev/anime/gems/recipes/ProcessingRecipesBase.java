package dev.anime.gems.recipes;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import dev.anime.gems.utils.LogHelper;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.registries.IForgeRegistryEntry;

public class ProcessingRecipesBase {
	
	// TODO: Finish implementation
	public static class SingleRecipeList<T extends RecipePiece> implements List<T> {
		
		private T piece;
		
		@Override
		public boolean add(T arg0) {
			if (piece != null) return false;
			piece = arg0;
			return true;
		}

		@Override
		public void add(int arg0, T arg1) {
			add(arg1);
		}

		@Override
		public boolean addAll(Collection<? extends T> arg0) {
			if (arg0.size() > 1) LogHelper.inform("Collection attempted to add multiple objects to a SingleRecipeList. This may be a problem. Adding the first entry");
			return add(arg0.iterator().next());
		}

		@Override
		public boolean addAll(int arg0, Collection<? extends T> arg1) {
			return addAll(arg1);
		}

		@Override
		public void clear() {
			piece = null;
		}

		@Override
		public boolean contains(Object arg0) {
			return arg0.equals(piece);
		}

		@Override
		public boolean containsAll(Collection<?> arg0) {
			if (arg0.size() > 1) {
				LogHelper.inform("Collection attempted to compare multiple objects to a SingleRecipeList. This may be a problem.");
				return false;
			} else return contains(arg0.iterator().next());
		}

		@Override
		public T get(int arg0) {
			if (arg0 >= 1) LogHelper.inform("Collection attempted to get index greater than 0 from a SingleRecipeList. This may be a problem. Returning single entry anyways.");
			return piece;
		}

		@Override
		public int indexOf(Object arg0) {
			if (contains(arg0)) return 0;
			else return -1;
		}

		@Override
		public boolean isEmpty() {
			return piece == null;
		}

		@Override
		public Iterator<T> iterator() {
			return new Iterator<T>() {
				
				private int pos = 0;
				
				@Override
				public boolean hasNext() {
					return pos == 0;
				}

				@Override
				public T next() {
					T temp;
					if (pos == 0) {
						temp = piece;
						pos++;
					} else temp = null;
					return temp;
				}
			};
		}

		@Override
		public int lastIndexOf(Object arg0) {
			return indexOf(arg0);
		}

		@Override
		public ListIterator<T> listIterator() {
			return new ListIterator<T>() {
				
				private int pos = 0;
				
				@Override
				public void add(T e) { }

				@Override
				public boolean hasNext() {
					return pos == 0;
				}

				@Override
				public boolean hasPrevious() {
					return pos >= 1;
				}

				@Override
				public T next() {
					T temp;
					if (pos == 0) {
						temp = piece;
						pos++;
					} else temp = null;
					return temp;
				}

				@Override
				public int nextIndex() {
					return pos + 1;
				}

				@Override
				public T previous() {
					T temp;
					if (pos == 1) {
						temp = piece;
						pos--;
					} else temp = null;
					return temp;
				}

				@Override
				public int previousIndex() {
					return pos - 1;
				}

				@Override
				public void remove() {
					if (pos == 1) piece = null;
				}

				@Override
				public void set(T e) {
					if (pos == 1) piece = e;
				}
			};
		}

		@Override
		public ListIterator<T> listIterator(int arg0) {
			return listIterator();
		}

		@Override
		public boolean remove(Object arg0) {
			if (contains(arg0)) {
				piece = null;
				return true;
			} else return false;
		}

		
		// TODO: Finish implementation
		@Override
		public T remove(int arg0) {
			
			return null;
		}

		@Override
		public boolean removeAll(Collection<?> arg0) {
			return false;
		}

		@Override
		public boolean retainAll(Collection<?> arg0) {
			return false;
		}

		@Override
		public T set(int arg0, T arg1) {
			return null;
		}

		@Override
		public int size() {
			return 0;
		}

		@Override
		public List<T> subList(int arg0, int arg1) {
			return null;
		}

		@Override
		public Object[] toArray() {
			return null;
		}

		@Override
		public <T> T[] toArray(T[] arg0) {
			return null;
		}
		
	}
	
	public static class RecipePiece {
		
		private ItemStack stack;
		private Item item;
		private Block block;
		
		private IForgeRegistryEntry<?> entry;
		
		private Object obj;

		private RecipeType type;
		
		public RecipePiece() { }
		
		public RecipePiece(ItemStack stack) {
			this.stack = stack;
		}
		
		public RecipePiece(Item item) {
			this.setItem(item);
		}
		
		public RecipePiece(Block block) {
			this.setBlock(block);
		}
		
		public RecipePiece(IForgeRegistryEntry<?> entry) {
			this.setEntry(entry);
		}
		
		public RecipePiece(Object obj) {
			this.setObj(obj);
		}
		
		public ItemStack getStack() {
			return stack;
		}

		public Item getItem() {
			return item;
		}

		public Block getBlock() {
			return block;
		}

		public IForgeRegistryEntry<?> getEntry() {
			return entry;
		}

		public Object getObj() {
			return obj;
		}

		public RecipePiece setStack(ItemStack stack) {
			this.clear();
			this.stack = stack;
			this.type = RecipeType.STACK;
			return this;
		}

		public RecipePiece setItem(Item item) {
			this.clear();
			this.item = item;
			this.type = RecipeType.ITEM;
			return this;
		}

		public RecipePiece setBlock(Block block) {
			this.clear();
			this.block = block;
			this.type = RecipeType.BLOCK;
			return this;
		}

		public RecipePiece setEntry(IForgeRegistryEntry<?> entry) {
			this.clear();
			this.entry = entry;
			this.type = RecipeType.REGISTRY;
			return this;
		}

		public RecipePiece setObj(Object obj) {
			this.clear();
			this.obj = obj;
			this.type = RecipeType.OTHER;
			return this;
		}
		
		private void clear() {
			this.stack = ItemStack.EMPTY;
			this.item = null;
			this.block = null;
			this.entry = null;
			this.obj = null;
		}
		
		public enum RecipeType {
			STACK, ITEM, BLOCK, REGISTRY, OTHER;
		}
		
	}
	
}
