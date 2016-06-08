package xyz.brassgoggledcoders.modularutilities.modules.miscellaneous;

import net.minecraft.init.Blocks;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.oredict.OreDictionary;
import xyz.brassgoggledcoders.boilerplate.blocks.BlockBase;
import xyz.brassgoggledcoders.boilerplate.module.Module;
import xyz.brassgoggledcoders.boilerplate.module.ModuleBase;

@Module
public class MiscellaneousModule extends ModuleBase {
	
	public static BlockBase blockFeathers;
	public static BlockBase blockFusedQuartz;
	
	@Override
	public String getName() {
		return "Miscellaneous";
	}
	@Override
	public void preInit(FMLPreInitializationEvent event)
	{
		blockFeathers = new BlockFeathers();
		this.getBlockRegistry().registerBlock(blockFeathers);
		OreDictionary.registerOre("wool", blockFeathers);
		OreDictionary.registerOre("wool", Blocks.WOOL);
		//TODO Add 
		blockFusedQuartz = new BlockFusedQuartz();
		this.getBlockRegistry().registerBlock(blockFusedQuartz);
		/*
		 * TODO:
		 * - Redstone Sand
		 * - Resistor - opposite of repeater, changing settings changes resistance rather than delay
		 * - Dungeon loot extension
		 * - Villager trade extension
		 * - Blockdrop extension
		 * - Enchantments
		 * - Commands
		 * NOTE: Check for Quark Overlap
		 * - Throwable Rocks 
		 * - Splitter TNT 
		 * - Flamethrower
		 * - Jump Pad: Increases Jump height, slime block + piston 
		 * - Sticky Slime Block: Slime Block + water. No Jumping. 
		 * - Pile of Bones block: Gravity, acts like old bonemeal+ other buff somehow? 4x4 or 9x9 Bones? 
		 * - Compressed Bone Block: Like Pile of bones, but with no gravity. 4x4 pile o bones. 
		 * - Gunpowder + String = Fuses. Place like redstone, light one end, and fire will quickly travel down it 
		 * - Enchanting water bottle = Bottle of Enchanting? 
		 * - Bundle of Sugar Cane: Looks like bamboo 
		 * - Smelt Glowstone into Smooth glowstone? 
		 * - Polished Endstone and Obsidian. 4x4. 
		 * - Bedroll from Hay Bales/Feather Block. Durability, somehow? Won't set spawn. 
		 * - Splash Water bottle for fire extinguishing.
		 * - Desert Golem that throws sand, giving you blindness? 
		 * - Smoothstone Stairs!! 
		 * - Liquid Concrete: hardens into concrete 
		 * - Rebar: Makes solid concrete harder 
		 * - Ping sound when your name is said in chat? 
		 * - Stone Pillars! 
		 * - Brick + Netherbrick in 4x4 is special brick texture http://imgur.com/a/PVvHP#4 
		 * - Low Gravity Field: Higher Jump in a radius (5x5x5?) around block 
		 * - Weighted Boots: Causes player to fall very fast, but allows them to sink quickly in water? If you fall on something it damages it - damages entities, breaks glass, cracks bricks. 
		 * - Some way to turn off rain.
		 * - Spear: Throw with right, stab with left -
		 * - Slime in a Bucket :D 
		 * - Antigrav field 
		 * - Ender Pearl storage block 
		 * - Nether Brick Chest. Because.
		 *  - TNT tweaked to have contact with other entities. e.g. can be punched 
		 * - https://www.reddit.com/r/MinecraftModIdeas/comments/3azim0/simple_client_side_mod_for_measuring/
		 */
	}
}
