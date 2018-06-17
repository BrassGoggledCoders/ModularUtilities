package xyz.brassgoggledcoders.modularutilities.proxies;

import com.teamacronymcoders.base.client.ClientHelper;

import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ClientProxy extends CommonProxy {
    @Override
    public void spawnFX(EnumParticleTypes type, BlockPos pos) {
        World world = ClientHelper.world();
        if (type == EnumParticleTypes.PORTAL)
            for (int j = 0; j < 70; ++j)
                world.spawnParticle(type, pos.getX() + (-0.2 + world.rand.nextDouble()), pos.getY(),
                        pos.getZ() + (-0.2 + world.rand.nextDouble()), 0, 0, 0);
        else if (type == EnumParticleTypes.FLAME) {
            for (int j = 0; j < 5; ++j)
                world.spawnParticle(EnumParticleTypes.LAVA, pos.getX() + (-0.2 + world.rand.nextDouble()), pos.getY(),
                        pos.getZ() + (-0.2 + world.rand.nextDouble()), world.rand.nextGaussian(),
                        world.rand.nextGaussian(), world.rand.nextGaussian());
            for (int j = 0; j < 5; ++j)
                world.spawnParticle(EnumParticleTypes.LAVA, pos.getX() + (-0.2 + world.rand.nextDouble()), pos.getY(),
                        pos.getZ() + (-0.2 + world.rand.nextDouble()), -world.rand.nextGaussian(),
                        -world.rand.nextGaussian(), -world.rand.nextGaussian());
        }
    }
}
