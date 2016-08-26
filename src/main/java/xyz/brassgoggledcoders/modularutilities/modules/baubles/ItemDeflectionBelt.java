package xyz.brassgoggledcoders.modularutilities.modules.baubles;

import java.util.List;

import baubles.api.BaubleType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IProjectile;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Optional;
import xyz.brassgoggledcoders.boilerplate.items.ItemBaubleBase;

public class ItemDeflectionBelt extends ItemBaubleBase {

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
		World world = living.worldObj;
		List<Entity> entities = world.getEntitiesWithinAABBExcludingEntity(living,
				living.getEntityBoundingBox().expand(5.0D, 5.0D, 5.0D));
		for(int i = 0; i < entities.size(); i++) {
			Entity entity = entities.get(i);
			if(entity instanceof IProjectile) {
				entity.setVelocity(-entity.motionX, entity.motionY, -entity.motionZ);
			}
		}
	}
}
