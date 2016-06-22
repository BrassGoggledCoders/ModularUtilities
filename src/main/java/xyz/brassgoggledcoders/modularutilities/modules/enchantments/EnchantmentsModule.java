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
	public static Enchantment affluency, flame_touch, prospector, vampirism, soulbound;

	@Override
	public String getName() {
		return "Enchantments";
	}

	@Override
	public void preInit(FMLPreInitializationEvent event) {
		/*
		 * TODO
		 * Magnetism (attracts items)
		 * Salvage (like prospector for glass etc)
		 * Reach
		 * Teleport
		 * Spelunker (gives night vision when tool is held)
		 * Armour Enchant that increases regen
		 * Exploding arrow enchant
		 * Lightning arrow enchant
		 * Homing arrow enchant
		 * Critical arrow enchant
		 * May only have one of the above arrow enchs
		 * Perm jump/speed boost boots - generalise?
		 * Shield Enchant: repel mobs/arrows, explosive absorbtion, convert blocked damage to health
		 * Flamethorns
		 * Infinity II that doesn't need an arrow
		 * Elytra enchants
		 * Pass handling to enchantment classes themselves
		 */
		// TODO: Expand to work on swords
		affluency = addEnchantment("affluency",
				new CustomEnchantment(Enchantment.Rarity.UNCOMMON, EnumEnchantmentType.DIGGER, hand, 11, 0, 3));
		flame_touch = addEnchantment("flame_touch",
				new CustomEnchantment(Enchantment.Rarity.RARE, EnumEnchantmentType.DIGGER, hand, 0, 21, 1));
		prospector = addEnchantment("prospector",
				new EnchantmentProspector(Enchantment.Rarity.COMMON, EnumEnchantmentType.DIGGER, hand, 5, 0, 4));
		vampirism = addEnchantment("vampirism",
				new CustomEnchantment(Enchantment.Rarity.VERY_RARE, EnumEnchantmentType.WEAPON, hand, 20, 10, 2));
		soulbound = addEnchantment("soulbound", new CustomEnchantment(Enchantment.Rarity.VERY_RARE,
				EnumEnchantmentType.ALL, EntityEquipmentSlot.values(), 30, 0, 1).setTreasureEnchantment());

		MinecraftForge.EVENT_BUS.register(new EnchantmentEventHandler());
	}

	private Enchantment addEnchantment(String name, Enchantment ench) {
		// TODO Is this the correct way to do things?
		Enchantment.REGISTRY.register(0, new ResourceLocation(ModularUtilities.MODID, name), ench);
		ench.setName(name);
		return ench;
	}
}
