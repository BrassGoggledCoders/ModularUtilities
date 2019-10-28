package xyz.brassgoggledcoders.modularutilities.modules.enchantments;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingAttackEvent;

public class EnchantmentVampirism extends CustomEnchantment {

    public EnchantmentVampirism(Rarity rarityIn, EnumEnchantmentType typeIn, EntityEquipmentSlot[] slots,
            int multiplier, int minEnchant, int maxLevel) {
        super(rarityIn, typeIn, slots, multiplier, minEnchant, maxLevel);
    }

    @Override
    public void onEntityAttacked(LivingAttackEvent event) {

        if(event.getSource().getTrueSource() instanceof EntityLivingBase) {
            EntityLivingBase ent = (EntityLivingBase) event.getSource().getTrueSource();
            ItemStack held = ent.getActiveItemStack();
            int vampAmount = EnchantmentHelper.getEnchantmentLevel(EnchantmentsModule.vampirism, held);
            if(vampAmount == 1) {
                if(event.getEntityLiving() instanceof EntityMob) {
                    ent.heal(event.getAmount() * 0.25F);
                }
            }
            else if(vampAmount == 2) {
                ent.heal(event.getAmount() * 0.25F);
            }
        }
    }
}
