package xyz.brassgoggledcoders.modularutilities.modules.construction;

import com.teamacronymcoders.base.blocks.BlockBase;
import com.teamacronymcoders.base.modulesystem.Module;
import com.teamacronymcoders.base.modulesystem.ModuleBase;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialLiquid;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.ForgeModContainer;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.UniversalBucket;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import xyz.brassgoggledcoders.modularutilities.ModularUtilities;

@Module(ModularUtilities.MODID)
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
		getBlockRegistry().register(blast_glass);

		filler_fluid = new Fluid("dirt", new ResourceLocation(ModularUtilities.MODID, "blocks/filler_fluid"),
				new ResourceLocation(ModularUtilities.MODID, "blocks/filler_fluid_flow")).setUnlocalizedName("dirt");
		FluidRegistry.registerFluid(filler_fluid);
		FluidRegistry.addBucketForFluid(filler_fluid);

		filler_fluid_block = new BlockFillerFluid(Material.WATER, "filler_fluid", filler_fluid);
		getBlockRegistry().register(filler_fluid_block);

		concrete_fluid = new Fluid("concrete", new ResourceLocation(ModularUtilities.MODID, "blocks/concrete_fluid"),
				new ResourceLocation(ModularUtilities.MODID, "blocks/concrete_fluid_flow")).setDensity(2000)
						.setViscosity(2000).setUnlocalizedName("concrete");
		FluidRegistry.registerFluid(concrete_fluid);
		FluidRegistry.addBucketForFluid(concrete_fluid);

		concrete_fluid_block = new BlockConcreteFluid(new MaterialConcrete(), "concrete_fluid", concrete_fluid);
		getBlockRegistry().register(concrete_fluid_block);

		concrete = new BlockBase(Material.ROCK, "concrete").setHardness(3).setResistance(18);
		getBlockRegistry().register(concrete);

		registerFluidBlockRendering(concrete_fluid, concrete_fluid.getName());
		registerFluidBlockRendering(filler_fluid, filler_fluid.getName());
		/*
		 * TODO:
		 * - Quickdry Concrete
		 * - Rebar: Makes solid concrete harder
		 * - 'polished' concrete
		 * - Paintable concrete
		 * - Tar?
		 * - Laser level
		 * - Scaffolding
		 * - Blast resistant door, pushable blast blocks.
		 */
	}

	public void registerFluidBlockRendering(Fluid fluid, String name) {

		FluidStateMapper mapper = new FluidStateMapper(fluid, name);
		Block block = fluid.getBlock();
		Item item = Item.getItemFromBlock(block);

		// item-model
		if (item != null) {
			ModelLoader.registerItemVariants(item);
			ModelLoader.setCustomMeshDefinition(item, mapper);
		}
		// block-model
		ModelLoader.setCustomStateMapper(block, mapper);
	}

	@Override
	public void postInit(FMLPostInitializationEvent event) {
		GameRegistry.addShapedRecipe(new ItemStack(blast_glass, 9), "OGO", "GOG", "OGO", 'O', Blocks.OBSIDIAN, 'G',
				Blocks.GLASS);
		GameRegistry.addShapedRecipe(
				UniversalBucket.getFilledBucket(ForgeModContainer.getInstance().universalBucket,
						FluidRegistry.getFluid("dirt")),
				"CDC", "DWD", /* AMX */"CDC", 'C', Blocks.CLAY, 'D', Blocks.DIRT, 'W', Items.WATER_BUCKET);
		GameRegistry.addShapedRecipe(
				UniversalBucket.getFilledBucket(ForgeModContainer.getInstance().universalBucket,
						FluidRegistry.getFluid("concrete")),
				"CDC", "DWD", /* AMX */"CDC", 'C', Blocks.COBBLESTONE, 'D', Blocks.SAND, 'W', Items.WATER_BUCKET);
	}

	public class MaterialConcrete extends MaterialLiquid {

		public MaterialConcrete() {
			super(MapColor.CLAY);
		}

		@Override
		public boolean blocksMovement() {
			return true;
		}
	}

	public static class FluidStateMapper extends StateMapperBase implements ItemMeshDefinition {

		public final Fluid fluid;
		public final ModelResourceLocation location;

		public FluidStateMapper(Fluid fluid, String name) {
			this.fluid = fluid;
			location = new ModelResourceLocation(ModularUtilities.MODID + ":fluids", name);
		}

		public FluidStateMapper(Fluid fluid) {
			this(fluid, fluid.getName());
		}


		@Override
		protected ModelResourceLocation getModelResourceLocation(IBlockState state) {
			return location;
		}

		@Override
		public ModelResourceLocation getModelLocation(ItemStack stack) {
			return location;
		}
	}
}
