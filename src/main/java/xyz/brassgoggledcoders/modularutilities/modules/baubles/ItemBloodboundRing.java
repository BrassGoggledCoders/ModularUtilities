package xyz.brassgoggledcoders.modularutilities.modules.baubles;

import baubles.api.BaubleType;
import baubles.api.IBauble;
import com.teamacronymcoders.base.items.ItemBase;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.fml.common.Optional.Interface;
import net.minecraftforge.fml.common.Optional.Method;

import java.util.Optional;

@Interface(iface = "baubles.api.IBauble", modid = "baubles")
public class ItemBloodboundRing extends ItemBase implements IBauble {

    public ItemBloodboundRing() {
        super("bloodbound_ring");
        this.setMaxStackSize(1);
    }

    @Override
    @Method(modid = "Baubles")
    public BaubleType getBaubleType(ItemStack arg0) {
        return BaubleType.RING;
    }

    @Override
    @Method(modid = "Baubles")
    public boolean canUnequip(ItemStack arg0, EntityLivingBase arg1) {
        return false;
    }

    @Override
    @Method(modid = "Baubles")
    public void onWornTick(ItemStack stack, EntityLivingBase living) {
        if (living.getHealth() == living.getMaxHealth()) {
            living.setHealth(living.getMaxHealth() * 0.75F);
            Optional.ofNullable(Potion.getPotionFromResourceLocation("strength"))
                    .ifPresent(value -> living.addPotionEffect(new PotionEffect(value, 120, 2, false, false)));
        }
    }
}
