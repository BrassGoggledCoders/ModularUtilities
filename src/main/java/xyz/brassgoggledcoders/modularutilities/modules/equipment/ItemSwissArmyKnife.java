package xyz.brassgoggledcoders.modularutilities.modules.equipment;

import com.teamacronymcoders.base.items.ItemBase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

/*
 * @formatter:off
 * ||##############################|
 * ||##############################|
 * ||#############    #############|
 * ||#############    #############|
 * ||#########            #########|
 * ||#########            #########|
 * ||#############    #############|
 * ||#############    #############|
 * ||##############################|
 * ||##############################|
 * ||~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * @formatter:on
 */
public class ItemSwissArmyKnife extends ItemBase {
    public ItemSwissArmyKnife() {
        super("swiss_army_knife");
        this.setMaxStackSize(1);
        this.setMaxDamage(Item.ToolMaterial.IRON.getMaxUses());
    }

    // Sword & Hoe handling. The rest is in ModuleEquipment.class

    @Override
    public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity) {
        ModuleEquipment.convertToTool(stack, Items.IRON_SWORD, player);
        return false;
    }

    @Override
    @Nonnull
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer player, @Nonnull EnumHand hand) {
        ItemStack stack = player.getHeldItem(hand);
        if (Items.IRON_HOE.onItemRightClick(worldIn, player, hand) == new ActionResult<>(EnumActionResult.SUCCESS, stack)) {
            ModuleEquipment.convertToTool(stack, Items.IRON_HOE, player);
            return new ActionResult<>(EnumActionResult.SUCCESS, stack);
        }
        return new ActionResult<>(EnumActionResult.PASS, stack);
    }
}
