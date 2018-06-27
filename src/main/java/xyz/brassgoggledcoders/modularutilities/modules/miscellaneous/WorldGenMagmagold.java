package xyz.brassgoggledcoders.modularutilities.modules.miscellaneous;

import java.util.Random;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenMagmagold extends WorldGenerator {

	public WorldGenMagmagold() {
	}

	@Override
	public boolean generate(World worldIn, Random rand, BlockPos position) {
		BlockPos blockpos = position.add(rand.nextInt(8) - rand.nextInt(8), rand.nextInt(4) - rand.nextInt(4),
				rand.nextInt(8) - rand.nextInt(8));

		if(worldIn.isAirBlock(blockpos) && (blockpos.getY() < worldIn.getHeight() - 1)
				&& ((BlockMagmagold) MiscellaneousModule.magmagold).canBlockStay(worldIn, blockpos,
						MiscellaneousModule.magmagold.getDefaultState())) {
			worldIn.setBlockState(blockpos, MiscellaneousModule.magmagold.getDefaultState(), 2);
		}

		return true;
	}
}