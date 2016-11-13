package xyz.brassgoggledcoders.modularutilities.modules.decoration.proxy;

import com.teamacronymcoders.base.modulesystem.proxies.ModuleProxyBase;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ColorizerFoliage;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.biome.BiomeColorHelper;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import xyz.brassgoggledcoders.modularutilities.modules.decoration.DecorationModule;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class DecorationClient extends ModuleProxyBase {
	@Override
	public void init(FMLInitializationEvent event) {
		Block[] toColourise = new Block[] {DecorationModule.leaf_cover, DecorationModule.hedge,
				DecorationModule.hedge_opaque, DecorationModule.leaf_cover_opaque};
		Minecraft.getMinecraft().getBlockColors().registerBlockColorHandler(new LeafColors(), toColourise);
		Minecraft.getMinecraft().getItemColors().registerItemColorHandler(new LeafColors(), toColourise);
	}

	// TODO Birch/Spruce Specialcasing
	public class LeafColors implements IBlockColor, IItemColor {
		@Override
		public int colorMultiplier(@Nonnull IBlockState state, @Nullable IBlockAccess worldIn, @Nullable BlockPos pos,
				int tintIndex) {
			return (worldIn != null && pos != null) ? BiomeColorHelper.getFoliageColorAtPos(worldIn, pos)
					: ColorizerFoliage.getFoliageColorBasic();
		}

		@Override
		public int getColorFromItemstack(@Nonnull ItemStack stack, int tintIndex) {
			return ColorizerFoliage.getFoliageColorBasic();
		}
	}
}
