package xyz.brassgoggledcoders.modularutilities.modules.enchantments;

import java.util.Iterator;
import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraftforge.event.entity.living.LivingExperienceDropEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import xyz.brassgoggledcoders.boilerplate.utils.ItemStackUtils;

public class EnchantmentEventHandler
{

	@SubscribeEvent
	public void onBlockBreak(BlockEvent.BreakEvent event)
	{
		EntityPlayer player = event.getPlayer();

		int affAmount =
				EnchantmentHelper.getEnchantmentLevel(EnchantmentsModule.affluency, player.inventory.getCurrentItem());

		if(event.getExpToDrop() > 0)
		{
			int XP = event.getExpToDrop();
			int affXP = XP + affAmount * affAmount / 2;

			event.setExpToDrop(affXP);
		}
	}

	@SubscribeEvent
	public void onLivingXPDrop(LivingExperienceDropEvent event)
	{
		EntityPlayer player = event.getAttackingPlayer();

		int affAmount =
				EnchantmentHelper.getEnchantmentLevel(EnchantmentsModule.affluency, player.inventory.getCurrentItem());

		if(event.getDroppedExperience() > 0)
		{
			int XP = event.getDroppedExperience();
			int affXP = XP + affAmount * affAmount / 2;

			event.setDroppedExperience(affXP);
		}
	}

	@SubscribeEvent(priority = EventPriority.HIGH)
	public void onBlockHarvest(BlockEvent.HarvestDropsEvent event)
	{
		if(event.getHarvester() != null && event.getHarvester() instanceof EntityPlayer)
		{
			ItemStack held = event.getHarvester().inventory.getCurrentItem();
			if(EnchantmentHelper.getEnchantmentLevel(EnchantmentsModule.flame_touch, held) > 0)
			{
				if(event.getWorld().isRemote)
					return;

				Iterator<ItemStack> drops = event.getDrops().iterator();
				while(drops.hasNext())
				{
					ItemStack current = drops.next();
					if(ItemStackUtils.isSmeltable(current))
					{
						event.getDrops().remove(current);
						event.getDrops().add(FurnaceRecipes.instance().getSmeltingResult(current));
					}
				}
			}
			else if(EnchantmentHelper.getEnchantmentLevel(EnchantmentsModule.prospector, held) > 0)
			{
				int prosAmount = EnchantmentHelper.getEnchantmentLevel(EnchantmentsModule.prospector, held);
				Material m = event.getState().getMaterial();
				if(m == Material.GROUND || m == Material.ROCK)
				{
					Random rand = new Random();
					if(rand.nextInt(10 - prosAmount) == 0 && !event.getWorld().isRemote)
						event.getDrops().add(new ItemStack(Items.GOLD_NUGGET, 1 + rand.nextInt(3 + prosAmount)));
					// TODO Custom loot table
				}
			}
		}
	}
}
