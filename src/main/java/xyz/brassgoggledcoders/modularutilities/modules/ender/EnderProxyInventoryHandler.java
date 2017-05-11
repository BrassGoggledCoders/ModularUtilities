package xyz.brassgoggledcoders.modularutilities.modules.ender;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;

import javax.annotation.Nonnull;

public class EnderProxyInventoryHandler implements IItemHandler {
	private IItemHandler enderChest;
	public EntityPlayer player;

	public IItemHandler getEInv() {
		if(enderChest == null && player != null)
			enderChest = new InvWrapper(player.getInventoryEnderChest());
		return enderChest;
	}

	@Override
	public int getSlots() {
		return getEInv() != null ? getEInv().getSlots() : 0;
	}

	@Override
	@Nonnull
	public ItemStack getStackInSlot(int slot) {
		return getEInv().getStackInSlot(slot);
	}

	@Override
	@Nonnull
	public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
		return getEInv().insertItem(slot, stack, simulate);
	}

	@Override
	@Nonnull
	public ItemStack extractItem(int slot, int amount, boolean simulate) {
		return getEInv().extractItem(slot, amount, simulate);
	}

	@Override
	public int getSlotLimit(int slot) {
		return getEInv().getSlotLimit(slot);
	}
}
