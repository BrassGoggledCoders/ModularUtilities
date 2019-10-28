package xyz.brassgoggledcoders.modularutilities.modules.baubles;

import com.teamacronymcoders.base.items.ItemBase;

import baubles.api.BaubleType;
import baubles.api.IBauble;
import baubles.api.cap.BaublesCapabilities;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.common.Optional.Interface;
import net.minecraftforge.fml.common.Optional.Method;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;

@Interface(iface = "baubles.api.IBauble", modid = "baubles")
public class ItemFireCharm extends ItemBase implements IBauble {

    @ObjectHolder("minecraft:block.furnace.fire_crackle")
    private static SoundEvent FIRE_CRACKLE;

    public ItemFireCharm() {
        super("fire_charm");
    }

    @Override
    @Method(modid = "Baubles")
    public BaubleType getBaubleType(ItemStack bauble) {
        return BaubleType.AMULET;
    }

    @Override
    @Method(modid = "Baubles")
    public void onWornTick(ItemStack stack, EntityLivingBase living) {
        if(living instanceof EntityPlayerMP) {
            EntityPlayerMP player = (EntityPlayerMP) living;
            if(player.isBurning() && !(player.isInLava()) && !(player.isImmuneToFire())) {
                player.extinguish();
                if(stack.attemptDamageItem(1, player.getRNG(), player)) {
                    if(player.hasCapability(BaublesCapabilities.CAPABILITY_BAUBLES, null)) {
                        player.getCapability(BaublesCapabilities.CAPABILITY_BAUBLES, null).setStackInSlot(0,
                                ItemStack.EMPTY);
                    }
                    player.setFire(5);
                    player.getEntityWorld().playSound(player, player.getPosition(), FIRE_CRACKLE, SoundCategory.AMBIENT,
                            1F, 1F);
                }
            }
        }
    }
}
