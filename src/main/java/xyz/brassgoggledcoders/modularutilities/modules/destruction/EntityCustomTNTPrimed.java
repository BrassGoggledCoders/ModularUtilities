package xyz.brassgoggledcoders.modularutilities.modules.destruction;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public class EntityCustomTNTPrimed extends EntityTNTPrimed {

	public EntityCustomTNTPrimed(World world) {
		super(world);
	}

	public EntityCustomTNTPrimed(World worldIn, double x, double y, double z, EntityLivingBase igniter) {
		super(worldIn, x, y, z, igniter);
	}

	@Override
	public void onUpdate() {
		this.prevPosX = this.posX;
		this.prevPosY = this.posY;
		this.prevPosZ = this.posZ;
		this.motionY -= 0.03999999910593033D;
		this.move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
		this.motionX *= 0.9800000190734863D;
		this.motionY *= 0.9800000190734863D;
		this.motionZ *= 0.9800000190734863D;

		if(this.onGround) {
			this.motionX *= 0.699999988079071D;
			this.motionZ *= 0.699999988079071D;
			this.motionY *= -0.5D;
		}

		this.setFuse(this.getFuse() - 1);

		if(this.getFuse() <= 0) {
			this.setDead();

			if(!this.getEntityWorld().isRemote)
				this.doExplode();
		}
		else {
			this.handleWaterMovement();
			this.getEntityWorld().spawnParticle(EnumParticleTypes.SMOKE_NORMAL, this.posX, this.posY + 0.5D, this.posZ, 0.0D,
					0.0D, 0.0D);
		}
	}

	public void doExplode() {
		float f = 4.0F;
		this.getEntityWorld().createExplosion(this, this.posX, this.posY + this.height / 16.0F, this.posZ, f, true);
	}
}
