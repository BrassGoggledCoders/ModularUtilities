package xyz.brassgoggledcoders.modularutilities.modules.baubles;

import baubles.api.BaubleType;
import com.teamacronymcoders.base.items.IHasRecipe;
import com.teamacronymcoders.base.items.ItemBaubleBase;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.fml.common.Optional;
import net.minecraftforge.oredict.ShapedOreRecipe;

import java.util.List;

public class ItemBloodboundRing extends ItemBaubleBase implements IHasRecipe {

	public ItemBloodboundRing() {
		super("bloodbound_ring");
		this.setMaxStackSize(1);
	}

	@Override
	@Optional.Method(modid = "Baubles")
	public BaubleType getBaubleType(ItemStack arg0) {
		return BaubleType.RING;
	}

	@Override
	@Optional.Method(modid = "Baubles")
	public boolean canUnequip(ItemStack arg0, EntityLivingBase arg1) {
		return false;
	}

	@Override
	@Optional.Method(modid = "Baubles")
	public void onWornTick(ItemStack stack, EntityLivingBase living) {
		if(living.getHealth() == living.getMaxHealth()) {
			living.setHealth(living.getMaxHealth() * 0.75F);
			living.addPotionEffect(
					new PotionEffect(Potion.getPotionFromResourceLocation("strength"), 120, 2, false, false));
		}
	}

	@Override
	public List<IRecipe> getRecipes(List<IRecipe> recipes) {
		recipes.add(new ShapedOreRecipe(new ItemStack(this), "IBI", "I I", "III", 'I', "ingotIron", 'B', Items.BLAZE_POWDER));
		return recipes;
	}
}
