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
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

public class BlockLeafCover extends BlockFlat {

	public static PropertyEnum<EnumWoodBlock> type = PropertyEnum.create("type", EnumWoodBlock.class);
	private boolean opaque;

	public BlockLeafCover(String name, boolean opaque) {
		super(Material.LEAVES, EnumUtils.getNames(EnumWoodBlock.class));
		this.setUnlocalizedName(name);
		setDefaultState(this.blockState.getBaseState().withProperty(type, EnumWoodBlock.OAK));
		this.opaque = opaque;
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return AABB;
	}

	@Override
	@Nullable
	public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, World worldIn, BlockPos pos) {
		return NULL_AABB;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getBlockLayer() {
		return (opaque) ? BlockRenderLayer.SOLID : BlockRenderLayer.CUTOUT;
	}

	@Override
	public boolean canBlockStay(World worldIn, BlockPos pos) {
		return true;
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(type).ordinal();
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return getDefaultState().withProperty(type, EnumWoodBlock.values()[meta]);
	}

	@Override
	public void getSubBlocks(Item item, CreativeTabs creativeTabs, List<ItemStack> itemList) {
		for(EnumWoodBlock resourceType : EnumWoodBlock.values())
			itemList.add(new ItemStack(item, 1, resourceType.ordinal()));
	}

	@Override
	public BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, type);
	}
}
