package xyz.brassgoggledcoders.modularutilities.modules.baubles;

import baubles.api.IBauble;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import xyz.brassgoggledcoders.boilerplate.items.ItemBase;

public abstract class ItemBaubleBase extends ItemBase implements IBauble {

	public ItemBaubleBase(String name) {
		super(name);
	}

	@Override
	public boolean canEquip(ItemStack arg0, EntityLivingBase arg1) {
		return true;
	}

	@Override
	public boolean canUnequip(ItemStack arg0, EntityLivingBase arg1) {
		return true;
	}

	@Override
	public void onEquipped(ItemStack arg0, EntityLivingBase arg1) {}

	@Override
	public void onUnequipped(ItemStack arg0, EntityLivingBase arg1) {}

	@Override
	public void onWornTick(ItemStack stack, EntityLivingBase living) {}
}
