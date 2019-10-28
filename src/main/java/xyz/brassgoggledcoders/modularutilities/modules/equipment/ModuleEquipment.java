package xyz.brassgoggledcoders.modularutilities.modules.equipment;

import com.teamacronymcoders.base.modulesystem.Module;
import com.teamacronymcoders.base.modulesystem.ModuleBase;
import com.teamacronymcoders.base.util.ItemStackUtils;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import xyz.brassgoggledcoders.modularutilities.ModularUtilities;

@EventBusSubscriber
@Module(ModularUtilities.MODID)
public class ModuleEquipment extends ModuleBase {

    public static Item swiss_army_knife;
    public static Item machete;

    @Override
    public String getClientProxyPath() {
        return "xyz.brassgoggledcoders.modularutilities.modules.equipment.ClientProxy";
    }

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        machete = new ItemMachete();
        getItemRegistry().register(machete);

        // TODO Enchantment stuff. Manual cycling of tools.
        swiss_army_knife = new ItemSwissArmyKnife();
        getItemRegistry().register(swiss_army_knife);
    }

    @Override
    public String getName() {
        return "Equipment";
    }

    // Everything below this line deals with the swiss army knife. Sword & hoe
    // handling is done in the Knife's Item
    // class.

    @SubscribeEvent
    public static void onItemInteract(PlayerInteractEvent.RightClickItem event) {
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
    public static void onBlockLeftClicked(PlayerInteractEvent.LeftClickBlock event) {
        IBlockState state = event.getWorld().getBlockState(event.getPos());
        if(ItemStackUtils.doItemsMatch(event.getItemStack(), swiss_army_knife)) {
            if(Items.IRON_PICKAXE.getDestroySpeed(event.getItemStack(), state) > 1.0F) {
                convertToTool(event.getItemStack(), Items.IRON_PICKAXE, event.getEntityPlayer());
            }
            else if(Items.IRON_AXE.getDestroySpeed(event.getItemStack(), state) > 1.0F) {
                convertToTool(event.getItemStack(), Items.IRON_AXE, event.getEntityPlayer());
            }
            else if(Items.IRON_SHOVEL.getDestroySpeed(event.getItemStack(), state) > 1.0F) {
                convertToTool(event.getItemStack(), Items.IRON_SHOVEL, event.getEntityPlayer());
            }
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
