package xyz.brassgoggledcoders.modularutilities.modules.miscellaneous;

import java.util.Random;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraftforge.fml.common.IWorldGenerator;

public class WorldGeneratorModularUtils implements IWorldGenerator {
    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world,
            net.minecraft.world.gen.IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
        if(world.provider.getDimensionType() == DimensionType.NETHER) {
            new WorldGenMagmagold().generate(world, random,
                    new BlockPos(chunkX + random.nextInt(16), random.nextInt(128), chunkZ + random.nextInt(16)));
        }
    }
}
