package xyz.brassgoggledcoders.modularutilities.modules.explosives;

import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import xyz.brassgoggledcoders.boilerplate.module.ModuleBase;

//@Module(mod = ModularUtilities.MODID)
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
		 * - Mining Charge: drops all exploded blocks
		 * - Flamethrower
		 * - Gunpowder + String = Fuses. Place like redstone, light one end, and fire will quickly travel down it 
		 */
	}
}
