// package xyz.brassgoggledcoders.modularutilities.modules.achievements;
//
// import java.util.List;
//
// import com.teamacronymcoders.base.modulesystem.Module;
// import com.teamacronymcoders.base.modulesystem.ModuleBase;
// import com.teamacronymcoders.base.registrysystem.config.ConfigEntry;
// import com.teamacronymcoders.base.util.ItemStackUtils;
//
// import net.minecraft.enchantment.Enchantment;
// import net.minecraft.entity.monster.EntityElderGuardian;
// import net.minecraft.entity.monster.EntityIronGolem;
// import net.minecraft.entity.player.EntityPlayer;
// import net.minecraft.entity.player.EntityPlayerMP;
// import net.minecraft.init.Blocks;
// import net.minecraft.init.Items;
// import net.minecraft.init.SoundEvents;
// import net.minecraft.item.ItemFishFood;
// import net.minecraft.item.ItemStack;
// import net.minecraft.stats.Achievement;
// import net.minecraft.stats.AchievementList;
// import net.minecraft.stats.StatisticsManagerServer;
// import net.minecraft.util.SoundCategory;
// import net.minecraft.util.math.AxisAlignedBB;
// import net.minecraft.util.math.BlockPos;
// import net.minecraftforge.common.MinecraftForge;
// import net.minecraftforge.common.config.Property;
// import net.minecraftforge.event.entity.EntityJoinWorldEvent;
// import net.minecraftforge.event.entity.living.LivingDeathEvent;
// import net.minecraftforge.event.entity.player.AchievementEvent;
// import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
// import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
// import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;
// import xyz.brassgoggledcoders.modularutilities.ModularUtilities;
//
// @Module(ModularUtilities.MODID)
// public class ModuleAchievements extends ModuleBase {
//
// boolean giveEXPForAchievements;
//
// public static Achievement banker, stockbroker, hired_help, unstoppable, demigod, audiophile, doctor, undersea;
// // public static AchievementPage page = new AchievementPage("Modular Utilities", hired_help);
//
// @Override
// public String getName() {
// return "Achievements";
// }
//
// @Override
// public void preInit(FMLPreInitializationEvent event) {
// this.getConfigRegistry().addEntry(new ConfigEntry("options", "achievementXP", Property.Type.BOOLEAN, "true"));
// giveEXPForAchievements = this.getConfigRegistry().getBoolean("achievementXP", true);
//
// // TODO How to trigger?
// // banker = addAchievement("banker", "banker", -5, 5, new ItemStack(Blocks.DIAMOND_BLOCK),
// // AchievementList.DIAMONDS);
// // stockbroker = addAchievement("stockbroker", "stockbroker", -7, 5, new ItemStack(Items.EMERALD), banker);
//
// // Horizontal, Vertical
// hired_help = addAchievement("hired_help", -4, -4, new ItemStack(Blocks.PUMPKIN), AchievementList.ACQUIRE_IRON);
// unstoppable =
// addAchievement("unstoppable", -6, 5, new ItemStack(Items.DIAMOND_CHESTPLATE), AchievementList.DIAMONDS);
// ItemStack stack = new ItemStack(Items.DIAMOND_SWORD);
// stack.addEnchantment(Enchantment.getEnchantmentByLocation("mending"), 1);
// demigod = addAchievement("demigod", -6, 4, stack, AchievementList.ENCHANTMENTS).setSpecial();
// undersea = addAchievement("undersea", 10, -1,
// new ItemStack(Items.FISH, 1, ItemFishFood.FishType.PUFFERFISH.getMetadata()),
// AchievementList.KILL_ENEMY);
// // AchievementPage.registerAchievementPage(page);
// MinecraftForge.EVENT_BUS.register(this);
// }
//
// @SubscribeEvent
// public void onAchievementUnlocked(AchievementEvent event) {
// if(event.getEntityPlayer() instanceof EntityPlayerMP) {
// EntityPlayerMP entityPlayerMP = (EntityPlayerMP) event.getEntityPlayer();
// StatisticsManagerServer file = entityPlayerMP.getStatFile();
// Achievement achievement = event.getAchievement();
// if(file.canUnlockAchievement(achievement) && !file.hasAchievementUnlocked(achievement)) {
// if(giveEXPForAchievements) {
// if(achievement.getSpecial()) {
// entityPlayerMP.addExperienceLevel(1 + entityPlayerMP.getRNG().nextInt(3));
// }
// else {
// entityPlayerMP.addExperience(entityPlayerMP.getRNG().nextInt(6));
// }
//
// entityPlayerMP.getEntityWorld().playSound(null, entityPlayerMP.getPosition(),
// SoundEvents.ENTITY_ARROW_HIT_PLAYER, SoundCategory.PLAYERS, 10F, 1F);
// }
// }
// }
// }
//
// private static Achievement addAchievement(String unlocalizedName, int column, int row, ItemStack iconStack,
// Achievement parent) {
// Achievement achievement = new Achievement(unlocalizedName, unlocalizedName, column, row, iconStack, parent);
// achievement.registerStat();
// AchievementList.ACHIEVEMENTS.add(achievement);
// // TODO
// // page.getAchievements().add(achievement);
// return achievement;
// }
//
// @SubscribeEvent
// public void onJoined(EntityJoinWorldEvent event) {
// if(!event.getWorld().isRemote) {
// if(event.getEntity() instanceof EntityIronGolem) {
// EntityIronGolem golem = (EntityIronGolem) event.getEntity();
// if(golem.isPlayerCreated()) {
// AxisAlignedBB axisalignedbb =
// new AxisAlignedBB(new BlockPos(golem.posX, golem.posY, golem.posZ)).grow(5);
// List<EntityPlayer> list = event.getWorld().getEntitiesWithinAABB(EntityPlayer.class, axisalignedbb);
//
// for(EntityPlayer entityplayer : list) {
// entityplayer.addStat(hired_help);
// }
// }
// }
// }
// }
//
// @SubscribeEvent
// public void onPlayerTick(PlayerTickEvent event) {
// if(event.player == null || !ItemStackUtils.isValid(event.player.inventory.armorInventory.get(0)))
// return;
//
// if(ItemStackUtils.doItemsMatch(event.player.inventory.armorInventory.get(0), Items.DIAMOND_BOOTS)
// && ItemStackUtils.doItemsMatch(event.player.inventory.armorInventory.get(1), Items.DIAMOND_LEGGINGS)
// && ItemStackUtils.doItemsMatch(event.player.inventory.armorInventory.get(2), Items.DIAMOND_CHESTPLATE)
// && ItemStackUtils.doItemsMatch(event.player.inventory.armorInventory.get(3), Items.DIAMOND_HELMET)) {
// event.player.addStat(unstoppable);
// // N.B. We know the armour slots have diamond armour in, no need to nullcheck them.
// if(event.player.inventory.armorInventory.get(0).isItemEnchanted()
// && event.player.inventory.armorInventory.get(1).isItemEnchanted()
// && event.player.inventory.armorInventory.get(2).isItemEnchanted()
// && event.player.inventory.armorInventory.get(3).isItemEnchanted()
// && ItemStackUtils.doItemsMatch(event.player.getHeldItemMainhand(), Items.DIAMOND_SWORD)
// && event.player.getHeldItemMainhand().isItemEnchanted())
// event.player.addStat(demigod);
// }
// }
//
// @SubscribeEvent
// public void onLivingDeath(LivingDeathEvent event) {
// if(event.getEntityLiving() instanceof EntityElderGuardian) {
// if(event.getSource().getTrueSource() instanceof EntityPlayer) {
// EntityPlayer player = (EntityPlayer) event.getSource().getTrueSource();
// player.addStat(undersea);
// }
// }
// }
// }
