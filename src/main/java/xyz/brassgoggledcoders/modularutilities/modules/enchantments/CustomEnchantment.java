package xyz.brassgoggledcoders.modularutilities.modules.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.inventory.EntityEquipmentSlot;

public class CustomEnchantment extends Enchantment
{

	private int multiplier;
	private int minEnchant;
	private int maxLevel;

	public CustomEnchantment(Rarity rarityIn, EnumEnchantmentType typeIn, EntityEquipmentSlot[] slots, int multiplier,
			int minEnchant, int maxLevel)
	{
		super(rarityIn, typeIn, slots);
		this.multiplier = multiplier;
		this.minEnchant = minEnchant;
		this.maxLevel = maxLevel;
	}

	@Override
	public int getMinEnchantability(int level)
	{
		// Either a fixed value, if minEnchant != 0, or per level, if multiplier != 0
		return minEnchant + multiplier * (level - 1);
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
