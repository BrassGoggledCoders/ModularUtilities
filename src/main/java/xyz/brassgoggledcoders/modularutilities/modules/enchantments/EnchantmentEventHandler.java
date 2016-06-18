package xyz.brassgoggledcoders.modularutilities.modules.enchantments;

import java.util.Iterator;
import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.util.EnumParticleTypes;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingExperienceDropEvent;
import net.minecraftforge.event.entity.player.PlayerDropsEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import xyz.brassgoggledcoders.boilerplate.utils.ItemStackUtils;
import xyz.brassgoggledcoders.modularutilities.ModularUtilities;

public class EnchantmentEventHandler
{

	@SubscribeEvent
	public void onBlockBreak(BlockEvent.BreakEvent event)
	{
		EntityPlayer player = event.getPlayer();

		int affAmount =
				EnchantmentHelper.getEnchantmentLevel(EnchantmentsModule.affluency, player.getHeldItemMainhand());

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

		// Occurs when using /kill...
		if(player == null || player.getHeldItemMainhand() == null)
			return;

		int affAmount =
				EnchantmentHelper.getEnchantmentLevel(EnchantmentsModule.affluency, player.getHeldItemMainhand());

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
			ItemStack held = event.getHarvester().getHeldItemMainhand();
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
						drops.remove();
						event.getDrops().add(FurnaceRecipes.instance().getSmeltingResult(current));
						ModularUtilities.proxy.spawnFX(EnumParticleTypes.FLAME, event.getPos());
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

	@SubscribeEvent
	public void onEntityAttacked(LivingAttackEvent event)
	{
		if(event.getAmount() == 0)
			return;
		if(event.getSource().getDamageType() == "player")
		{
			EntityPlayer player = (EntityPlayer) event.getSource().getSourceOfDamage();
			ItemStack held = player.getHeldItemMainhand();
			int vampAmount = EnchantmentHelper.getEnchantmentLevel(EnchantmentsModule.vampirism, held);
			if(vampAmount == 1)
			{
				if(event.getEntityLiving() instanceof EntityMob)
					player.heal(event.getAmount() * 0.25F);
			}
			else if(vampAmount == 2)
			{
				player.heal(event.getAmount() * 0.25F);
			}
		}
	}

	@SubscribeEvent
	public void onPlayerDrops(PlayerDropsEvent event)
	{
		Iterator<EntityItem> drops = event.getDrops().iterator();
		while(drops.hasNext())
		{
			ItemStack current = drops.next().getEntityItem();
			if(EnchantmentHelper.getEnchantmentLevel(EnchantmentsModule.soulbound, current) != 0)
			{
				event.getEntityPlayer().inventory.addItemStackToInventory(current);
				drops.remove();
			}
		}
	}

	@SubscribeEvent
	public void onPlayerRespawn(PlayerEvent.Clone event)
	{
		if(event.isWasDeath())
		{
			event.getEntityPlayer().inventory.copyInventory(event.getOriginal().inventory);
		}
	}
}
