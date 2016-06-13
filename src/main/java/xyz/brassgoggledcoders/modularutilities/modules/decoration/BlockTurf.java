package xyz.brassgoggledcoders.modularutilities.modules.decoration;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import xyz.brassgoggledcoders.boilerplate.blocks.BlockBase;

public class BlockTurf extends BlockBase {
	
	public static final PropertyEnum<EnumBlockType> type = PropertyEnum.create("type", EnumBlockType.class);
	protected static final AxisAlignedBB AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.0625D, 1.0D);
	
	public BlockTurf() {
		super(Material.GRASS);
		this.setUnlocalizedName("turf");
		setDefaultState(this.blockState.getBaseState().withProperty(type, EnumBlockType.NORMAL));
	}
	
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
	{
		return AABB;
	}
    
	@Override
	public boolean isOpaqueCube(IBlockState state)
	{
		return false;
	}
    
	@Override
	public boolean isFullCube(IBlockState state)
	{
	    return false;
	}
    
	@Override
    public boolean canPlaceBlockAt(World worldIn, BlockPos pos)
    {
        return super.canPlaceBlockAt(worldIn, pos) && this.canBlockStay(worldIn, pos);
    }
    
	@Override
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn)
    {
        this.checkForDrop(worldIn, pos, state);
    }

    private boolean checkForDrop(World worldIn, BlockPos pos, IBlockState state)
    {
        if (!this.canBlockStay(worldIn, pos))
        {
            this.dropBlockAsItem(worldIn, pos, state, 0);
            worldIn.setBlockToAir(pos);
            return false;
        }
        else
        {
            return true;
        }
    }

    private boolean canBlockStay(World worldIn, BlockPos pos)
    {
        return !worldIn.isAirBlock(pos.down());
    }

    @SideOnly(Side.CLIENT)
	@Override
    public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side)
    {
    	//return true;
        return side == EnumFacing.UP ? true : (blockAccess.getBlockState(pos.offset(side)).getBlock() == this ? true : super.shouldSideBeRendered(blockState, blockAccess, pos, side));
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
		DRY(1),
		FROZEN(2),
		JUNGLE(3),
		SWAMP(4),
		PODZOL(5),
		MYCELIUM(6);
		
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
