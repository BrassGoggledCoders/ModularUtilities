package xyz.brassgoggledcoders.modularutilities.modules.enchantments;

import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;

public class EnchantmentProspector extends CustomEnchantment
{

	public EnchantmentProspector(Rarity rarityIn, EnumEnchantmentType typeIn, EntityEquipmentSlot[] slots,
			int multiplier, int minEnchant, int maxLevel)
	{
		super(rarityIn, typeIn, slots, multiplier, minEnchant, maxLevel);
	}

	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack)
	{
		return (stack.getItem().getToolClasses(stack).contains("axe")) ? false : super.canApplyAtEnchantingTable(stack);
	}

}
