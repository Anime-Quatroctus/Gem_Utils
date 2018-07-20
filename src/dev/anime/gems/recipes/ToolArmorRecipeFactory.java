package dev.anime.gems.recipes;

import java.util.Map.Entry;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.IRecipeFactory;
import net.minecraftforge.common.crafting.JsonContext;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class ToolArmorRecipeFactory implements IRecipeFactory {
	
	private static final Ingredient EMPTY = Ingredient.EMPTY;
	
	@Override
	public IRecipe parse(JsonContext context, JsonObject json) {
		Ingredient main = CraftingHelper.getIngredient(json.get("main"), context), handle = CraftingHelper.getIngredient(json.get("handle"), context);
		if (json.has("tools")) {
			if (json.has("secondary") && json.has("ternary")) {
				parseTools(context, json.getAsJsonObject("tools").getAsJsonObject(), handle, main, CraftingHelper.getIngredient(json.get("secondary")), CraftingHelper.getIngredient(json.get("ternary")));
			} else if (json.has("secondary")) {
				parseTools(context, json.getAsJsonObject("tools").getAsJsonObject(), handle, main, CraftingHelper.getIngredient(json.get("secondary")));
			} else parseTools(context, json.get("tools").getAsJsonObject(), handle, main);
		}
		if (json.has("armor")) {
			if (json.has("secondary") && json.has("ternary")) {
				parseArmor(context, json.getAsJsonObject("armor").getAsJsonObject(), main, CraftingHelper.getIngredient(json.get("secondary")), CraftingHelper.getIngredient(json.get("ternary")));
			} else if (json.has("secondary")) {
				parseArmor(context, json.getAsJsonObject("armor").getAsJsonObject(), main, CraftingHelper.getIngredient(json.get("secondary")));
			} else parseArmor(context, json.get("armor").getAsJsonObject(), main);
		}
		return new EmptyRecipe();
	}
	
	private void parseTools(JsonContext ctx, JsonObject tools, Ingredient handle, Ingredient... materials) {
		Ingredient main= materials[0], secondary = materials.length >= 2 ? materials[1] : materials[0], ternary = materials.length >= 2 ? materials[1] : materials[0];
		if (tools.has("pickaxe")) ForgeRegistries.RECIPES.register(new ShapedRecipes("", 3, 3, getList(main, secondary, ternary, EMPTY, handle, EMPTY, EMPTY, handle, EMPTY), parseResult(tools.get("pickaxe").getAsJsonObject())).setRegistryName(ctx.getModId(), main.getMatchingStacks()[0].getItem().getRegistryName().getResourcePath() + "pickaxe"));
		if (tools.has("axe")) ForgeRegistries.RECIPES.register(new ShapedRecipes("", 2, 3, getList(main, secondary, ternary, handle, EMPTY, handle), parseResult(tools.get("axe").getAsJsonObject())).setRegistryName(ctx.getModId(), main.getMatchingStacks()[0].getItem().getRegistryName().getResourcePath() + "_axe"));
		if (tools.has("shovel")) ForgeRegistries.RECIPES.register(new ShapedRecipes("", 1, 3, getList(main, handle, handle), parseResult(tools.get("shovel").getAsJsonObject())).setRegistryName(ctx.getModId(), main.getMatchingStacks()[0].getItem().getRegistryName().getResourcePath() + "_shovel"));
		if (tools.has("hoe")) ForgeRegistries.RECIPES.register(new ShapedRecipes("", 2, 3, getList(main, secondary, EMPTY, handle, Ingredient.EMPTY, handle), parseResult(tools.get("hoe").getAsJsonObject())).setRegistryName(ctx.getModId(), main.getMatchingStacks()[0].getItem().getRegistryName().getResourcePath() + "_hoe"));		
		if (tools.has("sword")) ForgeRegistries.RECIPES.register(new ShapedRecipes("", 1, 3, getList(main, secondary, handle), parseResult(tools.get("sword").getAsJsonObject())).setRegistryName(ctx.getModId(), main.getMatchingStacks()[0].getItem().getRegistryName().getResourcePath() + "_sword"));
	}
	
	private void parseArmor(JsonContext ctx, JsonObject armor, Ingredient... materials) {
		Ingredient main= materials[0], secondary = materials.length >= 2 ? materials[1] : materials[0], ternary = materials.length >= 2 ? materials[1] : materials[0];
		if (armor.has("head")) ForgeRegistries.RECIPES.register(new ShapedRecipes("", 3, 2, getList(main, ternary, main, secondary, EMPTY, secondary), parseResult(armor.get("head").getAsJsonObject())).setRegistryName(ctx.getModId(), main.getMatchingStacks()[0].getItem().getRegistryName().getResourcePath() + "_head"));
		if (armor.has("chest")) ForgeRegistries.RECIPES.register(new ShapedRecipes("", 3, 3, getList(main, EMPTY, main, secondary, ternary, secondary, main, main, main), parseResult(armor.get("chest").getAsJsonObject())).setRegistryName(ctx.getModId(), main.getMatchingStacks()[0].getItem().getRegistryName().getResourcePath() + "_chest"));
		if (armor.has("legs")) ForgeRegistries.RECIPES.register(new ShapedRecipes("", 3, 3, getList(main, main, main, secondary, EMPTY, secondary, ternary, EMPTY, ternary), parseResult(armor.get("legs").getAsJsonObject())).setRegistryName(ctx.getModId(), main.getMatchingStacks()[0].getItem().getRegistryName().getResourcePath() + "_legs"));
		if (armor.has("boots")) ForgeRegistries.RECIPES.register(new ShapedRecipes("", 3, 2, getList(main, EMPTY, main, secondary, EMPTY, secondary), parseResult(armor.get("boots").getAsJsonObject())).setRegistryName(ctx.getModId(), main.getMatchingStacks()[0].getItem().getRegistryName().getResourcePath() + "_boots"));
	}
	
	private ItemStack parseResult(JsonObject obj) {
		int meta = 0, amount = 1;
		if (obj.has("meta")) meta = obj.get("meta").getAsInt();
		if (obj.has("amount")) amount = obj.get("amount").getAsInt();
		ItemStack stack = new ItemStack(Item.getByNameOrId(obj.get("item").getAsString()), amount, meta);
		if (obj.has("nbt")) stack.setTagCompound(parseNBT(obj.get("nbt").getAsJsonObject()));
		return stack;
	}
	
	private NonNullList<Ingredient> getList(Ingredient... ingredients) {
		NonNullList<Ingredient> returns = NonNullList.create();
		for (Ingredient ingredient : ingredients) if (ingredient != null) returns.add(ingredient);
		return returns;
	}
	
	private NBTTagCompound parseNBT(JsonObject nbt) {
		NBTTagCompound tag = new NBTTagCompound();
		for (Entry<String, JsonElement> entry : nbt.entrySet()) {
			if (entry.getValue().isJsonObject()) {
				
			} else if (entry.getValue().isJsonArray()) {
				
			} else if (entry.getValue().isJsonPrimitive()) {
				try {
					int value = entry.getValue().getAsInt();
					tag.setInteger(entry.getKey(), value);
				} catch (Exception e1) {
					try {
						double value = entry.getValue().getAsDouble();
						tag.setDouble(entry.getKey(), value);
					} catch (Exception e2) {
						String value = entry.getValue().getAsString();
						tag.setString(entry.getKey(), value);
					}
				}
			}
		}
		return tag;
	}
	
	public class EmptyRecipe implements IRecipe {
		
		private ResourceLocation registryName;
		
		@Override
		public IRecipe setRegistryName(ResourceLocation name) {
			this.registryName = name;
			return this;
		}

		@Override
		public ResourceLocation getRegistryName() {
			return registryName;
		}

		@Override
		public Class<IRecipe> getRegistryType() {
			return IRecipe.class;
		}

		@Override
		public boolean matches(InventoryCrafting inv, World worldIn) {
			return false;
		}

		@Override
		public ItemStack getCraftingResult(InventoryCrafting inv) {
			return ItemStack.EMPTY;
		}

		@Override
		public boolean canFit(int width, int height) {
			return false;
		}

		@Override
		public ItemStack getRecipeOutput() {
			return ItemStack.EMPTY;
		}
		
	}
	
}
