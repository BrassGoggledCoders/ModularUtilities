package xyz.brassgoggledcoders.modularutilities.modules.equipment;

import com.teamacronymcoders.base.modulesystem.proxies.ModuleProxyBase;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ClientProxy extends ModuleProxyBase {

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        super.preInit(event);
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public static void tooltipEvent(ItemTooltipEvent event) {
        if(ModuleEquipment.isStackSwiss(event.getItemStack())) {
            // TODO Lang
            event.getToolTip().add("\u00A7c" + "Shift-R Click to return to knife");
        }
    }
}
