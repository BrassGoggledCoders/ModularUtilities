package xyz.brassgoggledcoders.modularutilities.modules.baubles;

import baubles.api.BaubleType;
import baubles.api.IBauble;
import baubles.common.lib.PlayerHandler;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import xyz.brassgoggledcoders.boilerplate.items.ItemBase;

public class ItemFireCharm extends ItemBase implements IBauble {

	public ItemFireCharm() {
		super("fire_charm");
		this.setMaxStackSize(1);
		this.setMaxDamage(800);
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
	public BaubleType getBaubleType(ItemStack arg0) {
		return BaubleType.AMULET;
	}

	@Override
	public void onEquipped(ItemStack arg0, EntityLivingBase arg1) {

	}

	@Override
	public void onUnequipped(ItemStack arg0, EntityLivingBase arg1) {

	}

	@Override
	// TODO Perform on server and sync
	public void onWornTick(ItemStack stack, EntityLivingBase living) {
		if(living instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) living;
			if(player.isBurning() && !(player.isInLava()) && !(player.isImmuneToFire())) {
				player.extinguish();
				if(stack.attemptDamageItem(1, player.getRNG())) {
					PlayerHandler.getPlayerBaubles(player).setInventorySlotContents(0, null);
					player.setFire(5);
					player.getEntityWorld().playSound(player, player.getPosition(),
							SoundEvent.REGISTRY.getObject(new ResourceLocation("block.furnace.fire_crackle")),
							SoundCategory.AMBIENT, 1F, 1F);
				}
			}
		}
	}
}
