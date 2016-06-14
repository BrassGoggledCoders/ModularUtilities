package xyz.brassgoggledcoders.modularutilities.modules.ender;

import javax.annotation.Nullable;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
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
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, @Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ)
	{
		// if(playerIn.isSneaking())
		// {
		TileEntityEnderChestProxy ex = (TileEntityEnderChestProxy) worldIn.getTileEntity(pos);
		ex.setPlacerUUID(playerIn.getUniqueID());
		ex.handler = new EnderProxyInventoryHandler(worldIn, playerIn.getUniqueID());
		// }
		// TODO Print linked player
		return super.onBlockActivated(worldIn, pos, state, playerIn, hand, heldItem, side, hitX, hitY, hitZ);
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
}
