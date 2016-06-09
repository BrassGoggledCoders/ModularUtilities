package xyz.brassgoggledcoders.modularutilities.modules.tools;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import xyz.brassgoggledcoders.boilerplate.items.BaseSword;

public class ItemMachete extends BaseSword  {

	public ItemMachete() {
		super(ToolMaterial.IRON, "machete");
	}
	
	@Override
	 public float getStrVsBlock(ItemStack stack, IBlockState state)
    {
        return getStrVsBlock(state);
    }
	public float getStrVsBlock(IBlockState state)
    {
        Block block = state.getBlock();
        Material m = state.getMaterial();
        //Tool can fast-break web and plant type blocks
        return (block == Blocks.WEB || (m == Material.LEAVES || m == Material.GOURD || m == Material.CACTUS || m == Material.PLANTS)) ? 15.0F : 1.0F;
    }
	
	@Override
    public boolean onBlockDestroyed(ItemStack stack, World worldIn, IBlockState state, BlockPos pos, EntityLivingBase entityLiving)
    {
        stack.damageItem(1, entityLiving);
        return (getStrVsBlock(state) == 15) ? true : false; 
    }
    
    @Override
    public boolean canHarvestBlock(IBlockState blockIn)
    {
        return (getStrVsBlock(blockIn.getBlock().getDefaultState()) == 15) ? true : false;
    }
}
