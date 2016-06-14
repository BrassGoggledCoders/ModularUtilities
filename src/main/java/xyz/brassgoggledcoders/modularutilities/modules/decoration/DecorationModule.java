package xyz.brassgoggledcoders.modularutilities.modules.decoration;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import xyz.brassgoggledcoders.boilerplate.blocks.BlockBase;
import xyz.brassgoggledcoders.boilerplate.module.Module;
import xyz.brassgoggledcoders.boilerplate.module.ModuleBase;
import xyz.brassgoggledcoders.modularutilities.ModularUtilities;

@Module(mod = ModularUtilities.MODID)
public class DecorationModule extends ModuleBase
{

	public static Block turf, leaf_cover, clinker_brick;

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

		clinker_brick = new BlockBase(Material.ROCK, "clinker_brick");
		getBlockRegistry().registerBlock(clinker_brick);
		/*
		 * TODO:
		 * - Stairs version of Path/Grass/Dirt/Smoothstone
		 * - Turf & Leaf Covers
		 * - Smooth Glowstone https://i.imgur.com/lywsYXl.jpg
		 * - Polished Endstone and Obsidian. 4x4.
		 * - Ender Pearl storage block
		 */
	}

	@Override
	public void init(FMLInitializationEvent event)
	{

	}

}
