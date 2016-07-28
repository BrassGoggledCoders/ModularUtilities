package xyz.brassgoggledcoders.modularutilities.modules.equipment;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapelessOreRecipe;
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

		// TODO Enchantment stuff. Manual cycling of tools.
		swiss_army_knife = new ItemSwissArmyKnife();
		this.getItemRegistry().registerItem(swiss_army_knife);

		MinecraftForge.EVENT_BUS.register(this);
	}

	@Override
	public void postInit(FMLPostInitializationEvent event) {
		GameRegistry.addRecipe(new ItemStack(machete),
				new Object[] {"SI", 'S', Items.IRON_SWORD, 'I', Items.IRON_INGOT});
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(swiss_army_knife),
				new Object[] { Items.IRON_SWORD, Items.IRON_AXE, Items.IRON_HOE, Items.IRON_PICKAXE, Items.IRON_SHOVEL, "dyeRed", "ingotIron", "ingotIron", "plankWood"}));
	}

	@Override
	public String getName() {
		return "Equipment";
	}

	// Everything below this line deals with the swiss army knife. Sword & hoe handling is done in the Knife's Item
	// class.

	@SubscribeEvent
	public void onItemInteract(PlayerInteractEvent.RightClickItem event) {
		if(event.getEntityPlayer().isSneaking() && isStackSwiss(event.getItemStack())) {

			ItemStack knife = new ItemStack(swiss_army_knife);
			knife.damageItem(event.getItemStack().getItemDamage(), event.getEntityPlayer());
			// TODO
			// NBTTagList list = event.getItemStack().getEnchantmentTagList();
			// knife.setTagInfo("ench", list);
			event.getEntityPlayer().inventory.setInventorySlotContents(event.getEntityPlayer().inventory.currentItem,
					knife);
		}
	}

	@SubscribeEvent
	public void onBlockLeftClicked(PlayerInteractEvent.LeftClickBlock event) {
		IBlockState state = event.getWorld().getBlockState(event.getPos());
		if(ItemStackUtils.doItemsMatch(event.getItemStack(), swiss_army_knife)) {
			if(Items.IRON_PICKAXE.getStrVsBlock(event.getItemStack(), state) > 1.0F) {
				convertToTool(event.getItemStack(), Items.IRON_PICKAXE, event.getEntityPlayer());
			}
			else if(Items.IRON_AXE.getStrVsBlock(event.getItemStack(), state) > 1.0F) {
				convertToTool(event.getItemStack(), Items.IRON_AXE, event.getEntityPlayer());
			}
			else if(Items.IRON_SHOVEL.getStrVsBlock(event.getItemStack(), state) > 1.0F) {
				convertToTool(event.getItemStack(), Items.IRON_SHOVEL, event.getEntityPlayer());
			}
		}
	}

	@SubscribeEvent
	public void tooltipEvent(ItemTooltipEvent event) {
		if(isStackSwiss(event.getItemStack())) {
			// TODO Lang
			event.getToolTip().add("\u00A7c" + "Shift-R Click to return to knife");
		}
	}

	public static boolean isStackSwiss(ItemStack stack) {
		if(stack.hasTagCompound()
				&& (ItemStackUtils.doItemsMatch(stack, Items.IRON_AXE)
						|| ItemStackUtils.doItemsMatch(stack, Items.IRON_HOE)
						|| ItemStackUtils.doItemsMatch(stack, Items.IRON_PICKAXE)
						|| ItemStackUtils.doItemsMatch(stack, Items.IRON_SHOVEL)
						|| ItemStackUtils.doItemsMatch(stack, Items.IRON_SWORD))
				&& stack.getTagCompound().getBoolean("isSwiss")) {
			return true;
		}

		return false;
	}

	public static void convertToTool(ItemStack knife, Item target_tool, EntityPlayer player) {
		NBTTagCompound tag = new NBTTagCompound();
		tag.setBoolean("isSwiss", true);
		ItemStack stack = new ItemStack(target_tool);
		stack.setTagCompound(tag);
		stack.damageItem(knife.getItemDamage(), player);
		player.inventory.setInventorySlotContents(player.inventory.currentItem, stack);
	}
}
