package xyz.brassgoggledcoders.modularutilities.modules.achievements;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Achievement;
import net.minecraft.stats.AchievementList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.AchievementEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import xyz.brassgoggledcoders.boilerplate.module.ModuleBase;

// @Module(mod = ModularUtilities.MODID)
public class ModuleAchievements extends ModuleBase {

	public static Achievement banker, stockbroker, hired_help, unstoppable, audiophile, doctor;

	@Override
	public String getName() {
		return "Achievements";
	}

	@Override
	public void preInit(FMLPreInitializationEvent event) {
		MinecraftForge.EVENT_BUS.register(this);
		banker = addAchievement("banker", "banker", -5, 5, new ItemStack(Blocks.DIAMOND_BLOCK),
				AchievementList.DIAMONDS);
		stockbroker = addAchievement("stockbroker", "stockbroker", -7, 5, new ItemStack(Items.EMERALD), banker);
		hired_help = addAchievement("hired_help", "hired_help", -4, -4, new ItemStack(Blocks.PUMPKIN),
				AchievementList.ACQUIRE_IRON);
		unstoppable = addAchievement("unstoppable", "unstoppable", -6, -6, new ItemStack(Items.DIAMOND_CHESTPLATE),
				AchievementList.DIAMONDS);
	}

	@SubscribeEvent
	public void onAchievementUnlocked(AchievementEvent event) {
		// TODO
		event.getEntityPlayer().addExperienceLevel(1);
		event.getEntityPlayer()
				.playSound(SoundEvent.REGISTRY.getObject(new ResourceLocation("entity.arrow.hit_player")), 1F, 1F);
	}

	private static Achievement addAchievement(String unlocalizedName, String identifier, int column, int row,
			ItemStack iconStack, Achievement parent) {
		Achievement achievement = new Achievement(unlocalizedName, identifier, column, row, iconStack, parent);
		achievement.registerStat();
		AchievementList.ACHIEVEMENTS.add(achievement);
		return achievement;
	}
}
