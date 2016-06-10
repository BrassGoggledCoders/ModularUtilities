package xyz.brassgoggledcoders.modularutilities.modules.redstone;

import net.minecraft.block.Block;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import xyz.brassgoggledcoders.boilerplate.module.Module;
import xyz.brassgoggledcoders.boilerplate.module.ModuleBase;
import xyz.brassgoggledcoders.modularutilities.ModularUtilities;

@Module(mod = ModularUtilities.MODID)
public class RedstoneModule extends ModuleBase {
	
	public static Block fused_quartz, redstone_sand;
	
	@Override
	public String getName() {
		return "Redstone";
	}
	@Override
	public void preInit(FMLPreInitializationEvent event)
	{
		fused_quartz = new BlockFusedQuartz();
		this.getBlockRegistry().registerBlock(fused_quartz);
		redstone_sand = new BlockRedstoneSand();
		this.getBlockRegistry().registerBlock(redstone_sand);
		/*
		 * TODO:
		 * - Resistor: opposite of repeater, changing settings changes resistance rather than delay
		 * - Redstone cable that's waterproof and freestanding
		 * - Second colour of redstone. Wire only.
		 * - Very basic Logic Gates
		 * - Variable strength emmitter 
		 */
	}
}
