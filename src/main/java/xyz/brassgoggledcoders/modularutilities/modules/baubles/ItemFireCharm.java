package xyz.brassgoggledcoders.modularutilities.modules.baubles;

import baubles.api.BaubleType;
import com.teamacronymcoders.base.items.IHasRecipe;
import com.teamacronymcoders.base.items.ItemBaubleBase;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.fml.common.Optional;
import net.minecraftforge.oredict.ShapedOreRecipe;

import java.util.List;

public class ItemFireCharm extends ItemBaubleBase implements IHasRecipe {

	public ItemFireCharm() {
		super("fire_charm");
	}

	@Override
	@Optional.Method(modid = "Baubles")
	public BaubleType getBaubleType(ItemStack bauble) {
		return BaubleType.AMULET;
	}

	@Override
	@Optional.Method(modid = "Baubles")
	public void onWornTick(ItemStack stack, EntityLivingBase living) {
		if(living instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) living;
			if(player.isBurning() && !(player.isInLava()) && !(player.isImmuneToFire())) {
				player.extinguish();
				// if(stack.attemptDamageItem(1, player.getRNG())) {
				// PlayerHandler.getPlayerBaubles(player).setInventorySlotContents(0, null);
				// player.setFire(5);
				// player.getEntityWorld().playSound(player, player.getPosition(),
				// SoundEvent.REGISTRY.getObject(new ResourceLocation("block.furnace.fire_crackle")),
				// SoundCategory.AMBIENT, 1F, 1F);
				// }
			}
		}
	}

	@Override
	public List<IRecipe> getRecipes(List<IRecipe> recipes) {
		recipes.add(new ShapedOreRecipe(new ItemStack(this), "SSS", "MDM", "OOO", 'S', Items.STRING, 'M', Blocks.MAGMA,
				'D', Items.DIAMOND, 'O', Blocks.OBSIDIAN));
		return recipes;
	}
}
