package xyz.brassgoggledcoders.modularutilities.modules.equipment;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import xyz.brassgoggledcoders.boilerplate.module.Module;
import xyz.brassgoggledcoders.boilerplate.module.ModuleBase;
import xyz.brassgoggledcoders.boilerplate.utils.ItemStackUtils;
import xyz.brassgoggledcoders.modularutilities.ModularUtilities;

@Module(mod = ModularUtilities.MODID)
public class ModuleEquipment extends ModuleBase {

	public static Item swiss_army_knife;
	public static Item machete;

	@Override
	public void preInit(FMLPreInitializationEvent event) {
		machete = new ItemMachete();
		this.getItemRegistry().registerItem(machete);

		// TODO Should be enchantable
		swiss_army_knife = new ItemSwissArmyKnife();
		this.getItemRegistry().registerItem(swiss_army_knife);

		MinecraftForge.EVENT_BUS.register(this);
	}

	@Override
	public void postInit(FMLPostInitializationEvent event) {
		GameRegistry.addRecipe(new ItemStack(machete),
				new Object[] {"SI", 'S', Items.IRON_SWORD, 'I', Items.IRON_INGOT});
	}

	@Override
	public String getName() {
		return "Equipment";
	}

	// TODO Must be a neater way to do this.
	@SubscribeEvent
	public void onItemInteract(PlayerInteractEvent.RightClickItem event) {
		if(event.getEntityPlayer().isSneaking()) {
			if(event.getItemStack().hasTagCompound()) {
				if(ItemStackUtils.doItemsMatch(event.getItemStack(), Items.IRON_AXE)
						|| ItemStackUtils.doItemsMatch(event.getItemStack(), Items.IRON_HOE)
						|| ItemStackUtils.doItemsMatch(event.getItemStack(), Items.IRON_PICKAXE)
						|| ItemStackUtils.doItemsMatch(event.getItemStack(), Items.IRON_SHOVEL)
						|| ItemStackUtils.doItemsMatch(event.getItemStack(), Items.IRON_SWORD)) {
					if(event.getItemStack().getTagCompound().getBoolean("isSwiss")) {
						ItemStack knife = new ItemStack(swiss_army_knife);
						knife.damageItem(event.getItemStack().getItemDamage(), event.getEntityPlayer());
						// TODO
						// NBTTagList list = event.getItemStack().getEnchantmentTagList();
						// knife.setTagInfo("ench", list);
						event.getEntityPlayer().inventory
								.setInventorySlotContents(event.getEntityPlayer().inventory.currentItem, knife);
					}
				}
			}
		}
	}
}
