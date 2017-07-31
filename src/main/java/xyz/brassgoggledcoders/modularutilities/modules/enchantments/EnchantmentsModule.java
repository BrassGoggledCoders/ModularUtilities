package xyz.brassgoggledcoders.modularutilities.modules.enchantments;

import java.util.Iterator;

import com.teamacronymcoders.base.modulesystem.Module;
import com.teamacronymcoders.base.modulesystem.ModuleBase;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingExperienceDropEvent;
import net.minecraftforge.event.entity.player.PlayerDropsEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import xyz.brassgoggledcoders.modularutilities.ModularUtilities;

@Module(ModularUtilities.MODID)
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
		affluency = addEnchantment("affluency",
				new EnchantmentAffluency(Enchantment.Rarity.UNCOMMON, EnumEnchantmentType.DIGGER, hand, 11, 0, 3));
		flame_touch = addEnchantment("flame_touch",
				new EnchantmentFlameTouch(Enchantment.Rarity.RARE, EnumEnchantmentType.DIGGER, hand, 0, 21, 1));
		prospector = addEnchantment("prospector",
				new EnchantmentProspector(Enchantment.Rarity.COMMON, EnumEnchantmentType.DIGGER, hand, 5, 0, 4));
		vampirism = addEnchantment("vampirism",
				new EnchantmentVampirism(Enchantment.Rarity.VERY_RARE, EnumEnchantmentType.WEAPON, hand, 20, 10, 2));
		soulbound = addEnchantment("soulbound", new CustomEnchantment(Enchantment.Rarity.VERY_RARE,
				EnumEnchantmentType.ALL, EntityEquipmentSlot.values(), 30, 0, 1).setTreasureEnchantment());

		if(Loader.isModLoaded("enderio")) {
			FMLInterModComms.sendMessage("enderio", "recipe:enchanter", "EnchanterRecipes_MoU.xml");
		}

		MinecraftForge.EVENT_BUS.register(this);
	}

	private Enchantment addEnchantment(String name, Enchantment ench) {
		Enchantment.REGISTRY.register(0, new ResourceLocation(ModularUtilities.MODID, name), ench);
		ench.setName(name);
		return ench;
	}

	@SubscribeEvent
	public void onBlockBreak(BlockEvent.BreakEvent event) {
		if(event.getPlayer().getHeldItemMainhand() == null
				|| !(event.getPlayer().getHeldItemMainhand().isItemEnchanted()))
			return;
		for(Enchantment ench : EnchantmentHelper.getEnchantments(event.getPlayer().getHeldItemMainhand()).keySet()) {
			if(ench instanceof CustomEnchantment) {
				((CustomEnchantment) ench).onBlockBreak(event);
			}
		}
	}

	@SubscribeEvent
	public void onLivingXPDrop(LivingExperienceDropEvent event) {
		if(event.getAttackingPlayer() == null || event.getAttackingPlayer().getHeldItemMainhand() == null
				|| !(event.getAttackingPlayer().getHeldItemMainhand().isItemEnchanted()))
			return;
		for(Enchantment ench : EnchantmentHelper.getEnchantments(event.getAttackingPlayer().getHeldItemMainhand())
				.keySet()) {
			if(ench instanceof CustomEnchantment) {
				((CustomEnchantment) ench).onLivingXPDrop(event);
			}
		}
	}

	@SubscribeEvent(priority = EventPriority.HIGH)
	public void onBlockHarvest(BlockEvent.HarvestDropsEvent event) {
		if(event.getHarvester() == null || event.getHarvester().getHeldItemMainhand() == null
				|| !(event.getHarvester().getHeldItemMainhand().isItemEnchanted()))
			return;

		for(Enchantment ench : EnchantmentHelper.getEnchantments(event.getHarvester().getHeldItemMainhand()).keySet()) {
			if(ench instanceof CustomEnchantment) {
				((CustomEnchantment) ench).onBlockHarvest(event);
			}
		}
	}

	@SubscribeEvent
	public void onEntityAttacked(LivingAttackEvent event) {
		if(event.getAmount() == 0)
			return;
		if(event.getSource() instanceof EntityDamageSource) {
			if(event.getSource().getTrueSource() == null
					|| !(event.getSource().getTrueSource() instanceof EntityLivingBase))
				return;
			EntityLivingBase ent = (EntityLivingBase) event.getSource().getTrueSource();
			if(ent.getHeldItemMainhand() == null || !(ent.getHeldItemMainhand().isItemEnchanted()))
				return;
			for(Enchantment ench : EnchantmentHelper.getEnchantments(ent.getHeldItemMainhand()).keySet()) {
				if(ench instanceof CustomEnchantment) {
					((CustomEnchantment) ench).onEntityAttacked(event);
				}
			}
		}
	}

	// Special handling for soulbound
	@SubscribeEvent(priority = EventPriority.HIGH)
	public void onPlayerDrops(PlayerDropsEvent event) {
		Iterator<EntityItem> drops = event.getDrops().iterator();
		while(drops.hasNext()) {
			ItemStack current = drops.next().getItem();
			if(EnchantmentHelper.getEnchantmentLevel(EnchantmentsModule.soulbound, current) != 0) {
				event.getEntityPlayer().inventory.addItemStackToInventory(current);
				drops.remove();
			}
		}
	}

	@SubscribeEvent
	public void onPlayerRespawn(PlayerEvent.Clone event) {
		if(event.isWasDeath())
			event.getEntityPlayer().inventory.copyInventory(event.getOriginal().inventory);
	}
}
