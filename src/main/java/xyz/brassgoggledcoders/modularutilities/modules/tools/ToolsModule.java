package xyz.brassgoggledcoders.modularutilities.modules.tools;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
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
	public static Item ender_glove, ender_pocket;

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

		// TODO EnderStorage compatibility.
		ender_glove = new ItemBase("ender_glove");
		getItemRegistry().registerItem(ender_glove);
		ender_pocket = new ItemEnderPocket();
		getItemRegistry().registerItem(ender_pocket);
		// TODO Ender Totem (experience) and Ender X - transfers items out of ender chest.

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
				List<EntityItem> items = new ArrayList<EntityItem>(event.getDrops());
				for(int i = 0; i < items.size(); i++)
				{
					if(player.getInventoryEnderChest().addItem(items.get(i).getEntityItem()) == null)
						event.getDrops().remove(i);
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
				List<ItemStack> items = new ArrayList<ItemStack>(event.getDrops());
				for(int i = 0; i < items.size(); i++)
				{
					if(player.getInventoryEnderChest().addItem(items.get(i)) == null)
						event.getDrops().remove(i);
				}
			}
		}
	}
}
