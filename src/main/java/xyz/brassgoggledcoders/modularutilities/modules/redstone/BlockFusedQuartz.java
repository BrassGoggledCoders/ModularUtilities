package xyz.brassgoggledcoders.modularutilities.modules.redstone;

import com.teamacronymcoders.base.blocks.BlockBase;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class BlockFusedQuartz extends BlockBase {

    public BlockFusedQuartz() {
        // Non-transparent block
        super(Material.ROCK, "fused_quartz");
    }

    @Override
    public boolean isSideSolid(IBlockState base_state, IBlockAccess world, BlockPos pos, EnumFacing side) {
        return true;
    }

    @Override
    public boolean shouldCheckWeakPower(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side) {
        return false;
    }
}
