package xyz.brassgoggledcoders.modularutilities.modules.equipment;

import com.teamacronymcoders.base.items.tools.ItemSwordBase;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.ParametersAreNonnullByDefault;

public class ItemMachete extends ItemSwordBase {

    public ItemMachete() {
        super(Item.ToolMaterial.IRON, "machete");
    }

    @Override
    public float getDestroySpeed(ItemStack stack, IBlockState state) {
        return getStrVsBlock(state);
    }

    public float getStrVsBlock(IBlockState state) {
        Block block = state.getBlock();
        Material m = state.getMaterial();
        // Tool can fast-break web and plant type blocks
        return block == Blocks.WEB || m == Material.LEAVES || m == Material.GOURD || m == Material.CACTUS
                || m == Material.PLANTS ? 15.0F : 1.0F;
    }

    @Override
    @ParametersAreNonnullByDefault
    public boolean onBlockDestroyed(ItemStack stack, World worldIn, IBlockState state, BlockPos pos,
                                    EntityLivingBase entityLiving) {
        stack.damageItem(1, entityLiving);
        return getStrVsBlock(state) == 15;
    }

    @Override
    public boolean canHarvestBlock(IBlockState blockIn) {
        return getStrVsBlock(blockIn.getBlock().getDefaultState()) == 15;
    }
}
