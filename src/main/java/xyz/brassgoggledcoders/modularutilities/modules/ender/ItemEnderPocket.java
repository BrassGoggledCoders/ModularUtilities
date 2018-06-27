package xyz.brassgoggledcoders.modularutilities.modules.ender;

import javax.annotation.Nonnull;

import com.teamacronymcoders.base.items.ItemBase;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.world.World;

public class ItemEnderPocket extends ItemBase {

	public ItemEnderPocket() {
		super("ender_pocket");
	}

	@Override
	@Nonnull
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, @Nonnull EnumHand hand) {
		playerIn.displayGUIChest(playerIn.getInventoryEnderChest());
		return new ActionResult<>(EnumActionResult.SUCCESS, playerIn.getHeldItem(hand));
	}
}
