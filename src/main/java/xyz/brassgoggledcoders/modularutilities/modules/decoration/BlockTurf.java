package xyz.brassgoggledcoders.modularutilities.modules.decoration;

import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.teamacronymcoders.base.blocks.BlockFlat;
import com.teamacronymcoders.base.util.EnumUtils;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import xyz.brassgoggledcoders.modularutilities.ModularUtilities;

public class BlockTurf extends BlockFlat {
	public static final PropertyEnum<EnumDirtType> type = PropertyEnum.create("type", EnumDirtType.class);

	public BlockTurf() {
		super(Material.GRASS, EnumUtils.getNames(EnumDirtType.class));
		setUnlocalizedName("turf");
		setDefaultState(blockState.getBaseState().withProperty(type, EnumDirtType.NORMAL));
	}

	@Override
	@Nonnull
	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getBlockLayer() {
		return BlockRenderLayer.CUTOUT_MIPPED;
	}

	@Override
	public boolean isOpaqueCube(IBlockState blockState) {
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
	@Nonnull
	public BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, type);
	}

	@Override
	@Nonnull
	@SuppressWarnings("deprecation")
	public IBlockState getStateFromMeta(int meta) {
		return getDefaultState().withProperty(type, EnumDirtType.values()[meta]);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World player, List<String> tooltip, ITooltipFlag advanced) {
		tooltip.add(I18n.translateToLocal(ModularUtilities.MODID + ".turf.info"));
	}

	// @Override
	// public void getSubBlocks(@Nonnull Item item, CreativeTabs creativeTabs,
	// NonNullList<ItemStack> itemList) {
	// for(EnumDirtType resourceType : EnumDirtType.values())
	// itemList.add(new ItemStack(item, 1, resourceType.ordinal()));
	// }

	@Override
	@SideOnly(Side.CLIENT)
	public List<ModelResourceLocation> getModelResourceLocations(List<ModelResourceLocation> models) {
		for(EnumDirtType leaveType : EnumDirtType.values()) {
			models.add(new ModelResourceLocation(getMod().getID() + ":turf", "type=" + leaveType.getName()));
		}
		return models;
	}

	public enum EnumDirtType implements IStringSerializable {
		NORMAL, DRY, FROZEN, JUNGLE, SWAMP, PODZOL, MYCELIUM;

		@Override
		public String getName() {
			return name().toLowerCase();
		}
	}
}
