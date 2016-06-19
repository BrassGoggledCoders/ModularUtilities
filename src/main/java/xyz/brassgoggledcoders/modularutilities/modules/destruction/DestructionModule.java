package xyz.brassgoggledcoders.modularutilities.modules.destruction;

import net.minecraft.block.Block;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import xyz.brassgoggledcoders.boilerplate.module.Module;
import xyz.brassgoggledcoders.boilerplate.module.ModuleBase;
import xyz.brassgoggledcoders.modularutilities.ModularUtilities;

@Module(mod = ModularUtilities.MODID)
public class DestructionModule extends ModuleBase
{
	public static Block splitter_tnt;

	@Override
	public String getName()
	{
		return "Destruction";
	}

	@Override
	public void preInit(FMLPreInitializationEvent event)
	{
		splitter_tnt = new BlockSplitterTNT("splitter_tnt");
		getBlockRegistry().registerBlock(splitter_tnt);
		/*
		 * TODO:
		 * - Incendiary TNT
		 * - Modular TNT
		 * - Mining Charge: drops all exploded blocks
		 * - Flamethrower
		 * - Gunpowder + String = Fuses. Place like redstone, light one end, and fire will quickly travel down it
		 * - Method of land flattening
		 */
	}
}
