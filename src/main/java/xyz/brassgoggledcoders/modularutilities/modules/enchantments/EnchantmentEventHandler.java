package xyz.brassgoggledcoders.modularutilities.modules.enchantments;

import java.util.ArrayList;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.FMLLog;
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
	{;
		if(event.getHarvester() != null && event.getHarvester() instanceof EntityPlayer)
		{
			if((EnchantmentHelper.getEnchantmentLevel(EnchantmentsModule.flame_touch, event.getHarvester().inventory.getCurrentItem()) > 0))
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
						}
						else {
							//TODO Fancy flame particles 
							return; 
						}
					}
				}
			}
		}
	}
}
