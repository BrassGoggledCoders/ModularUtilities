package xyz.brassgoggledcoders.modularutilities.modules.tools;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.world.BlockEvent.HarvestDropsEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import xyz.brassgoggledcoders.boilerplate.items.ItemBase;
import xyz.brassgoggledcoders.boilerplate.module.Module;
import xyz.brassgoggledcoders.boilerplate.module.ModuleBase;
import xyz.brassgoggledcoders.boilerplate.utils.ItemStackUtils;
import xyz.brassgoggledcoders.modularutilities.ModularUtilities;

@Module(mod = ModularUtilities.MODID)
public class ToolsModule extends ModuleBase
{

	public static ItemMachete machete;
	public static Item ender_glove;

	@Override
	public String getName()
	{
		return "Tools";
	}

	@Override
	public void preInit(FMLPreInitializationEvent event)
	{
		machete = new ItemMachete();
		this.getItemRegistry().registerItem(machete);

		ender_glove = new ItemBase("ender_glove");
		getItemRegistry().registerItem(ender_glove);
		/*
		 * TODO:
		 * - Ender Pocket
		 */
		MinecraftForge.EVENT_BUS.register(this);
	}

	@SubscribeEvent
	public void onLivingDrops(LivingDropsEvent event)
	{
		if(event.getSource().getDamageType() == "player")
		{
			EntityPlayer player = (EntityPlayer) event.getSource().getEntity();
			if(ItemStackUtils.doItemsMatch(player.inventory.offHandInventory[0], ender_glove))
			{
				for(int i = 0; i < event.getDrops().size(); i++)
				{
					if(player.getInventoryEnderChest().addItem(event.getDrops().get(i).getEntityItem()) == null)
						event.getDrops().remove(0);
				}
			}
		}
	}

	@SubscribeEvent
	public void onBlockDrops(HarvestDropsEvent event)
	{
		if(event.getHarvester() != null)
		{
			EntityPlayer player = event.getHarvester();
			if(ItemStackUtils.doItemsMatch(player.inventory.offHandInventory[0], ender_glove))
			{
				for(int i = 0; i < event.getDrops().size(); i++)
				{
					if(player.getInventoryEnderChest().addItem(event.getDrops().get(i)) == null)
						event.getDrops().remove(0);
				}
			}
		}
	}
}
