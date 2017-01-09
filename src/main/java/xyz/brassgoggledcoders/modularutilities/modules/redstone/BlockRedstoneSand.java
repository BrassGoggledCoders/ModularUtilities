package xyz.brassgoggledcoders.modularutilities.modules.redstone;

import com.teamacronymcoders.base.blocks.IHasItemBlock;
import com.teamacronymcoders.base.client.models.IHasModel;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

import java.util.List;

public class BlockRedstoneSand extends BlockFalling implements IHasItemBlock, IHasModel {
    public BlockRedstoneSand() {
        super();
        this.setUnlocalizedName("redstone_sand");
    }

    @Override
    public boolean canProvidePower(IBlockState state) {
        return true;
    }

    @Override
    public int getWeakPower(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
        return 15;
    }

    @Override
    public ItemBlock getItemBlock() {
        return new ItemBlock(this);
    }

    @Override
    public List<String> getModelNames(List<String> modelNames) {
        String name = this.getUnlocalizedName();
        if (name.startsWith("tile."))
            name = name.substring(5);
        modelNames.add(name);
        return modelNames;
    }

    @Override
    public List<ItemStack> getAllSubItems(List<ItemStack> itemStacks) {
        itemStacks.add(new ItemStack(this));
        return itemStacks;
    }
}
