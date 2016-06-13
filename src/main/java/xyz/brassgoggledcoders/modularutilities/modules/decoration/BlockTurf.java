package xyz.brassgoggledcoders.modularutilities.modules.decoration;

import java.util.List;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import xyz.brassgoggledcoders.boilerplate.blocks.BlockBase;

public class BlockTurf extends BlockBase {
	
	public static final PropertyEnum<EnumBlockType> type = PropertyEnum.create("type", EnumBlockType.class);
	
	public BlockTurf() {
		super(Material.GRASS);
		this.setUnlocalizedName("turf");
		setDefaultState(this.blockState.getBaseState().withProperty(type, EnumBlockType.NORMAL));
	}
	
	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, type);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(type).getMeta();
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return getDefaultState().withProperty(type, EnumBlockType.VALUES[meta]);
	}
	
	@Override
	public void getSubBlocks(Item item, CreativeTabs creativeTabs, List<ItemStack> itemList) {
		for (EnumBlockType resourceType : EnumBlockType.VALUES) {
			itemList.add(new ItemStack(item, 1, resourceType.getMeta()));
		}
	}

	@Override
	public int damageDropped(IBlockState state) {
		return getMetaFromState(state);
	}
	
	public enum EnumBlockType implements IStringSerializable {
		NORMAL(0),
		DRY(1);
		
		public static final EnumBlockType[] VALUES = values();
		
		private final int meta;
		
		EnumBlockType(int meta) {
			this.meta = meta;
		}
		
		public int getMeta() {
			return meta;
		}
		
		@Override
		public String getName() {
			return name().toLowerCase();
		}	
	}
}
