package xyz.brassgoggledcoders.modularutilities.modules.miscellaneous;

import java.util.List;
import java.util.Random;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.teamacronymcoders.base.blocks.BlockBase;

import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockMagmagold extends BlockBase implements IGrowable {
    // TODO Raise number of stages
    public static final PropertyInteger AGE = PropertyInteger.create("age", 0, 1);
    private static final AxisAlignedBB[] CROPS_AABB = new AxisAlignedBB[] {
            new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.125D, 1.0D),
            new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.25D, 1.0D),
            new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.375D, 1.0D),
            new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.5D, 1.0D),
            new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.625D, 1.0D),
            new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.75D, 1.0D),
            new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.875D, 1.0D),
            new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D) };

    public BlockMagmagold(Material mat, String name) {
        super(mat, name);
        setDefaultState(blockState.getBaseState().withProperty(getAgeProperty(), 0));
        setTickRandomly(true);
        setHardness(0.0F);
        setSoundType(SoundType.PLANT);
    }

    @Override
    public boolean canHarvestBlock(IBlockAccess world, @Nonnull BlockPos pos, @Nonnull EntityPlayer player) {
        return true;
    }

    @Override
    @Nonnull
    public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, @Nonnull IBlockState state, int fortune) {
        List<ItemStack> ret = super.getDrops(world, pos, state, fortune);
        int age = getAge(state);
        Random rand = world instanceof World ? ((World) world).rand : new Random();
        if(age >= getMaxAge()) {
            for(int i = 0; i < 2 + fortune; ++i) {
                if(rand.nextBoolean()) {
                    ret.add(new ItemStack(getDrop(), 1));
                }
            }
        }
        return ret;
    }

    @Override
    @Nullable
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, @Nonnull IBlockAccess world,
            @Nonnull BlockPos pos) {
        return NULL_AABB;
    }

    @Override
    @Nonnull
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Override
    @Nonnull
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return CROPS_AABB[state.getValue(getAgeProperty())];
    }

    protected boolean canSustainPlant(IBlockState state) {
        return state.getBlock() == Blocks.MAGMA;
    }

    protected PropertyInteger getAgeProperty() {
        return AGE;
    }

    public int getMaxAge() {
        return 1;
    }

    protected int getAge(IBlockState state) {
        return state.getValue(getAgeProperty());
    }

    public IBlockState withAge(int age) {
        return getDefaultState().withProperty(getAgeProperty(), age);
    }

    public boolean isMaxAge(IBlockState state) {
        return state.getValue(getAgeProperty()) >= getMaxAge();
    }

    @Override
    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
        super.updateTick(worldIn, pos, state, rand);
        // TODO Less magic/obscure randomisation
        if(rand.nextInt((int) (25.0F / getGrowthChance(this, worldIn, pos)) + 1) == 0) {
            grow(worldIn, pos, state);
        }
    }

    public void grow(World worldIn, BlockPos pos, IBlockState state) {
        if(!isMaxAge(state)) {
            worldIn.setBlockState(pos, withAge(getAge(state) + 1), 2);
        }
    }

    // TODO Nearby lava increases growth chance.
    protected static float getGrowthChance(Block blockIn, World worldIn, BlockPos pos) {
        return 0.5F;
    }

    public boolean canBlockStay(World worldIn, BlockPos pos, IBlockState state) {
        IBlockState soil = worldIn.getBlockState(pos.down());
        return soil.getBlock().canSustainPlant(soil, worldIn, pos.down(), net.minecraft.util.EnumFacing.UP,
                (IPlantable) MiscellaneousModule.goldpetal);
    }

    protected Item getDrop() {
        return MiscellaneousModule.goldpetal;
    }

    @Override
    @Nonnull
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return getDrop();
    }

    @Override
    public boolean canSilkHarvest(World world, BlockPos pos, @Nonnull IBlockState state, EntityPlayer player) {
        return false;
    }

    @Override
    public boolean canGrow(@Nonnull World worldIn, @Nonnull BlockPos pos, @Nonnull IBlockState state,
            boolean isClient) {
        return !isMaxAge(state);
    }

    @Override
    public void grow(@Nonnull World worldIn, @Nonnull Random rand, @Nonnull BlockPos pos, @Nonnull IBlockState state) {
        this.grow(worldIn, pos, state);
    }

    @Override
    @Nonnull
    public IBlockState getStateFromMeta(int meta) {
        return withAge(meta);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return getAge(state);
    }

    @Override
    @Nonnull
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, AGE);
    }

    @Override
    public boolean canUseBonemeal(@Nonnull World worldIn, @Nonnull Random rand, @Nonnull BlockPos pos,
            @Nonnull IBlockState state) {
        return false;
    }

    @SuppressWarnings("deprecation")
    @Override
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos blockPos) {
        super.neighborChanged(state, worldIn, pos, blockIn, blockPos);
        checkAndDropBlock(worldIn, pos, state);
    }

    protected void checkAndDropBlock(World worldIn, BlockPos pos, IBlockState state) {
        if(!canBlockStay(worldIn, pos, state)) {
            dropBlockAsItem(worldIn, pos, state, 0);
            worldIn.setBlockState(pos, Blocks.AIR.getDefaultState(), 3);
        }
    }
}
