package dev.anime.gems.items;

import com.google.common.collect.Multimap;

import dev.anime.gems.Main;
import dev.anime.gems.init.ModItems;
import dev.anime.gems.utils.ICustomModel;
import dev.anime.gems.utils.ItemStackHelper;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;

public class GemHoe extends ItemHoe implements ICustomModel {

	private static final String[] types = new String[] { "ruby" };
	
	public GemHoe(ToolMaterial material) {
		super(material);
		this.setRegistryName("gem_hoe");
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
    	return getToolMaterial(stack).getDamageVsEntity();
    }
    
    public float getAttackSpeed(ItemStack stack) {
    	return 2.8F;
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
		for (int i = 0; i < locations.length; i++) locations[i] = new ResourceLocation(Main.MODID, types[i] + "_hoe");
		Main.PROXY.registerModelVariants(this, locations);
	}
    
}
