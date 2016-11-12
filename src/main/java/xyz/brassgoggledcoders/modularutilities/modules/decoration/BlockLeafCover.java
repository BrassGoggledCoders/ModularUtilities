package xyz.brassgoggledcoders.modularutilities.modules.decoration;

import com.teamacronymcoders.base.blocks.BlockFlat;
import com.teamacronymcoders.base.util.EnumUtils;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
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

	public static PropertyEnum<EnumLeaveType> type = PropertyEnum.create("type", EnumLeaveType.class);
	private boolean opaque;

	public BlockLeafCover(String name, boolean opaque) {
		super(Material.LEAVES, EnumUtils.getNames(EnumLeaveType.class));
		this.setUnlocalizedName(name);
		setDefaultState(this.blockState.getBaseState().withProperty(type, EnumLeaveType.OAK));
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
		return getDefaultState().withProperty(type, EnumLeaveType.values()[meta]);
	}

	@Override
	public void getSubBlocks(Item item, CreativeTabs creativeTabs, List<ItemStack> itemList) {
		for(EnumLeaveType resourceType : EnumLeaveType.values())
			itemList.add(new ItemStack(item, 1, resourceType.ordinal()));
	}

	@Override
	public BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, type);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public List<ModelResourceLocation> getModelResourceLocations(List<ModelResourceLocation> models) {
		String modelName = "leaf_cover" + ((this.opaque) ? "_opaque" : "");
		for(EnumLeaveType leaveType : EnumLeaveType.values()) {
			models.add(new ModelResourceLocation(getMod().getPrefix() + modelName, "type=" + leaveType.getName()));
		}
		return models;
	}
}
