package xyz.brassgoggledcoders.modularutilities;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ColorizerFoliage;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeColorHelper;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import xyz.brassgoggledcoders.modularutilities.modules.decoration.BlockLeafCover;
import xyz.brassgoggledcoders.modularutilities.modules.decoration.BlockStoneDecor;
import xyz.brassgoggledcoders.modularutilities.modules.decoration.BlockTurf;
import xyz.brassgoggledcoders.modularutilities.modules.decoration.DecorationModule;

public class ClientProxy extends CommonProxy
{
	@Override
	public void registerModels()
	{
		if(ModularUtilities.instance.getModuleHandler().isModuleEnabled("Decoration"))
		{
			registerVariantsDefaulted(DecorationModule.turf, BlockTurf.EnumTurfBlockType.class, "type");
			registerVariantsDefaulted(DecorationModule.leaf_cover, BlockLeafCover.EnumLeafCoverBlockType.class, "type");
			registerVariantsDefaulted(DecorationModule.stone_decor, BlockStoneDecor.EnumBlockType.class, "type");
		}
	}

	@Override
	public void registerColors()
	{
		if(ModularUtilities.instance.getModuleHandler().isModuleEnabled("Decoration"))
		{
			Minecraft.getMinecraft().getBlockColors().registerBlockColorHandler(new LeafColors(),
					new Block[] {DecorationModule.leaf_cover});
			Minecraft.getMinecraft().getItemColors().registerItemColorHandler(new LeafColors(),
					new Block[] {DecorationModule.leaf_cover});
		}
	}

	@Override
	public void spawnFX(EnumParticleTypes type, BlockPos pos)
	{
		World world = Minecraft.getMinecraft().theWorld;
		if(type == EnumParticleTypes.PORTAL)
		{
			for(int j = 0; j < 100; ++j)
			{
				world.spawnParticle(type, pos.getX() + (-0.2 + world.rand.nextDouble()), pos.getY(),
						pos.getZ() + (-0.2 + world.rand.nextDouble()), 0, 0, 0, new int[0]);
			}
		}
		else if(type == EnumParticleTypes.FLAME)
		{
			for(int j = 0; j < 20; ++j)
			{
				world.spawnParticle(type, pos.getX() + (-0.2 + world.rand.nextDouble()), pos.getY(),
						pos.getZ() + (-0.2 + world.rand.nextDouble()), world.rand.nextDouble(), world.rand.nextDouble(),
						world.rand.nextDouble(), new int[0]);
			}
			for(int j = 0; j < 20; ++j)
			{
				world.spawnParticle(type, pos.getX() + (-0.2 + world.rand.nextDouble()), pos.getY(),
						pos.getZ() + (-0.2 + world.rand.nextDouble()), -world.rand.nextDouble(),
						-world.rand.nextDouble(), -world.rand.nextDouble(), new int[0]);
			}
		}
	}

	private static <T extends Enum<T> & IStringSerializable> void registerVariantsDefaulted(Block b, Class<T> enumclazz,
			String variantHeader)
	{
		Item item = Item.getItemFromBlock(b);
		for(T e : enumclazz.getEnumConstants())
		{
			String baseName = ForgeRegistries.BLOCKS.getKey(b).toString();
			String variantName = variantHeader + "=" + e.getName();
			ModelLoader.setCustomModelResourceLocation(item, e.ordinal(),
					new ModelResourceLocation(baseName, variantName));
		}
	}

	public class LeafColors implements IBlockColor, IItemColor
	{
		public int colorMultiplier(IBlockState state, @Nullable IBlockAccess worldIn, @Nullable BlockPos pos,
				int tintIndex)
		{
			return worldIn != null && pos != null ? BiomeColorHelper.getFoliageColorAtPos(worldIn, pos)
					: ColorizerFoliage.getFoliageColorBasic();
		}

		@Override
		public int getColorFromItemstack(ItemStack stack, int tintIndex)
		{
			return ColorizerFoliage.getFoliageColorBasic();
		}
	}
}
