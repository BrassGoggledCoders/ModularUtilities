package xyz.brassgoggledcoders.modularutilities.modules.construction;

import com.teamacronymcoders.base.blocks.BlockFluidBase;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;

import javax.annotation.Nonnull;
import java.util.Random;

public class BlockFillerFluid extends BlockFluidBase {

	public BlockFillerFluid(Material mat, String name, Fluid fluid) {
		super(name, fluid, mat);
		this.setTickRandomly(true);
	}

	@Override
	public void updateTick(@Nonnull World world, @Nonnull BlockPos pos, @Nonnull IBlockState state, @Nonnull Random rand) {
		int quantaRemaining = quantaPerBlock - state.getValue(LEVEL);
		int expQuanta = -101;

		// check adjacent block levels if non-source
		if(quantaRemaining < quantaPerBlock) {
			if(world.getBlockState(pos.add(0, -densityDir, 0)).getBlock() == this
					|| world.getBlockState(pos.add(-1, -densityDir, 0)).getBlock() == this
					|| world.getBlockState(pos.add(1, -densityDir, 0)).getBlock() == this
					|| world.getBlockState(pos.add(0, -densityDir, -1)).getBlock() == this
					|| world.getBlockState(pos.add(0, -densityDir, 1)).getBlock() == this) {
				expQuanta = quantaPerBlock - 1;
			}
			else {
				int maxQuanta = -100;
				maxQuanta = getLargerQuanta(world, pos.add(-1, 0, 0), maxQuanta);
				maxQuanta = getLargerQuanta(world, pos.add(1, 0, 0), maxQuanta);
				maxQuanta = getLargerQuanta(world, pos.add(0, 0, -1), maxQuanta);
				maxQuanta = getLargerQuanta(world, pos.add(0, 0, 1), maxQuanta);

				expQuanta = maxQuanta - 1;
			}

			// decay calculation
			if(expQuanta != quantaRemaining) {
				quantaRemaining = expQuanta;

				if(expQuanta <= 0) {
					world.setBlockState(pos, Blocks.DIRT.getDefaultState());
				}
				else {
					world.setBlockState(pos, state.withProperty(LEVEL, quantaPerBlock - expQuanta), 2);
					world.scheduleUpdate(pos, this, tickRate);
					world.notifyNeighborsOfStateChange(pos, this, true);
				}
			}
		}
		// This is a "source" block, set meta to zero, and send a server only update
		else if(quantaRemaining >= quantaPerBlock) {
			world.setBlockState(pos, this.getDefaultState(), 2);
		}

		// Flow vertically if possible
		if(canDisplace(world, pos.up(densityDir))) {
			flowIntoBlock(world, pos.up(densityDir), 1);
			return;
		}

		// Flow outward if possible
		int flowMeta = quantaPerBlock - quantaRemaining + 1;
		if(flowMeta >= quantaPerBlock) {
			return;
		}

		if(isSourceBlock(world, pos) || !isFlowingVertically(world, pos)) {
			if(world.getBlockState(pos.down(densityDir)).getBlock() == this) {
				flowMeta = 1;
			}
			boolean flowTo[] = getOptimalFlowDirections(world, pos);

			if(flowTo[0])
				flowIntoBlock(world, pos.add(-1, 0, 0), flowMeta);
			if(flowTo[1])
				flowIntoBlock(world, pos.add(1, 0, 0), flowMeta);
			if(flowTo[2])
				flowIntoBlock(world, pos.add(0, 0, -1), flowMeta);
			if(flowTo[3])
				flowIntoBlock(world, pos.add(0, 0, 1), flowMeta);
		}
		if(rand.nextInt(3) == 0)
			world.setBlockState(pos, Blocks.DIRT.getDefaultState());
	}

}
