package xyz.brassgoggledcoders.modularutilities.modules.baubles;

import java.util.List;

import baubles.api.BaubleType;
import baubles.api.IBauble;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IProjectile;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemDeflectionBelt extends ItemBaubleBase implements IBauble {

	public ItemDeflectionBelt() {
		super("deflection_belt");
		this.setMaxStackSize(1);
	}

	@Override
	public BaubleType getBaubleType(ItemStack arg0) {
		return BaubleType.BELT;
	}

	@Override
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
