package xyz.brassgoggledcoders.modularutilities.modules.enchantments;

import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;

public class EnchantmentAffluency extends CustomEnchantment
{

	public EnchantmentAffluency(Rarity rarityIn, EnumEnchantmentType typeIn, EntityEquipmentSlot[] slots,
			int multiplier, int minEnchant, int maxLevel)
	{
		super(rarityIn, typeIn, slots, multiplier, minEnchant, maxLevel);
	}

	public boolean canApplyAtEnchantingTable(ItemStack stack)
	{
		if(stack.getItem() instanceof ItemSword)
			return true;
		return this.type.canEnchantItem(stack.getItem());
	}
}
