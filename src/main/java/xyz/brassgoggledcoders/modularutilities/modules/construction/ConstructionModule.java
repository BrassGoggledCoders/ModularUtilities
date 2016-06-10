package xyz.brassgoggledcoders.modularutilities.modules.construction;

import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import xyz.brassgoggledcoders.boilerplate.module.Module;
import xyz.brassgoggledcoders.boilerplate.module.ModuleBase;
import xyz.brassgoggledcoders.modularutilities.ModularUtilities;

@Module(mod = ModularUtilities.MODID)
public class ConstructionModule extends ModuleBase {
	
	@Override
	public String getName() {
		return "Construction";
	}
	@Override
	public void preInit(FMLPreInitializationEvent event)
	{
		/*
		 * TODO:
		 * - Filler Fluid (turns into dirt)
		 * - Liquid Concrete: hardens into concrete 
		 * - Rebar: Makes solid concrete harder 
		 * - Laser level
		 */
	}
}
