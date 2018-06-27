package xyz.brassgoggledcoders.modularutilities.modules.ender;

import java.util.UUID;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryEnderChest;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;

public class TileEntityEnderChestProxy extends TileEntity {
	private UUID playerID;
	public EnderProxyInventoryHandler handler;

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		if(!compound.getString("player_id").isEmpty()) {
			playerID = UUID.fromString(compound.getString("player_id"));
		}
		super.readFromNBT(compound);
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		if(playerID != null) {
			compound.setString("player_id", playerID.toString());
		}
		super.writeToNBT(compound);
		return compound;
	}

	public void setPlacerUUID(UUID uniqueID) {
		playerID = uniqueID;
	}

	public UUID getPlacerUUID() {
		return playerID;
	}

	public EntityPlayer getPlacer() {
		return getWorld().getPlayerEntityByUUID(getPlacerUUID());
	}

	public InventoryEnderChest getEnderInventory() {
		return getWorld().getPlayerEntityByUUID(getPlacerUUID()).getInventoryEnderChest();
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY && playerID != null) {
			return true;
		}
		return super.hasCapability(capability, facing);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY && playerID != null) {
			EnderProxyInventoryHandler handler = new EnderProxyInventoryHandler();
			handler.player = getWorld().getPlayerEntityByUUID(playerID);
			return (T) handler;
		}
		return super.getCapability(capability, facing);
	}
}
