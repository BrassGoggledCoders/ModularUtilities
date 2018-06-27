package xyz.brassgoggledcoders.modularutilities.modules.redstone;

import java.util.List;

import com.teamacronymcoders.base.IBaseMod;
import com.teamacronymcoders.base.blocks.IHasItemBlock;
import com.teamacronymcoders.base.client.models.IHasModel;
import com.teamacronymcoders.base.items.itemblocks.ItemBlockModel;

import net.minecraft.block.BlockFalling;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.*;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class BlockRedstoneSand extends BlockFalling implements IHasItemBlock, IHasModel {
	private ItemBlock itemBlock;
	private IBaseMod mod;

	public BlockRedstoneSand() {
		super();
		setUnlocalizedName("redstone_sand");
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean canProvidePower(IBlockState state) {
		return true;
	}

	@SuppressWarnings("deprecation")
	@Override
	public int getWeakPower(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
		return 15;
	}

	@Override
	public ItemBlock getItemBlock() {
		if(itemBlock == null) {
			itemBlock = new ItemBlockModel<>(this);
		}
		return itemBlock;
	}

	@Override
	public List<String> getModelNames(List<String> modelNames) {
		String name = getUnlocalizedName();
		if(name.startsWith("tile.")) {
			name = name.substring(5);
		}
		modelNames.add(name);
		return modelNames;
	}

	@Override
	public List<ItemStack> getAllSubItems(List<ItemStack> itemStacks) {
		itemStacks.add(new ItemStack(this));
		return itemStacks;
	}

	@Override
	public Item getItem() {
		return getItemBlock();
	}

	@Override
	public IBaseMod getMod() {
		return mod;
	}

	@Override
	public void setMod(IBaseMod mod) {
		this.mod = mod;
	}
}
