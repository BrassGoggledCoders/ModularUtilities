package xyz.brassgoggledcoders.modularutilities.modules.enchantments;

import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.world.BlockEvent.HarvestDropsEvent;

public class EnchantmentProspector extends CustomEnchantment {

    public EnchantmentProspector(Rarity rarityIn, EnumEnchantmentType typeIn, EntityEquipmentSlot[] slots,
            int multiplier, int minEnchant, int maxLevel) {
        super(rarityIn, typeIn, slots, multiplier, minEnchant, maxLevel);
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
        return !stack.getItem().getToolClasses(stack).contains("axe") && super.canApplyAtEnchantingTable(stack);
    }

    @Override
    public void onBlockHarvest(HarvestDropsEvent event) {
        ItemStack held = event.getHarvester().getActiveItemStack();
        if(EnchantmentHelper.getEnchantmentLevel(EnchantmentsModule.prospector, held) > 0) {
            int prosAmount = EnchantmentHelper.getEnchantmentLevel(EnchantmentsModule.prospector, held);
            Material m = event.getState().getMaterial();
            if(m == Material.GROUND || m == Material.ROCK) {
                Random rand = new Random();
                if(rand.nextInt(10 - prosAmount) == 0 && !event.getWorld().isRemote) {
                    event.getDrops().add(new ItemStack(Items.GOLD_NUGGET, 1 + rand.nextInt(3 + prosAmount)));
                    // TODO Custom loot table
                }
            }
        }
    }
}
