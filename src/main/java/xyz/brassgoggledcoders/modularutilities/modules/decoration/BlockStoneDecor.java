package xyz.brassgoggledcoders.modularutilities.modules.decoration;

import java.util.List;

import javax.annotation.Nonnull;

import com.teamacronymcoders.base.blocks.BlockSubBase;
import com.teamacronymcoders.base.util.EnumUtils;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.util.IStringSerializable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockStoneDecor extends BlockSubBase {
	public static final PropertyEnum<EnumStoneType> type = PropertyEnum.create("type", EnumStoneType.class);

	public BlockStoneDecor() {
		super(Material.ROCK, EnumUtils.getNames(EnumStoneType.class));
		this.setUnlocalizedName("stone_decor");
		setDefaultState(this.blockState.getBaseState().withProperty(type, EnumStoneType.CLINKER_BRICK));
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
		return getDefaultState().withProperty(type, EnumStoneType.values()[meta]);
	}

	// @Override
	// public void getSubBlocks(@Nonnull Item item, CreativeTabs creativeTabs, NonNullList<ItemStack> itemList) {
	// for(EnumStoneType resourceType : EnumStoneType.values())
	// itemList.add(new ItemStack(item, 1, resourceType.ordinal()));
	// }

	@Override
	@SideOnly(Side.CLIENT)
	public List<ModelResourceLocation> getModelResourceLocations(List<ModelResourceLocation> models) {
		for(EnumStoneType stoneType : EnumStoneType.values()) {
			models.add(new ModelResourceLocation(getMod().getID() + ":stone_decor", "type=" + stoneType.getName()));
		}
		return models;
	}

	public enum EnumStoneType implements IStringSerializable {
		CLINKER_BRICK, CARVED_STONE, ELDER_PRISMARINE_ROUGH, ELDER_PRISMARINE_BRICKS, ELDER_PRISMARINE_DARK,
		MORTARLESS_BRICK;

		@Override
		public String getName() {
			return name().toLowerCase();
		}
	}
}
