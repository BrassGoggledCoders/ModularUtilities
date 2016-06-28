package xyz.brassgoggledcoders.modularutilities.modules.miscellaneous;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.IPlantable;
import xyz.brassgoggledcoders.boilerplate.items.ItemBase;

public class ItemGoldPetal extends ItemBase implements IPlantable {

	public ItemGoldPetal(String name) {
		super(name);
	}

	@Override
	public EnumActionResult onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		net.minecraft.block.state.IBlockState state = worldIn.getBlockState(pos);
		if(facing == EnumFacing.UP && playerIn.canPlayerEdit(pos.offset(facing), facing, stack)
				&& state.getBlock().canSustainPlant(state, worldIn, pos, EnumFacing.UP, this)
				&& worldIn.isAirBlock(pos.up())) {
			worldIn.setBlockState(pos.up(), MiscellaneousModule.magmagold.getDefaultState());
			--stack.stackSize;
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
