package xyz.brassgoggledcoders.modularutilities.modules.construction;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import xyz.brassgoggledcoders.boilerplate.IBoilerplateMod;
import xyz.brassgoggledcoders.boilerplate.IModAware;
import xyz.brassgoggledcoders.boilerplate.blocks.IHasItemBlock;
import xyz.brassgoggledcoders.boilerplate.client.models.IHasModel;

public class BlockFillerFluid extends BlockFluidClassic implements IModAware, IHasModel, IHasItemBlock {
	IBoilerplateMod mod;

	public BlockFillerFluid(Material mat, String name, Fluid fluid) {
		super(fluid, mat);
		this.setUnlocalizedName(name);
	}

	@Override
	public IBoilerplateMod getMod() {
		return this.mod;
	}

	@Override
	public void setMod(IBoilerplateMod mod) {
		this.mod = mod;
	}

	@Override
	public String[] getResourceLocations() {
		String name = this.getUnlocalizedName();
		if(name.startsWith("tile."))
			name = name.substring(5);
		return new String[] {name};
	}

	@Override
	public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
		super.updateTick(world, pos, state, rand);
		if(world.getBlockState(pos.down()).isOpaqueCube())
			world.setBlockState(pos, Blocks.DIRT.getDefaultState());
	}

	@Override
	public ItemBlock getItemBlockClass(Block block) {
		return new ItemBlock(block) {
			@Override
			@SideOnly(Side.CLIENT)
			public CreativeTabs getCreativeTab() {
				return null;
			}
		};
	}
}
