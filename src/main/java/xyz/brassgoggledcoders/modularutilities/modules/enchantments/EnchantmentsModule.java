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
		int enchID = 80;
		addEnchantment(enchID, "affluency", new CustomEnchantment(Enchantment.Rarity.UNCOMMON, EnumEnchantmentType.DIGGER, EnchantmentsModule.hand, 11, 3)); //TODO Expand to work on swords
		addEnchantment(enchID++, "flame_touch", new CustomEnchantment(Enchantment.Rarity.RARE, EnumEnchantmentType.DIGGER, EnchantmentsModule.hand, 1, 21, new Enchantment[]{Enchantment.getEnchantmentByLocation("silk_touch")}));
	}
	
	private Enchantment addEnchantment(int id, String name, Enchantment ench)
	{
		Enchantment.REGISTRY.register(this.getConfigRegistry().getInt("enchantmentId" + name, id), new ResourceLocation(name), ench);
		ench.setName(name);
		return ench;
	}
}
