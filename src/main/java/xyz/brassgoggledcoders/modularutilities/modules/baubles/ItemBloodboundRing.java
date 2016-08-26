package xyz.brassgoggledcoders.modularutilities.modules.baubles;

import baubles.api.BaubleType;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.fml.common.Optional;
import xyz.brassgoggledcoders.boilerplate.items.ItemBaubleBase;

public class ItemBloodboundRing extends ItemBaubleBase {

	public ItemBloodboundRing() {
		super("bloodbound_ring");
		this.setMaxStackSize(1);
	}

	@Override
	@Optional.Method(modid = "Baubles")
	public BaubleType getBaubleType(ItemStack arg0) {
		return BaubleType.RING;
	}

	@Override
	@Optional.Method(modid = "Baubles")
	public boolean canUnequip(ItemStack arg0, EntityLivingBase arg1) {
		return false;
	}

	@Override
	@Optional.Method(modid = "Baubles")
	public void onWornTick(ItemStack stack, EntityLivingBase living) {
		if(living.getHealth() == living.getMaxHealth()) {
			living.setHealth(living.getMaxHealth() * 0.75F);
			living.addPotionEffect(
					new PotionEffect(Potion.getPotionFromResourceLocation("strength"), 120, 2, false, false));
		}
	}
}
