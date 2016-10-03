package xyz.brassgoggledcoders.modularutilities;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.ForgeModContainer;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.UniversalBucket;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import xyz.brassgoggledcoders.boilerplate.BaseCreativeTab;
import xyz.brassgoggledcoders.boilerplate.BoilerplateModBase;
import xyz.brassgoggledcoders.modularutilities.modules.construction.ConstructionModule;
import xyz.brassgoggledcoders.modularutilities.modules.enchantments.CustomEnchantment;
import xyz.brassgoggledcoders.modularutilities.modules.ender.EnderModule;
import xyz.brassgoggledcoders.modularutilities.proxies.CommonProxy;

@Mod(modid = ModularUtilities.MODID, name = ModularUtilities.MODNAME, version = ModularUtilities.MODVERSION,
		dependencies = "required-after:boilerplate")
public class ModularUtilities extends BoilerplateModBase {
	public ModularUtilities() {
		super(MODID, MODNAME, MODVERSION, tab);
	}

	@Instance("modularutilities")
	public static ModularUtilities instance;

	@SidedProxy(clientSide = "xyz.brassgoggledcoders.modularutilities.proxies.ClientProxy",
			serverSide = "xyz.brassgoggledcoders.modularutilities.proxies.CommonProxy")
	public static CommonProxy proxy;

	public static final String MODID = "modularutilities";
	public static final String MODNAME = "Modular Utilities";
	public static final String MODVERSION = "@VERSION@";

	public static CreativeTabs tab = new MUTab();

	static {
		FluidRegistry.enableUniversalBucket();
	}

	@Override
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		super.preInit(event);
		proxy.registerModels();
	}

	@Override
	@EventHandler
	public void init(FMLInitializationEvent event) {
		super.init(event);
		proxy.registerColors();
	}

	@Override
	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		super.postInit(event);
	}

	@Override
	public Object getInstance() {
		return instance;
	}

	public static class MUTab extends BaseCreativeTab {
		public MUTab() {
			super(MODID);
			this.setRelevantEnchantmentTypes(EnumEnchantmentType.values());
		}

		@SideOnly(Side.CLIENT)
		@Override
		public void addEnchantmentBooksToList(List<ItemStack> itemList, EnumEnchantmentType... enchantmentType) {
			for(Enchantment enchantment : Enchantment.REGISTRY)
				// A little expensive, but its on load, so shouldn't be a big deal
				if(enchantment instanceof CustomEnchantment)
					itemList.add(Items.ENCHANTED_BOOK
							.getEnchantedItemStack(new EnchantmentData(enchantment, enchantment.getMaxLevel())));
		}

		@Override
		@SideOnly(Side.CLIENT)
		public void displayAllRelevantItems(List<ItemStack> items) {
			if(ModularUtilities.instance.getModuleHandler().isModuleEnabled("Construction")) {
				items.add(UniversalBucket.getFilledBucket(ForgeModContainer.getInstance().universalBucket,
						FluidRegistry.getFluid("dirt")));
				items.add(UniversalBucket.getFilledBucket(ForgeModContainer.getInstance().universalBucket,
						FluidRegistry.getFluid("concrete")));
			}
			super.displayAllRelevantItems(items);
		}

		@Override
		public Item getTabIconItem() {
			if(ModularUtilities.instance.getModuleHandler().isModuleEnabled("Ender"))
				return EnderModule.ender_glove;
			else if(ModularUtilities.instance.getModuleHandler().isModuleEnabled("Construction"))
				return Item.getItemFromBlock(ConstructionModule.blast_glass);
			else
				return Item.getItemFromBlock(Blocks.SPONGE);
		}
	}
}
