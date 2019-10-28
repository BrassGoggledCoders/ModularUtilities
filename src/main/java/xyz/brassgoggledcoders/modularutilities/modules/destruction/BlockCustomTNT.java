package xyz.brassgoggledcoders.modularutilities.modules.destruction;

import java.util.List;

import javax.annotation.Nonnull;

import com.teamacronymcoders.base.IBaseMod;
import com.teamacronymcoders.base.IModAware;
import com.teamacronymcoders.base.blocks.IHasItemBlock;
import com.teamacronymcoders.base.client.models.IHasModel;
import com.teamacronymcoders.base.items.itemblocks.ItemBlockModel;

import net.minecraft.block.BlockTNT;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.*;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.*;

public class BlockCustomTNT extends BlockTNT implements IHasItemBlock, IHasModel, IModAware {
    private ItemBlock itemBlock;
    private IBaseMod mod;

    public BlockCustomTNT(String name) {
        super();
        setTranslationKey(name);
    }

    @Override
    public void onExplosionDestroy(World worldIn, @Nonnull BlockPos pos, @Nonnull Explosion explosionIn) {
        if(!worldIn.isRemote) {
            EntityCustomTNTPrimed entitytntprimed = new EntityCustomTNTPrimed(worldIn, pos.getX() + 0.5F, pos.getY(),
                    pos.getZ() + 0.5F, null);
            worldIn.spawnEntity(entitytntprimed);
        }
    }

    @Override
    public void explode(World worldIn, @Nonnull BlockPos pos, @Nonnull IBlockState state,
            @Nonnull EntityLivingBase igniter) {
        if(!worldIn.isRemote) {
            if(state.getValue(EXPLODE)) {
                EntityCustomTNTPrimed entitytntprimed = new EntityCustomTNTPrimed(worldIn, pos.getX() + 0.5F,
                        pos.getY(), pos.getZ() + 0.5F, igniter);
                worldIn.spawnEntity(entitytntprimed);
                worldIn.playSound(null, entitytntprimed.posX, entitytntprimed.posY, entitytntprimed.posZ,
                        SoundEvents.ENTITY_TNT_PRIMED, SoundCategory.BLOCKS, 1.0F, 1.0F);
            }
        }
    }

    @Override
    public void breakBlock(@Nonnull World world, @Nonnull BlockPos pos, @Nonnull IBlockState state) {
        world.updateComparatorOutputLevel(pos, this);

        super.breakBlock(world, pos, state);
    }

    @Override
    public void onBlockAdded(World worldIn, @Nonnull BlockPos pos, @Nonnull IBlockState state) {
        super.onBlockAdded(worldIn, pos, state);
        updateState(worldIn, pos, state);
    }

    @Override
    public void onNeighborChange(IBlockAccess world, BlockPos pos, BlockPos neighborPos) {
        updateState(world, pos, world.getBlockState(neighborPos));
    }

    protected void updateState(IBlockAccess world, BlockPos pos, IBlockState state) {

    }

    @Override
    public ItemBlock getItemBlock() {
        if(itemBlock == null) {
            itemBlock = new ItemBlockModel<>(this);
        }
        return itemBlock;
    }

    @Override
    public List<ItemStack> getAllSubItems(List<ItemStack> itemStacks) {
        itemStacks.add(new ItemStack(this));
        return itemStacks;
    }

    @Override
    public Item getItem() {
        return getItemBlock();
    }

    @Override
    public IBaseMod getMod() {
        return mod;
    }

    @Override
    public void setMod(IBaseMod mod) {
        this.mod = mod;
    }
}
