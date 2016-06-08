package xyz.brassgoggledcoders.modularutilities.modules.miscellaneous;

import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import xyz.brassgoggledcoders.boilerplate.blocks.BlockBase;
import xyz.brassgoggledcoders.modularutilities.ModularUtilities;

public class BlockFeathers extends BlockBase {

	public BlockFeathers() {
		super(Material.CLOTH, "feathers");
		this.setResistance(0);
		this.setHardness(0.4F);
	}
	
	@Override
	public void onFallenUpon(World worldIn, BlockPos pos, Entity entityIn, float fallDistance)
    {
		//Reduce fall damage by 3/4ths
		entityIn.fall(fallDistance, 0.25F);
    }
}
