package xyz.brassgoggledcoders.modularutilities.modules.miscellaneous;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.item.ItemExpireEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import xyz.brassgoggledcoders.boilerplate.blocks.BlockBase;
import xyz.brassgoggledcoders.boilerplate.module.Module;
import xyz.brassgoggledcoders.boilerplate.module.ModuleBase;
import xyz.brassgoggledcoders.modularutilities.ModularUtilities;

@Module(mod = ModularUtilities.MODID)
public class MiscellaneousModule extends ModuleBase
{
	public static BlockBase feathers;
	public static ItemMachete machete;

	@Override
	public String getName()
	{
		return "Miscellaneous";
	}

	@Override
	public void preInit(FMLPreInitializationEvent event)
	{
		feathers = new BlockFeathers();
		this.getBlockRegistry().registerBlock(feathers);

		machete = new ItemMachete();
		this.getItemRegistry().registerItem(machete);

		MinecraftForge.EVENT_BUS.register(this);

		/*
		 * TODO:
		 * Gen
		 * - Dungeon loot extension
		 * - Villager trade & house type (Stable) extension
		 * - Skeleton skulls spawn rarely on the desert surface
		 * - Patches of surface block appear under biomes
		 * - Large Dark Oak trees
		 * - Villager Blacksmith Slab -> Anvil
		 * - Red Netherbrick for floor in Nether Fortresses
		 * ---
		 * - Commands
		 * - Achievement Extension
		 * - Jump Pad: Increases Jump height, slime block + piston
		 * - Sticky Slime Block: Slime Block + water. No Jumping.
		 * - Enchanting water bottle = Bottle of Enchanting?
		 * - Bedroll from Hay Bales/Feather Block. Durability, somehow? Won't set spawn.
		 * - Splash Water bottle for fire extinguishing.
		 * - Desert Golem that throws sand, giving you blindness?
		 * - Tool/Armour breakdown
		 * - Sound & Particle expansion
		 * - onFirstLogin screen for servers/pack devs
		 * - Splatter: swamp creeper that explodes into lingering potion effects
		 * - Quicksand
		 * https://www.reddit.com/r/minecraftsuggestions/comments/4nug0e/dweller_zombie_variant/
		 * Super sponges
		 */
	}

	@Override
	public void init(FMLInitializationEvent event)
	{
		OreDictionary.registerOre("wool", feathers);
		OreDictionary.registerOre("wool", Blocks.WOOL);

		Blocks.DRAGON_EGG.setCreativeTab(CreativeTabs.DECORATIONS);
		Blocks.FARMLAND.setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
	}

	@Override
	public void postInit(FMLPostInitializationEvent event)
	{
		// Extra Vanilla recipes
		GameRegistry.addShapelessRecipe(new ItemStack(Items.STRING, 2), new Object[] {new ItemStack(Blocks.WOOL)});
		GameRegistry.addShapelessRecipe(new ItemStack(Items.DYE, 6, 15), new Object[] {Items.BONE, Items.ROTTEN_FLESH});
		GameRegistry.addShapelessRecipe(new ItemStack(Blocks.DISPENSER), new Object[] {Blocks.DROPPER, Items.BOW});
		GameRegistry.addShapelessRecipe(new ItemStack(Blocks.PISTON),
				new Object[] {Blocks.STICKY_PISTON, Items.WATER_BUCKET});
		GameRegistry.addShapelessRecipe(new ItemStack(Blocks.SAND, 4),
				new Object[] {new ItemStack(Blocks.SANDSTONE, 1, OreDictionary.WILDCARD_VALUE)});
		GameRegistry.addShapelessRecipe(new ItemStack(Blocks.SAND, 4, 1),
				new Object[] {new ItemStack(Blocks.RED_SANDSTONE, 1, OreDictionary.WILDCARD_VALUE)});
		GameRegistry.addShapelessRecipe(new ItemStack(Items.QUARTZ, 6), new Object[] {Blocks.QUARTZ_STAIRS});
		GameRegistry.addShapelessRecipe(new ItemStack(Items.QUARTZ, 2),
				new Object[] {new ItemStack(Blocks.STONE_SLAB, 1, 7)});

		// Custom Recipes
		GameRegistry.addRecipe(new ItemStack(feathers), new Object[] {"XXX", "XXX", "XXX", 'X', Items.FEATHER});
		GameRegistry.addRecipe(new ItemStack(machete),
				new Object[] {"SI", 'S', Items.IRON_SWORD, 'I', Items.IRON_INGOT});
	}

	@SubscribeEvent
	public void onItemExpire(ItemExpireEvent event)
	{
		if(event.getEntityItem().getEntityItem().getRarity() == EnumRarity.EPIC)
		{
			event.setExtraLife(600);
			event.setCanceled(true);
		}
	}
}
