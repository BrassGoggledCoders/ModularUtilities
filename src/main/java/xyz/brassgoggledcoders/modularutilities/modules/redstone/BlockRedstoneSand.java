package xyz.brassgoggledcoders.modularutilities.modules.redstone;

import com.teamacronymcoders.base.blocks.IHasItemBlock;
import com.teamacronymcoders.base.client.models.IHasModel;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

import java.util.List;

public class BlockRedstoneSand extends BlockFalling implements IHasItemBlock, IHasModel {
    private ItemBlock itemBlock;

    public BlockRedstoneSand() {
        super();
        this.setUnlocalizedName("redstone_sand");
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean canProvidePower(IBlockState state) {
        return true;
    }

    @SuppressWarnings("deprecation")
    @Override
    public int getWeakPower(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
        return 15;
    }

    @Override
    public ItemBlock getItemBlock() {
        if (this.itemBlock == null) {
            this.itemBlock = new ItemBlock(this);
        }
        return this.itemBlock;
    }

    @Override
    public List<String> getModelNames(List<String> modelNames) {
        String name = this.getUnlocalizedName();
        if (name.startsWith("tile.")) {
            name = name.substring(5);
        }
        modelNames.add(name);
        return modelNames;
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
}
