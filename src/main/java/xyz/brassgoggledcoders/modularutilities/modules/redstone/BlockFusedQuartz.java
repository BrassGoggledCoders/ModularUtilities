package xyz.brassgoggledcoders.modularutilities.modules.redstone;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import xyz.brassgoggledcoders.boilerplate.blocks.BlockBase;

public class BlockFusedQuartz extends BlockBase {
	
	public BlockFusedQuartz() {
		//Non-transparent block
		super(Material.ROCK, "fusedquartz");
	}
	//Never allow the block to be powered.
	@Override
	public int getWeakPower(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side)
    {
        return 0;
    }
	@Override
	public int getStrongPower(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side)
    {
        return 0;
    }
}
