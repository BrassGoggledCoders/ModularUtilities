package xyz.brassgoggledcoders.modularutilities.modules.decoration;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import xyz.brassgoggledcoders.boilerplate.blocks.BlockSubBase;
import xyz.brassgoggledcoders.boilerplate.blocks.IBlockType;
import xyz.brassgoggledcoders.boilerplate.blocks.ItemSubBlock;

public class BlockHedge extends BlockSubBase {

	public static final PropertyBool UP = PropertyBool.create("up");
	public static final PropertyBool NORTH = PropertyBool.create("north");
	public static final PropertyBool EAST = PropertyBool.create("east");
	public static final PropertyBool SOUTH = PropertyBool.create("south");
	public static final PropertyBool WEST = PropertyBool.create("west");
	public static final PropertyEnum<EnumBlockType> TYPE = PropertyEnum.create("type", EnumBlockType.class);
	protected static final AxisAlignedBB[] AABB_BY_INDEX =
			new AxisAlignedBB[] {new AxisAlignedBB(0.25D, 0.0D, 0.25D, 0.75D, 1.0D, 0.75D),
					new AxisAlignedBB(0.25D, 0.0D, 0.25D, 0.75D, 1.0D, 1.0D),
					new AxisAlignedBB(0.0D, 0.0D, 0.25D, 0.75D, 1.0D, 0.75D),
					new AxisAlignedBB(0.0D, 0.0D, 0.25D, 0.75D, 1.0D, 1.0D),
					new AxisAlignedBB(0.25D, 0.0D, 0.0D, 0.75D, 1.0D, 0.75D),
					new AxisAlignedBB(0.3125D, 0.0D, 0.0D, 0.6875D, 0.875D, 1.0D),
					new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.75D, 1.0D, 0.75D),
					new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.75D, 1.0D, 1.0D),
					new AxisAlignedBB(0.25D, 0.0D, 0.25D, 1.0D, 1.0D, 0.75D),
					new AxisAlignedBB(0.25D, 0.0D, 0.25D, 1.0D, 1.0D, 1.0D),
					new AxisAlignedBB(0.0D, 0.0D, 0.3125D, 1.0D, 0.875D, 0.6875D),
					new AxisAlignedBB(0.0D, 0.0D, 0.25D, 1.0D, 1.0D, 1.0D),
					new AxisAlignedBB(0.25D, 0.0D, 0.0D, 1.0D, 1.0D, 0.75D),
					new AxisAlignedBB(0.25D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D),
					new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 0.75D),
					new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D)};
	protected static final AxisAlignedBB[] CLIP_AABB_BY_INDEX = new AxisAlignedBB[] {AABB_BY_INDEX[0].setMaxY(1.5D),
			AABB_BY_INDEX[1].setMaxY(1.5D), AABB_BY_INDEX[2].setMaxY(1.5D), AABB_BY_INDEX[3].setMaxY(1.5D),
			AABB_BY_INDEX[4].setMaxY(1.5D), AABB_BY_INDEX[5].setMaxY(1.5D), AABB_BY_INDEX[6].setMaxY(1.5D),
			AABB_BY_INDEX[7].setMaxY(1.5D), AABB_BY_INDEX[8].setMaxY(1.5D), AABB_BY_INDEX[9].setMaxY(1.5D),
			AABB_BY_INDEX[10].setMaxY(1.5D), AABB_BY_INDEX[11].setMaxY(1.5D), AABB_BY_INDEX[12].setMaxY(1.5D),
			AABB_BY_INDEX[13].setMaxY(1.5D), AABB_BY_INDEX[14].setMaxY(1.5D), AABB_BY_INDEX[15].setMaxY(1.5D)};

	public BlockHedge(String name) {
		super(Material.LEAVES);
		this.setHardness(0.2F);
		this.setResistance(0F);
		this.setUnlocalizedName(name);
		this.setDefaultState(this.blockState.getBaseState().withProperty(TYPE, EnumBlockType.OAK)
				.withProperty(UP, Boolean.valueOf(false)).withProperty(NORTH, Boolean.valueOf(false))
				.withProperty(EAST, Boolean.valueOf(false)).withProperty(SOUTH, Boolean.valueOf(false))
				.withProperty(WEST, Boolean.valueOf(false)));
	}

	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		state = this.getActualState(state, source, pos);
		return AABB_BY_INDEX[getAABBIndex(state)];
	}

	@Nullable
	public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, World worldIn, BlockPos pos) {
		blockState = this.getActualState(blockState, worldIn, pos);
		return CLIP_AABB_BY_INDEX[getAABBIndex(blockState)];
	}

	private static int getAABBIndex(IBlockState state) {
		int i = 0;

		if(((Boolean) state.getValue(NORTH)).booleanValue()) {
			i |= 1 << EnumFacing.NORTH.getHorizontalIndex();
		}

		if(((Boolean) state.getValue(EAST)).booleanValue()) {
			i |= 1 << EnumFacing.EAST.getHorizontalIndex();
		}

		if(((Boolean) state.getValue(SOUTH)).booleanValue()) {
			i |= 1 << EnumFacing.SOUTH.getHorizontalIndex();
		}

		if(((Boolean) state.getValue(WEST)).booleanValue()) {
			i |= 1 << EnumFacing.WEST.getHorizontalIndex();
		}

		return i;
	}

	public boolean isFullCube(IBlockState state) {
		return false;
	}

	public boolean isPassable(IBlockAccess worldIn, BlockPos pos) {
		return false;
	}

	/**
	 * Used to determine ambient occlusion and culling when rebuilding chunks for render
	 */
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	private boolean canConnectTo(IBlockAccess worldIn, BlockPos pos) {
		IBlockState iblockstate = worldIn.getBlockState(pos);
		Block block = iblockstate.getBlock();
		return (block instanceof BlockFenceGate) || (iblockstate.isFullCube());
	}

	@Override
	public ItemBlock getItemBlockClass(Block block) {
		return new ItemSubBlock(block, EnumBlockType.names());
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(TYPE).getMeta();
	}

	@Override
	public BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] {TYPE, UP, NORTH, SOUTH, EAST, WEST});
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return getDefaultState().withProperty(TYPE, EnumBlockType.VALUES[meta]);
	}

	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos,
			EnumFacing side) {
		return side == EnumFacing.DOWN ? super.shouldSideBeRendered(blockState, blockAccess, pos, side) : true;
	}

	public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
		boolean flag = this.canConnectTo(worldIn, pos.north());
		boolean flag1 = this.canConnectTo(worldIn, pos.east());
		boolean flag2 = this.canConnectTo(worldIn, pos.south());
		boolean flag3 = this.canConnectTo(worldIn, pos.west());
		boolean flag4 = flag && !flag1 && flag2 && !flag3 || !flag && flag1 && !flag2 && flag3;
		return state.withProperty(UP, Boolean.valueOf(!flag4 || !worldIn.isAirBlock(pos.up())))
				.withProperty(NORTH, Boolean.valueOf(flag)).withProperty(EAST, Boolean.valueOf(flag1))
				.withProperty(SOUTH, Boolean.valueOf(flag2)).withProperty(WEST, Boolean.valueOf(flag3));
	}

	@Override
	public void getSubBlocks(Item item, CreativeTabs creativeTabs, List<ItemStack> itemList) {
		for(EnumBlockType resourceType : EnumBlockType.VALUES)
			itemList.add(new ItemStack(item, 1, resourceType.getMeta()));
	}

	public enum EnumBlockType implements IBlockType {
		OAK(0), SPRUCE(1), BIRCH(2), JUNGLE(3), ACACIA(4), BIG_OAK(5);

		public static final EnumBlockType[] VALUES = values();

		private final int meta;

		EnumBlockType(int meta) {
			this.meta = meta;
		}

		@Override
		public int getMeta() {
			return meta;
		}

		@Override
		public String getName() {
			return name().toLowerCase();
		}

		public static String[] names() {
			ArrayList<String> names = new ArrayList<String>();
			for(EnumBlockType element : VALUES)
				names.add(element.toString().toLowerCase());

			return names.toArray(new String[0]);
		}
	}
}