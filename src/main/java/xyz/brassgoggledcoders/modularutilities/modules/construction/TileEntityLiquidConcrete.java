package xyz.brassgoggledcoders.modularutilities.modules.construction;

import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;

public class TileEntityLiquidConcrete extends TileEntity implements ITickable { // TODO Find a better way...

	private int dryingTicks = 150;

	public void setDryingTicks(int dryingTicks) {
		this.dryingTicks = dryingTicks;
	}

	@Override
	public void update() {
		if(dryingTicks == 0) {
			getWorld().markTileEntityForRemoval(this);
			getWorld().setBlockState(getPos(), Blocks.STONE.getDefaultState());
		}
		else {
			dryingTicks--;
		}
	}
}
