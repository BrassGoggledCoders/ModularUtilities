package xyz.brassgoggledcoders.modularutilities.modules.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import xyz.brassgoggledcoders.boilerplate.module.Module;
import xyz.brassgoggledcoders.boilerplate.module.ModuleBase;
import xyz.brassgoggledcoders.modularutilities.ModularUtilities;

@Module(mod = ModularUtilities.MODID)
public class EnchantmentsModule extends ModuleBase {

	static EntityEquipmentSlot[] hand = new EntityEquipmentSlot[] {EntityEquipmentSlot.MAINHAND};
	public static Enchantment affluency, flame_touch, prospector; 
	
	@Override
	public String getName() {
		return "Enchantments";
	}
	@Override
	public void preInit(FMLPreInitializationEvent event)
	{
																															//multiplier, min (flat), maxLevel 
		affluency = addEnchantment("affluency", new CustomEnchantment(Enchantment.Rarity.UNCOMMON, EnumEnchantmentType.DIGGER, hand, 11, 0, 3)); //TODO Expand to work on swords
		flame_touch = addEnchantment("flame_touch", new CustomEnchantment(Enchantment.Rarity.RARE, EnumEnchantmentType.DIGGER, hand, 0, 21, 1));
		//TODO Magnetism enchant
		prospector = addEnchantment("prospector", new CustomEnchantment(Enchantment.Rarity.COMMON, EnumEnchantmentType.DIGGER, hand, 11, 3, 4));
		
		
		MinecraftForge.EVENT_BUS.register(new EnchantmentEventHandler());
	}

	private Enchantment addEnchantment(/*int id,*/ String name, Enchantment ench)
	{
		//ConfigEntry cEntry = new ConfigEntry("Enchantment IDs", name, Type.INTEGER, String.valueOf(id));
		//this.getConfigRegistry().addEntry(cEntry);
		Enchantment.REGISTRY.register(0, new ResourceLocation(name), ench);
		ench.setName(name);
		return ench;
	}
}
