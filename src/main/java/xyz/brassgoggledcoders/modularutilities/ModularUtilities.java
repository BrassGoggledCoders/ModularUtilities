package xyz.brassgoggledcoders.modularutilities;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import xyz.brassgoggledcoders.boilerplate.BaseCreativeTab;
import xyz.brassgoggledcoders.boilerplate.BoilerplateModBase;

@Mod(modid = ModularUtilities.MODID, name = ModularUtilities.MODNAME, version = ModularUtilities.MODVERSION)
public class ModularUtilities extends BoilerplateModBase {
		public ModularUtilities() {
			super(MODID, MODNAME, MODVERSION, tab);
		}

		@Instance("modularutilities")
		public static ModularUtilities instance;

		public static final String MODID = "modularutilities";
		public static final String MODNAME = "Modular Utilities";
		public static final String MODVERSION = "@VERSION@";

		public static CreativeTabs tab = new MUTab();
		
		@EventHandler
		public void preInit(FMLPreInitializationEvent event)
		{
			super.preInit(event);
		}

		@EventHandler
		public void init(FMLInitializationEvent event)
		{
			super.init(event);
		}

		@EventHandler
		public void postInit(FMLPostInitializationEvent event)
		{
			super.postInit(event);
		}
		
		@Override
		public Object getInstance()
		{
			return instance;
		}
		public static class MUTab extends BaseCreativeTab
		{

			public MUTab()
			{
				super(MODID);
			}

			@Override
			public Item getTabIconItem()
			{
				return Items.APPLE;
			}

		}
	}
