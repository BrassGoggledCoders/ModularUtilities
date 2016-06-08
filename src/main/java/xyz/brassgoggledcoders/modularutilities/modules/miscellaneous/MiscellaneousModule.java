package xyz.brassgoggledcoders.modularutilities.modules.miscellaneous;

import net.minecraft.init.Blocks;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.oredict.OreDictionary;
import xyz.brassgoggledcoders.boilerplate.blocks.BlockBase;
import xyz.brassgoggledcoders.boilerplate.module.Module;
import xyz.brassgoggledcoders.boilerplate.module.ModuleBase;
import xyz.brassgoggledcoders.modularutilities.ModularUtilities;

@Module(mod = ModularUtilities.MODID)
public class MiscellaneousModule extends ModuleBase {
	
	public static BlockBase blockFeathers; 
	
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
		/*
		 * TODO:
		 * - Dungeon loot extension
		 * - Villager trade & house type (Stable) extension
		 * - Blockdrop extension
		 * - Enchantments
		 * - Commands
		 * - Throwable Rocks 
		 * - Jump Pad: Increases Jump height, slime block + piston 
		 * - Sticky Slime Block: Slime Block + water. No Jumping. 
		 * - Gunpowder + String = Fuses. Place like redstone, light one end, and fire will quickly travel down it 
		 * - Enchanting water bottle = Bottle of Enchanting? 
		 * - Polished Endstone and Obsidian. 4x4. 
		 * - Bedroll from Hay Bales/Feather Block. Durability, somehow? Won't set spawn. 
		 * - Splash Water bottle for fire extinguishing.
		 * - Desert Golem that throws sand, giving you blindness? 
		 * - Smoothstone Stairs 
		 * - Ping sound when your name is said in chat?  
		 * - Brick + Netherbrick in 4x4 is special brick texture http://imgur.com/a/PVvHP#4 
		 * - Ender Pearl storage block  
		 * - https://www.reddit.com/r/MinecraftModIdeas/comments/3azim0/simple_client_side_mod_for_measuring/
		 */
	}
}
