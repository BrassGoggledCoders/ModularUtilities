package xyz.brassgoggledcoders.modularutilities.modules.construction;

import java.util.LinkedHashMap;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import xyz.brassgoggledcoders.boilerplate.api.IDebuggable;

public class TileEntityLiquidConcrete extends TileEntity implements ITickable, IDebuggable { // TODO Find a better
																								// way...

	private int dryingTicks = 150;

	public void setDryingTicks(int dryingTicks) {
		this.dryingTicks = dryingTicks;
	}

	@Override
	public void update() {
		if(dryingTicks == 0) {
			getWorld().markTileEntityForRemoval(this);
			getWorld().setBlockState(getPos(), ConstructionModule.concrete.getDefaultState());
		}
		else {
			dryingTicks--;
		}
	}

	@Override
	public LinkedHashMap<String, String> getDebugStrings() {
		LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
		map.put("dryingTicks", String.valueOf(dryingTicks));
		return map;
	}
}
