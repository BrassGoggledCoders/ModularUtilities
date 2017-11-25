package xyz.brassgoggledcoders.modularutilities.modules.destruction;

import com.teamacronymcoders.base.IBaseMod;
import com.teamacronymcoders.base.blocks.IHasItemBlock;
import com.teamacronymcoders.base.client.models.IHasModel;
import com.teamacronymcoders.base.items.itemblocks.ItemBlockModel;
import net.minecraft.block.BlockTNT;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.List;

public class BlockCustomTNT extends BlockTNT implements IHasItemBlock, IHasModel {
    private ItemBlock itemBlock;
    private IBaseMod mod;

    public BlockCustomTNT(String name) {
        super();
        this.setUnlocalizedName(name);
    }

    @Override
    public void onBlockDestroyedByExplosion(World worldIn, @Nonnull BlockPos pos, @Nonnull Explosion explosionIn) {
        if (!worldIn.isRemote) {
            EntityCustomTNTPrimed entitytntprimed =
                    new EntityCustomTNTPrimed(worldIn, pos.getX() + 0.5F, pos.getY(), pos.getZ() + 0.5F, null);
            worldIn.spawnEntity(entitytntprimed);
        }
    }

    @Override
    public void explode(World worldIn, @Nonnull BlockPos pos, @Nonnull IBlockState state, @Nonnull EntityLivingBase igniter) {
        if (!worldIn.isRemote)
            if (state.getValue(EXPLODE)) {
                EntityCustomTNTPrimed entitytntprimed =
                        new EntityCustomTNTPrimed(worldIn, pos.getX() + 0.5F, pos.getY(), pos.getZ() + 0.5F, igniter);
                worldIn.spawnEntity(entitytntprimed);
                worldIn.playSound(null, entitytntprimed.posX, entitytntprimed.posY, entitytntprimed.posZ,
                        SoundEvents.ENTITY_TNT_PRIMED, SoundCategory.BLOCKS, 1.0F, 1.0F);
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
        this.updateState(worldIn, pos, state);
    }

    @Override
    public void onNeighborChange(IBlockAccess world, BlockPos pos, BlockPos neighborPos) {
        this.updateState(world, pos, world.getBlockState(neighborPos));
    }

    protected void updateState(IBlockAccess world, BlockPos pos, IBlockState state) {

    }

    @Override
    public ItemBlock getItemBlock() {
        if (this.itemBlock == null) {
            this.itemBlock = new ItemBlockModel<>(this);
        }
        return this.itemBlock;
    }

    @Override
    public List<ItemStack> getAllSubItems(List<ItemStack> itemStacks) {
        itemStacks.add(new ItemStack(this));
        return itemStacks;
    }

    @Override
    public Item getItem() {
        return this.getItemBlock();
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
