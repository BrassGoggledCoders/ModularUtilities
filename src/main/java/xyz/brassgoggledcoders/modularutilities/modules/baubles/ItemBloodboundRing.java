package xyz.brassgoggledcoders.modularutilities.modules.baubles;

import baubles.api.BaubleType;
import baubles.api.IBauble;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

public class ItemBloodboundRing extends ItemBaubleBase implements IBauble {

	public ItemBloodboundRing() {
		super("bloodbound_ring");
		this.setMaxStackSize(1);
	}

	@Override
	public BaubleType getBaubleType(ItemStack arg0) {
		return BaubleType.RING;
	}

	@Override
	public boolean canUnequip(ItemStack arg0, EntityLivingBase arg1) {
		return false;
	}

	@Override
	public void onWornTick(ItemStack stack, EntityLivingBase living) {
		if(living.getHealth() == living.getMaxHealth()) {
			living.setHealth(living.getMaxHealth() * 0.75F);
			living.addPotionEffect(
					new PotionEffect(Potion.getPotionFromResourceLocation("strength"), 30, 3, false, false));
		}
	}
}
