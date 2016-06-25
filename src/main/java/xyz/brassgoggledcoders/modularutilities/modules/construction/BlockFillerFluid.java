package xyz.brassgoggledcoders.modularutilities.modules.construction;

import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;

public class BlockFillerFluid extends BlockModFluid {

	public BlockFillerFluid(Material mat, String name, Fluid fluid) {
		super(mat, name, fluid);
	}

	@Override
	public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
		super.updateTick(world, pos, state, rand);
		if(world.getBlockState(pos.down()).isOpaqueCube())
			world.setBlockState(pos, Blocks.DIRT.getDefaultState());
	}

}
