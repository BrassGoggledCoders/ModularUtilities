package xyz.brassgoggledcoders.modularutilities.modules.construction;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import xyz.brassgoggledcoders.boilerplate.module.Module;
import xyz.brassgoggledcoders.boilerplate.module.ModuleBase;
import xyz.brassgoggledcoders.modularutilities.ModularUtilities;

@Module(mod = ModularUtilities.MODID)
public class ConstructionModule extends ModuleBase
{
	public static Block blast_glass, filler_fluid_block;
	public static Fluid filler_fluid;

	@Override
	public String getName()
	{
		return "Construction";
	}

	@Override
	public void preInit(FMLPreInitializationEvent event)
	{
		blast_glass = new BlockBlastGlass(Material.GLASS, "blast_glass").setHardness(25F).setResistance(500F);
		getBlockRegistry().registerBlock(blast_glass);

		filler_fluid = new Fluid("filler_fluid", new ResourceLocation(ModularUtilities.MODID, "filler_fluid_still"),
				new ResourceLocation(ModularUtilities.MODID, "filler_fluid_flowing"));
		FluidRegistry.registerFluid(filler_fluid);
		FluidRegistry.addBucketForFluid(filler_fluid);

		filler_fluid_block = new BlockFillerFluid(Material.WATER, "filler_fluid", filler_fluid);
		getBlockRegistry().registerBlock(filler_fluid_block);
		/*
		 * TODO:
		 * - Liquid Concrete: hardens into concrete
		 * - Rebar: Makes solid concrete harder
		 * - Laser level
		 * - Scaffolding
		 */
	}

	@Override
	public void postInit(FMLPostInitializationEvent event)
	{
		GameRegistry.addShapedRecipe(new ItemStack(blast_glass, 9),
				new Object[] {"OGO", "GOG", "OGO", 'O', Blocks.OBSIDIAN, 'G', Blocks.GLASS});
	}
}
