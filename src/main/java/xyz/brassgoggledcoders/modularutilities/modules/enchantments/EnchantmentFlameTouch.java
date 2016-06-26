package xyz.brassgoggledcoders.modularutilities.modules.enchantments;

import java.util.ArrayList;
import java.util.Iterator;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.util.EnumParticleTypes;
import net.minecraftforge.event.world.BlockEvent.HarvestDropsEvent;
import xyz.brassgoggledcoders.boilerplate.utils.ItemStackUtils;
import xyz.brassgoggledcoders.modularutilities.ModularUtilities;

public class EnchantmentFlameTouch extends CustomEnchantment {
	public EnchantmentFlameTouch(Rarity rarityIn, EnumEnchantmentType typeIn, EntityEquipmentSlot[] slots,
			int multiplier, int minEnchant, int maxLevel) {
		super(rarityIn, typeIn, slots, multiplier, minEnchant, maxLevel);
	}

	@Override
	public void onBlockHarvest(HarvestDropsEvent event) {
		ItemStack held = event.getHarvester().getHeldItemMainhand();
		if(EnchantmentHelper.getEnchantmentLevel(EnchantmentsModule.flame_touch, held) > 0) {
			Iterator<ItemStack> drops = event.getDrops().iterator();
			ArrayList<ItemStack> toRemove = new ArrayList<ItemStack>();
			ArrayList<ItemStack> smelted = new ArrayList<ItemStack>();
			while(drops.hasNext()) {
				ItemStack current = drops.next();
				if(ItemStackUtils.isSmeltable(current)) {
					if(!event.getWorld().isRemote) {
						smelted.add(FurnaceRecipes.instance().getSmeltingResult(current).copy());
						toRemove.add(current);
					}
					ModularUtilities.proxy.spawnFX(EnumParticleTypes.FLAME, event.getPos());
				}
			}
			event.getDrops().removeAll(toRemove);
			event.getDrops().addAll(smelted);

		}
	}
}
