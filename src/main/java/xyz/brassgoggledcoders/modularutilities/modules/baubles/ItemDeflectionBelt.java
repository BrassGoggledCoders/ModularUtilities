package xyz.brassgoggledcoders.modularutilities.modules.baubles;

import java.util.List;

import com.teamacronymcoders.base.items.ItemBase;

import baubles.api.BaubleType;
import baubles.api.IBauble;
import net.minecraft.entity.*;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Optional;

@Optional.Interface(iface = "baubles.api.IBauble", modid = "baubles")
public class ItemDeflectionBelt extends ItemBase implements IBauble {

    public ItemDeflectionBelt() {
        super("deflection_belt");
    }

    @Override
    @Optional.Method(modid = "Baubles")
    public BaubleType getBaubleType(ItemStack arg0) {
        return BaubleType.BELT;
    }

    @Override
    @Optional.Method(modid = "Baubles")
    public void onWornTick(ItemStack stack, EntityLivingBase living) {
        World world = living.getEntityWorld();
        List<Entity> entities = world.getEntitiesWithinAABBExcludingEntity(living,
                living.getEntityBoundingBox().expand(5.0D, 5.0D, 5.0D));
        for (Entity entity : entities) {
            if (entity instanceof IProjectile) {
                entity.motionX = -entity.motionX;
                entity.motionY = -entity.motionY;
                entity.motionZ = -entity.motionZ;
            }
        }
    }
}
