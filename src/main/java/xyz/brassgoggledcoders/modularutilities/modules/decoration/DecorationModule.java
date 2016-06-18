package xyz.brassgoggledcoders.modularutilities.modules.decoration;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDirt;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickBlock;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.Event.Result;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import xyz.brassgoggledcoders.boilerplate.blocks.BlockBase;
import xyz.brassgoggledcoders.boilerplate.module.Module;
import xyz.brassgoggledcoders.boilerplate.module.ModuleBase;
import xyz.brassgoggledcoders.boilerplate.utils.ItemStackUtils;
import xyz.brassgoggledcoders.modularutilities.ModularUtilities;
import xyz.brassgoggledcoders.modularutilities.modules.decoration.BlockLeafCover.EnumLeafCoverBlockType;

@Module(mod = ModularUtilities.MODID)
public class DecorationModule extends ModuleBase
{

	public static Block turf, leaf_cover, stone_decor, smooth_glowstone;

	@Override
	public String getName()
	{
		return "Decoration";
	}

	@Override
	public void preInit(FMLPreInitializationEvent event)
	{
		turf = new BlockTurf();
		this.getBlockRegistry().registerBlock(turf);

		leaf_cover = new BlockLeafCover();
		this.getBlockRegistry().registerBlock(leaf_cover);

		stone_decor = new BlockStoneDecor();
		getBlockRegistry().registerBlock(stone_decor);

		smooth_glowstone = new BlockBase(Material.GLASS, "smooth_glowstone").setLightLevel(1F);
		getBlockRegistry().registerBlock(smooth_glowstone);
		/*
		 * TODO:
		 * - Stairs version of Path/Grass/Dirt/Smoothstone
		 * - Smooth Glowstone https://i.imgur.com/lywsYXl.jpg
		 * - Ender Pearl storage block
		 */
		MinecraftForge.EVENT_BUS.register(this);
	}

	@Override
	public void postInit(FMLPostInitializationEvent event)
	{
		for(int i = 0; i < EnumLeafCoverBlockType.VALUES.length - 2; i++)
			GameRegistry.addRecipe(new ItemStack(leaf_cover, 1, i), "XX", 'X', new ItemStack(Blocks.LEAVES, 1, i));
		GameRegistry.addRecipe(new ItemStack(leaf_cover, 1, 4), "XX", 'X', new ItemStack(Blocks.LEAVES2, 1, 0));
		GameRegistry.addRecipe(new ItemStack(leaf_cover, 1, 5), "XX", 'X', new ItemStack(Blocks.LEAVES2, 1, 1));

		GameRegistry.addRecipe(new ItemStack(stone_decor, 4, 0), "NB", "BN", 'N', Blocks.NETHER_BRICK, 'B',
				Blocks.BRICK_BLOCK);
		GameRegistry.addRecipe(new ItemStack(stone_decor, 4, 1), "NB", "BN", 'N', Blocks.STONE, 'B', Blocks.STONEBRICK);

		GameRegistry.addSmelting(Blocks.GLOWSTONE, new ItemStack(smooth_glowstone), 0);
	}

	@SubscribeEvent
	public void onBlockRightClicked(RightClickBlock event)
	{
		if(event.getWorld().isRemote)
			return;

		if(ItemStackUtils.doItemsMatch(event.getEntityPlayer().getHeldItemMainhand(), Items.SHEARS))
		{
			Block bl = event.getWorld().getBlockState(event.getPos()).getBlock();
			InventoryPlayer inv = event.getEntityPlayer().inventory;
			int meta = 0;
			boolean flag = false;

			if(bl == Blocks.GRASS)
			{
				Biome b = event.getWorld().getBiome(event.getPos());

				if(BiomeDictionary.isBiomeOfType(b, BiomeDictionary.Type.SANDY))
					meta = 1;
				else if(BiomeDictionary.isBiomeOfType(b, BiomeDictionary.Type.COLD))
					meta = 2;
				else if(BiomeDictionary.isBiomeOfType(b, BiomeDictionary.Type.JUNGLE))
					meta = 3;
				else if(BiomeDictionary.isBiomeOfType(b, BiomeDictionary.Type.SWAMP))
					meta = 4;

				flag = true;
			}
			else if(bl == Blocks.DIRT
					&& bl.getMetaFromState(event.getWorld().getBlockState(event.getPos())) == BlockDirt.DirtType.PODZOL
							.getMetadata())
			{
				meta = 5;
				flag = true;
			}
			else if(bl == Blocks.MYCELIUM)
			{
				meta = 6;
				flag = true;
			}

			if(flag && inv.addItemStackToInventory(new ItemStack(turf, 1, meta)))
			{
				event.getWorld().setBlockState(event.getPos(), Blocks.DIRT.getDefaultState());
				event.setUseItem(Result.ALLOW);
			}
		}
	}
}
