package xyz.brassgoggledcoders.modularutilities.modules.ender;

import java.util.UUID;

import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;

public class EnderProxyInventoryHandler implements IItemHandler
{
	private IItemHandler enderChest;

	public EnderProxyInventoryHandler(World world, UUID playerID)
	{
		enderChest = new InvWrapper(world.getPlayerEntityByUUID(playerID).getInventoryEnderChest());
	}

	public IItemHandler getWrappedEnderInventory()
	{
		return enderChest;
	}

	@Override
	public int getSlots()
	{
		return getWrappedEnderInventory().getSlots();
	}

	@Override
	public ItemStack getStackInSlot(int slot)
	{
		return getWrappedEnderInventory().getStackInSlot(slot);
	}

	@Override
	public ItemStack insertItem(int slot, ItemStack stack, boolean simulate)
	{
		return getWrappedEnderInventory().insertItem(slot, stack, simulate);
	}

	@Override
	public ItemStack extractItem(int slot, int amount, boolean simulate)
	{
		return getWrappedEnderInventory().extractItem(slot, amount, simulate);
	}
}
