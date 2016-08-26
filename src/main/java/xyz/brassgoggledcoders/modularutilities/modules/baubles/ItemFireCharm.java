package xyz.brassgoggledcoders.modularutilities.modules.baubles;

import baubles.api.BaubleType;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Optional;

public class ItemFireCharm extends ItemBaubleBase {

	public ItemFireCharm() {
		super("fire_charm");
	}

	@Override
	@Optional.Method(modid = "Baubles")
	public BaubleType getBaubleType(ItemStack arg0) {
		return BaubleType.AMULET;
	}

	@Override
	@Optional.Method(modid = "Baubles")
	public void onWornTick(ItemStack stack, EntityLivingBase living) {
		if(living instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) living;
			if(player.isBurning() && !(player.isInLava()) && !(player.isImmuneToFire())) {
				player.extinguish();
				// if(stack.attemptDamageItem(1, player.getRNG())) {
				// PlayerHandler.getPlayerBaubles(player).setInventorySlotContents(0, null);
				// player.setFire(5);
				// player.getEntityWorld().playSound(player, player.getPosition(),
				// SoundEvent.REGISTRY.getObject(new ResourceLocation("block.furnace.fire_crackle")),
				// SoundCategory.AMBIENT, 1F, 1F);
				// }
			}
		}
	}
}
