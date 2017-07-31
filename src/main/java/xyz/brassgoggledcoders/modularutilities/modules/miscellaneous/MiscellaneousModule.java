package xyz.brassgoggledcoders.modularutilities.modules.miscellaneous;

import java.util.Arrays;

import com.teamacronymcoders.base.blocks.BlockBase;
import com.teamacronymcoders.base.modulesystem.Module;
import com.teamacronymcoders.base.modulesystem.ModuleBase;

import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.item.ItemExpireEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import xyz.brassgoggledcoders.modularutilities.ModularUtilities;

@Module(ModularUtilities.MODID)
public class MiscellaneousModule extends ModuleBase {
	public static BlockBase feathers, magmagold;
	public static Item goldpetal;

	@Override
	public String getName() {
		return "Miscellaneous";
	}

	@Override
	public void preInit(FMLPreInitializationEvent event) {
		feathers = new BlockFeathers();
		this.getBlockRegistry().register(feathers);

		goldpetal = new ItemGoldPetal("goldpetal");
		this.getItemRegistry().register(goldpetal);

		magmagold = new BlockMagmagold(Material.ROCK, "magmagold");
		this.getBlockRegistry().register(magmagold);

		MinecraftForge.EVENT_BUS.register(this);
		GameRegistry.registerWorldGenerator(new WorldGeneratorModularUtils(), 2);
		/*
		 * TODO:
		 * Gen
		 * - Dungeon loot extension
		 * - More villager things
		 * - Skeleton skulls spawn rarely on the desert surface
		 * - Patches of surface block appear under biomes
		 * - Large Dark Oak trees
		 * - Villager Blacksmith Slab -> Anvil
		 * - Red Netherbrick for floor in Nether Fortresses
		 * ---
		 * - Baubles
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
		 * - Super sponges
		 * - 'Booster' that adds velocity to entites in a direction.
		 * - Golden Shield
		 * - Spreading fire
		 * - Burnt grass?
		 * - New death messages
		 * - https://www.reddit.com/r/minecraftsuggestions/comments/4o4oho/suggestion_new_death_messages/
		 * -
		 * https://www.reddit.com/r/minecraftsuggestions/comments/4nxnrd/farms_in_zombie_villages_should_be_barren_and/
		 * -
		 * https://www.reddit.com/r/minecraftsuggestions/comments/4o3nhr/zombies_and_skeletons_that_spawn_with_leather/
		 * - https://www.reddit.com/r/minecraftsuggestions/comments/4o4h9y/dripping_water_should_have_sound_effects/
		 * - Easier item filtering
		 * - Either tweak water to not look flowing next to glass, or add a special glass that allows seeing through
		 * water.
		 * - https://www.reddit.com/r/minecraftsuggestions/comments/4ok9ob/appropriate_water_texture/
		 * - Interdictor - a block that prevents hostile mobs from spawning within 15 blocks
		 * - Method of obtaining multiple dragon eggs
		 * - Barrels, maybe
		 * - Some simple charset stuff?
		 * - Mob spawner relocation method?
		 * - Nether Star Block
		 */
	}

	@Override
	public void init(FMLInitializationEvent event) {
		OreDictionary.registerOre("wool", feathers);
		OreDictionary.registerOre("wool", Blocks.WOOL);

		Blocks.DRAGON_EGG.setCreativeTab(CreativeTabs.DECORATIONS);
		Blocks.FARMLAND.setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
	}

	@Override
	public void postInit(FMLPostInitializationEvent event) {
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
		GameRegistry.addRecipe(new ItemStack(Items.GOLD_INGOT), new Object[] {"XXX", "XXX", "XXX", 'X', goldpetal});
	}

	@SubscribeEvent
	public void onItemExpire(ItemExpireEvent event) {
		ItemStack[] rareStuff = new ItemStack[] {new ItemStack(Items.ELYTRA)};
		if(event.getEntityItem().getItem().getRarity() == EnumRarity.EPIC
				|| Arrays.asList(rareStuff).contains(event.getEntityItem().getItem())) {
			event.setExtraLife(600);
			event.setCanceled(true);
		}
	}
}
