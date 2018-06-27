package xyz.brassgoggledcoders.modularutilities.modules.miscellaneous;

import javax.annotation.Nonnull;

import com.teamacronymcoders.base.items.ItemBase;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.IPlantable;

public class ItemGoldPetal extends ItemBase implements IPlantable {

	public ItemGoldPetal(String name) {
		super(name);
	}

	@Override
	@Nonnull
	public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand,
			EnumFacing facing, float hitX, float hitY, float hitZ) {
		IBlockState state = worldIn.getBlockState(pos);
		ItemStack stack = player.getHeldItem(hand);
		if(facing == EnumFacing.UP && player.canPlayerEdit(pos.offset(facing), facing, stack)
				&& state.getBlock().canSustainPlant(state, worldIn, pos, EnumFacing.UP, this)
				&& worldIn.isAirBlock(pos.up())) {
			worldIn.setBlockState(pos.up(), MiscellaneousModule.magmagold.getDefaultState());
			stack.shrink(1);
			return EnumActionResult.SUCCESS;
		}
		else {
			return EnumActionResult.FAIL;
		}
	}

	@Override
	public EnumPlantType getPlantType(IBlockAccess world, BlockPos pos) {
		return EnumPlantType.Nether;
	}

	@Override
	public IBlockState getPlant(IBlockAccess world, BlockPos pos) {
		return MiscellaneousModule.magmagold.getDefaultState();
	}

}
