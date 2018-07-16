package dev.anime.gems.recipes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.tuple.Triple;

import dev.anime.gems.recipes.ProcessingRecipes.MultiRecipeList;
import dev.anime.gems.recipes.ProcessingRecipes.RecipePiece;
import dev.anime.gems.recipes.ProcessingRecipes.SingleRecipeList;
import dev.anime.gems.utils.LogHelper;

public class ProcessingRecipe {
	
	private final Triple<List<RecipePiece>, List<RecipePiece>, List<Object>> recipe;
	
	public ProcessingRecipe(boolean singleIn, boolean singleOut) {
		List<RecipePiece> in = new MultiRecipeList<RecipePiece>(), out = new MultiRecipeList<RecipePiece>();
		if (singleIn) in = new SingleRecipeList<RecipePiece>();
		if (singleOut) out = new SingleRecipeList<RecipePiece>();
		recipe = Triple.of(in, out, new ArrayList<Object>());
	}
	
	// TODO: Implementation methods
	
	public RecipePiece getInput(int index) {
		return recipe.getLeft().get(index);
	}
	
	public RecipePiece getOutput(int index) {
		return recipe.getMiddle().get(index);
	}
	
	public List<Object> getExtraParameters() {
		return recipe.getRight();
	}
	
	// Untested in all conditions.
	public boolean matches(RecipePiece[] pieces, boolean ordered, boolean ignoreLength) {
		List<RecipePiece> inputs = recipe.getLeft();
		if (!ignoreLength && inputs.size() != pieces.length) return false;
		int min = Math.min(inputs.size(), pieces.length);
		boolean[] matches = new boolean[ignoreLength ? min : inputs.size()];
		Arrays.fill(matches, false);
		if (ordered) {
			for (int i = 0; i < min; i++) {
				if (pieces[i].equals(inputs.get(i))) matches[i] = true;
			}
		} else {
			main:for (int i = 0; i < inputs.size(); i++) {
				for (int j = 0; j < pieces.length; j++) {
					if (inputs.get(i).equals(pieces[j])) {
						matches[ignoreLength ? j : i] = true;
						continue main;
					}
				}
				return false; // No match for input, no use continuing.
			}
		}
		return allTrue(matches);
	}
	
	private boolean allTrue(boolean[] matches) {
		for (boolean b : matches) if (b == false) return false;
		return true;
	}
	
	public ProcessingRecipe addInput(RecipePiece input) {
		if (isSingleInput() && recipe.getLeft().get(0) != null) {
			LogHelper.warn("Attempted to add an input to a single input recipe that already had an input. Ignoring.");
			return this;
		}
		recipe.getLeft().add(input);
		return this;
	}
	
	public ProcessingRecipe addOutput(RecipePiece output) {
		if (isSingleOutput() && recipe.getMiddle().get(0) != null) {
			LogHelper.warn("Attempted to add an output to a single output recipe that already had an output. Ignoring.");
			return this;
		}
		recipe.getMiddle().add(output);
		return this;
	}
	
	public ProcessingRecipe addExtraParameter(Object obj) {
		recipe.getRight().add(obj);
		return this;
	}
	
	public boolean isSingleInput() {
		return recipe.getLeft() instanceof SingleRecipeList;
	}
	
	public boolean isSingleOutput() {
		return recipe.getMiddle() instanceof SingleRecipeList;
	}
	
}
