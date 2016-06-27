package xyz.brassgoggledcoders.modularutilities.proxies;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
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
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import xyz.brassgoggledcoders.modularutilities.ModularUtilities;
import xyz.brassgoggledcoders.modularutilities.modules.construction.ConstructionModule;
import xyz.brassgoggledcoders.modularutilities.modules.decoration.BlockHedge;
import xyz.brassgoggledcoders.modularutilities.modules.decoration.DecorationModule;

public class ClientProxy extends CommonProxy {
	@Override
	public void registerModels() {
		if(ModularUtilities.instance.getModuleHandler().isModuleEnabled("Construction")) {
			xyz.brassgoggledcoders.boilerplate.proxies.ClientProxy.registerFluidModel(
					ConstructionModule.filler_fluid_block,
					new ModelResourceLocation(ModularUtilities.MODID + ":fluids", "filler_fluid"));
			xyz.brassgoggledcoders.boilerplate.proxies.ClientProxy.registerFluidModel(
					ConstructionModule.concrete_fluid_block,
					new ModelResourceLocation(ModularUtilities.MODID + ":fluids", "concrete_fluid"));
		}
		if(ModularUtilities.instance.getModuleHandler().isModuleEnabled("Decoration")) {
			for(int i = 0; i < BlockHedge.EnumBlockType.values().length; i++) {
				ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(DecorationModule.hedge), i,
						new ModelResourceLocation(ForgeRegistries.BLOCKS.getKey(DecorationModule.hedge).toString(),
								"inventory"));
				ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(DecorationModule.hedge_opaque), i,
						new ModelResourceLocation(
								ForgeRegistries.BLOCKS.getKey(DecorationModule.hedge_opaque).toString(), "inventory"));
			}
		}
	}

	// TODO Generalise. 'Typed/Suffixed StateMapper'
	/*
	 * public class HedgeStateMapper implements IStateMapper {
	 * // TODO Ideally withName should be removed.
	 * StateMap stateMap = new StateMap.Builder().withName(BlockHedge.TYPE).withSuffix("_hedge").build();
	 * @Override
	 * public Map<IBlockState, ModelResourceLocation> putStateModelLocations(Block block) {
	 * Map<IBlockState, ModelResourceLocation> map =
	 * (Map<IBlockState, ModelResourceLocation>) stateMap.putStateModelLocations(block);
	 * Map<IBlockState, ModelResourceLocation> newMap = Maps.newHashMap();
	 * for(Entry<IBlockState, ModelResourceLocation> e : map.entrySet()) {
	 * ModelResourceLocation loc = e.getValue();
	 * newMap.put(e.getKey(),
	 * new ModelResourceLocation("modularutilities:" + loc.getResourcePath(), loc.getVariant()));
	 * }
	 * return newMap;
	 * }
	 * }
	 */

	@Override
	public void registerColors() {
		if(ModularUtilities.instance.getModuleHandler().isModuleEnabled("Decoration")) {
			Block[] toColourise = new Block[] {DecorationModule.leaf_cover, DecorationModule.hedge,
					DecorationModule.hedge_opaque, DecorationModule.leaf_cover_opaque};
			Minecraft.getMinecraft().getBlockColors().registerBlockColorHandler(new LeafColors(), toColourise);
			Minecraft.getMinecraft().getItemColors().registerItemColorHandler(new LeafColors(), toColourise);
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

	// TODO Birch/Spruce Specialcasing
	public class LeafColors implements IBlockColor, IItemColor {
		@Override
		public int colorMultiplier(IBlockState state, @Nullable IBlockAccess worldIn, @Nullable BlockPos pos,
				int tintIndex) {
			return (worldIn != null && pos != null) ? BiomeColorHelper.getFoliageColorAtPos(worldIn, pos)
					: ColorizerFoliage.getFoliageColorBasic();
		}

		@Override
		public int getColorFromItemstack(ItemStack stack, int tintIndex) {
			return ColorizerFoliage.getFoliageColorBasic();
		}
	}
}
