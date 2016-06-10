package xyz.brassgoggledcoders.modularutilities.modules.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.inventory.EntityEquipmentSlot;

public class CustomEnchantment extends Enchantment {
	
	private int multiplier; 
	private int maxLevel;
	private int minEnchant = 0;
	private Enchantment[] conflicts;

	
	public CustomEnchantment(Rarity rarityIn, EnumEnchantmentType typeIn, EntityEquipmentSlot[] slots, int multiplier, int maxLevel) {
		super(rarityIn, typeIn, slots);
		this.multiplier = multiplier;
		this.maxLevel = maxLevel;
	}
	
	public CustomEnchantment(Rarity rarityIn, EnumEnchantmentType typeIn, EntityEquipmentSlot[] slots, int maxLevel, int minEnchant, Enchantment[] conflicts) {
		super(rarityIn, typeIn, slots);
		this.multiplier = 0;
		this.maxLevel = maxLevel;
		this.minEnchant = minEnchant;
		this.conflicts = conflicts;
	}
	
	//TODO Doesn't appear to work, or anvil overrides
	@Override
	public boolean canApplyTogether(Enchantment enchantment)
	{
		boolean isConflicted = false;
		for(int i = 0; i < conflicts.length; i++)
		{
			if(conflicts[i] == enchantment)
				isConflicted = true;
		}
		return isConflicted;
	}
	
	@Override
	public int getMinEnchantability(int level)
	{
		//Either a fixed value, if minEnchant != 0, or per level, if multiplier != 0
		return minEnchant + (multiplier * (level - 1));
	}

	@Override
	public int getMaxEnchantability(int level)
	{
		return super.getMinEnchantability(level) + 50;
	}
	
	@Override
	public int getMaxLevel()
	{
		return maxLevel;
	}
}
