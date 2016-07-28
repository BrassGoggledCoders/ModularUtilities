package xyz.brassgoggledcoders.modularutilities.modules.decoration;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDirt;
import net.minecraft.block.BlockPrismarine;
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

@Module(mod = ModularUtilities.MODID)
public class DecorationModule extends ModuleBase {

	public static Block turf, leaf_cover, leaf_cover_opaque, stone_decor, smooth_glowstone, hedge, hedge_opaque,
			soul_glass, elder_sea_lantern;

	@Override
	public String getName() {
		return "Decoration";
	}

	@Override
	public void preInit(FMLPreInitializationEvent event) {
		turf = new BlockTurf();
		this.getBlockRegistry().registerBlock(turf);

		leaf_cover = new BlockLeafCover("leaf_cover", false);
		this.getBlockRegistry().registerBlock(leaf_cover);

		leaf_cover_opaque = new BlockLeafCover("leaf_cover_opaque", true);
		this.getBlockRegistry().registerBlock(leaf_cover_opaque);

		hedge = new BlockHedge("hedge", false);
		getBlockRegistry().registerBlock(hedge);

		hedge_opaque = new BlockHedge("hedge_opaque", true);
		getBlockRegistry().registerBlock(hedge_opaque);

		stone_decor = new BlockStoneDecor();
		getBlockRegistry().registerBlock(stone_decor);

		elder_sea_lantern = new BlockBase(Material.GLASS, "elder_sea_lantern").setLightLevel(15);
		getBlockRegistry().registerBlock(elder_sea_lantern);

		smooth_glowstone = new BlockBase(Material.GLASS, "smooth_glowstone").setLightLevel(1F);
		getBlockRegistry().registerBlock(smooth_glowstone);

		soul_glass = new BlockSoulGlass(Material.GLASS, "soul_glass");
		getBlockRegistry().registerBlock(soul_glass);
		/*
		 * TODO:
		 * - Stairs version of Path/Grass/Dirt/Smoothstone
		 * - Ender Pearl storage block
		 * - Ghost glass - can be walked through
		 * - Clear glass?
		 * - https://www.reddit.com/r/minecraftsuggestions/comments/4s6j64/glow_rods/?st=iqh3p22q&sh=84b6b566
		 */
		MinecraftForge.EVENT_BUS.register(this);
	}

	@Override
	public void postInit(FMLPostInitializationEvent event) {
		for(int i = 0; i < BlockLeafCover.EnumBlockType.VALUES.length - 2; i++) {
			GameRegistry.addRecipe(new ItemStack(leaf_cover, 2, i), "XX", 'X', new ItemStack(Blocks.LEAVES, 1, i));
			GameRegistry.addRecipe(new ItemStack(hedge, 6, i), "XXX", "XXX", 'X', new ItemStack(Blocks.LEAVES, 1, i));
		}
		GameRegistry.addRecipe(new ItemStack(leaf_cover, 2, 4), "XX", 'X', new ItemStack(Blocks.LEAVES2, 1, 0));
		GameRegistry.addRecipe(new ItemStack(leaf_cover, 2, 5), "XX", 'X', new ItemStack(Blocks.LEAVES2, 1, 1));
		GameRegistry.addRecipe(new ItemStack(hedge, 6, 4), "XXX", "XXX", 'X', new ItemStack(Blocks.LEAVES2, 1, 0));
		GameRegistry.addRecipe(new ItemStack(hedge, 6, 5), "XXX", "XXX", 'X', new ItemStack(Blocks.LEAVES2, 1, 1));
		for(int i = 0; i < BlockLeafCover.EnumBlockType.VALUES.length; i++) {
			GameRegistry.addShapelessRecipe(new ItemStack(leaf_cover_opaque, 1, i), Blocks.SAND,
					new ItemStack(leaf_cover, 1, i));
			GameRegistry.addShapelessRecipe(new ItemStack(hedge_opaque, 1, i), Blocks.SAND, new ItemStack(hedge, 1, i));
		}

		GameRegistry.addRecipe(new ItemStack(stone_decor, 4, 0), "NB", "BN", 'N', Blocks.NETHER_BRICK, 'B',
				Blocks.BRICK_BLOCK);
		GameRegistry.addRecipe(new ItemStack(stone_decor, 4, 1), "NB", "BN", 'N', Blocks.STONE, 'B', Blocks.STONEBRICK);
		for(int i = 0; i < BlockPrismarine.VARIANT.getAllowedValues().size(); i++) {
			GameRegistry.addShapelessRecipe(new ItemStack(stone_decor, 1, i + 2),
					new ItemStack(Blocks.PRISMARINE, 1, i), Items.GUNPOWDER);
		}
		GameRegistry.addShapelessRecipe(new ItemStack(elder_sea_lantern), Items.GUNPOWDER, Blocks.SEA_LANTERN);

		GameRegistry.addSmelting(Blocks.GLOWSTONE, new ItemStack(smooth_glowstone), 0);
		GameRegistry.addSmelting(Blocks.SOUL_SAND, new ItemStack(soul_glass), 0.3F);
		GameRegistry.addSmelting(new ItemStack(Blocks.BRICK_BLOCK), new ItemStack(stone_decor, 1, 5), 0.05F);
	}

	@SubscribeEvent
	public void onBlockRightClicked(RightClickBlock event) {
		if(event.getWorld().isRemote)
			return;

		if(ItemStackUtils.doItemsMatch(event.getEntityPlayer().getHeldItemMainhand(), Items.SHEARS)) {
			Block bl = event.getWorld().getBlockState(event.getPos()).getBlock();
			InventoryPlayer inv = event.getEntityPlayer().inventory;
			int meta = 0;
			boolean flag = false;

			if(bl == Blocks.GRASS) {
				Biome b = event.getWorld().getBiomeGenForCoords(event.getPos());

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
							.getMetadata()) {
				meta = 5;
				flag = true;
			}
			else if(bl == Blocks.MYCELIUM) {
				meta = 6;
				flag = true;
			}

			if(flag && inv.addItemStackToInventory(new ItemStack(turf, 1, meta))) {
				event.getWorld().setBlockState(event.getPos(), Blocks.DIRT.getDefaultState());
				event.setUseItem(Result.ALLOW);
			}
		}
	}
}
