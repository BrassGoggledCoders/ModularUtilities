package xyz.brassgoggledcoders.modularutilities;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.IStringSerializable;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import xyz.brassgoggledcoders.modularutilities.modules.decoration.BlockTurf;
import xyz.brassgoggledcoders.modularutilities.modules.decoration.DecorationModule;

public class ClientProxy extends CommonProxy
{
	@Override
	public void registerModels()
	{
		registerVariantsDefaulted(DecorationModule.turf, BlockTurf.EnumTurfBlockType.class, "type");
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
}
