package xyz.brassgoggledcoders.modularutilities.modules.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.item.ItemStack;

public class EnchantmentFlameTouch extends Enchantment
{
	public EnchantmentFlameTouch()
	{
		super(Enchantment.Rarity.RARE, EnumEnchantmentType.DIGGER, EnchantmentsModule.hand);
		this.setName("flametouch");
	}

	@Override
	public int getMinEnchantability(int level)
	{
		return 21;
	}

	@Override
	public int getMaxEnchantability(int level)
	{
		return super.getMinEnchantability(level) + 50;
	}

	@Override
	public int getMaxLevel()
	{
		return 1;
	}

	@Override
	public boolean canApplyAtEnchantingTable(ItemStack is)
	{
		return this.canApply(is);
	}

	@Override
	public boolean canApplyTogether(Enchantment enchantment)
	{
		return (enchantment == Enchantment.getEnchantmentByLocation("silk_touch")) ? false : true;
	}
}
