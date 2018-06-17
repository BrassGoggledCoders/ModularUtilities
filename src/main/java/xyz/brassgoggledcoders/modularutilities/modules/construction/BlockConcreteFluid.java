package xyz.brassgoggledcoders.modularutilities.modules.construction;

import java.util.Random;

import javax.annotation.Nonnull;

import com.teamacronymcoders.base.blocks.BlockFluidBase;
import com.teamacronymcoders.base.blocks.IHasTileEntity;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;

public class BlockConcreteFluid extends BlockFluidBase implements IHasTileEntity {

    public BlockConcreteFluid(Material mat, String name, Fluid fluid) {
        super(name, fluid, mat);
        this.setQuantaPerBlock(6);
        this.setDensity(this.definedFluid.getDensity());
    }

    @Override
    public Boolean isAABBInsideMaterial(World world, BlockPos pos, AxisAlignedBB boundingBox, Material materialIn) {
        if (materialIn == Material.WATER)
            return Boolean.TRUE;
        return null;
    }

    @Override
    public Boolean isEntityInsideMaterial(IBlockAccess world, BlockPos blockpos, IBlockState iblockstate, Entity entity,
                                          double yToTest, Material materialIn, boolean testingHead) {
        if (materialIn == Material.WATER)
            return Boolean.TRUE;
        return null;
    }

    // Method identical to that in BlockFluidClassic, except for removing 'total decay'. Better way?
    @Override
    public void updateTick(@Nonnull World world, @Nonnull BlockPos pos, @Nonnull IBlockState state, @Nonnull Random rand) {
        int quantaRemaining = quantaPerBlock - state.getValue(LEVEL);
        int expQuanta = -101;

        // check adjacent block levels if non-source
        if (quantaRemaining < quantaPerBlock) {
            if (world.getBlockState(pos.add(0, -densityDir, 0)).getBlock() == this
                    || world.getBlockState(pos.add(-1, -densityDir, 0)).getBlock() == this
                    || world.getBlockState(pos.add(1, -densityDir, 0)).getBlock() == this
                    || world.getBlockState(pos.add(0, -densityDir, -1)).getBlock() == this
                    || world.getBlockState(pos.add(0, -densityDir, 1)).getBlock() == this) {
                expQuanta = quantaPerBlock - 1;
            } else {
                int maxQuanta = -100;
                maxQuanta = getLargerQuanta(world, pos.add(-1, 0, 0), maxQuanta);
                maxQuanta = getLargerQuanta(world, pos.add(1, 0, 0), maxQuanta);
                maxQuanta = getLargerQuanta(world, pos.add(0, 0, -1), maxQuanta);
                maxQuanta = getLargerQuanta(world, pos.add(0, 0, 1), maxQuanta);

                expQuanta = maxQuanta - 1;
            }

            // decay calculation
            if (expQuanta != quantaRemaining) {
                quantaRemaining = expQuanta;

                if (expQuanta <= 0) {
                    // world.setBlockToAir(pos);
                } else {
                    world.setBlockState(pos, state.withProperty(LEVEL, quantaPerBlock - expQuanta), 2);
                    world.scheduleUpdate(pos, this, tickRate);
                    world.notifyNeighborsOfStateChange(pos, this, true);
                }
            }
        }
        // This is a "source" block, set meta to zero, and send a server only update
        else if (quantaRemaining >= quantaPerBlock) {
            world.setBlockState(pos, this.getDefaultState(), 2);
        }

        // Flow vertically if possible
        if (canDisplace(world, pos.up(densityDir))) {
            flowIntoBlock(world, pos.up(densityDir), 1);
            return;
        }

        // Flow outward if possible
        int flowMeta = quantaPerBlock - quantaRemaining + 1;
        if (flowMeta >= quantaPerBlock) {
            return;
        }

        if (isSourceBlock(world, pos) || !isFlowingVertically(world, pos)) {
            if (world.getBlockState(pos.down(densityDir)).getBlock() == this) {
                flowMeta = 1;
            }
            boolean flowTo[] = getOptimalFlowDirections(world, pos);

            if (flowTo[0])
                flowIntoBlock(world, pos.add(-1, 0, 0), flowMeta);
            if (flowTo[1])
                flowIntoBlock(world, pos.add(1, 0, 0), flowMeta);
            if (flowTo[2])
                flowIntoBlock(world, pos.add(0, 0, -1), flowMeta);
            if (flowTo[3])
                flowIntoBlock(world, pos.add(0, 0, 1), flowMeta);
        }
    }

    // TODO
    /*
	 * @Override
	 * public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
	 * EnumHand hand, @Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
	 * if(!worldIn.isRemote) {
	 * if(ItemStackUtils.isItemNonNull(heldItem)) {
	 * if(OreDictionary.containsMatch(false, OreDictionary.getOres("sand"), new ItemStack[] {heldItem})) {
	 * if(worldIn.getTileEntity(pos) instanceof TileEntityLiquidConcrete) {
	 * ((TileEntityLiquidConcrete) worldIn.getTileEntity(pos)).setDryingTicks(1);
	 * }
	 * }
	 * }
	 * return true;
	 * }
	 * return false;
	 * }
	 */

    @Override
    public boolean hasTileEntity(IBlockState blockState) {
        return true;
    }

    @Override
    public TileEntity createTileEntity(@Nonnull World world, @Nonnull IBlockState state) {
        return new TileEntityLiquidConcrete();
    }

    @Override
    public Class<? extends TileEntity> getTileEntityClass() {
        return TileEntityLiquidConcrete.class;
    }
}
