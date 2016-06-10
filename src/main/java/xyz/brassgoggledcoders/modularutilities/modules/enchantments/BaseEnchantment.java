package xyz.brassgoggledcoders.modularutilities.modules.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.inventory.EntityEquipmentSlot;

public class BaseEnchantment extends Enchantment {
	private int rarityMultiplier; 
	private int maxLevel;

	
	public BaseEnchantment(Rarity rarityIn, EnumEnchantmentType typeIn, EntityEquipmentSlot[] slots, int rarityMultiplier, int maxLevel) {
		super(rarityIn, typeIn, slots);
		this.rarityMultiplier = rarityMultiplier;
		this.maxLevel = maxLevel;
	}
	
	@Override
	public int getMinEnchantability(int level)
	{
		return 5 + (rarityMultiplier * (level - 1));
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
