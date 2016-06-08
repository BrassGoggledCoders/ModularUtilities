package xyz.brassgoggledcoders.modularutilities.modules.explosives;

import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import xyz.brassgoggledcoders.boilerplate.module.Module;
import xyz.brassgoggledcoders.boilerplate.module.ModuleBase;
import xyz.brassgoggledcoders.modularutilities.ModularUtilities;

@Module(mod = ModularUtilities.MODID)
public class DestructionModule extends ModuleBase {
	
	@Override
	public String getName() {
		return "Destruction";
	}
	@Override
	public void preInit(FMLPreInitializationEvent event)
	{
		/*
		 * TODO:
		 * - Splitter TNT 
		 * - Incendiary TNT
		 * - Modular TNT
		 * - Flamethrower
		 */
	}
}
