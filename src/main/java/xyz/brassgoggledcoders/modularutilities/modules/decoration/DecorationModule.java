package xyz.brassgoggledcoders.modularutilities.modules.decoration;

import net.minecraft.block.Block;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import xyz.brassgoggledcoders.boilerplate.module.Module;
import xyz.brassgoggledcoders.boilerplate.module.ModuleBase;
import xyz.brassgoggledcoders.modularutilities.ModularUtilities;

@Module(mod = ModularUtilities.MODID)
public class DecorationModule extends ModuleBase
{

	public static Block turf, leaf_cover;

	@Override
	public String getName()
	{
		return "Decoration";
	}

	@Override
	public void preInit(FMLPreInitializationEvent event)
	{
		turf = new BlockTurf();
		this.getBlockRegistry().registerBlock(turf);

		leaf_cover = new BlockLeafCover();
		this.getBlockRegistry().registerBlock(leaf_cover);
		/*
		 * TODO:
		 * - Stairs version of Path/Grass/Dirt/Smoothstone
		 * - Turf & Leaf Covers
		 * - Smooth Glowstone https://i.imgur.com/lywsYXl.jpg
		 * - Polished Endstone and Obsidian. 4x4.
		 * - Brick + Netherbrick in 4x4 is special brick texture http://imgur.com/a/PVvHP#4
		 * - Ender Pearl storage block
		 */
	}
}
