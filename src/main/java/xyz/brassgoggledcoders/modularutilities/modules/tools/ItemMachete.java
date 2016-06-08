package xyz.brassgoggledcoders.modularutilities.modules.tools;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import xyz.brassgoggledcoders.boilerplate.items.ItemBase;
import xyz.brassgoggledcoders.modularutilities.ModularUtilities;

public class ItemMachete extends ItemBase {

	public ItemMachete() {
		super("machete");
		this.setMaxStackSize(1);
        this.setMaxDamage(238);
	}
	public float getStrVsBlock(ItemStack stack, IBlockState state)
    {
        Block block = state.getBlock();
        Material m = state.getMaterial();
        //Tool can fast-break web and plant type blocks
        return (block != Blocks.WEB && (m != Material.LEAVES && m != Material.GOURD && m != Material.CACTUS && m != Material.PLANTS)) ? 5.0F : 15.0F;
    }

}
