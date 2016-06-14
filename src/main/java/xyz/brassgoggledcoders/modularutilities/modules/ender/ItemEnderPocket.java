package xyz.brassgoggledcoders.modularutilities.modules.ender;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import xyz.brassgoggledcoders.boilerplate.items.ItemBase;

public class ItemEnderPocket extends ItemBase
{

	public ItemEnderPocket()
	{
		super("ender_pocket");
	}

	public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn,
			EnumHand hand)
	{
		playerIn.displayGUIChest(playerIn.getInventoryEnderChest());
		return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemStackIn);
	}
}
