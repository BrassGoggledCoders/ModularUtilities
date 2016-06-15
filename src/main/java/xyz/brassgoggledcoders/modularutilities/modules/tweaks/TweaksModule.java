package xyz.brassgoggledcoders.modularutilities.modules.tweaks;

import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import xyz.brassgoggledcoders.boilerplate.module.ModuleBase;

// @Module(mod = ModularUtilities.MODID)
public class TweaksModule extends ModuleBase
{

	@Override
	public String getName()
	{
		return "Tweaks";
	}

	@Override
	public void preInit(FMLPreInitializationEvent event)
	{
		/*
		 * TODO:
		 * - TNT tweaked to have contact with other entities. e.g. can be punched
		 * - In-game messages (not chat) appear in the 'info' (music disks etc) slot rather than chatbar
		 * - Gold Tools/Armor Tweaked to be useful
		 * - Ding Sound when getting achievements (excluding starter ones) and when your name is mentioned in chat
		 * - Spectator mode on death in hardcore
		 * - Wild wolves become agressive during a full moon
		 * - Easier slimeballs?
		 * - Maps show biome colour
		 */
	}
}
