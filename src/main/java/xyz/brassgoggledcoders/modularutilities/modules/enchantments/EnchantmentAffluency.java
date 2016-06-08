package xyz.brassgoggledcoders.modularutilities.modules.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.util.ResourceLocation;

/**
 * @author Surseance
 *
 */
public class EnchantmentAffluency extends Enchantment
{
	public EnchantmentAffluency()
	{
		super(Enchantment.Rarity.RARE, EnumEnchantmentType.DIGGER, EnchantmentsModule.hand);
		this.setName("affluency");
		Enchantment.REGISTRY.register(71, new ResourceLocation("affluency"), this);
	}

	@Override
	public int getMinEnchantability(int level)
	{
		return 5 + (11 * (level - 1));
	}

	@Override
	public int getMaxEnchantability(int level)
	{
		return super.getMinEnchantability(level) + 50;
	}

	@Override
	public int getMaxLevel()
	{
		return 3;
	}
}
