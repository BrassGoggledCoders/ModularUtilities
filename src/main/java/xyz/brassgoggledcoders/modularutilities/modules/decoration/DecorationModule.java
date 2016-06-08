package xyz.brassgoggledcoders.modularutilities.modules.decoration;

import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import xyz.brassgoggledcoders.boilerplate.module.Module;
import xyz.brassgoggledcoders.boilerplate.module.ModuleBase;
import xyz.brassgoggledcoders.modularutilities.ModularUtilities;

@Module(mod = ModularUtilities.MODID)
public class DecorationModule extends ModuleBase {
	
	@Override
	public String getName() {
		return "Decoration";
	}
	@Override
	public void preInit(FMLPreInitializationEvent event)
	{
		/*	TODO:
		 * -  
		 * - Stairs version of Path/Grass/Dirt.
		 * - Turf & Leaf Covers  
		 */
	}
}
