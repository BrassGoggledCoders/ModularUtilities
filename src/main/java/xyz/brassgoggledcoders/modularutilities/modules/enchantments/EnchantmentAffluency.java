package xyz.brassgoggledcoders.modularutilities.modules.enchantments;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraftforge.event.entity.living.LivingExperienceDropEvent;
import net.minecraftforge.event.world.BlockEvent;

public class EnchantmentAffluency extends CustomEnchantment {

    public EnchantmentAffluency(Rarity rarityIn, EnumEnchantmentType typeIn, EntityEquipmentSlot[] slots,
                                int multiplier, int minEnchant, int maxLevel) {
        super(rarityIn, typeIn, slots, multiplier, minEnchant, maxLevel);
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
        if (stack.getItem() instanceof ItemSword)
            return true;
        return this.type.canEnchantItem(stack.getItem());
    }

    @Override
    public void onBlockBreak(BlockEvent.BreakEvent event) {
        int affAmount = EnchantmentHelper.getEnchantmentLevel(EnchantmentsModule.affluency,
                event.getPlayer().getHeldItemMainhand());
        if (event.getExpToDrop() > 0) {
            event.setExpToDrop(event.getExpToDrop() + affAmount * affAmount / 2);
        }
    }

    @Override
    public void onLivingXPDrop(LivingExperienceDropEvent event) {
        EntityPlayer player = event.getAttackingPlayer();

        // Occurs when using /kill...
        if (player == null || player.getHeldItemMainhand() == null)
            return;

        int affAmount =
                EnchantmentHelper.getEnchantmentLevel(EnchantmentsModule.affluency, player.getHeldItemMainhand());

        if (event.getDroppedExperience() > 0) {
            event.setDroppedExperience(event.getDroppedExperience() + affAmount * affAmount / 2);
        }
    }
}
