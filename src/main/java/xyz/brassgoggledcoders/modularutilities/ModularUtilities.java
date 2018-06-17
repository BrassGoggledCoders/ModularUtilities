package xyz.brassgoggledcoders.modularutilities;

import static xyz.brassgoggledcoders.modularutilities.ModularUtilities.*;

import java.io.File;

import javax.annotation.Nonnull;

import com.teamacronymcoders.base.BaseModFoundation;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.*;
import xyz.brassgoggledcoders.modularutilities.modules.construction.ConstructionModule;
import xyz.brassgoggledcoders.modularutilities.modules.ender.EnderModule;
import xyz.brassgoggledcoders.modularutilities.proxies.CommonProxy;

@Mod(modid = MODID, name = MODNAME, version = MODVERSION, dependencies = DEPENDS)
public class ModularUtilities extends BaseModFoundation<ModularUtilities> {
    public ModularUtilities() {
        super(MODID, MODNAME, MODVERSION, tab);
    }

    @Instance(ModularUtilities.MODID)
    public static ModularUtilities instance;

    @SidedProxy(clientSide = "xyz.brassgoggledcoders.modularutilities.proxies.ClientProxy",
            serverSide = "xyz.brassgoggledcoders.modularutilities.proxies.CommonProxy")
    public static CommonProxy proxy;

    public static final String MODID = "modularutilities";
    public static final String MODNAME = "Modular Utilities";
    public static final String MODVERSION = "@VERSION@";
    public static final String DEPENDS = "required-after:base@[0.0.0,];";

    public static CreativeTabs tab = new MUTab();

    public static File config;

    static {
        FluidRegistry.enableUniversalBucket();
    }

    @Override
    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        super.preInit(event);
        config = event.getSuggestedConfigurationFile();
    }

    @Override
    @EventHandler
    public void init(FMLInitializationEvent event) {
        super.init(event);
    }

    @Override
    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        super.postInit(event);
    }

    @Override
    public ModularUtilities getInstance() {
        return instance;
    }

    public static class MUTab extends CreativeTabs {
        public MUTab() {
            super(MODID);
        }

        @Override
        @Nonnull
        public ItemStack getTabIconItem() {
            if (ModularUtilities.instance.getModuleHandler().isModuleEnabled("Ender"))
                return new ItemStack(EnderModule.ender_glove);
            else if (ModularUtilities.instance.getModuleHandler().isModuleEnabled("Construction"))
                return new ItemStack(ConstructionModule.blast_glass);
            else
                return new ItemStack(Blocks.SPONGE);
        }
    }
}
