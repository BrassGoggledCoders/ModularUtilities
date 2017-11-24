package xyz.brassgoggledcoders.modularutilities.modules.destruction;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.List;

public class BlockSplitterTNT extends BlockCustomTNT {

    public BlockSplitterTNT(String name) {
        super(name);
    }

    @Override
    public void onBlockDestroyedByExplosion(World worldIn, BlockPos pos, Explosion explosionIn) {
        if (!worldIn.isRemote) {
            EntityTNTPrimed entitytntprimed =
                    new EntityTNTPrimed(worldIn, pos.getX() + 0.5F, pos.getY(), pos.getZ() + 0.5F, null);
            worldIn.spawnEntity(entitytntprimed);
            for (int i = 0; i < 3; i++) {
                EntityTNTPrimed entitytntprimed2 =
                        new EntityTNTPrimed(worldIn, pos.getX() + 0.5F, pos.getY(), pos.getZ() + 0.5F, null);
                entitytntprimed2.setFuse(120);
                worldIn.spawnEntity(entitytntprimed2);
            }
        }
    }

    @Override
    public void explode(World worldIn, @Nonnull BlockPos pos, @Nonnull IBlockState state, @Nonnull EntityLivingBase igniter) {
        if (!worldIn.isRemote)
            if (state.getValue(EXPLODE)) {
                EntityTNTPrimed entitytntprimed =
                        new EntityTNTPrimed(worldIn, pos.getX() + 0.5F, pos.getY(), pos.getZ() + 0.5F, igniter);
                worldIn.spawnEntity(entitytntprimed);
                for (int i = 0; i < 3; i++) {
                    EntityTNTPrimed entitytntprimed2 =
                            new EntityTNTPrimed(worldIn, pos.getX() + 0.5F, pos.getY(), pos.getZ() + 0.5F, igniter);
                    entitytntprimed2.setFuse(120);
                    worldIn.spawnEntity(entitytntprimed2);
                }
                worldIn.playSound(null, entitytntprimed.posX, entitytntprimed.posY, entitytntprimed.posZ,
                        SoundEvents.ENTITY_TNT_PRIMED, SoundCategory.BLOCKS, 1.0F, 1.0F);
            }
    }

    @Override
    public List<String> getModelNames(List<String> modelNames) {
        modelNames.add("splitter_tnt");
        return modelNames;
    }

}
