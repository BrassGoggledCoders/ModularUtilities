package xyz.brassgoggledcoders.modularutilities.modules.ender;

import com.teamacronymcoders.base.items.ItemBase;
import com.teamacronymcoders.base.modulesystem.Module;
import com.teamacronymcoders.base.modulesystem.ModuleBase;
import com.teamacronymcoders.base.util.ItemStackUtils;
import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumParticleTypes;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.world.BlockEvent.HarvestDropsEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import xyz.brassgoggledcoders.modularutilities.ModularUtilities;

import java.util.ArrayList;
import java.util.Iterator;

@Module(ModularUtilities.MODID)
public class EnderModule extends ModuleBase {
    public static Item ender_glove, ender_pocket;

    public static Block ender_proxy;

    @Override
    public String getName() {
        return "Ender";
    }

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        // TODO EnderStorage compatibility.
        ender_glove = new ItemBase("ender_glove").setMaxStackSize(1);
        getItemRegistry().register(ender_glove);
        ender_pocket = new ItemEnderPocket();
        getItemRegistry().register(ender_pocket);
        // TODO Ender Totem (experience) and Ender Dispenser/Dropper. Also inverse ender glove...

        ender_proxy = new BlockEnderChestProxy();
        getBlockRegistry().register(ender_proxy);

        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onLivingDrops(LivingDropsEvent event) {
        if (event.getSource().getDamageType().equalsIgnoreCase("player")) {
            if (event.getSource().getTrueSource() instanceof EntityPlayer) {
                EntityPlayer player = (EntityPlayer) event.getSource().getTrueSource();
                if (ItemStackUtils.isPlayerCarrying(ender_glove, player)) {
                    Iterator<EntityItem> drops = event.getDrops().iterator();
                    ArrayList<EntityItem> toRemove = new ArrayList<>();
                    while (drops.hasNext()) {
                        EntityItem current = drops.next();
                        if (player.getInventoryEnderChest().addItem(current.getItem()).isEmpty()) {
                            toRemove.add(current);
                        }
                    }
                    event.getDrops().removeAll(toRemove);
                }
            }
        }
    }

    @SubscribeEvent
    public void onBlockDrops(HarvestDropsEvent event) {
        if (event.getHarvester() != null) {
            EntityPlayer player = event.getHarvester();
            if (ItemStackUtils.doItemsMatch(player.getHeldItemOffhand(), ender_glove)) {
                Iterator<ItemStack> drops = event.getDrops().iterator();
                ArrayList<ItemStack> toRemove = new ArrayList<ItemStack>();
                while (drops.hasNext()) {
                    ItemStack current = drops.next();
                    if (player.getInventoryEnderChest().addItem(current).isEmpty()) {
                        toRemove.add(current);
                        ModularUtilities.proxy.spawnFX(EnumParticleTypes.PORTAL, event.getPos());
                    }
                }
                event.getDrops().removeAll(toRemove);
            }
        }
    }
}
