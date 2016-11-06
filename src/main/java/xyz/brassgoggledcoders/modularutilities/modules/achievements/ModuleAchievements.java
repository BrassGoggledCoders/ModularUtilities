package xyz.brassgoggledcoders.modularutilities.modules.achievements;

import com.teamacronymcoders.base.modulesystem.Module;
import com.teamacronymcoders.base.modulesystem.ModuleBase;
import com.teamacronymcoders.base.registry.config.ConfigEntry;
import com.teamacronymcoders.base.util.ItemStackUtils;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.monster.EntityGuardian;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemFishFood;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Achievement;
import net.minecraft.stats.AchievementList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.AchievementEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;
import xyz.brassgoggledcoders.modularutilities.ModularUtilities;

import java.util.List;

@Module(ModularUtilities.MODID)
public class ModuleAchievements extends ModuleBase {

	public static Achievement banker, stockbroker, hired_help, unstoppable, demigod, audiophile, doctor, undersea;
	// public static AchievementPage page = new AchievementPage("Modular Utilities", hired_help);

	@Override
	public String getName() {
		return "Achievements";
	}

	@Override
	public void preInit(FMLPreInitializationEvent event) {
		this.getConfigRegistry().addEntry(new ConfigEntry("options", "achievementXP", Property.Type.BOOLEAN, "true"));
		// TODO How to trigger?
		// banker = addAchievement("banker", "banker", -5, 5, new ItemStack(Blocks.DIAMOND_BLOCK),
		// AchievementList.DIAMONDS);
		// stockbroker = addAchievement("stockbroker", "stockbroker", -7, 5, new ItemStack(Items.EMERALD), banker);

		// Horizontal, Vertical
		hired_help = addAchievement("hired_help", -4, -4, new ItemStack(Blocks.PUMPKIN), AchievementList.ACQUIRE_IRON);
		unstoppable =
				addAchievement("unstoppable", -6, 5, new ItemStack(Items.DIAMOND_CHESTPLATE), AchievementList.DIAMONDS);
		ItemStack stack = new ItemStack(Items.DIAMOND_SWORD);
		stack.addEnchantment(Enchantment.getEnchantmentByLocation("mending"), 1);
		demigod = addAchievement("demigod", -6, 4, stack, AchievementList.ENCHANTMENTS).setSpecial();
		undersea = addAchievement("undersea", 10, -1,
				new ItemStack(Items.FISH, 1, ItemFishFood.FishType.PUFFERFISH.getMetadata()),
				AchievementList.KILL_ENEMY);
		// AchievementPage.registerAchievementPage(page);
		MinecraftForge.EVENT_BUS.register(this);
	}

	@SubscribeEvent
	public void onAchievementUnlocked(AchievementEvent event) {
		// Definitely needed. No idea why.
		if(event.getEntityPlayer().hasAchievement(event.getAchievement()))
			return;

		if(this.getConfigRegistry().getBoolean("achievementXP", true)) {
			if(event.getAchievement().getSpecial())
				event.getEntityPlayer().addExperienceLevel(1 + event.getEntityPlayer().getRNG().nextInt(3));
			else
				event.getEntityPlayer().addExperience(event.getEntityPlayer().getRNG().nextInt(6));

			event.getEntityPlayer().getEntityWorld().playSound(null, event.getEntityPlayer().getPosition(),
					SoundEvents.ENTITY_ARROW_HIT_PLAYER, SoundCategory.PLAYERS, 10F, 1F);
		}
	}

	private static Achievement addAchievement(String unlocalizedName, int column, int row, ItemStack iconStack,
			Achievement parent) {
		Achievement achievement = new Achievement(unlocalizedName, unlocalizedName, column, row, iconStack, parent);
		achievement.registerStat();
		AchievementList.ACHIEVEMENTS.add(achievement);
		// TODO
		// page.getAchievements().add(achievement);
		return achievement;
	}

	@SubscribeEvent
	public void onJoined(EntityJoinWorldEvent event) {
		if(!event.getWorld().isRemote) {
			if(event.getEntity() instanceof EntityIronGolem) {
				EntityIronGolem golem = (EntityIronGolem) event.getEntity();
				if(golem.isPlayerCreated()) {
					AxisAlignedBB axisalignedbb =
						new AxisAlignedBB(new BlockPos(golem.posX, golem.posY, golem.posZ)).expandXyz(5);
					List<EntityPlayer> list =
						event.getWorld().getEntitiesWithinAABB(EntityPlayer.class, axisalignedbb);

					for(EntityPlayer entityplayer : list) {
						entityplayer.addStat(hired_help);
					}
				}
			}
		}
	}

	@SubscribeEvent
	public void onPlayerTick(PlayerTickEvent event) {
		if(event.player == null || event.player.inventory.armorInventory[0] == null)
			return;

		if(ItemStackUtils.doItemsMatch(event.player.inventory.armorInventory[0], Items.DIAMOND_BOOTS)
				&& ItemStackUtils.doItemsMatch(event.player.inventory.armorInventory[1], Items.DIAMOND_LEGGINGS)
				&& ItemStackUtils.doItemsMatch(event.player.inventory.armorInventory[2], Items.DIAMOND_CHESTPLATE)
				&& ItemStackUtils.doItemsMatch(event.player.inventory.armorInventory[3], Items.DIAMOND_HELMET)) {
			event.player.addStat(unstoppable);
			// N.B. We know the armour slots have diamond armour in, no need to nullcheck them.
			if(event.player.inventory.armorInventory[0].isItemEnchanted()
					&& event.player.inventory.armorInventory[1].isItemEnchanted()
					&& event.player.inventory.armorInventory[2].isItemEnchanted()
					&& event.player.inventory.armorInventory[3].isItemEnchanted()
					&& ItemStackUtils.doItemsMatch(event.player.getHeldItemMainhand(), Items.DIAMOND_SWORD)
					&& event.player.getHeldItemMainhand().isItemEnchanted())
				event.player.addStat(demigod);
		}
	}

	@SubscribeEvent
	public void onLivingDeath(LivingDeathEvent event) {
		if(event.getEntityLiving() instanceof EntityGuardian && ((EntityGuardian) event.getEntityLiving()).isElder()) {
			if(event.getSource().getSourceOfDamage() instanceof EntityPlayer) {
				EntityPlayer player = (EntityPlayer) event.getSource().getSourceOfDamage();
				player.addStat(undersea);
			}
		}
	}
}
