package xyz.brassgoggledcoders.modularutilities.modules.enchantments;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EnchantmentEventHandler {
	//Affluency (increased XP drops) enchantment handling
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
}
