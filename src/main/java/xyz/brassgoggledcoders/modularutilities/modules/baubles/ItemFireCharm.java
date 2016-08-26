package xyz.brassgoggledcoders.modularutilities.modules.baubles;

import baubles.api.BaubleType;
import baubles.api.IBauble;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class ItemFireCharm extends ItemBaubleBase implements IBauble {

	public ItemFireCharm() {
		super("fire_charm");
		this.setMaxStackSize(1);
	}

	@Override
	public BaubleType getBaubleType(ItemStack arg0) {
		return BaubleType.AMULET;
	}

	@Override
	// TODO Perform on server and sync
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
