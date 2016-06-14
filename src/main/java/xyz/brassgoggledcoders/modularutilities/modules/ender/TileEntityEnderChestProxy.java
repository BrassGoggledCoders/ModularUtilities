package xyz.brassgoggledcoders.modularutilities.modules.ender;

import java.util.UUID;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryEnderChest;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class TileEntityEnderChestProxy extends TileEntity
{
	private UUID playerID;
	public IItemHandler handler;

	@Override
	public void readFromNBT(NBTTagCompound compound)
	{
		this.playerID = compound.getUniqueId("PlayerID");
		handler = new EnderProxyInventoryHandler(this.getWorld(), playerID);
		super.readFromNBT(compound);
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound)
	{
		compound.setUniqueId("PlayerID", this.playerID);
		super.writeToNBT(compound);
		return compound;
	}

	public void setPlacerUUID(UUID uniqueID)
	{
		this.playerID = uniqueID;
	}

	public UUID getPlacerUUID()
	{
		return playerID;
	}

	public EntityPlayer getPlacer()
	{
		return this.getWorld().getPlayerEntityByUUID(getPlacerUUID());
	}

	public InventoryEnderChest getEnderInventory()
	{
		return this.getWorld().getPlayerEntityByUUID(getPlacerUUID()).getInventoryEnderChest();
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing)
	{
		if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
			return true;
		return super.hasCapability(capability, facing);
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing)
	{
		if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
			return (T) handler;
		return super.getCapability(capability, facing);
	}
}
