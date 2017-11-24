package xyz.brassgoggledcoders.modularutilities.modules.ender;

import com.teamacronymcoders.base.items.ItemBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public class ItemEnderPocket extends ItemBase {

    public ItemEnderPocket() {
        super("ender_pocket");
    }

    @Override
    @Nonnull
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, @Nonnull EnumHand hand) {
        playerIn.displayGUIChest(playerIn.getInventoryEnderChest());
        return new ActionResult<>(EnumActionResult.SUCCESS, playerIn.getHeldItem(hand));
    }
}
