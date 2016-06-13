package xyz.brassgoggledcoders.modularutilities.modules.decoration;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import xyz.brassgoggledcoders.boilerplate.blocks.BlockThin;
import xyz.brassgoggledcoders.boilerplate.blocks.IBlockType;
import xyz.brassgoggledcoders.boilerplate.blocks.ItemSubBlock;

public class BlockLeafCover extends BlockThin
{

	public static PropertyEnum<EnumLeafCoverBlockType> type = PropertyEnum.create("type", EnumLeafCoverBlockType.class);

	public BlockLeafCover()
	{
		super(Material.LEAVES);
		this.setUnlocalizedName("leaf_cover");
		setDefaultState(this.blockState.getBaseState().withProperty(type, EnumLeafCoverBlockType.OAK));
	}

	@Override
	public ItemBlock getItemBlockClass(Block block)
	{
		return new ItemSubBlock(block, EnumLeafCoverBlockType.names());
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
	{
		return AABB;
	}

	@Override
	@Nullable
	public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, World worldIn, BlockPos pos)
	{
		return NULL_AABB;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getBlockLayer()
	{
		return BlockRenderLayer.CUTOUT;
	}

	@Override
	public boolean canBlockStay(World worldIn, BlockPos pos)
	{
		return true;
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		return state.getValue(type).getMeta();
	}

	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return getDefaultState().withProperty(type, EnumLeafCoverBlockType.VALUES[meta]);
	}

	@Override
	public void getSubBlocks(Item item, CreativeTabs creativeTabs, List<ItemStack> itemList)
	{
		for(EnumLeafCoverBlockType resourceType : EnumLeafCoverBlockType.VALUES)
			itemList.add(new ItemStack(item, 1, resourceType.getMeta()));
	}

	@Override
	public BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, type);
	}

	public enum EnumLeafCoverBlockType implements IBlockType
	{
		OAK(0), SPRUCE(1), JUNGLE(2), BIRCH(3), ACACIA(4), BIG_OAK(5);

		public static final EnumLeafCoverBlockType[] VALUES = values();

		private final int meta;

		EnumLeafCoverBlockType(int meta)
		{
			this.meta = meta;
		}

		@Override
		public int getMeta()
		{
			return meta;
		}

		@Override
		public String getName()
		{
			return name().toLowerCase();
		}

		public static String[] names()
		{
			ArrayList<String> names = new ArrayList<String>();
			for(int i = 0; i < VALUES.length; i++)
				names.add(VALUES[i].toString().toLowerCase());

			return names.toArray(new String[0]);
		}
	}
}
