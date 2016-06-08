package xyz.brassgoggledcoders.modularutilities.modules.enchantments;

import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import xyz.brassgoggledcoders.boilerplate.module.Module;
import xyz.brassgoggledcoders.boilerplate.module.ModuleBase;
import xyz.brassgoggledcoders.modularutilities.ModularUtilities;

@Module(mod = ModularUtilities.MODID)
public class EnchantmentsModule extends ModuleBase {
	
	static EntityEquipmentSlot[] hand = new EntityEquipmentSlot[] {EntityEquipmentSlot.MAINHAND};
	public static EnchantmentAffluency affluency;
	
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
		affluency = new EnchantmentAffluency();
	}
}
