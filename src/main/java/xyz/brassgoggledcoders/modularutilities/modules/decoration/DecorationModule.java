package xyz.brassgoggledcoders.modularutilities.modules.decoration;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import xyz.brassgoggledcoders.boilerplate.blocks.BlockBase;
import xyz.brassgoggledcoders.boilerplate.module.Module;
import xyz.brassgoggledcoders.boilerplate.module.ModuleBase;
import xyz.brassgoggledcoders.modularutilities.ModularUtilities;
import xyz.brassgoggledcoders.modularutilities.modules.decoration.BlockLeafCover.EnumLeafCoverBlockType;

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
	public void postInit(FMLPostInitializationEvent event)
	{
		for(int i = 0; i < EnumLeafCoverBlockType.VALUES.length - 2; i++)
			GameRegistry.addRecipe(new ItemStack(leaf_cover, 1, i), "XX", 'X', new ItemStack(Blocks.LEAVES, 1, i));
		GameRegistry.addRecipe(new ItemStack(leaf_cover, 1, 4), "XX", 'X', new ItemStack(Blocks.LEAVES2, 1, 0));
		GameRegistry.addRecipe(new ItemStack(leaf_cover, 1, 5), "XX", 'X', new ItemStack(Blocks.LEAVES2, 1, 1));
	}

}
