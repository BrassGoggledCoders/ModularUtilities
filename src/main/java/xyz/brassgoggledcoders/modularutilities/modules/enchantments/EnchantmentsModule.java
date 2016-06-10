package xyz.brassgoggledcoders.modularutilities.modules.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import xyz.brassgoggledcoders.boilerplate.module.Module;
import xyz.brassgoggledcoders.boilerplate.module.ModuleBase;
import xyz.brassgoggledcoders.modularutilities.ModularUtilities;

@Module(mod = ModularUtilities.MODID)
public class EnchantmentsModule extends ModuleBase {

	static EntityEquipmentSlot[] hand = new EntityEquipmentSlot[] {EntityEquipmentSlot.MAINHAND};
	public static Enchantment affluency;
	
	@Override
	public String getName() {
		return "Enchantments";
	}
	@Override
	public void preInit(FMLPreInitializationEvent event)
	{
		MinecraftForge.EVENT_BUS.register(new EnchantmentEventHandler());
	}
	@Override
	public void init(FMLInitializationEvent event)
	{
		//TODO Expand to work on swords
		addEnchantment(71, "affluency", new BaseEnchantment(Enchantment.Rarity.RARE, EnumEnchantmentType.DIGGER, EnchantmentsModule.hand, 11, 3));
	}
	
	private Enchantment addEnchantment(int id, String name, Enchantment ench)
	{
		Enchantment.REGISTRY.register(id, new ResourceLocation(name), ench);
		ench.setName(name);
		return ench;
	}
}
