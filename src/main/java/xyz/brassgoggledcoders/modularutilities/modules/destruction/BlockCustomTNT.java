package xyz.brassgoggledcoders.modularutilities.modules.destruction;

import javax.annotation.Nonnull;

import net.minecraft.block.Block;
import net.minecraft.block.BlockTNT;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import xyz.brassgoggledcoders.boilerplate.IBoilerplateMod;
import xyz.brassgoggledcoders.boilerplate.IModAware;
import xyz.brassgoggledcoders.boilerplate.blocks.IHasItemBlock;
import xyz.brassgoggledcoders.boilerplate.client.models.IHasModel;

public class BlockCustomTNT extends BlockTNT implements IModAware, IHasItemBlock, IHasModel
{
	IBoilerplateMod mod;

	public BlockCustomTNT(String name)
	{
		super();
		this.setUnlocalizedName(name);
	}

	@Override
	public void onBlockDestroyedByExplosion(World worldIn, BlockPos pos, Explosion explosionIn)
	{
		if(!worldIn.isRemote)
		{
			EntityCustomTNTPrimed entitytntprimed =
					new EntityCustomTNTPrimed(worldIn, (double) ((float) pos.getX() + 0.5F), (double) pos.getY(),
							(double) ((float) pos.getZ() + 0.5F), null);
			worldIn.spawnEntityInWorld(entitytntprimed);
		}
	}

	@Override
	public void explode(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase igniter)
	{
		if(!worldIn.isRemote)
		{
			if(((Boolean) state.getValue(EXPLODE)).booleanValue())
			{
				EntityCustomTNTPrimed entitytntprimed =
						new EntityCustomTNTPrimed(worldIn, (double) ((float) pos.getX() + 0.5F), (double) pos.getY(),
								(double) ((float) pos.getZ() + 0.5F), igniter);
				worldIn.spawnEntityInWorld(entitytntprimed);
				worldIn.playSound((EntityPlayer) null, entitytntprimed.posX, entitytntprimed.posY, entitytntprimed.posZ,
						SoundEvents.ENTITY_TNT_PRIMED, SoundCategory.BLOCKS, 1.0F, 1.0F);
			}
		}
	}

	@Override
	public void breakBlock(@Nonnull World world, @Nonnull BlockPos pos, @Nonnull IBlockState state)
	{
		world.updateComparatorOutputLevel(pos, this);

		super.breakBlock(world, pos, state);
	}

	@Override
	public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state)
	{
		this.updateState(worldIn, pos, state);
	}

	@Override
	public void onNeighborChange(IBlockAccess world, BlockPos pos, BlockPos neighborPos)
	{
		this.updateState(world, pos, world.getBlockState(neighborPos));
	}

	protected void updateState(IBlockAccess world, BlockPos pos, IBlockState state)
	{

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
	public ItemBlock getItemBlockClass(Block block)
	{
		return new ItemBlock(block);
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

}
