package xyz.brassgoggledcoders.modularutilities.modules.decoration;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import xyz.brassgoggledcoders.boilerplate.blocks.BlockBase;
import xyz.brassgoggledcoders.boilerplate.blocks.IBlockType;
import xyz.brassgoggledcoders.boilerplate.blocks.ItemSubBlock;

public class BlockStoneDecor extends BlockBase
{
	public static final PropertyEnum<EnumBlockType> type = PropertyEnum.create("type", EnumBlockType.class);
	protected static final AxisAlignedBB AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.0625D, 1.0D);

	public BlockStoneDecor()
	{
		super(Material.ROCK);
		this.setUnlocalizedName("stone_decor");
		setDefaultState(this.blockState.getBaseState().withProperty(type, EnumBlockType.CLINKER_BRICK));
	}

	@Override
	public ItemBlock getItemBlockClass(Block block)
	{
		return new ItemSubBlock(block, EnumBlockType.names());
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		return state.getValue(type).getMeta();
	}

	@Override
	public BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, type);
	}

	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return getDefaultState().withProperty(type, EnumBlockType.VALUES[meta]);
	}

	@Override
	public void getSubBlocks(Item item, CreativeTabs creativeTabs, List<ItemStack> itemList)
	{
		for(EnumBlockType resourceType : EnumBlockType.VALUES)
			itemList.add(new ItemStack(item, 1, resourceType.getMeta()));
	}

	public enum EnumBlockType implements IBlockType
	{
		CLINKER_BRICK(0), CARVED_STONE(1);

		public static final EnumBlockType[] VALUES = values();

		private final int meta;

		EnumBlockType(int meta)
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
