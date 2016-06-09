package xyz.brassgoggledcoders.modularutilities.modules.tools;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import xyz.brassgoggledcoders.boilerplate.items.ItemBase;

public class ItemMachete extends ItemBase {

	public ItemMachete() {
		super("machete");
		this.setFull3D();
		this.setMaxStackSize(1);
        this.setMaxDamage(238);
	}
	public float getStrVsBlock(ItemStack stack, IBlockState state)
    {
        Block block = state.getBlock();
        Material m = state.getMaterial();
        //Tool can fast-break web and plant type blocks
        return (block == Blocks.WEB || (m == Material.LEAVES || m == Material.GOURD || m == Material.CACTUS || m == Material.PLANTS)) ? 15.0F : 5.0F;
    }
	
	/**
     * Called when a Block is destroyed using this Item. Return true to trigger the "Use Item" statistic.
     */
    public boolean onBlockDestroyed(ItemStack stack, World worldIn, IBlockState state, BlockPos pos, EntityLivingBase entityLiving)
    {
        stack.damageItem(1, entityLiving);
        return (getStrVsBlock(stack, state) == 15) ? true : false; 
    }

}
