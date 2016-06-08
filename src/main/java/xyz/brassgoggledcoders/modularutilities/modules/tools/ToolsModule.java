package xyz.brassgoggledcoders.modularutilities.modules.tools;

import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import xyz.brassgoggledcoders.boilerplate.module.Module;
import xyz.brassgoggledcoders.boilerplate.module.ModuleBase;
import xyz.brassgoggledcoders.modularutilities.ModularUtilities;

@Module(mod = ModularUtilities.MODID)
public class ToolsModule extends ModuleBase {
	
	public static ItemMachete machete;
	
	@Override
	public String getName() {
		return "Tools";
	}
	@Override
	public void preInit(FMLPreInitializationEvent event)
	{
		machete = new ItemMachete();
		this.getItemRegistry().registerItem(machete);
		/*
		 * TODO:
		 * - Ender Glove
		 * - Ender Pocket
		 */
	}
}
