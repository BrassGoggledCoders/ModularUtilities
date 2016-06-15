package xyz.brassgoggledcoders.modularutilities.modules.ender;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.world.BlockEvent.HarvestDropsEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import xyz.brassgoggledcoders.boilerplate.items.ItemBase;
import xyz.brassgoggledcoders.boilerplate.module.Module;
import xyz.brassgoggledcoders.boilerplate.module.ModuleBase;
import xyz.brassgoggledcoders.boilerplate.utils.ItemStackUtils;
import xyz.brassgoggledcoders.modularutilities.ModularUtilities;

@Module(mod = ModularUtilities.MODID)
public class EnderModule extends ModuleBase
{
	public static Item ender_glove, ender_pocket;

	public static Block ender_proxy;

	@Override
	public String getName()
	{
		return "Ender";
	}

	@Override
	public void preInit(FMLPreInitializationEvent event)
	{
		// TODO EnderStorage compatibility.
		ender_glove = new ItemBase("ender_glove");
		getItemRegistry().registerItem(ender_glove);
		ender_pocket = new ItemEnderPocket();
		getItemRegistry().registerItem(ender_pocket);
		// TODO Ender Totem (experience) and Ender Dispenser/Dropper

		ender_proxy = new BlockEnderChestProxy();
		// getBlockRegistry().registerBlock(ender_proxy);

		MinecraftForge.EVENT_BUS.register(this);
	}

	@Override
	public void postInit(FMLPostInitializationEvent event)
	{
		GameRegistry.addRecipe(new ItemStack(ender_glove),
				new Object[] {"OOO", "OCO", "O O", 'O', Blocks.OBSIDIAN, 'C', Items.END_CRYSTAL});
		GameRegistry.addRecipe(new ItemStack(ender_pocket),
				new Object[] {"LCL", "LLL", 'L', Items.LEATHER, 'C', Blocks.ENDER_CHEST});
	}

	@SubscribeEvent
	public void onLivingDrops(LivingDropsEvent event)
	{
		if(event.getSource().getDamageType() == "player")
		{
			EntityPlayer player = (EntityPlayer) event.getSource().getEntity();
			if(ItemStackUtils.doItemsMatch(player.getHeldItemOffhand(), ender_glove))
			{
				/// TODO Test if needed
				List<EntityItem> items = new ArrayList<EntityItem>(event.getDrops());
				List<EntityItem> toRemove = new ArrayList<EntityItem>();
				for(int i = 0; i < items.size(); i++)
				{
					if(player.getInventoryEnderChest().addItem(items.get(i).getEntityItem()) == null)
						toRemove.add(items.get(i));
				}
				event.getDrops().removeAll(toRemove);
			}
		}
	}

	@SubscribeEvent
	public void onBlockDrops(HarvestDropsEvent event)
	{
		if(event.getHarvester() != null)
		{
			EntityPlayer player = event.getHarvester();
			if(ItemStackUtils.doItemsMatch(player.getHeldItemOffhand(), ender_glove))
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
