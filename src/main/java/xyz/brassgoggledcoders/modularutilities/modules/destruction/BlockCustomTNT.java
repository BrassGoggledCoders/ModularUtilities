package xyz.brassgoggledcoders.modularutilities.modules.destruction;

import com.teamacronymcoders.base.blocks.IHasItemBlock;
import com.teamacronymcoders.base.client.models.IHasModel;
import net.minecraft.block.BlockTNT;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.List;

public class BlockCustomTNT extends BlockTNT implements IHasItemBlock, IHasModel {

	public BlockCustomTNT(String name) {
		super();
		this.setUnlocalizedName(name);
	}

	@Override
	public void onBlockDestroyedByExplosion(World worldIn, BlockPos pos, Explosion explosionIn) {
		if(!worldIn.isRemote) {
			EntityCustomTNTPrimed entitytntprimed =
					new EntityCustomTNTPrimed(worldIn, pos.getX() + 0.5F, pos.getY(), pos.getZ() + 0.5F, null);
			worldIn.spawnEntityInWorld(entitytntprimed);
		}
	}

	@Override
	public void explode(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase igniter) {
		if(!worldIn.isRemote)
			if(state.getValue(EXPLODE).booleanValue()) {
				EntityCustomTNTPrimed entitytntprimed =
						new EntityCustomTNTPrimed(worldIn, pos.getX() + 0.5F, pos.getY(), pos.getZ() + 0.5F, igniter);
				worldIn.spawnEntityInWorld(entitytntprimed);
				worldIn.playSound((EntityPlayer) null, entitytntprimed.posX, entitytntprimed.posY, entitytntprimed.posZ,
						SoundEvents.ENTITY_TNT_PRIMED, SoundCategory.BLOCKS, 1.0F, 1.0F);
			}
	}

	@Override
	public void breakBlock(@Nonnull World world, @Nonnull BlockPos pos, @Nonnull IBlockState state) {
		world.updateComparatorOutputLevel(pos, this);

		super.breakBlock(world, pos, state);
	}

	@Override
	public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
		this.updateState(worldIn, pos, state);
	}

	@Override
	public void onNeighborChange(IBlockAccess world, BlockPos pos, BlockPos neighborPos) {
		this.updateState(world, pos, world.getBlockState(neighborPos));
	}

	protected void updateState(IBlockAccess world, BlockPos pos, IBlockState state) {

	}

	@Override
	public ItemBlock getItemBlock() {
		return new ItemBlock(this);
	}

	@Override
	public List<ItemStack> getAllSubItems(List<ItemStack> itemStacks) {
		itemStacks.add(new ItemStack(this));
		return itemStacks;
	}
}
