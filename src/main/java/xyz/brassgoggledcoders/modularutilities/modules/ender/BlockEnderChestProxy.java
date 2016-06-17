package xyz.brassgoggledcoders.modularutilities.modules.ender;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import xyz.brassgoggledcoders.boilerplate.blocks.BlockTEBase;

public class BlockEnderChestProxy extends BlockTEBase
{
	public BlockEnderChestProxy()
	{
		super(Material.ROCK, "ender_proxy");
	}

	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer,
			ItemStack stack)
	{
		if(placer instanceof EntityPlayer)
		{
			TileEntityEnderChestProxy ex = (TileEntityEnderChestProxy) worldIn.getTileEntity(pos);
			ex.setPlacerUUID(placer.getPersistentID());
			// TODO Print linked player
		}
	}

	@Override
	public boolean hasComparatorInputOverride(IBlockState state)
	{
		return true;
	}

	@Override
	public int getComparatorInputOverride(IBlockState blockState, World worldIn, BlockPos pos)
	{
		// TODO
		return Container.calcRedstoneFromInventory(
				((TileEntityEnderChestProxy) worldIn.getTileEntity(pos)).getEnderInventory());
	}

	@Override
	public Class<? extends TileEntity> getTileEntityClass()
	{
		return TileEntityEnderChestProxy.class;
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return new TileEntityEnderChestProxy();
	}

	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
	{
		worldIn.updateComparatorOutputLevel(pos, this);
		super.breakBlock(worldIn, pos, state);
	}
}
