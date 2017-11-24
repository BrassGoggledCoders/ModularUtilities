package xyz.brassgoggledcoders.modularutilities.modules.baubles;

import java.util.List;

import com.teamacronymcoders.base.items.IHasRecipe;
import com.teamacronymcoders.base.items.ItemBaubleBase;

import baubles.api.BaubleType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IProjectile;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Optional;
import net.minecraftforge.oredict.ShapedOreRecipe;

public class ItemDeflectionBelt extends ItemBaubleBase implements IHasRecipe {

	public ItemDeflectionBelt() {
		super("deflection_belt");
	}

	@Override
	@Optional.Method(modid = "Baubles")
	public BaubleType getBaubleType(ItemStack arg0) {
		return BaubleType.BELT;
	}

	@Override
	@Optional.Method(modid = "Baubles")
	public void onWornTick(ItemStack stack, EntityLivingBase living) {
		World world = living.getEntityWorld();
		List<Entity> entities = world.getEntitiesWithinAABBExcludingEntity(living,
				living.getEntityBoundingBox().expand(5.0D, 5.0D, 5.0D));
		for (Entity entity : entities) {
			if (entity instanceof IProjectile) {
				entity.motionX = -entity.motionX;
				entity.motionY = -entity.motionY;
				entity.motionZ = -entity.motionZ;
			}
		}
	}

	@Override
	public List<IRecipe> getRecipes(List<IRecipe> recipes) {
		recipes.add(new ShapedOreRecipe(new ItemStack(this), "LLL", "LDL", "LLL", 'L', Items.LEATHER, 'D',
				Items.DRAGON_BREATH));
		return recipes;
	}
}
