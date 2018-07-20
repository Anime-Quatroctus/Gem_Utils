package dev.anime.gems.items;

import java.util.Set;

import com.google.common.collect.Multimap;

import dev.anime.gems.Main;
import dev.anime.gems.init.ModItems;
import dev.anime.gems.utils.ICustomModel;
import dev.anime.gems.utils.ItemStackHelper;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

public class GemAxe extends ItemAxe implements ICustomModel {

	private Set<Block> effectiveBlocks;
	
	private static final String[] types = new String[] { "ruby" };
	
	public GemAxe(ToolMaterial material) {
		super(material, 0, 0);
		effectiveBlocks = ReflectionHelper.getPrivateValue(ItemTool.class, this, "effectiveBlocks", "field_150914_c");
		this.setRegistryName("gem_axe");
		this.setUnlocalizedName(getRegistryName().toString());
		this.setHasSubtypes(true);
		ModItems.ITEMS.add(this);
	}
	
	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
		if (tab == getCreativeTab()) {
			items.add(ItemStackHelper.createNBTItem(new ItemStack(this), "ruby"));
		}
	}
	
    public ToolMaterial getToolMaterial(ItemStack stack) {
    	switch(stack.getTagCompound().getString("gem_type")) {
    		case "ruby": return ToolMaterial.DIAMOND;
    	}
    	return this.toolMaterial;
    }
    
    public int getHarvestLevel(ItemStack stack) {
    	return getToolMaterial(stack).getHarvestLevel();
    }
    
    public int getDurability(ItemStack stack) {
    	return getToolMaterial(stack).getMaxUses();
    }
    
    @Override
    public int getItemEnchantability(ItemStack stack) {
    	return getToolMaterial(stack).getEnchantability();
    }
    
    public float getEfficiency(ItemStack stack) {
    	return getToolMaterial(stack).getEfficiencyOnProperMaterial();
    }
    
    public float getDamageVsEntity(ItemStack stack) {
    	return 8.0F;
    }
    
    public float getAttackSpeed(ItemStack stack) {
    	return 3.1F;
    }
    
    @Override
    public int getMaxDamage(ItemStack stack) {
    	return getDurability(stack);
    }
    
    @Override
    public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot slot, ItemStack stack) {
        Multimap<String, AttributeModifier> multimap = super.getAttributeModifiers(slot, stack);
        if (slot == EntityEquipmentSlot.MAINHAND) {
            multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Tool modifier", (double) getDamageVsEntity(stack), 0));
            multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getName(), new AttributeModifier(ATTACK_SPEED_MODIFIER, "Tool modifier", (double) getAttackSpeed(stack), 0));
        }
        return multimap;
    }
    
    @Override
    public int getHarvestLevel(ItemStack stack, String toolClass, @javax.annotation.Nullable net.minecraft.entity.player.EntityPlayer player, @javax.annotation.Nullable IBlockState blockState) {
    	if (getToolClasses(stack).contains(toolClass)) {
    		return getHarvestLevel(stack);
    	} else return -1;
    }
	
    @Override
    public float getStrVsBlock(ItemStack stack, IBlockState state) {
        Material material = state.getMaterial();
        for (String type : getToolClasses(stack))  if (state.getBlock().isToolEffective(type, state)) return getEfficiency(stack);
        return this.effectiveBlocks.contains(state.getBlock()) || material == Material.WOOD || material == Material.PLANTS || material == Material.VINE ? getEfficiency(stack) : 1.0F;
    }
	
	@Override
	public int getMaxMeta() {
		return 0;
	}
	
	@Override//TODO
	public String getUnlocalizedName(ItemStack stack) {
		return super.getUnlocalizedName(stack);
	}
	
	@Override
	public void handleModelLocations() {
		ResourceLocation[] locations = new ResourceLocation[types.length];
		for (int i = 0; i < locations.length; i++) locations[i] = new ResourceLocation(Main.MODID, types[i] + "_axe");
		Main.PROXY.registerModelVariants(this, locations);
	}
    
}
