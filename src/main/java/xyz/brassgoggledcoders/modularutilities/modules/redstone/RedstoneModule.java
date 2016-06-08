package xyz.brassgoggledcoders.modularutilities.modules.redstone;

import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import xyz.brassgoggledcoders.boilerplate.blocks.BlockBase;
import xyz.brassgoggledcoders.boilerplate.module.Module;
import xyz.brassgoggledcoders.boilerplate.module.ModuleBase;
import xyz.brassgoggledcoders.modularutilities.ModularUtilities;

@Module(mod = ModularUtilities.MODID)
public class RedstoneModule extends ModuleBase {
	
	public static BlockBase blockFusedQuartz;
	
	@Override
	public String getName() {
		return "Redstone";
	}
	@Override
	public void preInit(FMLPreInitializationEvent event)
	{
		blockFusedQuartz = new BlockFusedQuartz();
		this.getBlockRegistry().registerBlock(blockFusedQuartz);
		/*
		 * TODO:
		 * - Redstone Sand
		 * - Resistor: opposite of repeater, changing settings changes resistance rather than delay
		 */
	}
}
