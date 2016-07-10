package xyz.brassgoggledcoders.modularutilities.modules.miscellaneous;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import xyz.brassgoggledcoders.boilerplate.items.ItemBase;

public class ItemSwissArmyKnife extends ItemBase {
	public ItemSwissArmyKnife() {
		super("swiss_army_knife");
		this.setMaxStackSize(1);
	}

	@Override
	public boolean onBlockStartBreak(ItemStack itemstack, BlockPos pos, EntityPlayer player) {
		if(player.getEntityWorld().isRemote)
			return false;

		// TODO Utils method for this
		NBTTagCompound tag = new NBTTagCompound();
		tag.setBoolean("isSwiss", true);
		ItemStack stack = new ItemStack(Items.IRON_PICKAXE);
		stack.setTagCompound(tag);
		player.inventory.setInventorySlotContents(player.inventory.currentItem, stack);
		return true;
	}
}
