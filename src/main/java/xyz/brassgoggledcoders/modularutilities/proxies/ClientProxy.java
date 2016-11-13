package xyz.brassgoggledcoders.modularutilities.proxies;

import net.minecraft.client.Minecraft;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ClientProxy extends CommonProxy {
	@Override
	public void registerModels() {

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

	}

	@Override
	public void spawnFX(EnumParticleTypes type, BlockPos pos) {
		World world = Minecraft.getMinecraft().theWorld;
		if(type == EnumParticleTypes.PORTAL)
			for(int j = 0; j < 70; ++j)
				world.spawnParticle(type, pos.getX() + (-0.2 + world.rand.nextDouble()), pos.getY(),
						pos.getZ() + (-0.2 + world.rand.nextDouble()), 0, 0, 0);
		else if(type == EnumParticleTypes.FLAME) {
			for(int j = 0; j < 5; ++j)
				world.spawnParticle(EnumParticleTypes.LAVA, pos.getX() + (-0.2 + world.rand.nextDouble()), pos.getY(),
						pos.getZ() + (-0.2 + world.rand.nextDouble()), world.rand.nextGaussian(),
						world.rand.nextGaussian(), world.rand.nextGaussian());
			for(int j = 0; j < 5; ++j)
				world.spawnParticle(EnumParticleTypes.LAVA, pos.getX() + (-0.2 + world.rand.nextDouble()), pos.getY(),
						pos.getZ() + (-0.2 + world.rand.nextDouble()), -world.rand.nextGaussian(),
						-world.rand.nextGaussian(), -world.rand.nextGaussian());
		}
	}
}
