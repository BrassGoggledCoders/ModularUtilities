package xyz.brassgoggledcoders.modularutilities.modules.ender;

import java.util.UUID;

import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;

public class EnderProxyInventoryHandler implements IItemHandler
{
	private IItemHandler enderChest;

	public void setWrappedEnderInventory(World world, UUID playerID)
	{
		enderChest = new InvWrapper(world.getPlayerEntityByUUID(playerID).getInventoryEnderChest());
	}

	public IItemHandler getEInv()
	{
		return enderChest;
	}

	@Override
	public int getSlots()
	{
		return (getEInv() != null) ? getEInv().getSlots() : 0;
	}

	@Override
	public ItemStack getStackInSlot(int slot)
	{
		return getEInv().getStackInSlot(slot);
	}

	@Override
	public ItemStack insertItem(int slot, ItemStack stack, boolean simulate)
	{
		return getEInv().insertItem(slot, stack, simulate);
	}

	@Override
	public ItemStack extractItem(int slot, int amount, boolean simulate)
	{
		return getEInv().extractItem(slot, amount, simulate);
	}
}
