package xyz.brassgoggledcoders.modularutilities.modules.construction;

import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;
import xyz.brassgoggledcoders.boilerplate.IBoilerplateMod;
import xyz.brassgoggledcoders.boilerplate.IModAware;
import xyz.brassgoggledcoders.boilerplate.client.models.IHasModel;

public class BlockFillerFluid extends BlockFluidClassic implements IModAware, IHasModel
{
	IBoilerplateMod mod;

	public BlockFillerFluid(Material mat, String name, Fluid fluid)
	{
		super(fluid, mat);
		this.setUnlocalizedName(name);
	}

	@Override
	public IBoilerplateMod getMod()
	{
		return this.mod;
	}

	@Override
	public void setMod(IBoilerplateMod mod)
	{
		this.mod = mod;
	}

	@Override
	public String[] getResourceLocations()
	{
		String name = this.getUnlocalizedName();
		if(name.startsWith("tile."))
		{
			name = name.substring(5);
		}
		return new String[] {name};
	}

	@Override
	public void updateTick(World world, BlockPos pos, IBlockState state, Random rand)
	{
		super.updateTick(world, pos, state, rand);
		if(world.getBlockState(pos.down()).isOpaqueCube())
			world.setBlockState(pos, Blocks.DIRT.getDefaultState());
	}
}
