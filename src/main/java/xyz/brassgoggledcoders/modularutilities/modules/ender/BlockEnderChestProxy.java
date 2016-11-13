package xyz.brassgoggledcoders.modularutilities.modules.ender;

import com.teamacronymcoders.base.blocks.BlockTEBase;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.common.UsernameCache;

import javax.annotation.Nullable;
import java.util.UUID;

public class BlockEnderChestProxy extends BlockTEBase<TileEntityEnderChestProxy> {
	public BlockEnderChestProxy() {
		super(Material.ROCK, "ender_proxy");
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand,
			@Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
		if(!player.isSneaking()) {
			TileEntityEnderChestProxy ex = this.getTileEntity(world, pos);
			UUID placerID = ex.getPlacerUUID();
			if(!world.isRemote) {
				TextComponentString text;
				if(placerID != null) {
					text = new TextComponentString("Linked Player is: " + UsernameCache.getMap().get(placerID));
				} else {
					text = new TextComponentString("No linked player");
				}
				player.addChatMessage(text);
			}

			return true;
		}

		return false;
	}

	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer,
			ItemStack stack) {
		if(placer instanceof EntityPlayer) {
			TileEntityEnderChestProxy ex = (TileEntityEnderChestProxy) worldIn.getTileEntity(pos);
			ex.setPlacerUUID(placer.getPersistentID());
			// TODO Print linked player
		}
	}

	@Override
	public boolean hasComparatorInputOverride(IBlockState state) {
		return true;
	}

	@Override
	public int getComparatorInputOverride(IBlockState blockState, World worldIn, BlockPos pos) {
		// TODO
		return 0;
	}

	@Override
	public Class<? extends TileEntity> getTileEntityClass() {
		return TileEntityEnderChestProxy.class;
	}

	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
		worldIn.updateComparatorOutputLevel(pos, this);
		super.breakBlock(worldIn, pos, state);
	}

	@Override
	public TileEntity createTileEntity(World world, IBlockState blockState) {
		return new TileEntityEnderChestProxy();
	}
}
