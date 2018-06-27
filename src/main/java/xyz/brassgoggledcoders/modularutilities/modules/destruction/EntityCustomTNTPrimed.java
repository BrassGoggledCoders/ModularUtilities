package xyz.brassgoggledcoders.modularutilities.modules.destruction;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;

public class EntityCustomTNTPrimed extends EntityTNTPrimed {

	public EntityCustomTNTPrimed(World world) {
		super(world);
	}

	public EntityCustomTNTPrimed(World worldIn, double x, double y, double z, EntityLivingBase igniter) {
		super(worldIn, x, y, z, igniter);
	}

	@Override
	public void onUpdate() {
		prevPosX = posX;
		prevPosY = posY;
		prevPosZ = posZ;
		motionY -= 0.03999999910593033D;
		move(MoverType.SELF, motionX, motionY, motionZ);
		motionX *= 0.9800000190734863D;
		motionY *= 0.9800000190734863D;
		motionZ *= 0.9800000190734863D;

		if(onGround) {
			motionX *= 0.699999988079071D;
			motionZ *= 0.699999988079071D;
			motionY *= -0.5D;
		}

		setFuse(getFuse() - 1);

		if(getFuse() <= 0) {
			setDead();

			if(!getEntityWorld().isRemote) {
				doExplode();
			}
		}
		else {
			handleWaterMovement();
			getEntityWorld().spawnParticle(EnumParticleTypes.SMOKE_NORMAL, posX, posY + 0.5D, posZ, 0.0D, 0.0D, 0.0D);
		}
	}

	public void doExplode() {
		float f = 4.0F;
		getEntityWorld().createExplosion(this, posX, posY + height / 16.0F, posZ, f, true);
	}
}
