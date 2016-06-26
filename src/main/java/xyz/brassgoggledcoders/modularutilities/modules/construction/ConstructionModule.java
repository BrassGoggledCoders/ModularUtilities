package xyz.brassgoggledcoders.modularutilities.modules.construction;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.ForgeModContainer;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.UniversalBucket;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import xyz.brassgoggledcoders.boilerplate.blocks.BlockBase;
import xyz.brassgoggledcoders.boilerplate.module.Module;
import xyz.brassgoggledcoders.boilerplate.module.ModuleBase;
import xyz.brassgoggledcoders.modularutilities.ModularUtilities;

@Module(mod = ModularUtilities.MODID)
public class ConstructionModule extends ModuleBase {
	public static Block blast_glass, filler_fluid_block, concrete_fluid_block, concrete;
	public static Fluid filler_fluid, concrete_fluid;

	@Override
	public String getName() {
		return "Construction";
	}

	@Override
	public void preInit(FMLPreInitializationEvent event) {
		blast_glass = new BlockBlastGlass().setHardness(25F).setResistance(500F);
		getBlockRegistry().registerBlock(blast_glass);

		filler_fluid = new Fluid("dirt", new ResourceLocation(ModularUtilities.MODID, "blocks/filler_fluid"),
				new ResourceLocation(ModularUtilities.MODID, "blocks/filler_fluid_flow"));
		if(!FluidRegistry.isFluidRegistered("dirt")) {
			FluidRegistry.registerFluid(filler_fluid);
			FluidRegistry.addBucketForFluid(filler_fluid);
		}

		filler_fluid_block = new BlockFillerFluid(Material.WATER, "filler_fluid", filler_fluid);
		getBlockRegistry().registerBlock(filler_fluid_block);

		concrete_fluid = new Fluid("concrete", new ResourceLocation(ModularUtilities.MODID, "blocks/concrete_fluid"),
				new ResourceLocation(ModularUtilities.MODID, "blocks/concrete_fluid_flow")).setDensity(2000)
						.setViscosity(2000);
		if(!FluidRegistry.isFluidRegistered("concrete")) {
			FluidRegistry.registerFluid(concrete_fluid);
			FluidRegistry.addBucketForFluid(concrete_fluid);
		}

		concrete_fluid_block = new BlockConcreteFluid(Material.WATER, "concrete_fluid", concrete_fluid);
		getBlockRegistry().registerBlock(concrete_fluid_block);

		concrete = new BlockBase(Material.ROCK, "concrete").setHardness(3).setResistance(18);
		getBlockRegistry().registerBlock(concrete);
		/*
		 * TODO:
		 * - Quickdry Concrete
		 * - Rebar: Makes solid concrete harder
		 * - Laser level
		 * - Scaffolding
		 * - Blast resistant door, pushable blast blocks.
		 */
	}

	@Override
	public void postInit(FMLPostInitializationEvent event) {
		GameRegistry.addShapedRecipe(new ItemStack(blast_glass, 9),
				new Object[] {"OGO", "GOG", "OGO", 'O', Blocks.OBSIDIAN, 'G', Blocks.GLASS});
		GameRegistry.addShapedRecipe(
				UniversalBucket.getFilledBucket(ForgeModContainer.getInstance().universalBucket, filler_fluid),
				new Object[] {"CDC", "DWD", /* AMX */"CDC", 'C', Blocks.CLAY, 'D', Blocks.DIRT, 'W',
						Items.WATER_BUCKET});
	}
}
