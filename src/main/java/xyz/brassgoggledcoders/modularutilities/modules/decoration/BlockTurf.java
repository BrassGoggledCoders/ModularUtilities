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
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import xyz.brassgoggledcoders.boilerplate.blocks.BlockThin;
import xyz.brassgoggledcoders.boilerplate.blocks.IBlockType;
import xyz.brassgoggledcoders.boilerplate.blocks.ItemSubBlock;
import xyz.brassgoggledcoders.boilerplate.client.models.ISimpleVariant;

public class BlockTurf extends BlockThin implements ISimpleVariant {

	public static final PropertyEnum<EnumBlockType> type = PropertyEnum.create("type", EnumBlockType.class);
	protected static final AxisAlignedBB AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.0625D, 1.0D);

	public BlockTurf() {
		super(Material.GRASS, EnumBlockType.names());
		this.setUnlocalizedName("turf");
		setDefaultState(this.blockState.getBaseState().withProperty(type, EnumBlockType.NORMAL));
	}

	@Override
	public ItemBlock getItemBlockClass(Block block) {
		return new ItemSubBlock(block, EnumBlockType.names());
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
		return state.getValue(type).getMeta();
	}

	@Override
	public BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, type);
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return getDefaultState().withProperty(type, EnumBlockType.VALUES[meta]);
	}

	@Override
	public void getSubBlocks(Item item, CreativeTabs creativeTabs, List<ItemStack> itemList) {
		for(EnumBlockType resourceType : EnumBlockType.VALUES)
			itemList.add(new ItemStack(item, 1, resourceType.getMeta()));
	}

	public enum EnumBlockType implements IBlockType {
		NORMAL(0), DRY(1), FROZEN(2), JUNGLE(3), SWAMP(4), PODZOL(5), MYCELIUM(6);

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

	@Override
	public Class<? extends IBlockType> getEnumToSwitch() {
		return BlockTurf.EnumBlockType.class;
	}
}
