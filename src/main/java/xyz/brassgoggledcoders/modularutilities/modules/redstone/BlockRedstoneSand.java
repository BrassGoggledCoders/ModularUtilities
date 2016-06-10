package xyz.brassgoggledcoders.modularutilities.modules.redstone;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import xyz.brassgoggledcoders.boilerplate.IBoilerplateMod;
import xyz.brassgoggledcoders.boilerplate.IModAware;
import xyz.brassgoggledcoders.boilerplate.blocks.IHasItemBlock;
import xyz.brassgoggledcoders.boilerplate.client.models.IHasModel;

public class BlockRedstoneSand extends BlockFalling implements IModAware, IHasItemBlock, IHasModel
{
	IBoilerplateMod mod;

	public BlockRedstoneSand()
	{
		super();
		this.setUnlocalizedName("redstone_sand");
	}

	@Override
    public boolean canProvidePower(IBlockState state)
    {
        return true;
    }
	
	@Override
    public int getWeakPower(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side)
    {
        return 15;
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
	public ItemBlock getItemBlockClass(Block block) {
		return new ItemBlock(block);
	}

	@Override
	public String[] getResourceLocations() {
		String name = this.getUnlocalizedName();
		if(name.startsWith("tile."))
		{
			name = name.substring(5);
		}
		return new String[] { name };
	}
}