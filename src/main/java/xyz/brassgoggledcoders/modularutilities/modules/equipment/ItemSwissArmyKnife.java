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
	public ActionResult<ItemStack> onItemRightClick(ItemStack stack, World worldIn, EntityPlayer player,
			EnumHand hand) {
		if(Items.IRON_HOE.onItemRightClick(stack, worldIn, player,
				hand) == new ActionResult<ItemStack>(EnumActionResult.SUCCESS, stack)) {
			ModuleEquipment.convertToTool(stack, Items.IRON_HOE, player);
			return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, stack);
		}
		return new ActionResult<ItemStack>(EnumActionResult.PASS, stack);
	}
}
