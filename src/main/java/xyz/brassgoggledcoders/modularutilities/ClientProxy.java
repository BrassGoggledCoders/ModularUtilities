package xyz.brassgoggledcoders.modularutilities;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ColorizerFoliage;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeColorHelper;
import net.minecraftforge.client.model.ModelLoader;
import xyz.brassgoggledcoders.modularutilities.modules.construction.ConstructionModule;
import xyz.brassgoggledcoders.modularutilities.modules.decoration.BlockLeafCover;
import xyz.brassgoggledcoders.modularutilities.modules.decoration.BlockStoneDecor;
import xyz.brassgoggledcoders.modularutilities.modules.decoration.BlockTurf;
import xyz.brassgoggledcoders.modularutilities.modules.decoration.DecorationModule;

public class ClientProxy extends CommonProxy {
	@Override
	public void registerModels() {
		if(ModularUtilities.instance.getModuleHandler().isModuleEnabled("Decoration")) {
			xyz.brassgoggledcoders.boilerplate.proxies.ClientProxy.registerVariantsDefaulted(DecorationModule.turf,
					BlockTurf.EnumTurfBlockType.class, "type");
			xyz.brassgoggledcoders.boilerplate.proxies.ClientProxy.registerVariantsDefaulted(
					DecorationModule.leaf_cover, BlockLeafCover.EnumLeafCoverBlockType.class, "type");
			xyz.brassgoggledcoders.boilerplate.proxies.ClientProxy.registerVariantsDefaulted(
					DecorationModule.stone_decor, BlockStoneDecor.EnumBlockType.class, "type");
		}
		if(ModularUtilities.instance.getModuleHandler().isModuleEnabled("Construction")) {
			registerFluidModel(ConstructionModule.filler_fluid_block,
					new ModelResourceLocation(ModularUtilities.MODID + ":fluids", "filler_fluid"));
			registerFluidModel(ConstructionModule.concrete_fluid_block,
					new ModelResourceLocation(ModularUtilities.MODID + ":fluids", "concrete"));
		}
	}

	@Override
	public void registerColors() {
		if(ModularUtilities.instance.getModuleHandler().isModuleEnabled("Decoration")) {
			Minecraft.getMinecraft().getBlockColors().registerBlockColorHandler(new LeafColors(),
					new Block[] {DecorationModule.leaf_cover});
			Minecraft.getMinecraft().getItemColors().registerItemColorHandler(new LeafColors(),
					new Block[] {DecorationModule.leaf_cover});
		}
	}

	@Override
	public void spawnFX(EnumParticleTypes type, BlockPos pos) {
		World world = Minecraft.getMinecraft().theWorld;
		if(type == EnumParticleTypes.PORTAL)
			for(int j = 0; j < 70; ++j)
				world.spawnParticle(type, pos.getX() + (-0.2 + world.rand.nextDouble()), pos.getY(),
						pos.getZ() + (-0.2 + world.rand.nextDouble()), 0, 0, 0, new int[0]);
		else if(type == EnumParticleTypes.FLAME) {
			for(int j = 0; j < 5; ++j)
				world.spawnParticle(EnumParticleTypes.LAVA, pos.getX() + (-0.2 + world.rand.nextDouble()), pos.getY(),
						pos.getZ() + (-0.2 + world.rand.nextDouble()), world.rand.nextGaussian(),
						world.rand.nextGaussian(), world.rand.nextGaussian(), new int[0]);
			for(int j = 0; j < 5; ++j)
				world.spawnParticle(EnumParticleTypes.LAVA, pos.getX() + (-0.2 + world.rand.nextDouble()), pos.getY(),
						pos.getZ() + (-0.2 + world.rand.nextDouble()), -world.rand.nextGaussian(),
						-world.rand.nextGaussian(), -world.rand.nextGaussian(), new int[0]);
		}
	}

	private static void registerFluidModel(Block fluidBlock, final ModelResourceLocation loc) {
		Item fluidItem = Item.getItemFromBlock(fluidBlock);
		ModelBakery.registerItemVariants(fluidItem);
		ModelLoader.setCustomMeshDefinition(fluidItem, new ItemMeshDefinition() {
			@Override
			public ModelResourceLocation getModelLocation(ItemStack stack) {
				return loc;
			}
		});
		ModelLoader.setCustomStateMapper(fluidBlock, new StateMapperBase() {
			@Override
			protected ModelResourceLocation getModelResourceLocation(IBlockState state) {
				return loc;
			}
		});
	}

	public class LeafColors implements IBlockColor, IItemColor {
		@Override
		public int colorMultiplier(IBlockState state, @Nullable IBlockAccess worldIn, @Nullable BlockPos pos,
				int tintIndex) {
			return worldIn != null && pos != null ? BiomeColorHelper.getFoliageColorAtPos(worldIn, pos)
					: ColorizerFoliage.getFoliageColorBasic();
		}

		@Override
		public int getColorFromItemstack(ItemStack stack, int tintIndex) {
			return ColorizerFoliage.getFoliageColorBasic();
		}
	}
}
