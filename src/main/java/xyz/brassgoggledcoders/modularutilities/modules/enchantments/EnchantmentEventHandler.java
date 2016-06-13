package xyz.brassgoggledcoders.modularutilities.modules.enchantments;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import xyz.brassgoggledcoders.boilerplate.utils.ItemStackUtils;

public class EnchantmentEventHandler {
	
	@SubscribeEvent
	public void onBlockBreak(BlockEvent.BreakEvent event)
	{
		EntityPlayer player = event.getPlayer();
		
		int affAmount = EnchantmentHelper.getEnchantmentLevel(EnchantmentsModule.affluency, player.inventory.getCurrentItem());

		if (event.getExpToDrop() > 0)
		{
			int XP = event.getExpToDrop();
			int affXP = XP + ((affAmount * affAmount) / 2);

			event.setExpToDrop(affXP);
		}
	}
	
	@SubscribeEvent(priority = EventPriority.HIGH)
	public void onBlockHarvest(BlockEvent.HarvestDropsEvent event)
	{ 
		if(event.getHarvester() != null && event.getHarvester() instanceof EntityPlayer)
		{
			ItemStack current = event.getHarvester().inventory.getCurrentItem();
			if((EnchantmentHelper.getEnchantmentLevel(EnchantmentsModule.flame_touch, current) > 0))
			{
				//MUST iterate over a copy to avoid ConcurrentModificationException if another mod attempts to iterate over event.getDrops()
				for(ItemStack stack : new ArrayList<ItemStack>(event.getDrops()))
				{
					if(ItemStackUtils.isSmeltable(stack))
					{
						//Only handle drops on server
						if(!event.getWorld().isRemote)
						{
							//We can, however, add and remove from the original array without Exceptions.
							event.getDrops().remove(stack);
							event.getDrops().add(FurnaceRecipes.instance().getSmeltingResult(stack).copy()); //TODO Fortune
							//TODO
							//for (int i = 0; i < 10; ++i)
							//{
							//	event.getWorld().spawnParticle(EnumParticleTypes.FLAME, event.getPos().getX() + event.getWorld().rand.nextDouble(), event.getPos().getY() + event.getWorld().rand.nextDouble(), event.getWorld().rand.nextDouble(), 0, -event.getWorld().rand.nextDouble(), 0, new int[0]);
							//}
							return;
						}
					}
				}
			}
			else if(EnchantmentHelper.getEnchantmentLevel(EnchantmentsModule.prospector, current) > 0)
			{
				int prosAmount = EnchantmentHelper.getEnchantmentLevel(EnchantmentsModule.prospector, current);
				Material m = event.getState().getMaterial();
				if (m == Material.GROUND || m == Material.ROCK)
				{
					Random rand = new Random();
					if (rand.nextInt(10 - prosAmount) == 0 && !event.getWorld().isRemote)
					{
						event.getDrops().add(new ItemStack(Items.GOLD_NUGGET, 1 + rand.nextInt(3 + prosAmount))); //TODO Custom loot table
					}
				}
			}
		}
	}
}
