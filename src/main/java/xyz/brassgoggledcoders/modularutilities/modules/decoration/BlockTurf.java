package xyz.brassgoggledcoders.modularutilities.modules.decoration;

import com.teamacronymcoders.base.blocks.BlockFlat;
import com.teamacronymcoders.base.util.EnumUtils;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class BlockTurf extends BlockFlat {
	public static final PropertyEnum<EnumDirtType> type = PropertyEnum.create("type", EnumDirtType.class);

	public BlockTurf() {
		super(Material.GRASS, EnumUtils.getNames(EnumDirtType.class));
		this.setUnlocalizedName("turf");
		setDefaultState(this.blockState.getBaseState().withProperty(type, EnumDirtType.NORMAL));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getBlockLayer() {
		return BlockRenderLayer.CUTOUT_MIPPED;
	}

	@Override
	public boolean isVisuallyOpaque() {
		return false;
	}

	@Override
	public boolean canBlockStay(World worldIn, BlockPos pos) {
		return !worldIn.isAirBlock(pos.down());
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(type).ordinal();
	}

	@Override
	public BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, type);
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return getDefaultState().withProperty(type, EnumDirtType.values()[meta]);
	}

	@Override
	public void getSubBlocks(Item item, CreativeTabs creativeTabs, List<ItemStack> itemList) {
		for(EnumDirtType resourceType : EnumDirtType.values())
			itemList.add(new ItemStack(item, 1, resourceType.ordinal()));
	}

	public enum EnumDirtType implements IStringSerializable {
		NORMAL, DRY, FROZEN, JUNGLE, SWAMP, PODZOL, MYCELIUM;

		@Override
		public String getName() {
			return name().toLowerCase();
		}
	}
}
